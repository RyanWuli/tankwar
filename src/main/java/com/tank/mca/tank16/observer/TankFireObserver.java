package com.tank.mca.tank16.observer;

/**
 * @Author: Ryan
 * @Date: 2021/5/9 11:56
 * @Version: 1.0
 * @Description: 坦克 开火观察者接口
 */
public interface TankFireObserver {
    void actionOnFire(TankFireEvent tfe);
}
