package com.example.class06filterlistener.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/11/23 下午2:52
 */

@WebServlet("/wordsServlet")
public class WordsServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String words = req.getParameter("words");
        PrintWriter writer = resp.getWriter();
        writer.write(words);
        writer.close();
    }
}
