package com.tank.mca.tank17;

import java.awt.*;
import java.io.Serializable;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 15:02
 * @Version: 1.0
 * @Description: 物体父类
 */
public abstract class GameObject implements Serializable {

    int x, y;

    public abstract void paint(Graphics g);

}
