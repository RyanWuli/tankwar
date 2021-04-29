package com.tank.mca.tank7;

/**
 * @Author: Ryan
 * @Date: 2021/4/20 17:15
 * @Version: 1.0
 * @Description:
 */
public class FourDirFireStrategy implements FireStrategy {

    private static final FourDirFireStrategy FOUR_DIR_FIRE_STRATEGY = new FourDirFireStrategy();

    private FourDirFireStrategy() {
    }

    public static FourDirFireStrategy getInstance() {
        return FOUR_DIR_FIRE_STRATEGY;
    }

    @Override
    public void fire(Tank tank) {
        Dir[] dirs = Dir.values();

        for (Dir dir : dirs) {
            new Bullet(tank.x + Tank.TANK_WIDTH / 2 - Bullet.WIDTH / 2, tank.y + Tank.TANK_HEIGHT / 2 - Bullet.HEIGHT / 2, dir, tank.group, tank.tf);
        }

        if (tank.group == Group.GOOD)
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
