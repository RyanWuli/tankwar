package com.tank.mca.nettystudy.v04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author: Ryan
 * @Date: 2021/5/17 17:24
 * @Version: 1.0
 * @Description: netty 里面所有方法都是异步方法
 */
public class Client {
    public static void main(String[] args) {

        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1); // 客户端 1 可以了，默认 核数 x 2

        Bootstrap b = new Bootstrap(); // 辅助启动类

        try {
            ChannelFuture future = b.group(group)
                    .channel(NioSocketChannel.class) // 非阻塞，（也可以换成阻塞的）
                    .handler(new ClientChannelInitializer()) // 连上之后的回调
                    .connect("localhost", 8888);// 异步，可以直接执行，连没连上就不需理会
//                    .sync() // connect 执行完成才往下执行，同步

            future.addListener((ChannelFutureListener)channelFuture -> {
//                Thread.sleep(1000);
                if (!channelFuture.isSuccess()) {
                    System.out.println("connect fail!");
                } else {
                    System.out.println("connect success!");
                }
            });

            future.sync(); // 同步阻塞，不然直接执行完成了，这里需要结果之后再往后执行，连不成功报异常

            System.out.println("end.....");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully(); // 结束（优雅的结束）
        }
    }
}

/**
 * 这里关注处理业务就行了
 */
class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        System.out.println(socketChannel);
        socketChannel.pipeline().addLast(new ClientHandler());
    }
}

/**
 * 具体的处理（写）
 */
class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channel 第一次连上 会调用
        ByteBuf bb = Unpooled.copiedBuffer("hello".getBytes()); // byteBuf 是 direct memory（直接内存，从操作系统管理的内存直接读取）
        ctx.writeAndFlush(bb);
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
//            System.out.println(bb);
//            System.out.println(bb.refCnt()); // 多少引用
        } finally {
            if (bb != null) {
                ReferenceCountUtil.release(bb);
                System.out.println(bb.refCnt());
            }
        }
    }
}
