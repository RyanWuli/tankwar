package com.tank.mca.tank11.cor;


import com.tank.mca.tank11.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 17:28
 * @Version: 1.0
 * @Description:
 */
public class CollisionChain implements Collision {

    private List<Collision> collisions = new LinkedList<>();

    public CollisionChain() {
        add(new TankTankCollision());
        add(new BulletTankCollision());
    }

    public void add(Collision c) {
        collisions.add(c);
    }

    // 责任链 true false 控制是否继续往下执行
    public void collide(GameObject go, GameObject go2) {
        for (int i = 0; i < collisions.size(); i++) {
            collisions.get(i).collide(go, go2);
        }
    }
}
