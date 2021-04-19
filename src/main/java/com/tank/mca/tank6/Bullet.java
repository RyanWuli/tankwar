package com.tank.mca.tank6;

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
    public static final int WIDTH = ResourceMgr.bulletU.getWidth(), HEIGHT = ResourceMgr.bulletU.getHeight();
    // 子弹窗口引用
    private TankFrame tf;
    // 子弹的状态
    private boolean lived = true;
    // 子弹的阵营
    private Group group = Group.BAD;
    // 子弹 rect 方块
    public Rectangle rectangle = new Rectangle();

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    public void paint(Graphics g) {

        if (!lived) tf.bullets.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }

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

        rectangle.x = x;
        rectangle.y = y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) lived = false;
    }

    /**
     * 子弹和坦克是否碰撞
     * @param tank
     */
    public void collideWith(Tank tank) {

        if (this.group == tank.getGroup()) return;

//        Rectangle rectangle = new Rectangle(this.x, this.y, WIDTH, HEIGHT); // 获取子弹的块
//        Rectangle rectangle2 = new Rectangle(tank.getX(), tank.getY(), Tank.TANK_WIDTH, Tank.TANK_HEIGHT); // 获取 tank 的块
        if (rectangle.intersects(tank.rectangle)) { // 判断两个方块是否相交
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.TANK_WIDTH / 2 - Explode.WIDTH / 2;
            int eY = tank.getY() + Tank.TANK_HEIGHT / 2 - Explode.HEIGHT / 2;
            tf.explodes.add(new Explode(eX, eY , tf));
            new Thread(() -> new Audio("audio/explode.wav").play()).start();
        }
    }

    public void die() {
        this.lived = false;
    }
}
