package com.tank.mca.tank15.decorator;

import com.tank.mca.tank15.GameObject;

import java.awt.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 17:50
 * @Version: 1.0
 * @Description: 方块装饰器
 */
public class RectDecorator extends GODecorator {

    public RectDecorator(GameObject go) {
        super(go);
    }

    @Override
    public void paint(Graphics g) {
        // 需要设置到当前装饰器，直接取父类的多个装饰器同时使用可能为 0, 每层都要赋值；不然         GameModel.getInstance().add(new RectDecorator(new TailDecorator(
        //                new Bullet(this.x + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2, this.y + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.bulletU.getWidth() / 2, this.dir, this.group))));
        // 装饰器和用的时候只有 bullet 最里层的 go 有 x y w h 值，艺装饰器 go 为构造函数参数的 新的外层装饰器 new 出来的 x y w h 就会为空，所以需要每个装饰器都将父类的 x y w h 保存到自己的对象中
        this.x = go.x;
        this.y = go.y;
        this.h = go.h;
        this.w = go.w;

        super.paint(g);

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        // 画的位置是 物体的位置，不能直接拿，直接拿是默认 0，装饰方框会歪，因为 上面 paint 完了之后会 move ，只是用设计模式练习不用管
        // 子弹的碰撞也没有了，原因是现在是画的装饰器了，没有定义装饰器的碰撞
        g.drawRect(x, y, w + 2, h + 2); // 加点余量防止被覆盖
        g.setColor(c);
    }
}
