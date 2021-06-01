package com.tank.mca.nettystudy9_tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: Ryan
 * @Date: 2021/5/20 14:38
 * @Version: 1.0
 * @Description:
 */
public class Server {

    public static ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        ServerBootstrap sb = new ServerBootstrap();
        try {
            ChannelFuture future = sb.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline cp = socketChannel.pipeline();
                            cp
//                                    .addLast(new TankJoinMsgDecoder()) // 解码 , 服务器只需要转发不需要解码
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888)
                    .sync();
            ServerFrame.INSTANCE.updateText("server started!");
            System.out.println("server started!");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

/**
 * 处理  管道信息 ChannelInboundHandlerAdapter
 */
class ServerChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Server.cg.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        // 直接转发給所有的用户
        System.out.println("------ 收到信息了 msg ..... 开始 回写 .....");
        Server.cg.writeAndFlush(msg);

//        try {
//            TankJoinMsg tm = (TankJoinMsg) msg;
//            System.out.println(tm);
//        } finally {
//            ReferenceCountUtil.release(msg); // 释放 msg
//        }

//        ByteBuf bb = null;
//        bb = (ByteBuf) msg;
//        int i = bb.readableBytes();
//        byte[] bytes = new byte[i];
//        bb.getBytes(bb.readerIndex(), bytes);
//        String str = new String(bytes);
////        System.out.println(new String(bytes));
//        if ("_Bye_".equals(str)) {
//            System.out.println("客户端准备退出....." + ctx.channel().id());
//            ServerFrame.INSTANCE.updateText("客户端准备退出....." + ctx.channel().id());
//            Server.cg.remove(ctx.channel());
//            ctx.close(); // ctx close 了，里面的 channel 就 close 了
//            System.out.println("已关闭客户端连接....." + ctx.channel().id());
//            ServerFrame.INSTANCE.updateText("已关闭客户端连接....." + ctx.channel().id()); // server 消息写在左边
//        } else {
//            ServerFrame.INSTANCE.updateTextR(str); // 收到的消息写在右边
//            Server.cg.writeAndFlush(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 删除 group 中的异常 channel，并且关闭连接
        Server.cg.remove(ctx.channel());
        ctx.close(); // ctx close 了，里面的 channel 就 close 了
    }
}
