package com.tank.mca.nettystudy2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: Ryan
 * @Date: 2021/5/20 14:06
 * @Version: 1.0
 * @Description:
 *      channel 和 pipeline （channel 是通道，pipeline 是通道上的 handler 组成的责任链）
 */
public class ClientFrame extends Frame {

    TextArea ta = new TextArea();
    TextField tf = new TextField();

    Channel channel = null;

    public ClientFrame() {
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);

        tf.addActionListener(actionEvent -> {
            System.out.println(tf.getText());
            ByteBuf buf = Unpooled.copiedBuffer(tf.getText().getBytes());
            channel.writeAndFlush(buf);
            tf.setText("");
        });
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void client() {
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
                    .connect("localhost", 8888)
                    .sync();

            if (future.isSuccess()) {
                System.out.println("connect successfully!");
                this.channel = future.channel();
            }

            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.client();
    }

}



