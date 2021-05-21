package com.tank.mca.nettystudy.v05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;

/**
 * @Author: Ryan
 * @Date: 2021/5/17 17:31
 * @Version: 1.0
 * @Description: netty 版
 */
public class Server {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE); // 默认的单例线程 去处理 通道组的事件

    public static void main(String[] args) throws IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 处理连接 accept
        EventLoopGroup workerGroup = new NioEventLoopGroup(2); // 处理业务 链接上之后的

        try {
            ServerBootstrap sb = new ServerBootstrap();

            ChannelFuture cf = sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 链接上之后的回调，已经是 worker 来处理了
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
//                            System.out.println(socketChannel);
                            System.out.println(Thread.currentThread().getId());
                            ChannelPipeline pipeline = socketChannel.pipeline(); // 得到管道
                            pipeline.addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888) // 监听 8888 端口
                    .sync()
                    ;

            System.out.println("server started!");
            cf.channel().closeFuture().sync(); // 获取当前的 channel ，如果有调用 close(),则返回 future，否则一直阻塞，什么时候 调用 close(),什么时候执行然后继续后面的执行
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

/**
 * 如果管道有信息过来处理
 */
class ServerChildHandler extends ChannelInboundHandlerAdapter {

    /**
     * channel 连上就会调用此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel()); // 拿到对应的通道放到通道组里
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf bb = null;
        try {
            bb = (ByteBuf) msg; // 读到客户端的数据 buf
            int i = bb.readableBytes(); // 里面有多少有效数据
            byte[] bytes = new byte[i]; // 字节数组，按照 msg 长度新建
            bb.getBytes(bb.readerIndex(), bytes); // 读到 bytes 中去

            System.out.println(new String(bytes));

            // 所有通道都写数据，可以用作当群发的时候或者游戏联机
            Server.clients.writeAndFlush(msg); // 写回给客户端,writeAndFlush 会自动释放 byteBuf 引用，就不需要 ReferenceCountUtil.release(bb);释放
        } finally {
            if (bb != null && bb.refCnt() > 0) {
                ReferenceCountUtil.release(bb);
                System.out.println(bb.refCnt());
            }
        }

    }

    /**
     * 出错的时候调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
