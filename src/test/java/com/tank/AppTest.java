package com.tank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.tank.mca.nettystudy7.TankMsg;
import com.tank.mca.nettystudy7.TankMsgDecoder;
import com.tank.mca.nettystudy7.TankMsgEncoder;
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

        Assert.assertTrue(tm.x == 10 && tm.y == 10);
    }
}
