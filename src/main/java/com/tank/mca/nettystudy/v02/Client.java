package com.tank.mca.nettystudy.v02;

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
 * @Description: netty 里面所有方法都是异步方法
 */
public class Client {
    public static void main(String[] args) {

        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1); // 客户端 1 可以了，默认 核数 x 2

        Bootstrap b = new Bootstrap(); // 辅助启动类

        try {
            b.group(group)
                    .channel(NioSocketChannel.class) // 非阻塞，（也可以换成阻塞的）
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888) // 异步，可以直接执行，连没连上就不需理会
                    .sync(); // connect 执行完成才往下执行，同步
        } catch (InterruptedException e) {
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
        System.out.println(socketChannel);
    }
}
