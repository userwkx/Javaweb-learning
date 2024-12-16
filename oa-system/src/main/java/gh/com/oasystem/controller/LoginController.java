package gh.com.oasystem.controller;

import gh.com.oasystem.model.entity.User;
import gh.com.oasystem.service.UserService;
import gh.com.oasystem.service.impl.UserServiceImpl;
import gh.com.oasystem.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {
    private UserService userService;
    @Override
    public void init() throws ServletException {
        userService=new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        ResponseUtils responseUtils=new ResponseUtils();
        if(username==null && password==null){
            responseUtils=new ResponseUtils("-1","用户名密码不能为空");

        }
        User loginUser=userService.login(username, password);

        if(loginUser==null) {
            responseUtils = new ResponseUtils("-1", "登陆失败");

        }

        responseUtils.put("user", loginUser);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(responseUtils.toJsonString());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
