package com.tank.mca.nettystudy8_tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 11:25
 * @Version: 1.0
 * @Description: 处理 ui 线程阻塞等问题
 */
public class ServerFrame extends Frame {

    public static final ServerFrame INSTANCE = new ServerFrame();

    Button b = new Button("start");
    TextArea tal = new TextArea();
    TextArea tar = new TextArea();
    Server server = new Server();

    public ServerFrame() {
        this.setSize(1600, 600);
        this.setLocation(300, 30);
        this.add(b, BorderLayout.NORTH); // 添加 button 和 位置（北边-最上边）
        Panel p = new Panel(new GridLayout(1, 2)); // panel 一行两列
        p.add(tal);
        p.add(tar);
        this.add(p);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

//        this.b.addActionListener(e -> {
//            server.serverStart();
//        }); // 这个 线程 启动 的话 会造成启动完成之后的阻塞（ui 线程），感觉像是初始化都未完成
    }

    public void updateText(String text) {
        this.tal.setText(tal.getText() + System.getProperty("line.separator") + text);
    }

    public void updateTextR(String text) {
        this.tar.setText(tar.getText() + System.getProperty("line.separator") + text);
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true); // 显示窗口代码
        ServerFrame.INSTANCE.server.serverStart();
    }
}
