package com.tank.mca.nettystudy9_tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 14:52
 * @Version: 1.0
 * @Description: 编码器，转换成字节
 *              TankMsgEncoder 和 decoder 也是 channel handler 的一种
 */
public class TankJoinMsgEncoder extends MessageToByteEncoder<Msg> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {

        byteBuf.writeInt(msg.getMsgType().ordinal()); // 消息的类型，把 msgType 的下标写进去
        byte[] bytes = msg.toBytes();
        byteBuf.writeInt(bytes.length); // 消息的长度写进去
        byteBuf.writeBytes(msg.toBytes()); // 然后消息
    }
}
