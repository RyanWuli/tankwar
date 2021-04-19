package com.tank;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
}
