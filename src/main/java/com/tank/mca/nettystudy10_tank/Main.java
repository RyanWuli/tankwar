package com.tank.mca.nettystudy10_tank;

import com.tank.mca.nettystudy10_tank.net.Client;

import java.util.Random;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:04
 * @Version: 1.0
 * @Description:
 */
public class Main {

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



    public static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {

        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true); // 显示

        System.out.println(tf);
        System.out.println(tf.getWidth());
        System.out.println(tf.getHeight());


        /**
         * 先注释掉 敌方 tank
         */
        // 初始化敌方坦克
//        for (int i = 0; i < initBadTankCount; i++) {
//            int rX = r.nextInt(tankMaxX) % (tankMaxX - tankMinX + 1) + tankMinX;
//            int rY = r.nextInt(badTankMaxY) % (badTankMaxY - tankMinY + 1) + tankMinY;
////            tf.tanks.add(new Tank(i * 150, 150, Dir.DOWN, Group.BAD, true, tf));
//            tf.tanks.add(new Tank(rX, rY, Dir.DOWN, Group.BAD, true, tf));
//        }

        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        // 单独线程重画
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    tf.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        // 连接服务端
        Client client = Client.INSTANCE;
        client.connect();

    }

    /**
     * 获取范围随机整数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInt(int min, int max) {
        return r.nextInt(max) % (max - min + 1) + min;
    }
}
