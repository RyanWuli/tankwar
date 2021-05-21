package com.tank.mca.tank16;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 10:12
 * @Version: 1.0
 * @Description: 墙
 */
public class Wall extends GameObject {

    int w, h;

    public Rectangle rectangle;

    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.rectangle = new Rectangle(x, y, w ,h);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, w, h);
        g.setColor(c);
    }
}
