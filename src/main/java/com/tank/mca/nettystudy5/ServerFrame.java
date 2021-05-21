package com.tank.mca.nettystudy5;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: Ryan
 * @Date: 2021/5/21 11:25
 * @Version: 1.0
 * @Description:
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

        this.b.addActionListener(e -> {
            server.serverStart();
        });
    }

    public void updateText(String text) {
        this.tal.setText(tal.getText() + System.getProperty("line.separator") + text);
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true); // 显示窗口代码
    }
}
