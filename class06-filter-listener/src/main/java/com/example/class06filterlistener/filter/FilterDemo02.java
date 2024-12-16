package com.example.class06filterlistener.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;


//@WebFilter("/*")
public class FilterDemo02 implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    public void destroy() {
        System.out.println(" 02 过滤器销毁");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("放行资源之前 02 执行");
        //          放行资源
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("放行资源之后 02 执行");

    }
}