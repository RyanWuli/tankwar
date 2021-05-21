package com.tank.mca.tank17.cor;

import com.tank.mca.tank17.GameObject;
import com.tank.mca.tank17.Tank;
import com.tank.mca.tank17.Wall;

/**
 * @Author: Ryan
 * @Date: 2021/5/6 10:54
 * @Version: 1.0
 * @Description:
 */
public class TankWallCollision implements Collision {
    @Override
    public boolean collide(GameObject go, GameObject go2) {
        if (go instanceof Tank && go2 instanceof Wall) {
            Tank t = (Tank) go;
            Wall w = (Wall) go2;

            if (t.rectangle.intersects(w.rectangle)) {
                t.back();
            }

        } else if (go instanceof Wall && go2 instanceof Tank) {
            return collide(go2, go);
        }
        return true;
    }
}
