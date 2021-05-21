package com.tank.mca.tank16.observer;

import com.tank.mca.tank16.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/5/9 11:53
 * @Version: 1.0
 * @Description:
 */
public class TankFireEvent {

    Tank tank;

    public Tank getSource() {
        return tank;
    }

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }
}
