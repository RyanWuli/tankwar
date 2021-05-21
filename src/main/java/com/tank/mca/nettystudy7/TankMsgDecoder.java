package com.tank.mca.nettystudy7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 15:06
 * @Version: 1.0
 * @Description:
 */
public class TankMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return; // 解决了 TCP 的拆包和粘包问题，等字节数够了再处理

//        byteBuf.markReaderIndex();
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        list.add(new TankMsg(x, y));
    }
}
