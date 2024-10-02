package com.example.class03response.servletContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author wkx32
 * @description: TODO
 * @date 2024/9/28 下午4:59
 */
@WebServlet("/servletContextDemo03")
public class servletContextDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取 servletContext 对象
        ServletContext servletContext = req.getServletContext();
        //获取文件名
        String fileName ="hello.pdf";
        //获取资源文件类型
        String mimeType = servletContext.getMimeType(fileName);
        System.out.println(mimeType);
    }
}
