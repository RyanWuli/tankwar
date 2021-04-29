package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.*;

import java.awt.*;
import java.util.Random;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 10:26
 * @Version: 1.0
 * @Description:
 */
public abstract class BaseTank {

    // 随机数
    public Random random = new Random();

    // tank 窗口引用
    public TankFrame tf;


    public abstract void setSPEED(int goodTankSpeed);

    public abstract boolean isLived();

    public abstract void paint(Graphics g);

    public abstract Rectangle getRectangle();

    public abstract void setRectangle(Rectangle rectangle);

    public abstract void setRectangleX(int i);

    public abstract void setRectangleY(int i);

    public abstract void fire(FireStrategy strategy);

    public abstract void setMoving(boolean b);

    public abstract void setTankDir(boolean bL, boolean bU, boolean bR, boolean bD);

    public abstract Group getGroup();

    public abstract int getX();

    public abstract int getY();

    public abstract Dir getDir();

    public abstract void die();
}
