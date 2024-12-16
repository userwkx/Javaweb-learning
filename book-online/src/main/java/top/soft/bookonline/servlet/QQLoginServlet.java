package top.soft.bookonline.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.EmptyResultDataAccessException;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;
import top.soft.bookonline.service.impl.UserServiceImpl;

import java.io.IOException;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/27 下午7:46
 */
@WebServlet("/qq-login")
public class QQLoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = new UserServiceImpl();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String account = req.getParameter("account");
        String password = req.getParameter("password");
        //设置响应内容类型和编码
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 调用登录功能
        try {
            User user = userService.signInOther(account, password);
            if(user != null) {
                // 将用户对象记入 session
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                // 重定向回到 /index，进入 IndexServlet
                resp.sendRedirect(req.getContextPath()+"/index");
            }
        }catch (EmptyResultDataAccessException e) {
            // 返回错误信息
            resp.getWriter().write("<script>alert('账号或密码错误,请重新输入。');location.href='/qq-login-page';</script>");
        }finally {
            // 返回错误信息
            resp.getWriter().write("<script>alert('账号或密码不能为空');location.href='/qq-login-page';</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
