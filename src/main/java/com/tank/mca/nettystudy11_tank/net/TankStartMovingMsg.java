package com.tank.mca.nettystudy11_tank.net;

import com.tank.mca.nettystudy11_tank.Dir;
import com.tank.mca.nettystudy11_tank.Tank;
import com.tank.mca.nettystudy11_tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Author: Ryan
 * @Date: 2021/5/25 15:40
 * @Version: 1.0
 * @Description:
 */
public class TankStartMovingMsg extends Msg {


    // 字节 4 + 4 + 4 + 16 = 28
    int x,y;
    Dir dir;
    UUID id;

    public TankStartMovingMsg() {
    }

    public TankStartMovingMsg(int x, int y, Dir dir, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
    }

    public TankStartMovingMsg(Tank tank) {
        this.x = tank.x;
        this.y = tank.y;
        this.dir = tank.dir;
        this.id = tank.id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankStartMovingMsg{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", dir=").append(dir);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        baos = new ByteArrayOutputStream();
        dos = new DataOutputStream(baos);
        byte[] bytes = null;
        try {
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return bytes;
    }

    @Override
    void handle() {
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().id)) return; // 判断消息是否是自己的，是的话不作处理的
        Tank t = TankFrame.INSTANCE.findGoodTank(this.id); // 找到对应的 tank

        // 设置 tank 的属性
        if (t != null) {
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }
    }

    @Override
    void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
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
        return MsgType.TankStartMoving;
    }
}
