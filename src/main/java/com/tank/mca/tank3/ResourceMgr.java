package com.tank.mca.tank3;

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

    public static BufferedImage tankL, tankU, tankR, tankD;

    static {
        try {
            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankImg/images/tankL"));
            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankImg/images/tankU"));
            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankImg/images/tankR"));
            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("tankImg/images/tankD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
