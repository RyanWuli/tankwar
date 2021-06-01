package com.tank.mca.nettystudy8_tank.net;

import com.tank.mca.nettystudy8_tank.Dir;
import com.tank.mca.nettystudy8_tank.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 15:06
 * @Version: 1.0
 * @Description:
 */
public class TankJoinMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 33) return; // 解决了 TCP 的拆包和粘包问题，等字节数够了再处理（33 见 tankJoinMsg）

//        byteBuf.markReaderIndex();
        TankJoinMsg tjm = new TankJoinMsg();
        tjm.x = byteBuf.readInt();
        tjm.y = byteBuf.readInt();
        tjm.dir = Dir.values()[byteBuf.readInt()]; // 取出 enumerate 中的值（根据下标）
        tjm.moving = byteBuf.readBoolean();
        tjm.group = Group.values()[byteBuf.readInt()];
        tjm.id = new UUID(byteBuf.readLong(), byteBuf.readLong()); // 取出高八位和第八位 long 解析成 uuid 128 位

        list.add(tjm);
    }
}
