package com.tank.mca.tank12.cor;

import com.tank.mca.tank12.Bullet;
import com.tank.mca.tank12.GameObject;
import com.tank.mca.tank12.Tank;

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
            Bullet b = (Bullet) go;
            Tank t = (Tank) go2;
            return !b.collideWith(t);

        } else if (go instanceof Tank && go2 instanceof Bullet) {
            return collide(go2, go);
        }
        return true;
    }
}
