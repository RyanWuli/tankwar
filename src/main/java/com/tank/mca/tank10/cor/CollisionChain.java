package com.tank.mca.tank10.cor;


import com.tank.mca.tank10.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 17:28
 * @Version: 1.0
 * @Description:
 */
public class CollisionChain {

    private List<Collision> collisions = new LinkedList<>();

    public CollisionChain() {
        add(new BulletTankCollision());
        add(new TankTankCollision());
    }

    public void add(Collision c) {
        collisions.add(c);
    }

    public void collide(GameObject go, GameObject go2) {
        for (int i = 0; i < collisions.size(); i++) {
            collisions.get(i).collide(go, go2);
        }
    }
}
