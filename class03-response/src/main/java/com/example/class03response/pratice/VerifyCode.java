package com.example.class03response.pratice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author wkx32
 * @description: TODO
 * @date 2024/9/28 下午4:07
 */
@WebServlet("/verifyCode")
public class VerifyCode extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 160;
        int height = 45;
        //创建验证码的图片对象
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

        //创建画笔对象
        Graphics g = image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        String str = "1234567890qwertyuioplkjhgfdsa";

        //生成随机数
        Random random = new Random();
        for (int i = 0; i <= 5; i++) {
            int x = random.nextInt(str.length());
            char ch = str.charAt(x);
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g.setColor(color);
            g.drawString(String.valueOf(ch) , width/5*i, height/2);
        }
        for (int i = 0; i <= 10; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
        //将验证码图片展现到页面上去
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}
