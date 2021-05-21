package com.tank.mca.tank17;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/16 10:03
 * @Version: 1.0
 * @Description: 爆炸类
 */
public class Explode extends GameObject {

    public static int WIDTH = ResourceMgr.explodes[0] .getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x, y;
    private int step = 0;
    private boolean lived = true;
//    TankFrame tf = null;
    GameModel gm;
    public Explode(int x, int y, GameModel gm) {
        this.x = x;
        this.y = y;
        this.gm = gm;

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
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length) gm.remove(this);
    }
}
