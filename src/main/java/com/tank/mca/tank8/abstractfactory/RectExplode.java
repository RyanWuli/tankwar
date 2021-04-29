package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.Audio;
import com.tank.mca.tank8.ResourceMgr;
import com.tank.mca.tank8.TankFrame;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 11:32
 * @Version: 1.0
 * @Description:
 */
public class RectExplode extends BaseExplode {

    public static int WIDTH = ResourceMgr.explodes[0] .getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x, y;
    private int step = 0;
    private boolean lived = true;
    TankFrame tf = null;

    public RectExplode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;

        new Thread(() -> new Audio("audio/war1.wav").play()).start();
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

    public void paint(Graphics g) {
//        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
//        if (step >= ResourceMgr.explodes.length) tf.explodes.remove(this);
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, step * 10, step * 10);
        g.setColor(color);
        step++;
        if (step >= 5) tf.explodes.remove(this);
    }
}
