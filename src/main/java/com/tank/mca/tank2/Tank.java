package com.tank.mca.tank2;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/14 10:15
 * @Version: 1.0
 * @Description: 坦克类 封装
 */
public class Tank {

    // tank 坐标
    private int x, y;
    // tank 方向
    private Dir dir;
    // tank 速度
    private final int SPEED = 5;
    // tank 移动与禁止
    private boolean moving = false;
    // tank 窗口引用
    private TankFrame tf;

    public Tank(int x, int y, Dir dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.yellow);
        g.fillRect(x, y, 50, 50); // 绘制矩形，x、y为坐标，然后宽高
        g.setColor(color);
        move();
    }

    public void setTankDir(boolean bL, boolean bU, boolean bR, boolean bD) {
        if (bL) dir = Dir.LEFT;
        if (bU) dir = Dir.UP;
        if (bR) dir = Dir.RIGHT;
        if (bD) dir = Dir.DOWN;
    }

    public void move() {
        if (!moving) return;
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

    public void fire() {
        tf.bullet = new Bullet(this.x, this.y, this.dir);
    }
}
