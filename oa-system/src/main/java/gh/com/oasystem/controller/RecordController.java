package gh.com.oasystem.controller;

import gh.com.oasystem.common.Constant;
import gh.com.oasystem.model.vo.RecordLeaveVO;
import gh.com.oasystem.service.RecordService;
import gh.com.oasystem.service.impl.RecordServiceImpl;
import gh.com.oasystem.utils.ResponseUtils;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/api/record/*")
public class RecordController extends HttpServlet {
    private RecordService recordService=new RecordServiceImpl();
    @Override
    public void init(ServletConfig config) throws ServletException {
        recordService=new RecordServiceImpl();
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
        try {
            List<RecordLeaveVO> recordlist = recordService.leaveRecords(Long.parseLong(eid));
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (RecordLeaveVO recordLeaveVO : recordlist) {
                Map<String, Object> noticeMap = new HashMap<>();
                if (recordLeaveVO.getIsLast() == 0 && recordLeaveVO.getState().equals(Constant.FLOW_STATE_COMPLETE)) {
                    continue;
                }
                if (recordLeaveVO.getState().equals(Constant.FLOW_STATE_COMPLETE) && recordLeaveVO.getIsLast() == 1) {
                    noticeMap.put("formType", recordLeaveVO.getFormType());
                    noticeMap.put("startTime", recordLeaveVO.getStartTime());
                    noticeMap.put("endTime", recordLeaveVO.getEndTime());
                    noticeMap.put("reason", recordLeaveVO.getReason());
                    noticeMap.put("createTime", recordLeaveVO.getCreateTime());
                    noticeMap.put("isLast", 1);
                    noticeMap.put("operatorName", recordLeaveVO.getOperatorName());
                    noticeMap.put("departmentName", recordLeaveVO.getDepartmentName());
                    noticeMap.put("state", recordLeaveVO.getResult());

                }
                if (recordLeaveVO.getIsLast() == 1 && recordLeaveVO.getState().equals(Constant.FLOW_STATE_PROCESS)) {
                    noticeMap.put("formType", recordLeaveVO.getFormType());
                    noticeMap.put("startTime", recordLeaveVO.getStartTime());
                    noticeMap.put("endTime", recordLeaveVO.getEndTime());
                    noticeMap.put("reason", recordLeaveVO.getReason());
                    noticeMap.put("createTime", recordLeaveVO.getCreateTime());
                    noticeMap.put("isLast", 0);
                    noticeMap.put("operatorName", recordLeaveVO.getOperatorName());
                    noticeMap.put("departmentName", recordLeaveVO.getDepartmentName());
                    noticeMap.put("state", recordLeaveVO.getState());
                }
                if (recordLeaveVO.getIsLast() == 0 && recordLeaveVO.getState().equals(Constant.FLOW_STATE_PROCESS)) {
                    noticeMap.put("formType", recordLeaveVO.getFormType());
                    noticeMap.put("startTime", recordLeaveVO.getStartTime());
                    noticeMap.put("endTime", recordLeaveVO.getEndTime());
                    noticeMap.put("reason", recordLeaveVO.getReason());
                    noticeMap.put("createTime", recordLeaveVO.getCreateTime());
                    noticeMap.put("isLast", 0);
                    noticeMap.put("operatorName", recordLeaveVO.getOperatorName());
                    noticeMap.put("departmentName", recordLeaveVO.getDepartmentName());
                    noticeMap.put("state", recordLeaveVO.getState());
                }

                if (recordLeaveVO.getState().equals(Constant.FLOW_STATE_READY)) {
                    continue;
                }
                list.add(noticeMap);
                list.removeIf(Map::isEmpty);
            }
            result = new ResponseUtils().put("list", list);
        }catch (Exception e){
            result=new ResponseUtils("-1",e.getMessage());
        }

        if ("detail".equals(action)) {

        } else {
            resp.sendRedirect("/api/record/detail?eid=" + eid);
        }
        System.out.println(result.toJsonString());
        resp.getWriter().write(result.toJsonString());


    }

    private void RecordList(HttpServletRequest req, HttpServletResponse resp) {
    }
}
