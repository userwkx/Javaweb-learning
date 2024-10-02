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
 * @date 2024/9/28 下午4:36
 */
@WebServlet("/servletContextDemo01")
public class servletContextDemo1 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext servletContext01 = request.getServletContext();

        ServletContext servletContext02 = this.getServletContext();

        System.out.println(servletContext01.equals(servletContext02));
    }
}
