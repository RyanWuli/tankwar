package com.tank.mca.tank6;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author: Ryan
 * @Date: 2021/4/16 16:12
 * @Version: 1.0
 * @Description: 图片工具类
 */
public class ImageUtil {

    /**
     * 图片旋转
     * @param bi
     * @param degree
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bi, final int degree) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        int type = bi.getColorModel().getTransparency();
        BufferedImage bufferedImage;
        Graphics2D graphics2D;
        (graphics2D = (bufferedImage = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2D.drawImage(bi, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
