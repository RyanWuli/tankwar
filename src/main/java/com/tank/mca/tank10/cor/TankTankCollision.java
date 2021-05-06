package com.tank.mca.tank10.cor;

import com.tank.mca.tank10.GameObject;
import com.tank.mca.tank10.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 16:50
 * @Version: 1.0
 * @Description:
 */
public class TankTankCollision implements Collision {

    @Override
    public void collide(GameObject go, GameObject go2) {
        if (go instanceof Tank && go2 instanceof Tank) {
            Tank t = (Tank) go;
            Tank t2 = (Tank) go2;
            if (t.rectangle.intersects(t2.rectangle)) {
                t.reverseDirection();
                t2.reverseDirection();
            }
        }
    }
}
