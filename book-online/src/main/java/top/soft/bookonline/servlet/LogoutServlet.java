package top.soft.bookonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午4:36
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清除用户会话
        HttpSession session = request.getSession();
        session.invalidate();
        // 重定向到登录页面或首页
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
