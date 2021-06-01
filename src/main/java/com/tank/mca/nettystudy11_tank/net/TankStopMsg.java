package com.tank.mca.nettystudy11_tank.net;

import com.tank.mca.nettystudy11_tank.Dir;
import com.tank.mca.nettystudy11_tank.Tank;
import com.tank.mca.nettystudy11_tank.TankFrame;

import java.io.*;
import java.util.UUID;

/**
 * @Author: Ryan
 * @Date: 2021/5/29 17:09
 * @Version: 1.0
 * @Description:
 */
public class TankStopMsg extends Msg {

    private UUID id;
    private int x, y;

    public TankStopMsg() {
    }

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public TankStopMsg(Tank tank) {
        this.id = tank.id;
        this.x = tank.x;
        this.y = tank.y;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankStopMsg{");
        sb.append("id=").append(id);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    @Override
    byte[] toBytes() {
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
        // 如果是自己的 tank
        if (this.id.equals(TankFrame.INSTANCE.getMainTank().id)) return;
        Tank tank = TankFrame.INSTANCE.findGoodTank(this.id);

        if (tank != null) {
            tank.setMoving(false);
            tank.setX(x);
            tank.setY(y);
        }
    }

    @Override
    void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
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
        return MsgType.TankStop;
    }
}
