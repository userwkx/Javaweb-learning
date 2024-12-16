package com.example.class06filterlistener.Listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

import java.util.HashMap;
import java.util.Map;


@WebListener
public class ContextListener implements ServletContextListener , HttpSessionListener , HttpSessionAttributeListener{
    private static Map<String,Object> sessionMap;
    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextListener 初始化");
        sessionMap = new HashMap<String, Object>();
        sce.getServletContext().setAttribute("sessionMap",sessionMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextListener 销毁");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session =event.getSession();
        sessionMap.put(session.getId(),session.getAttribute("username"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        HttpSession session=event.getSession();
        sessionMap.remove(session.getId());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("sessionCreate 创建了新的会话");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sessionDestroyed 销毁了会话");
    }
}