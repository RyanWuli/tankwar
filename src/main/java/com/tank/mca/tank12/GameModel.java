package com.tank.mca.tank12;

import com.tank.mca.tank12.cor.CollisionChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 10:35
 * @Version: 1.0
 * @Description: 对于 tankFrame 来说是门面（facade），对于 各种物体模型来说是调停者（Mediator）
 */
public class GameModel {

    public static int initBadTankCount = ConfigMgr.getIntValue("initBadTankCount");
    public static int tankMinX = ConfigMgr.getIntValue("tankMinX");
    public static int tankMinY = ConfigMgr.getIntValue("tankMinY");
    public static int gameWidth = ConfigMgr.getIntValue("gameWidth");
    public static int gameHeight = ConfigMgr.getIntValue("gameHeight");
    public static int badTankMaxY = ConfigMgr.getIntValue("badTankMaxY");
    public static int tankMaxX = gameWidth - Tank.TANK_WIDTH - 2;
    public static int goodTankMinY = ConfigMgr.getIntValue("goodTankMinY");
    public static int goodTankMaxY = ConfigMgr.getIntValue("goodTankMaxY");
    public static int badTankSpeed = ConfigMgr.getIntValue("badTankSpeed");
    public static int goodTankSpeed = ConfigMgr.getIntValue("goodTankSpeed");

    int availableX = gameWidth - 40 - Tank.TANK_WIDTH; // 可用 x
    int intervalMaxX = availableX / initBadTankCount; // 最大间隙

    int rtx = Main.getRandomInt(tankMinX, tankMaxX);
    int rty = Main.getRandomInt(goodTankMinY, goodTankMaxY);

    Tank tank = new Tank(rtx, rty, Dir.LEFT, Group.GOOD, false, this);
    //    List<Tank> goodsTanks = new ArrayList<>(); // 主坦克
//    List<Bullet> bullets = new ArrayList<>(); // 可多颗子弹同时出现，子弹容器
//    List<Tank> tanks = new ArrayList<>(); // 坦克的 容器
//    List<Explode> explodes = new ArrayList<>();
//    Explode explode = new Explode(400, 400, this);

    // 物体全部装一个 list
    List<GameObject> objects = new ArrayList<>();

//    // 碰撞器（子弹坦克）
//    Collision c = new BulletTankCollision();
//
//    // 碰撞器（坦克 和 坦克）敌方
//    Collision c2 = new TankTankCollision();

    // 责任链碰撞器
    CollisionChain cc = new CollisionChain();

    public void add(GameObject go) {
        this.objects.add(go);
    }

    public void remove(GameObject go) {
        this.objects.remove(go);
    }

    public GameModel() {

        int rX = Main.r.nextInt(tankMaxX) % (tankMaxX - tankMinX + 1) + tankMinX;
        int increment = 0;
        // 初始化敌方坦克
        for (int i = 0; i < initBadTankCount; i++) {
            int x = rX + increment;
            increment += Main.r.nextInt(intervalMaxX) % (intervalMaxX - Tank.TANK_WIDTH + 1) + Tank.TANK_WIDTH;
            if (x > availableX) x -= availableX;
            int rY = Main.r.nextInt(badTankMaxY) % (badTankMaxY - tankMinY + 1) + tankMinY;
//            tf.tanks.add(new Tank(i * 150, 150, Dir.DOWN, Group.BAD, true, tf));
            add(new Tank(x, rY, Dir.DOWN, Group.BAD, true, this));
        }

    }

    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("子弹的数量：" + bullets.size(), 10, 50);
//        g.drawString("坦克的数量：" + tanks.size(), 10, 65);
//        g.drawString("爆炸的数量：" + explodes.size(), 10, 80);
        g.setColor(color);

        tank.setSPEED(goodTankSpeed); // 主坦克速度调快一点
//        System.out.println(tank.isLived());
        if (tank.isLived()) {
            tank.paint(g);
        } else { // 把主坦克的块放到界面之外，否则触碰检测一直在
            tank.rectangle.x = -100;
            tank.rectangle.y = -100;
        }

//        System.out.println("-------> begin");
//        tanks.forEach(System.out::println);
//        System.out.println("--------------> end");
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paint(g);
        }

        // 互相碰撞
        for (int i = 0; i < objects.size(); i++) {
            GameObject go = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                GameObject go2 = objects.get(j);
                cc.collide(go, go2); // 判断敌方 tank 是否击中
//                cc.collide(go, go2);
            }
            cc.collide(go, tank); // 判断我方 tank 是否击中
        }

        // 画出敌方坦克
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }

        // 判断是否击中坦克
//        for (int i = 0; i < bullets.size(); i++) {
//            // 是否击中我方坦克
//            bullets.get(i).collideWith(tank);
//            // 是否击中敌方坦克
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//        }

        // 画出爆炸图
//        for (int i = 0; i < explodes.size(); i++) {
//            explodes.get(i).paint(g);
//        }
    }

    /**
     * 获取主坦克
     * @return
     */
    public Tank geMainTank() {
        return tank;
    }
}
