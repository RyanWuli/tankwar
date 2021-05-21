package com.tank.mca.tank15.cor;

import com.tank.mca.tank15.*;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 15:41
 * @Version: 1.0
 * @Description:
 */
public class BulletTankCollision implements Collision {

    @Override
    public boolean collide(GameObject go, GameObject go2) {
        if (go instanceof Bullet && go2 instanceof Tank) {
            Bullet bullet = (Bullet) go;
            Tank tank = (Tank) go2;

            if (bullet.group == tank.getGroup()) return true; // 需要继续检测
            if (bullet.rectangle.intersects(tank.rectangle)) { // 判断两个方块是否相交
                tank.die();
                bullet.die();
                int eX = tank.getX() + ResourceMgr.goodTankU.getWidth() / 2 - ResourceMgr.explodes[0] .getWidth() / 2;
                int eY = tank.getY() + ResourceMgr.goodTankU.getHeight() / 2 - ResourceMgr.explodes[0].getHeight() / 2;
                GameModel gm = GameModel.getInstance();
                gm.add(new Explode(eX, eY));
                new Thread(() -> new Audio("audio/explode.wav").play()).start();
                return false; // 不需要继续检测
            }

            return true; // 需要继续检测

        } else if (go instanceof Tank && go2 instanceof Bullet) {
            return collide(go2, go);
        }
        return true; // 需要继续检测
    }
}
