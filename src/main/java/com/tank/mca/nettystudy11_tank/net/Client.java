package com.tank.mca.nettystudy11_tank.net;

import com.tank.mca.nettystudy11_tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @Author: Ryan
 * @Date: 2021/5/20 17:15
 * @Version: 1.0
 * @Description:
 */
public class Client {

    public static final Client INSTANCE = new Client();

    Channel channel;

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture future = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) {
                            channel.pipeline() // pipeline 可以加一系列的处理器
                                    .addLast(new MsgEncoder()) // 编码
                                    .addLast(new MsgDecoder()) // 解码
                                    .addLast(new ClientHandler()) // 处理 channelRead 服务器写回来的时候的业务逻辑
                            ;
                        }
                    })
                    .connect("localhost", 8888);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) { // 连接完毕的监听器,监听器中传入 future ，之后处理用这个！！！！！
                    if (channelFuture.isSuccess()) {
                        System.out.println("connect successfully!");
                        channel = channelFuture.channel();
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

//    public void send(String msg) {
//        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
//        channel.writeAndFlush(buf);
//    }
    public void send(Msg msg) {
//        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(msg);
    }

//    public void closeConnect() {
//        this.send("_Bye_"); // 发送 _Bye_ 证明要关闭
//        System.out.println("已退出");
//    }

}

/**
 * 管道处理器，处理管道事件 ChannelInboundHandlerAdapter
 * 简单管道处理器，可指定泛型 SimpleChannelInboundHandler<TankJoinMsg>
 */
class ClientHandler extends SimpleChannelInboundHandler<Msg> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank())); // 发送主坦克信息到服务器
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
//        // 如果服务器发回来的的 tank 信息是自己的，则不处理 或者 是已经加入列表了
//        if(tankJoinMsg.id.equals(TankFrame.INSTANCE.getMainTank().id) || TankFrame.INSTANCE.findGoodTank(tankJoinMsg.id) != null) return;
//        TankFrame.INSTANCE.addGoodTank(new Tank(tankJoinMsg));
////        System.out.println("收到服务器消息.....");
//        System.out.println(tankJoinMsg);
//
//        // 把自己写出去 - 因为新加的 tank 也需要你的信息
//        channelHandlerContext.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));

        System.out.println(msg);
        msg.handle();


    }



//    @Override
//    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank())); // 发送主坦克信息到服务器
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf bb = (ByteBuf) msg;
//        try {
//            byte[] bytes = new byte[bb.readableBytes()];
//            bb.getBytes(bb.readerIndex(), bytes);
//            ClientFrame.INSTANCE.updateText(new String(bytes));
//        } finally {
//            if (bb != null) ReferenceCountUtil.release(bb);
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close(); // 捕获异常
//    }
}

