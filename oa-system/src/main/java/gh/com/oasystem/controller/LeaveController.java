package gh.com.oasystem.controller;

import gh.com.oasystem.model.entity.LeaveForm;
import gh.com.oasystem.model.vo.AuditLeaveVO;
import gh.com.oasystem.service.LeaveService;
import gh.com.oasystem.utils.ResponseUtils;
import gh.com.oasystem.service.impl.LeaveServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@WebServlet(value = "/api/leave/*")
public class LeaveController extends HttpServlet {
    private LeaveService leaveService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        leaveService=new LeaveServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        // 取出最后一个uri
        String action = uri.substring(uri.lastIndexOf("/")+ 1);
        switch (action){
            case "create"->createLeave(req, resp);
            case "list" -> leaveList(req, resp);
            case "audit" -> auditLeave(req,resp);

        }
    }

    private void auditLeave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        ResponseUtils responseUtils;
        String eid = req.getParameter("eid");
        String formId = req.getParameter("formId");
        String result = req.getParameter("result");
        String reason = req.getParameter("reason");

        try{
            leaveService.auditLeave(Long.parseLong(eid), Long.parseLong(formId),result,reason);
            responseUtils=new ResponseUtils();
        }catch (Exception e){
            responseUtils=new ResponseUtils("-1",e.getMessage());
        }
        resp.getWriter().write(responseUtils.toJsonString());
    }

    private void createLeave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String eid = req.getParameter("eid");
        String formType = req.getParameter("formType");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String reason = req.getParameter("reason");

        LeaveForm leaveForm = LeaveForm.builder()
                .employeeId(Long.parseLong(eid))
                .formType(Integer.parseInt(formType))
                .startTime(Instant.ofEpochMilli(Long.parseLong(startTime)).atZone(ZoneOffset.ofHours(8)).toLocalDateTime())
                .endTime(Instant.ofEpochMilli(Long.parseLong(endTime)).atZone(ZoneOffset.ofHours(8)).toLocalDateTime())
                .reason(reason)
                .createTime(LocalDateTime.now())
                .build();
        ResponseUtils result;
        resp.setContentType("application/json;charset=utf-8");
        try {
            leaveService.createLeave(leaveForm);
            result = new ResponseUtils();
            resp.getWriter().write(result.toJsonString());
        } catch (Exception e) {
            result = new ResponseUtils("-1", e.getMessage());
            resp.getWriter().write(result.toJsonString());
        }
    }
    private void leaveList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eid = req.getParameter("eid");
        ResponseUtils result;
        resp.setContentType("application/json;charset=utf-8");
        try{
            List<AuditLeaveVO> list = leaveService.queryLeaveList(Long.parseLong(eid));
            result = new ResponseUtils().put("list", list);
        }catch (Exception e){
            result = new ResponseUtils("-1", e.getMessage());
        }
        resp.getWriter().write(result.toJsonString());
    }
}
