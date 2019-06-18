package com.tblog.common;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author tyc
 * @date 2019/5/10
 */
public class Captcha {

    private static final int CAPTCHA_LENGTH = 4;

    public static String drawImage(HttpServletRequest request, HttpServletResponse response) {
        String captcha = getRandomNum(CAPTCHA_LENGTH);

        int width = 90;
        int height = 30;
        //建立BufferedImage对象,制定图片的长宽和颜色
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bi.createGraphics();
        Font font = new Font("微软雅黑", Font.PLAIN, 25);
        Color color = new Color(0, 0, 0);
        g.setFont(font);
        g.setColor(color);

        g.setBackground(new Color(200, 200, 200));
        g.clearRect(0, 0, width, height);


        //绘制形状,获取矩形对象
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(captcha, context);
//        double x = (width - bounds.getWidth()) / 2;
//        double y = (height - bounds.getHeight() / 2);
//        double ascent = bounds.getY();
//        double baseY = y - ascent;
//        g.drawString(captcha, (int) x, (int) baseY);
        g.drawString(captcha, 15, 25);


        //随机产生100个干扰点,使图象中的认证码不易被其它程序探测到
        Random random = new Random();
        for (int iIndex = 0; iIndex < 100; iIndex++) {
            int randomX = random.nextInt(width);
            int randomY = random.nextInt(height);
            g.drawLine(randomX, randomY, randomX, randomY);
        }

        //将字符串存入session中
        request.getSession().setAttribute("captcha", captcha);

        g.dispose();

        try {
            ImageIO.write(bi, "JPEG", response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return captcha;
    }

    /**
     * 生成随机数
     *
     * @param length
     * @return
     */
    private static String getRandomNum(int length) {
        String string = "QWERTYUPASDFGHJKZXCVBNM23456789qwertyupasfghjkzxcvbnm";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        char a;
        for (int i = 0; i < length; i++) {
            a = string.charAt(random.nextInt(string.length()));
            captcha.append(a);
        }
        return captcha.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomNum(5));
    }

}
