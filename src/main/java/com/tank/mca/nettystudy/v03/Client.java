package com.tank.mca.nettystudy.v03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
            ChannelFuture future = b.group(group)
                    .channel(NioSocketChannel.class) // 非阻塞，（也可以换成阻塞的）
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);// 异步，可以直接执行，连没连上就不需理会
//                    .sync() // connect 执行完成才往下执行，同步

            future.addListener((ChannelFutureListener)channelFuture -> {
                Thread.sleep(1000);
                if (!channelFuture.isSuccess()) {
                    System.out.println("connect fail!");
                } else {
                    System.out.println("connect success!");
                }
            });

            future.sync(); // 同步阻塞，不然直接执行完成了，这里需要结果之后再往后执行，连不成功报异常

            System.out.println("end.....");
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
        System.out.println(socketChannel);
    }
}
