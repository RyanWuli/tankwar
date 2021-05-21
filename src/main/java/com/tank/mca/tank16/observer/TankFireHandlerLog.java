package com.tank.mca.tank16.observer;

import com.tank.mca.tank16.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/5/9 11:59
 * @Version: 1.0
 * @Description: 另一个观察者实现 打日志
 */
public class TankFireHandlerLog implements TankFireObserver {
    @Override
    public void actionOnFire(TankFireEvent tfe) {
        Tank t = tfe.getSource();
        System.out.println(t + "正在开火。。。。。");
    }
}
