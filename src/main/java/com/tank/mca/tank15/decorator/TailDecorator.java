package com.tank.mca.tank15.decorator;

import com.tank.mca.tank15.GameObject;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 17:50
 * @Version: 1.0
 * @Description: 尾巴装饰器（用斜线代替）
 */
public class TailDecorator extends GODecorator {

    public TailDecorator(GameObject go) {
        super(go);

    }

    @Override
    public void paint(Graphics g) {
        // 需要设置到当前装饰器，直接取父类的多个装饰器同时使用可能为 0
        this.x = go.x;
        this.y = go.y;
        this.w = go.w;
        this.h = go.h;

        super.paint(g);

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        // 子弹的碰撞也没有了，原因是现在是画的装饰器了，没有定义装饰器的碰撞
        g.drawLine(x, y, x + 20, y + 20); // 加点余量防止被覆盖
        g.setColor(c);
    }
}
