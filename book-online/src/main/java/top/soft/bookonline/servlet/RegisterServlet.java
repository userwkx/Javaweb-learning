package top.soft.bookonline.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import top.soft.bookonline.dao.UserDao;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;
import top.soft.bookonline.service.impl.UserServiceImpl;

import java.io.IOException;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/27 下午2:59
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        String repassword = req.getParameter("repassword");
        // 设置响应内容类型和编码
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if(password.equals(repassword)) {

            try{
                User user = userService.findRepeatUser(account) ;


            }catch(EmptyResultDataAccessException e){
                // 返回错误信息
                userService.register(account, password);
                // 清除用户会话
                HttpSession session = req.getSession();
                session.invalidate();

                // 重定向到登录页面或首页
                resp.sendRedirect(req.getContextPath() + "/register-page");
            }catch (IncorrectResultSizeDataAccessException e){
                resp.getWriter().write("<script>alert('用户名已存在');location.href='/register-page';</script>");
            }
//            User user = userService.findRepeatUser(account) ;
//            if(user != null) {
//                userService.register(account, password);
//                // 清除用户会话
//                HttpSession session = req.getSession();
//                session.invalidate();
//
//                // 重定向到登录页面或首页
//                resp.sendRedirect(req.getContextPath() + "/register-page");
//
//            }else {
//                resp.getWriter().write("<script>alert('用户名已存在');location.href='/register-page';</script>");
//            }
//
//        }else {
//            // 返回错误信息
//            resp.getWriter().write("<script>alert('密码设置不一致');location.href='/register-page';</script>");
          }
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
