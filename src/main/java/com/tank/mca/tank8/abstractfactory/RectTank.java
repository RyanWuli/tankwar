package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 16:28
 * @Version: 1.0
 * @Description:
 */
public class RectTank extends BaseTank {

    // tank 坐标
    public int x, y;
    // tank 方向
    public Dir dir;
    // tank 速度
    public int SPEED = Main.badTankSpeed;
    // tank 移动与禁止
    public boolean moving = true;

    // tank 的大小
    public static int TANK_WIDTH = ResourceMgr.goodTankU.getWidth(), TANK_HEIGHT = ResourceMgr.goodTankU.getHeight();
    // tank 的状态
    public boolean lived = true;
    // tank 阵营
    public Group group = Group.BAD;
    // tank 的方块 rect
    public Rectangle rectangle = new Rectangle();



    public RectTank(int x, int y, Dir dir, Group group, boolean moving, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        this.moving = moving;

        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = TANK_WIDTH;
        rectangle.height = TANK_HEIGHT;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void setRectangleX(int i) {
        this.rectangle.x = i;
    }

    @Override
    public void setRectangleY(int i) {
        this.rectangle.y = i;
    }

    public static int getTankWidth() {
        return TANK_WIDTH;
    }

    public static void setTankWidth(int tankWidth) {
        TANK_WIDTH = tankWidth;
    }

    public static int getTankHeight() {
        return TANK_HEIGHT;
    }

    public static void setTankHeight(int tankHeight) {
        TANK_HEIGHT = tankHeight;
    }

    public void paint(Graphics g) {

        // 如果 tank 状态为不对则删除
        if (!this.lived) {
            if (this.group == Group.BAD) {
                tf.tanks.remove(this);
            }
        }

//        BufferedImage image = null;
//        switch (dir) {
//            case LEFT:
//                image = this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL;
//                break;
//            case UP:
//                image = this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU;
//                break;
//            case RIGHT:
//                image = this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR;
//                break;
//            case DOWN:
//                image = this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD;
//                break;
//        }

//        g.drawImage(image, x, y, null); // 绘制图片

        Color color = g.getColor();
        g.setColor(this.group == Group.GOOD ? Color.GREEN : Color.BLUE);
        g.fillRect(x, y, 50, 50);
        g.setColor(color);

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

        // 随机数 1-10 产生 9 的时候才开火
        if (this.group == Group.BAD && random.nextInt(10) > 8) {
            this.fire(DefaultFireStrategy.getInstance()); // 可用 lambda 表达式把 DefaultFireStrategy 实现写在这里，但是我用了两次就独立出去了
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
    @Override
    public void fire(FireStrategy strategy) {
        strategy.fire(this);
//        tf.bullets.add(new Bullet(this.x + TANK_WIDTH / 2 - Bullet.WIDTH / 2, this.y + TANK_HEIGHT / 2 - Bullet.HEIGHT / 2, this.dir, this.group, tf));
//        if (this.group == Group.GOOD)
//        new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }

    // tank 被击中
    public void die() {
        this.lived = false;
    }
}
