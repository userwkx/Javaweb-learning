package com.example.class06filterlistener.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;


//@WebFilter("/*")
public class FilterDemo01 implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    public void destroy() {
        System.out.println("过滤器销毁");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("放行资源之前执行");
        //          放行资源
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("放行资源之后执行");

    }
}