package com.tank.mca.tank7;

/**
 * @Author: Ryan
 * @Date: 2021/4/20 16:35
 * @Version: 1.0
 * @Description: 开火用策略模式：可能有多重开火方式
 */
public interface FireStrategy {

    void fire(Tank tank);
}
