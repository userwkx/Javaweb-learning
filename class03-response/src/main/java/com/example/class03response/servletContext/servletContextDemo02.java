package com.example.class03response.servletContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wkx32
 * @description: 通过 servletContext 获取文件内容
 * @date 2024/9/28 下午4:42
 */
@WebServlet("/servletContextDemo02")
public class servletContextDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取 servletContext 对象
        ServletContext servletContext = req.getServletContext();
        resp.setContentType("text/html;charset=utf-8");
        //获取 webapp 目录下的资源
        String aRealPath = servletContext.getRealPath("/3.txt");
        System.out.println(aRealPath);
        //获取 WEB-INF 目录下的资源
        String bRealPath = servletContext.getRealPath("/WEB-INF/1.txt");
        System.out.println(bRealPath);
        File file = new File(aRealPath);
        InputStream in = new FileInputStream(file);
        int read = 0;
        ServletOutputStream out = resp.getOutputStream();
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        in.close();
        out.flush();
        out.close();
    }
}
