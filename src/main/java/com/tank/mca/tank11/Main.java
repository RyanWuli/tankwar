package com.tank.mca.tank11;

import java.util.Random;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:04
 * @Version: 1.0
 * @Description:
 */
public class Main {

    public static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {

        TankFrame tf = new TankFrame();


        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }


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
