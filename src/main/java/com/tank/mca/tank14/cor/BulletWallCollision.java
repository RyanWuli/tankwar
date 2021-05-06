package com.tank.mca.tank14.cor;

import com.tank.mca.tank14.Bullet;
import com.tank.mca.tank14.GameObject;
import com.tank.mca.tank14.Wall;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 10:40
 * @Version: 1.0
 * @Description:
 */
public class BulletWallCollision implements Collision {

    @Override
    public boolean collide(GameObject go, GameObject go2) {
        if (go instanceof Bullet && go2 instanceof Wall) {
            Bullet b = (Bullet) go;
            Wall w = (Wall) go2;

            if (b.rectangle.intersects(w.rectangle)) {
                b.die();
            }

        } else if (go instanceof Wall && go2 instanceof Bullet) {
            return collide(go2, go);
        }
        return true;
    }
}
