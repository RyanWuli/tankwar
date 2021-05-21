package com.tank.mca.nettystudy3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/20 17:15
 * @Version: 1.0
 * @Description:
 */
public class Client {

    Channel channel;

    TextArea ta = null;

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture future = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 8888);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) { // 连接完毕的监听器
                    if (future.isSuccess()) {
                        System.out.println("connect successfully!");
                        channel = future.channel();
                    } else {
                        System.out.println("connect fail!");
                    }

                }
            });
            future.sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }


    class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf bb = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[bb.readableBytes()];
                bb.getBytes(bb.readerIndex(), bytes);

                ta.setText(ta.getText() + "\n" +  new String(bytes));
            } finally {
                if (bb != null) ReferenceCountUtil.release(bb);
            }
        }
    }
}


