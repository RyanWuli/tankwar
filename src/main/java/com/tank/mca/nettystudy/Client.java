package com.tank.mca.nettystudy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: Ryan
 * @Date: 2021/5/17 17:24
 * @Version: 1.0
 * @Description:
 */
public class Client {
    public static void main(String[] args) {

        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1); // 客户端 1 可以了，默认 核数 x 2

        Bootstrap b = new Bootstrap(); // 辅助启动类

        try {
            b.group(group)
                    .channel(NioSocketChannel.class) // 非阻塞，（也可以换成阻塞的）
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        }
                    })
                    .connect("localhost", 8888)
                    .sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully(); // 结束（优雅的结束）
        }
    }
}
