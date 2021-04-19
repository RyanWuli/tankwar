package com.tank.mca.tank3;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:04
 * @Version: 1.0
 * @Description:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        TankFrame tf = new TankFrame();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }


    }
}
