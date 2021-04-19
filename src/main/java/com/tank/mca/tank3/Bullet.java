package com.tank.mca.tank3;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/14 11:23
 * @Version: 1.0
 * @Description: 子弹
 */
public class Bullet {

    // 子弹速度
    private static final int SPEED = 10;
    // 子弹的位置
    private int x, y;
    // 子弹的方向
    private Dir dir;
    // 子弹大小
    public static final int WIDTH = 20, HEIGHT = 20;
    // 子弹窗口引用
    private TankFrame tf;
    // 子弹的状态
    private boolean lived = true;

    public Bullet(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {

        if (!lived) tf.bullets.remove(this);

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

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) lived = false;
    }
}
