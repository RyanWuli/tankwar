package com.tank.mca.tank2;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/14 11:23
 * @Version: 1.0
 * @Description: 子弹
 */
public class Bullet {

    // 子弹速度
    private static final int SPEED = 1;
    // 子弹的位置
    private int x, y;
    // 子弹的方向
    private Dir dir;
    // 子弹大小
    private final int WIDTH = 20, HEIGHT = 20;

    public Bullet(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, WIDTH, HEIGHT); // 绘制圆形，x、y为坐标，然后宽高
        g.setColor(color);
        move();
    }

    public void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
    }
}
