package com.example.class05ajaxaxios;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/11/9 下午2:28
 */
@WebServlet("/check")
public class CheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        从请求参数中获取用户名
        String username = req.getParameter("username") ;
//        判断是否存在
        boolean equals = !"admin".equals(username);
//        响应结果
        resp.getWriter().write(String.valueOf(equals));
    }
}
