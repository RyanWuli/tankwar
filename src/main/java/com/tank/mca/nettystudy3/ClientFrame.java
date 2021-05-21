package com.tank.mca.nettystudy3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import javafx.scene.shape.Line;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: Ryan
 * @Date: 2021/5/20 14:06
 * @Version: 1.0
 * @Description:
 *      channel 和 pipeline （channel 是通道，pipeline 是通道上的 handler 组成的责任链）
 */
public class ClientFrame extends Frame {

    TextArea ta = new TextArea();
    TextField tf = new TextField();

    Client client = null;

    public ClientFrame() {
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.add(ta, BorderLayout.CENTER);
        this.add(tf, BorderLayout.SOUTH);

        tf.addActionListener(actionEvent -> { // 回车的监听方法
            System.out.println(tf.getText());
            client.send(tf.getText());
            tf.setText("");
        });
        this.setVisible(true);
        connectToServer();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void connectToServer() {
        client = new Client();
        client.ta = ta;
        client.connect();
    }

//    private void updateText(String msgAccept) {
//        ta.setText(ta.getText() + System.getProperty("line.separator") + msgAccept); // System.getProperty("line.separator") 换行，linux 和 windows 都可
//    }



    public static void main(String[] args) {
        new ClientFrame();
    }

}



