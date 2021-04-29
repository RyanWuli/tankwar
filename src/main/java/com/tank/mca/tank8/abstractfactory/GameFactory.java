package com.tank.mca.tank8.abstractfactory;

import com.tank.mca.tank8.Dir;
import com.tank.mca.tank8.Group;
import com.tank.mca.tank8.TankFrame;
import com.tank.mca.tank8.Tank;

/**
 * @Author: Ryan
 * @Date: 2021/4/28 10:24
 * @Version: 1.0
 * @Description: 抽象工厂
 */
public abstract class GameFactory {

    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, boolean moving, TankFrame tf);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
}
