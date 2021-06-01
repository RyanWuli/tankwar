package com.tank.mca.nettystudy9_tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.*;

/**
 * @Author: Ryan
 * @Date: 2021/4/13 15:39
 * @Version: 1.0
 * @Description:
 */
public class TankFrame extends Frame {

    public static final int GAME_WIDTH = Main.gameWidth, GAME_HEIGHT = Main.gameHeight;

    public static final TankFrame INSTANCE = new TankFrame();
    int rtx = Main.getRandomInt(Main.tankMinX, Main.tankMaxX);
    int rty = Main.getRandomInt(Main.goodTankMinY, Main.goodTankMaxY);

    Tank tank = new Tank(rtx, rty, Dir.UP, Group.GOOD, false, this);

    Map<UUID, Tank> goodTanks = new HashMap<>(); // good tank 列表 联网游戏的时候

//    List<Tank> goodsTanks = new ArrayList<>(); // 主坦克
    List<Bullet> bullets = new ArrayList<>(); // 可多颗子弹同时出现，子弹容器
    List<Tank> tanks = new ArrayList<>(); // 坦克的 容器
    List<Explode> explodes = new ArrayList<>();
//    Explode explode = new Explode(400, 400, this);

    public TankFrame() {
//        System.out.println(GAME_WIDTH);
//        System.out.println(GAME_HEIGHT);
        setSize(GAME_WIDTH, GAME_HEIGHT); // 设置窗口大小宽度800，高度600，单位像素 px
        setResizable(false); // 能否改变窗口大小
        setTitle("Tank War"); // 设置窗口标题
//        setVisible(true); // 显示窗口 // 单例化了，main 方法中显示

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

    /*
    // 窗口需要重新绘制的时候调用，启动/窗口被挡住从新出来/窗口大小改变等
        Graphics：相当于画笔
     */
    @Override
    public void paint(Graphics g) {

        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量：" + bullets.size(), 10, 50);
        g.drawString("坦克的数量：" + tanks.size(), 10, 65);
        g.drawString("爆炸的数量：" + explodes.size(), 10, 80);
        g.setColor(color);


        // 画出 good tanks

        tank.setSPEED(Main.goodTankSpeed); // 主坦克速度调快一点
//        System.out.println(tank.isLived());
        if (tank.isLived()) {
            tank.paint(g);
        } else { // 把主坦克的块放到界面之外，否则触碰检测一直在
            tank.rectangle.x = -100;
            tank.rectangle.y = -100;
        }
        goodTanks.values().forEach(t -> t.paint(g));

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

    class MyKeyListener extends KeyAdapter {

        // 按下键盘可移动

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

    public Tank getMainTank() {
        return this.tank;
    }

    public void addGoodTank(Tank tank) {
        goodTanks.put(tank.id, tank);
    }

    public Tank findGoodTank(UUID id) {
        return goodTanks.get(id);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TankFrame{");
        sb.append("rtx=").append(rtx);
        sb.append(", rty=").append(rty);
        sb.append(", tank=").append(tank);
        sb.append(", bullets=").append(bullets);
        sb.append(", tanks=").append(tanks);
        sb.append(", explodes=").append(explodes);
        sb.append(", image=").append(image);
        sb.append('}');
        return sb.toString();
    }
}
