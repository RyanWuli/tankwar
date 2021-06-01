package com.tank;

import com.tank.mca.nettystudy10_tank.net.MsgDecoder;
import com.tank.mca.nettystudy10_tank.net.MsgEncoder;
import com.tank.mca.nettystudy10_tank.net.TankStartMovingMsg;
import com.tank.mca.nettystudy7.TankMsg;
import com.tank.mca.nettystudy7.TankMsgDecoder;
import com.tank.mca.nettystudy7.TankMsgEncoder;
import com.tank.mca.nettystudy8_tank.Dir;
import com.tank.mca.nettystudy8_tank.Group;
import com.tank.mca.nettystudy8_tank.net.TankJoinMsg;
import com.tank.mca.nettystudy8_tank.net.TankJoinMsgDecoder;
import com.tank.mca.nettystudy8_tank.net.TankJoinMsgEncoder;
import com.tank.mca.nettystudy9_tank.net.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test() throws IOException {


        // AppTest.class.getClassLoader().getResource("./tankimg/images/BadTank1.png").getPath() 也可
        // String path = this.getClass().getClassLoader().getResource("./tankimg/images/BadTank1.png").getPath(); 也可
        String path = this.getClass().getClassLoader().getResource("tankimg/images/BadTank1.png").getPath();
        System.out.println(path);
        BufferedImage read = ImageIO.read(new File(path));
        assertNotNull(read);

        BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("tankimg/images/BadTank1.png"));
        BufferedImage image2 = ImageIO.read(AppTest.class.getClassLoader().getResourceAsStream("tankimg/images/BadTank1.png"));
        assertNotNull(image);
        assertNotNull(image2);

        Enumeration<URL> resources = this.getClass().getClassLoader().getResources("audio/war1.wav");
        System.out.println(resources);

        String path2 = this.getClass().getClassLoader().getResource("audio/war1.wav").getPath();
        System.out.println(path2);


    }

    @Test
    public void test2() throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(1000);
            System.out.println(random.nextInt(4));
        }
    }


    /**
     * ************************************** netty codec test ***********************************
     */
    @Test
    public void codecTestEncoder() {
        TankMsg tm = new TankMsg(10, 10);
        EmbeddedChannel ec = new EmbeddedChannel(new TankMsgEncoder()); // 虚拟的 channel ，不是网络的
        ec.writeOutbound(tm); // 往外写一个 message

        ByteBuf buf = ec.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        Assert.assertTrue(x == 10 && y == 10);
        buf.release();
    }

    @Test
    public void codecTestEncoder2() {

        // tankMsg 转换为 byteBuf
        ByteBuf buf = Unpooled.buffer();
        TankMsg tm = new TankMsg(10, 10);
        buf.writeInt(tm.x);
        buf.writeInt(tm.y);

        EmbeddedChannel ec = new EmbeddedChannel(new TankMsgEncoder(), new TankMsgDecoder()); // byteBuf 会略过 encoder 直接到 decoder
        ec.writeInbound(buf.duplicate()); // writeInbound 往 server 端（测试端只有一个）方向写

        TankMsg t = ec.readInbound(); // 读进来 （解码）
        System.out.println(t);
//        Assert.assertTrue(tm.x == 10 && tm.y == 10);
    }


    /**
     * 编码对应的是往外写，所以是用的 outBound
     */
    @Test
    public void tankEncoderTest() {
        UUID uuid = UUID.randomUUID();
        TankJoinMsg tjm = new TankJoinMsg(5, 10, Dir.LEFT, true, Group.GOOD, uuid);
        EmbeddedChannel ec = new EmbeddedChannel(); // 模拟管道
        ec.pipeline().addLast(new TankJoinMsgEncoder()); // 添加编码器
        ec.writeOutbound(tjm); // 像管道写实体，会通过之定义的编码器编码
        ByteBuf bb = (ByteBuf) ec.readOutbound(); // 从管道取出来数据

        // 解析取出来的数据(按照编码的顺序取出来)
        int x = bb.readInt();
        int y = bb.readInt();
        Dir dir = Dir.values()[bb.readInt()];
        boolean moving = bb.readBoolean();
        Group group = Group.values()[bb.readInt()];
        UUID id = new UUID(bb.readLong(), bb.readLong());

        // 对比前后的值是否相同
        Assert.assertEquals(5, x);
        Assert.assertEquals(10, y);
        Assert.assertEquals(Dir.LEFT, dir);
        assertTrue(moving);
        Assert.assertEquals(Group.GOOD, group);
        Assert.assertEquals(uuid, id);
    }

    /**
     * 解码是读进来 所以用 in writeInbound
     */
    @Test
    public void tankDecoderTest() {
        UUID uuid = UUID.randomUUID();
        TankJoinMsg tjm = new TankJoinMsg(5, 10, Dir.LEFT, true, Group.GOOD, uuid);
        EmbeddedChannel ec = new EmbeddedChannel();
        ec.pipeline()
                .addLast(new TankJoinMsgDecoder());

        ByteBuf buffer = Unpooled.buffer(); // 新建一个 byteBuffer
        buffer.writeBytes(tjm.toBytes()); // buffer 中写入数组字节

        ec.writeInbound(buffer.duplicate()); // 写进 通道 （duplicate 复制一份）
        TankJoinMsg msg = ec.readInbound();

         // 对比前后的值是否相同
        Assert.assertEquals(5, msg.x);
        Assert.assertEquals(10, msg.y);
        Assert.assertEquals(Dir.LEFT, msg.dir);
        assertTrue(msg.moving);
        Assert.assertEquals(Group.GOOD, msg.group);
        Assert.assertEquals(uuid, msg.id);
    }


    /**
     * netty tank 9
     */
    @Test
    public void msgEncoderTest() {
        UUID uuid = UUID.randomUUID();
        com.tank.mca.nettystudy9_tank.net.TankJoinMsg tjm = new com.tank.mca.nettystudy9_tank.net.TankJoinMsg(5, 10, com.tank.mca.nettystudy9_tank.Dir.UP, true, com.tank.mca.nettystudy9_tank.Group.GOOD, uuid);
        EmbeddedChannel ec = new EmbeddedChannel(); // 模拟管道
        ec.pipeline().addLast(new com.tank.mca.nettystudy9_tank.net.TankJoinMsgEncoder()); // 添加编码器
        ec.writeOutbound(tjm); // 像管道写实体，会通过之定义的编码器编码
        ByteBuf bb = (ByteBuf) ec.readOutbound(); // 从管道取出来数据

        assertEquals(MsgType.TankJoin, MsgType.values()[bb.readInt()]); // 类型取出来解析是否正确

        int length = bb.readInt();
        assertEquals(33, length); // 长度取出来解析看是否正确

        // 解析取出来的数据(按照编码的顺序取出来)
        int x = bb.readInt();
        int y = bb.readInt();
        com.tank.mca.nettystudy9_tank.Dir dir = com.tank.mca.nettystudy9_tank.Dir.values()[bb.readInt()];
        boolean moving = bb.readBoolean();
        com.tank.mca.nettystudy9_tank.Group group = com.tank.mca.nettystudy9_tank.Group.values()[bb.readInt()];
        UUID id = new UUID(bb.readLong(), bb.readLong());

        // 对比前后的值是否相同
        Assert.assertEquals(5, x);
        Assert.assertEquals(10, y);
        Assert.assertEquals(com.tank.mca.nettystudy9_tank.Dir.UP, dir);
        assertTrue(moving);
        Assert.assertEquals(com.tank.mca.nettystudy9_tank.Group.GOOD, group);
        Assert.assertEquals(uuid, id);
    }


    /**
     * netty tank 9
     */
    @Test
    public void msgDecoderTest() {
        UUID uuid = UUID.randomUUID();
        com.tank.mca.nettystudy9_tank.net.TankJoinMsg tjm = new com.tank.mca.nettystudy9_tank.net.TankJoinMsg(5, 10, com.tank.mca.nettystudy9_tank.Dir.LEFT, true, com.tank.mca.nettystudy9_tank.Group.GOOD, uuid);
        EmbeddedChannel ec = new EmbeddedChannel();
        ec.pipeline()
                .addLast(new com.tank.mca.nettystudy9_tank.net.TankJoinMsgDecoder());

        ByteBuf buffer = Unpooled.buffer(); // 新建一个 byteBuffer
        buffer.writeInt(MsgType.TankJoin.ordinal()); // 放入类型
        buffer.writeInt(tjm.toBytes().length); // 写入长度
        buffer.writeBytes(tjm.toBytes()); // buffer 中写入数组字节

        ec.writeInbound(buffer.duplicate()); // 写进 通道 （duplicate 复制一份）
        com.tank.mca.nettystudy9_tank.net.TankJoinMsg msg = ec.readInbound();

        // 对比前后的值是否相同
        Assert.assertEquals(5, msg.x);
        Assert.assertEquals(10, msg.y);
        Assert.assertEquals(com.tank.mca.nettystudy9_tank.Dir.LEFT, msg.dir);
        assertTrue(msg.moving);
        Assert.assertEquals(com.tank.mca.nettystudy9_tank.Group.GOOD, msg.group);
        Assert.assertEquals(uuid, msg.id);
    }

    @Test
    public void tankStartMovingEncoderTest() {
        UUID id = UUID.randomUUID();
        TankStartMovingMsg tsmm = new TankStartMovingMsg(5, 5, com.tank.mca.nettystudy10_tank.Dir.LEFT, id);
        EmbeddedChannel ec = new EmbeddedChannel(); // 测试通道
        ec.pipeline().addLast(new MsgEncoder()); // 管道添加 消息 编码器
        ec.writeOutbound(tsmm);
        ByteBuf buf = ec.readOutbound();

        com.tank.mca.nettystudy10_tank.net.MsgType msgType = com.tank.mca.nettystudy10_tank.net.MsgType.values()[buf.readInt()];
        assertEquals(msgType, com.tank.mca.nettystudy10_tank.net.MsgType.TankStartMoving);

        assertEquals(buf.readInt(), 28);

        UUID uuid = new UUID(buf.readLong(), buf.readLong());
        assertEquals(uuid, id);

        assertEquals(buf.readInt(), 5);
        assertEquals(buf.readInt(), 5);
        assertEquals(com.tank.mca.nettystudy10_tank.Dir.values()[buf.readInt()], com.tank.mca.nettystudy10_tank.Dir.LEFT);
    }


    @Test
    public void tankStartMovingDecoderTest() {
        UUID id = UUID.randomUUID();
        TankStartMovingMsg tsmm = new TankStartMovingMsg(5, 5, com.tank.mca.nettystudy10_tank.Dir.LEFT, id);
        EmbeddedChannel ec = new EmbeddedChannel();
        ec.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(com.tank.mca.nettystudy10_tank.net.MsgType.TankStartMoving.ordinal());
        buf.writeInt(28);
        buf.writeBytes(tsmm.toBytes());

        ec.writeInbound(buf); // 测试 写出读取的数据 - 读取的解码，

        TankStartMovingMsg t = ec.readInbound();

        assertEquals(t.getX(), 5);
        assertEquals(t.getY(), 5);
        assertEquals(t.getDir(), com.tank.mca.nettystudy10_tank.Dir.LEFT);
        assertEquals(t.getId(), id);
    }
}
