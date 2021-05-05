package com.tank.mca.tank9;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/5/5 10:35
 * @Version: 1.0
 * @Description:
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

    int rtx = Main.getRandomInt(tankMinX, tankMaxX);
    int rty = Main.getRandomInt(goodTankMinY, goodTankMaxY);

    Tank tank = new Tank(rtx, rty, Dir.LEFT, Group.GOOD, false, this);
    //    List<Tank> goodsTanks = new ArrayList<>(); // 主坦克
    java.util.List<Bullet> bullets = new ArrayList<>(); // 可多颗子弹同时出现，子弹容器
    java.util.List<Tank> tanks = new ArrayList<>(); // 坦克的 容器
    List<Explode> explodes = new ArrayList<>();
//    Explode explode = new Explode(400, 400, this);


    public GameModel() {

        // 初始化敌方坦克
        for (int i = 0; i < initBadTankCount; i++) {
            int rX = Main.r.nextInt(tankMaxX) % (tankMaxX - tankMinX + 1) + tankMinX;
            int rY = Main.r.nextInt(badTankMaxY) % (badTankMaxY - tankMinY + 1) + tankMinY;
//            tf.tanks.add(new Tank(i * 150, 150, Dir.DOWN, Group.BAD, true, tf));
            tanks.add(new Tank(rX, rY, Dir.DOWN, Group.BAD, true, this));
        }

    }

    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 50);
        g.drawString("坦克的数量：" + tanks.size(), 10, 65);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 80);
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
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 画出敌方坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        // 判断是否击中坦克
        for (int i = 0; i < bullets.size(); i++) {
            // 是否击中我方坦克
            bullets.get(i).collideWith(tank);
            // 是否击中敌方坦克
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

        // 画出爆炸图
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
    }

    /**
     * 获取主坦克
     * @return
     */
    public Tank geMainTank() {
        return tank;
    }
}
