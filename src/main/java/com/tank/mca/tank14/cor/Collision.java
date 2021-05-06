package com.tank.mca.tank14.cor;

import com.tank.mca.tank14.GameObject;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 15:34
 * @Version: 1.0
 * @Description: 碰撞器（检测碰撞）
 */
public interface Collision {

    boolean collide(GameObject go, GameObject go2);
}
