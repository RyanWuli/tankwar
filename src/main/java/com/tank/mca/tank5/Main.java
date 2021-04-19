package com.tank.mca.tank5;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:04
 * @Version: 1.0
 * @Description:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        TankFrame tf = new TankFrame();
        // 初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank(i * 150, 150, Dir.DOWN, Group.BAD, true, tf));
        }

        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }


    }
}
