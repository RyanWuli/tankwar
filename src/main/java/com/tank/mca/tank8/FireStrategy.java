package com.tank.mca.tank8;

import com.tank.mca.tank8.abstractfactory.BaseTank;

/**
 * @Author: Ryan
 * @Date: 2021/4/20 16:35
 * @Version: 1.0
 * @Description: 开火用策略模式：可能有多重开火方式
 *               tank、爆炸、子弹用工厂模式替换风格
 */
public interface FireStrategy {

    void fire(BaseTank tank);
}
