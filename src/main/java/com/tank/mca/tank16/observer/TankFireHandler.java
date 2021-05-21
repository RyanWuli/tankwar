package com.tank.mca.tank16.observer;

import com.tank.mca.tank16.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/5/9 11:57
 * @Version: 1.0
 * @Description: 坦克开火观察者实现
 */
public class TankFireHandler implements TankFireObserver {
    @Override
    public void actionOnFire(TankFireEvent tfe) {
        Tank t = tfe.getSource();
        t.fire();
    }
}
