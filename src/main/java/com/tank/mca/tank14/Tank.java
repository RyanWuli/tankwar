package com.tank.mca.tank14;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Ryan
 * @Date: 2021/4/14 10:15
 * @Version: 1.0
 * @Description: 坦克类 封装
 */
public class Tank extends GameObject {

    // tank 坐标
    private int x, y;
    // tank 方向
    private Dir dir;
    // tank 速度
    private int SPEED = GameModel.badTankSpeed;
    // tank 移动与禁止
    private boolean moving = true;
//    // tank 窗口引用
//    private TankFrame tf;
    // tank 的大小
    public static int TANK_WIDTH = ResourceMgr.goodTankU.getWidth(), TANK_HEIGHT = ResourceMgr.goodTankU.getHeight();
    // tank 的状态
    private boolean lived = true;
    // tank 阵营
    private Group group = Group.BAD;
    // tank 的方块 rect
    public Rectangle rectangle = new Rectangle();
    // 上一个 x
    private int oldX;
    // 上一个 y
    private int oldY;

    // 随机类
    private Random random = new Random();

//    // 门面类 gameModel
//    public static final GameModel gm = GameModel.getInstance();

    public Tank(int x, int y, Dir dir, Group group, boolean moving) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.moving = moving;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = TANK_WIDTH;
        rectangle.height = TANK_HEIGHT;

        if (group == Group.BAD) GameModel.getInstance().add(this);
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
        if (!this.lived) {
            if (this.group == Group.BAD) {
                GameModel.getInstance().remove(this);
            }
        }

        BufferedImage image = null;
        switch (dir) {
            case LEFT:
                image = this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL;
                break;
            case UP:
                image = this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU;
                break;
            case RIGHT:
                image = this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR;
                break;
            case DOWN:
                image = this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD;
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
        this.oldX = x;
        this.oldY = y;
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

        // 随机数 1-10 产生 9 的时候才开火
        if (this.group == Group.BAD && random.nextInt(10) > 8) {
            this.fire();
            changeDir();
        }

        boundCheck();

        rectangle.x = x;
        rectangle.y = y;

        if (group == Group.GOOD) {
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
        }
    }

    /**
     * 检测位置是否在 界面之中的
     */
    private void boundCheck() {
        if (x < 2) x = 2;
        if (y < 28) y = 28;
        if (x > TankFrame.GAME_WIDTH - Tank.TANK_WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.TANK_WIDTH - 2;
        if (y > TankFrame.GAME_HEIGHT - Tank.TANK_HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.TANK_HEIGHT - 2;
    }

    // 变换方向
    private void changeDir() {
        Dir[] values = Dir.values();
        int i = random.nextInt(4);
        this.dir = values[i];
    }

    // 开火
    public void fire() {
        GameModel.getInstance().add(new Bullet(this.x + TANK_WIDTH / 2 - Bullet.WIDTH / 2, this.y + TANK_HEIGHT / 2 - Bullet.HEIGHT / 2, this.dir, this.group));
        if (this.group == Group.GOOD)
        new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }

    // tank 被击中
    public void die() {
        this.lived = false;
    }

    // 反向
    public void reverseDirection() {
        switch (dir) {
            case LEFT:
                dir = Dir.RIGHT;
                break;
            case UP:
                dir = Dir.DOWN;
                break;
            case RIGHT:
                dir = Dir.LEFT;
                break;
            case DOWN:
                dir = Dir.UP;
                break;
            default:
                break;
        }
    }

    /**
     * 返回上一个位置
     */
    public void back() {
        this.x = oldX;
        this.y = oldY;
    }
}
