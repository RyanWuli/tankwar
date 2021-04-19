package com.tank.mca.tank3;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:39
 * @Version: 1.0
 * @Description:
 */
public class TankFrame extends Frame {

    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    Tank tank = new Tank(200, 200, Dir.DOWN, this);
    List<Bullet> bullets = new ArrayList<>(); // 可多颗子弹同时出现

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

        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 60);
        g.setColor(color);

        tank.paint(g);
        // 这里面直接删除 list 元素会报错 java.util.ConcurrentModificationException 是由于底层修改次数记录和期望修改次数不等二抛的异常
//        for (Bullet b : bullets) {
//            b.paint(g);
//        }

//        Iterator<Bullet> iterator = bullets.iterator();
//        while (iterator.hasNext()) {
//            iterator.next().paint(g);
//        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

    }

    Image image = null; // 定义一张图片

    /*
    解决闪烁的问题
    update 方法在 paint 之前会被调用
     */
    @Override
    public void update(Graphics g) {
        if (image == null) image = this.createImage(GAME_WIDTH, GAME_HEIGHT); // 初始化一张图片
        Graphics graphics = image.getGraphics(); // 图片拿到一只画笔
        Color color = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT); // 重新画一遍背景
        graphics.setColor(color);
        paint(graphics);
        g.drawImage(image, 0, 0, null); // 最后再把图片画到屏幕上面
    }

    class MyKeyListener extends KeyAdapter {

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) { // 键盘按下时触发

            int keyCode = e.getKeyCode();


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
                case KeyEvent.VK_SPACE:
                    tank.fire();
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD) {
                tank.setMoving(false);
            } else {
                tank.setMoving(true);
            }
            tank.setTankDir(bL, bU, bR, bD);
        }
    }
}
