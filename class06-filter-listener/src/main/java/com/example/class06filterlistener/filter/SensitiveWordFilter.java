package top.soft.class06filterlistener.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 27596
 * @description: TODO
 * @date 2024/11/23 14:27
 */
@WebFilter("/*")
public class SensitiveWordFilter implements Filter {
    //      敏感词集合
    List<String> list = new ArrayList<>();
    //      执行方法名
    private final String methodName = "getParameter";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try{
            ServletContext servletContext = filterConfig.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词.txt");
            InputStreamReader inputStream = new InputStreamReader(new FileInputStream(realPath), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            String line;
            while((line = bufferedReader.readLine()) != null){
                list.add(line);
            }
            System.out.println("数组长度"+list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html;charset=utf-8");
//          通过代理增强 getParameter 方法
        ServletRequest proxyRequest = (ServletRequest) Proxy.newProxyInstance(servletRequest.getClass().getClassLoader(), servletRequest.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodName.equals(method.getName())) {
                    String value = (String)method.invoke(servletRequest, args);
                    System.out.println("value的值"+value);
                    if (value != null) {
                        for (String str : list) {
                            if (value.contains(str)) {
                                value = value.replaceAll(str, "******");
                            }
                        }
                    }
                    return value;
                }
                return method.invoke(servletRequest, args);
            }
        });

        filterChain.doFilter(proxyRequest, servletResponse);
    }
}