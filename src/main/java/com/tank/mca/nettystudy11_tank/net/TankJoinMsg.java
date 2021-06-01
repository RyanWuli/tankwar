package com.tank.mca.nettystudy11_tank.net;

import com.tank.mca.nettystudy11_tank.Dir;
import com.tank.mca.nettystudy11_tank.Group;
import com.tank.mca.nettystudy11_tank.Tank;
import com.tank.mca.nettystudy11_tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 14:50
 * @Version: 1.0
 * @Description:
 */
public class TankJoinMsg extends Msg {

    // 字节计算 4+4+4+1+4+16 = 33 toByte 方法
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Tank tank) {
        this.x = tank.x;
        this.y = tank.y;
        this.moving = tank.moving;
        this.dir = tank.dir;
        this.group = tank.group;
        this.id = tank.id;
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }


    public byte[] toBytes() {
        ByteArrayOutputStream baos = null; // 字节数组里面写 的 stream
        DataOutputStream dos = null; // 专门用来写数据的
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y); // 四个字节
            dos.writeInt(dir.ordinal()); // enumerate 中的下标
            dos.writeBoolean(moving); // 一个字节（0，1）只占一位，但是传输最小单位是 字节，不同的内容不会混到一个字节中
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits()); // 高64位（ uuid 是 128 位，所以拆分成 高 64 位 和低 64 位写 - 高）
            dos.writeLong(id.getLeastSignificantBits()); // 低64位

//            dos.writeUTF(""); // 可以写字符但是长度不确定，最好用确定字节长度的，拆包粘包好处理，不去定的话还需要定义消息头，包括消息的长度，取的时候才好处理
            // TCP 协议不用字符串，HTTP 是建立在 TCP 协议之上的

            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }

    @Override
    void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().id) || TankFrame.INSTANCE.findGoodTank(this.id) != null) return;
        TankFrame.INSTANCE.addGoodTank(new Tank(this));

        // send msg to other,告诉新来的自己的
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    @Override
    void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    MsgType getMsgType() {
        return MsgType.TankJoin;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankJoinMsg{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", dir=").append(dir);
        sb.append(", moving=").append(moving);
        sb.append(", group=").append(group);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
