package com.tank.mca.tank;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:04
 * @Version: 1.0
 * @Description:
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Frame frame = new Frame(); // 代表的是窗口
//        frame.setSize(800, 600); // 设置窗口大小高度800，宽度600，单位像素 px
//        frame.setResizable(false); // 能否改变窗口大小
//        frame.setTitle("Tank War"); // 设置窗口标题
//        frame.setVisible(true); // 显示窗口
//
//        /*
//        WindowAdapter 实现了 windowListener 的接口
//         */
//        frame.addWindowListener(new WindowAdapter() { // 窗口监听
//            @Override
//            public void windowClosing(WindowEvent e) { // 窗口关闭监听，点击 × 的时候
//                System.exit(0); // 退出系统
//            }
//        });




        TankFrame tf = new TankFrame();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }

        // 循环移动
//        while (true) {
//            Thread.sleep(50);
//            tf.repaint();
//        }

    }
}
