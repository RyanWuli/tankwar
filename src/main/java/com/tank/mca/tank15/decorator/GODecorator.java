package com.tank.mca.tank15.decorator;

import com.tank.mca.tank15.GameObject;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 17:46
 * @Version: 1.0
 * @Description:
 */
public class GODecorator extends GameObject {

    protected GameObject go;

    public GODecorator(GameObject go) {
        this.go = go;
    }

    @Override
    public void paint(Graphics g) {
        go.paint(g);
    }
}
