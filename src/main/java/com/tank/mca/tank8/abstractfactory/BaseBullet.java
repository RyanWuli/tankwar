package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.Tank;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 10:32
 * @Version: 1.0
 * @Description:
 */
public abstract class BaseBullet {

    public abstract void paint(Graphics g);

    public abstract void collideWith(BaseTank tank);
}
