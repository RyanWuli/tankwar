package com.tank.mca.nettystudy7;

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
public class TankMsgEncoder extends MessageToByteEncoder<TankMsg> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankMsg tankMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(tankMsg.x);
        byteBuf.writeInt(tankMsg.y);
    }
}
