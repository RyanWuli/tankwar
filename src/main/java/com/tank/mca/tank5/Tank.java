package com.tank.mca.tank5;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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
    private int SPEED = 1;
    // tank 移动与禁止
    private boolean moving = true;
    // tank 窗口引用
    private TankFrame tf;
    // tank 的大小
    public static final int TANK_WIDTH = ResourceMgr.tankL.getWidth(), TANK_HEIGHT = ResourceMgr.tankL.getHeight();
    // tank 的状态
    private boolean lived = true;
    // tank 阵营
    private Group group = Group.BAD;

    // 随机类
    private Random random = new Random();

    public Tank(int x, int y, Dir dir, Group group, boolean moving, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        this.moving = moving;
    }

    public int getSPEED() {
        return SPEED;
    }

    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLived() {
        return lived;
    }

    public void setLived(boolean lived) {
        this.lived = lived;
    }

    public void paint(Graphics g) {

        // 如果 tank 状态为不对则删除
        if (!this.lived) tf.tanks.remove(this);

        BufferedImage image = null;
        switch (dir) {
            case LEFT:
                image = ResourceMgr.tankL;
                break;
            case UP:
                image = ResourceMgr.tankU;
                break;
            case RIGHT:
                image = ResourceMgr.tankR;
                break;
            case DOWN:
                image = ResourceMgr.tankD;
                break;
        }
        g.drawImage(image, x, y, null); // 绘制图片

        if (this.moving) move();

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

        // 随机数 1-10 产生 9 的时候才
        if (this.group == Group.BAD && random.nextInt(10) > 8) {
            this.fire();
            changeDir();
        }

    }

    // 变换方向
    private void changeDir() {
        Dir[] values = Dir.values();
        int i = random.nextInt(4);
        this.dir = values[i];
    }

    // 开火
    public void fire() {
        tf.bullets.add(new Bullet(this.x + TANK_WIDTH / 2 - Bullet.WIDTH / 2, this.y + TANK_HEIGHT / 2 - Bullet.HEIGHT / 2, this.dir, this.group, tf));
    }

    // tank 被击中
    public void die() {
        this.lived = false;
    }
}
