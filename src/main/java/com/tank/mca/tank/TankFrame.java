package com.tank.mca.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:39
 * @Version: 1.0
 * @Description:
 */
public class TankFrame extends Frame {

    int x = 200, y = 200;
    Dir dir = Dir.LEFT;
    private static final int SPEED = 10;

    public TankFrame() {
        setSize(800, 600); // 设置窗口大小宽度800，高度600，单位像素 px
        setResizable(false); // 能否改变窗口大小
        setTitle("Tank War"); // 设置窗口标题
        setVisible(true); // 显示窗口

        addKeyListener(new MyKeyListener());

        /*
        WindowAdapter 实现了 windowListener 的接口
         */
        addWindowListener(new WindowAdapter() { // 窗口监听
            @Override
            public void windowClosing(WindowEvent e) { // 窗口关闭监听，点击 × 的时候
                System.exit(0); // 退出系统
            }
        });
    }

    /*
    // 窗口需要重新绘制的时候调用，启动/窗口被挡住从新出来/窗口大小改变等
        Graphics：相当于画笔
     */
    @Override
    public void paint(Graphics g) {
        System.out.println("---------> paint");
        System.out.println("------------> x,y=" + x + "," + y);
        g.fillRect(x, y, 50, 50); // 绘制矩形，x、y为坐标，然后宽高
//        x += 10;
//        y += 10;

        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }
    }


    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) { // 键盘按下时触发
//            System.out.println("pressed:" + e.getKeyCode() + "-" + e.getKeyChar());

            int keyCode = e.getKeyCode();

                // 上下左右键移动
//            if (e.getKeyCode() == 38) y -= 50;
//            else if (e.getKeyCode() == 40) y += 50;
//            else if (e.getKeyCode() == 39) x += 50;
//            else if (e.getKeyCode() == 37) x -= 50;
//            repaint();

                // 上下左右键移动-这个好像是可以两个方向，但是不太灵敏
//            switch (keyCode) {
//                case KeyEvent.VK_LEFT:
//                    x -= 10;
//                    break;
//                case KeyEvent.VK_UP:
//                    y -= 10;
//                    break;
//                case KeyEvent.VK_RIGHT:
//                    x += 10;
//                    break;
//                case KeyEvent.VK_DOWN:
//                    y += 10;
//                    break;
//            }
//            repaint();

            // 上下左右移动，并且能够两个方向同时移动-灵敏
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) { // 键盘弹起时触发
//            System.out.println("released:" + e.getKeyCode() + "-" + e.getKeyChar());

            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            if (bL) dir = Dir.LEFT;
            if (bU) dir = Dir.UP;
            if (bR) dir = Dir.RIGHT;
            if (bD) dir = Dir.DOWN;
        }
    }
}
