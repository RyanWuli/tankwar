package com.tank.mca.tank11.cor;

import com.tank.mca.tank11.Bullet;
import com.tank.mca.tank11.GameObject;
import com.tank.mca.tank11.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 15:41
 * @Version: 1.0
 * @Description:
 */
public class BulletTankCollision implements Collision {

    @Override
    public void collide(GameObject go, GameObject go2) {
        if (go instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go;
            Tank t = (Tank) go2;
            b.collideWith(t);

        } else if (go instanceof Tank && go2 instanceof Bullet) {
            collide(go2, go);
        }
    }
}
