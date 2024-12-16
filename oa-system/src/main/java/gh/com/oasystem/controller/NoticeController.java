package gh.com.oasystem.controller;
import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.model.entity.Notice;
import gh.com.oasystem.service.NoticeService;
import gh.com.oasystem.service.impl.NoticeServiceImpl;
import gh.com.oasystem.utils.ResponseUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/notice/*")
public class NoticeController extends HttpServlet {
    private NoticeService noticeService=new NoticeServiceImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {
        noticeService=new NoticeServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);

        String eid = req.getParameter("eid");
        ResponseUtils result;
        List<Notice> notices = noticeService.queryAllNotices(Long.valueOf(eid));
        List<Map<String, Object>> noticeList = new ArrayList<>();
        Map<String, Object> NoticeMap = new HashMap<>();

        for (Notice notice : notices) {
            Map<String, Object> noticeMap = new HashMap<>();
            noticeMap.put("noticeId", notice.getNoticeId());
            noticeMap.put("createTime", notice.getCreateTime());
            noticeMap.put("content", notice.getContent());
            noticeList.add(noticeMap);
        }


        result = new ResponseUtils().put("list", noticeList);

        if ("detail".equals(action)) {

        } else {
            resp.sendRedirect("/api/notice/detail?eid=" + eid);
        }
        System.out.println(result.toJsonString());
        resp.getWriter().write(result.toJsonString());
    }

}
