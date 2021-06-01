package com.tank.mca.nettystudy11_tank.net;

/**
 * @Author: Ryan
 * @Date: 2021/5/25 10:29
 * @Version: 1.0
 * @Description:
 */
public abstract class Msg {

    abstract byte[] toBytes();
    abstract void handle();
    abstract void parse(byte[] bytes);
    abstract MsgType getMsgType();
}
