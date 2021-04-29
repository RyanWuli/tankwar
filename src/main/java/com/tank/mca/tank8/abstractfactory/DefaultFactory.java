package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 10:39
 * @Version: 1.0
 * @Description: 默认实现工厂
 */
public class DefaultFactory extends GameFactory {

    private static final DefaultFactory INSTANCE = new DefaultFactory();

    private DefaultFactory() {}

    public static DefaultFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, boolean moving, TankFrame tf) {
        return new Tank(x, y, dir, group, moving, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new Bullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new Explode(x, y, tf);
    }
}
