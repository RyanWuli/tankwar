package com.tank.mca.tank7;

/**
 * @Author: Ryan
 * @Date: 2021/4/20 16:52
 * @Version: 1.0
 * @Description: 默认开火策略，单例
 */
public class DefaultFireStrategy implements FireStrategy {

    public static final DefaultFireStrategy INSTANCE = new DefaultFireStrategy();

    private DefaultFireStrategy() {}

    public static DefaultFireStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public void fire(Tank tank) {
        new Bullet(tank.x + Tank.TANK_WIDTH / 2 - Bullet.WIDTH / 2, tank.y + Tank.TANK_HEIGHT / 2 - Bullet.HEIGHT / 2, tank.dir, tank.group, tank.tf);
        if (tank.group == Group.GOOD)
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
