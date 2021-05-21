package com.tank.mca.tank15;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 15:02
 * @Version: 1.0
 * @Description: 物体父类
 */
public abstract class GameObject {

    public int x, y;

    public int w, h;

    public abstract void paint(Graphics g);

}
