package com.example.class03response.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author wkx32
 * @description: 转发和重定向测试
 * @date 2024/9/28 下午2:29
 */
@WebServlet("/responseDemo02")
public class ResponseDemo2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //转发
//        request.getRequestDispatcher("responseDemo01?username=zhangsan").forward(request, response);
        //重定向
//        response.sendRedirect("responseDemo01?username=zhangsan");
        response.sendRedirect("https://www.baidu.com/");
    }
}
