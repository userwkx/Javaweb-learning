package top.soft.bookonline.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.dao.impl.UserDaoImpl;

import java.io.IOException;


@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String address = request.getParameter("address");
        String account = request.getParameter("account");

        System.out.println("userId: " + userId);
        System.out.println("address: " + address);
        System.out.println("account: " + account);
        UserDao userDao = new UserDaoImpl();

        // 更新地址
        if (address != null && !address.isEmpty()) {
            userDao.updateUserAddress(userId, address);
        }

        // 更新账号
        if (account != null && !account.isEmpty()) {
            userDao.updateUserAccount(userId, account);
        }

        // 重定向到用户信息页面
        response.sendRedirect("userInfo.jsp?userId=" + userId);
    }
}
