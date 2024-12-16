package com.example.classoa.servlet;

import com.example.classoa.entity.Notice;
import com.example.classoa.service.NoticeService;
import com.example.classoa.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/notice/list")
public class NoticeServlet extends HttpServlet {
    private final NoticeService noticeService = new NoticeService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeId = req.getParameter("eid");
        ResponseUtils result;
        try {
            List<Notice> noticeList = noticeService.getNoticeList(Long.parseLong(employeeId));
            result = new ResponseUtils().put("list", noticeList);
        } catch (Exception e) {
            e.printStackTrace();
            result = new ResponseUtils(e.getClass().getSimpleName(), e.getMessage());

        }

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().println(result.toJsonString());
    }
}
