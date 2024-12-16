package top.soft.bookonline.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化方法，如果需要
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 设置请求和响应编码
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html;charset=utf-8");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 放行不需要登录的资源
        String[] urls = {"/images", "/css", "/login.html", "/login-page", "/login"};
        String requestUri = request.getRequestURI(); // 获取请求的 URI（不含主机名）

        for (String url : urls) {
            if (requestUri.startsWith(url)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        // 检查用户是否已登录
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        if (user == null) {
            // 未登录，转发到登录页面
            response.setContentType("text/html;charset=utf-8");
            request.getRequestDispatcher("/login.html").forward(request, response);
        } else {
            // 已登录，继续过滤链
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        // 销毁方法，如果需要
    }
}
