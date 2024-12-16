package top.soft.bookonline.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.springframework.dao.EmptyResultDataAccessException;
import top.soft.bookonline.entity.User;
import top.soft.bookonline.service.UserService;
import top.soft.bookonline.service.impl.UserServiceImpl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author Kieran
 * @description: TODO javaweb-learning
 * @date 2024/10/26 下午2:46
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型和编码
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        // 获取表单数据
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        String inputCaptcha = req.getParameter("captcha");

        // 验证验证码
        String sessionCaptcha = (String) req.getSession().getAttribute("captcha");
        if (inputCaptcha == null || !inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
            req.setAttribute("errorMessage", "验证码错误");
            resp.getWriter().write("<script>alert('验证码错误。');location.href='/login-page';</script>");
            //RequestDispatcher dispatcher = req.getRequestDispatcher("/login.html");
            //dispatcher.forward(req, resp);
            return;
        }

        // 调用登录功能
        try {

            User user = userService.signIn(account, password);
            if(user != null){
                // 将用户对象记入 session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if(remember != null){
                    Cookie usernameCookie = new Cookie( "account",account);
                    Cookie passwordCookie = new Cookie( "password", password);
                    usernameCookie.setMaxAge(60*60*24 *7);
                    passwordCookie.setMaxAge(60*60*24 *7);
                    resp.addCookie(usernameCookie);
                    resp.addCookie(passwordCookie);
            }

            // 重定向回到 /index，进入 IndexServlet
            resp.sendRedirect(req.getContextPath()+"/index");
            }else{
                // 返回错误信息
                resp.getWriter().write("<script>alert('账号或密码错误,请重新输入。');location.href='/login-page';</script>");
            }
        }catch (EmptyResultDataAccessException e) {
            // 返回错误信息
            resp.getWriter().write("<script>alert('账号或密码错误,请重新输入。');location.href='/login-page';</script>");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("captcha".equals(action)) {
            generateCaptcha(req, resp); // 生成验证码图片
        } else {
            this.doPost(req, resp);
        }
    }

    private void generateCaptcha(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String captcha = generateCaptcha();
        writeCaptchaImage(captcha, req, resp);
        req.getSession().setAttribute("captcha", captcha);
    }

    private String generateCaptcha() {
        Random random = new Random();
        StringBuilder captchaStringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captchaStringBuilder.append(random.nextInt(10));
        }
        return captchaStringBuilder.toString();
    }

    private void writeCaptchaImage(String captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 160;
        int height = 40;
        response.setContentType("image/jpeg");

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = new Font("Fixedsys", Font.BOLD, 20);
        g.setFont(font);

        Random random = new Random();

        // 添加干扰线
        for (int i = 0; i < 20; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            g.setColor(getRandomColor());
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加验证码
        for (int i = 0; i < captcha.length(); i++) {
            String strRand = String.valueOf(captcha.charAt(i));
            g.setColor(getRandomColor());
            g.drawString(strRand, 15 * i + 6, 24);
        }

        g.dispose();

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", bao);
        bao.writeTo(response.getOutputStream());
    }

    private Color getRandomColor() {
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b);
    }
}

