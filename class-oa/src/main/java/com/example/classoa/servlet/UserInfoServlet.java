package com.example.classoa.servlet;

import com.example.classoa.entity.Department;
import com.example.classoa.entity.Employee;
import com.example.classoa.entity.Node;
import com.example.classoa.service.DepartmentService;
import com.example.classoa.service.EmployeeService;
import com.example.classoa.service.NodeService;
import com.example.classoa.utils.ResponseUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/user")
public class UserInfoServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService();
    private final DepartmentService departmentService = new DepartmentService();
    private final NodeService nodeService = new NodeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException{
        String uid=req.getParameter("uid");
        String eid=req.getParameter("eid");
        Employee employee = employeeService.selectById(Long.parseLong(eid));
        Department department = departmentService.selectById(employee.getDepartmentId());
        List<Node> nodes =nodeService.selectNodeByUserId(Long.parseLong(uid));
        List<Map<String,Object>> treeList = new ArrayList<>();
        Map<String,Object> module=null;
        for(Node node : nodes){
            if(node.getNodeType()==1){
                module=new LinkedHashMap<>();
                module.put("node",node);
                module.put("children",new ArrayList<>());
                treeList.add(module);
            }else if(node.getNodeType()==2){
                assert module!=null;
                List<Node> children = (List<Node>) module.get("children");
                children.add(node);
            }
        }
        String json = new ResponseUtils().put("employees",employee)
                .put("department",department)
                .put("nodeList",treeList).toJsonString();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException{
        this.doGet(req, resp);
    }
}
