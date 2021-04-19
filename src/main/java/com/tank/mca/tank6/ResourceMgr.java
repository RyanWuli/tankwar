package com.tank.mca.tank6;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: Ryan
 * @Date: 2021/4/15 11:36
 * @Version: 1.0
 * @Description: 资源管理类
 */
public class ResourceMgr {

    // 子弹图片
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;
    // 爆炸图片
    public static BufferedImage[] explodes = new BufferedImage[16];
    // 我方坦克图片
    public static BufferedImage goodTankL, goodTankU, goodTankR, goodTankD;
    // 敌方坦克图片
    public static BufferedImage badTankL, badTankU, badTankR, badTankD;

    static {
        try {
            goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankimg/images/GoodTank1.png"));
            goodTankL = ImageUtil.rotateImage(goodTankU,-90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);

            badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankimg/images/BadTank1.png"));
            badTankL = ImageUtil.rotateImage(badTankU,-90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);
            badTankD = ImageUtil.rotateImage(badTankU, 180);

            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankimg/images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU, -90);
            bulletR = ImageUtil.rotateImage(bulletU, 90);
            bulletD = ImageUtil.rotateImage(bulletU, 180);

            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankimg/images/e" + (i+1) +".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
