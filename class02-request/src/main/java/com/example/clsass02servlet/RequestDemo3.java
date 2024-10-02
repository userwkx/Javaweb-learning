package com.example.clsass02servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/requestDemo3")
public class RequestDemo3 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求头数据:user-agent
        String agent = request.getHeader("user-agent");
        //判断agent的浏览器版本
        if (agent.contains("Edg")) {
            System.out.println("Edge 浏览器...");
        } else if (agent.contains("Chrome")) {
            System.out.println("Chrome 浏览器...");
        }
    }
}