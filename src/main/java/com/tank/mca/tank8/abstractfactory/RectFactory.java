package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.Dir;
import com.tank.mca.tank8.Group;
import com.tank.mca.tank8.TankFrame;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 11:27
 * @Version: 1.0
 * @Description:
 */
public class RectFactory extends GameFactory {

    private static final RectFactory INSTANCE = new RectFactory();

    public static RectFactory getInstance() {return INSTANCE;}

    private RectFactory() {}

    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, boolean moving, TankFrame tf) {
        return new RectTank(x, y, dir, group, moving, tf);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tf) {
        return new RectExplode(x, y, tf);
    }
}
