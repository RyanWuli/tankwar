package com.tank.mca.nettystudy10_tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 15:06
 * @Version: 1.0
 * @Description:
 */
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {

        if (byteBuf.readableBytes() < 8) return; // 判断长度是否小于8，因为类型和长度至少8位，如果没有8位肯定解析不出来数据的（小于 8 不处理，不小于 8 的时候才处理）
        byteBuf.markReaderIndex(); // byteBuf 读数据到哪里的标记
        MsgType mt = MsgType.values()[byteBuf.readInt()]; // 首先读出来的是类型的下标值，然后转换成为 类型
        int length = byteBuf.readInt(); // 第二个字节就是 长度

        if (byteBuf.readableBytes() < length) { // 收到的长度小于 信息的长度则不处理
            byteBuf.resetReaderIndex(); // 恢复 mark 标记的位置
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        switch (mt) {
            case TankJoin:
                TankJoinMsg tjm = new TankJoinMsg();
                tjm.parse(bytes);
                list.add(tjm);
                break;
            case TankStartMoving:
                TankStartMovingMsg tsmm = new TankStartMovingMsg();
                tsmm.parse(bytes);
                list.add(tsmm);
                break;
            default:
                break;
        }


//        if (byteBuf.readableBytes() < 33) return; // 解决了 TCP 的拆包和粘包问题，等字节数够了再处理（33 见 tankJoinMsg）
//
////        byteBuf.markReaderIndex();
//        TankJoinMsg tjm = new TankJoinMsg();
//        tjm.x = byteBuf.readInt();
//        tjm.y = byteBuf.readInt();
//        tjm.dir = Dir.values()[byteBuf.readInt()]; // 取出 enumerate 中的值（根据下标）
//        tjm.moving = byteBuf.readBoolean();
//        tjm.group = Group.values()[byteBuf.readInt()];
//        tjm.id = new UUID(byteBuf.readLong(), byteBuf.readLong()); // 取出高八位和第八位 long 解析成 uuid 128 位
//
//        list.add(tjm);
    }
}
