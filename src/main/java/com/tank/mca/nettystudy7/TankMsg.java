package com.tank.mca.nettystudy7;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 14:50
 * @Version: 1.0
 * @Description:
 */
public class TankMsg {

    public int x, y;

    public TankMsg(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankMsg{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
