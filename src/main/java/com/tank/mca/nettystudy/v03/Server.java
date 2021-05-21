package com.tank.mca.nettystudy.v03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Ryan
 * @Date: 2021/5/17 17:31
 * @Version: 1.0
 * @Description:
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(8888));

        Socket s = ss.accept();
        System.out.println("a client connected!");
    }
}
