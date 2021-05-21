package com.tank.mca.tank16.cor;


import com.tank.mca.tank16.GameObject;

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
        add(new BulletWallCollision());
        add(new TankWallCollision());
        add(new BulletTankCollision());
    }

    public void add(Collision c) {
        collisions.add(c);
    }

    // 责任链 true false 控制是否继续往下执行
    public boolean collide(GameObject go, GameObject go2) {
        for (int i = 0; i < collisions.size(); i++) {
            if (!collisions.get(i).collide(go, go2)) return false;
        }
        return true;
    }
}
