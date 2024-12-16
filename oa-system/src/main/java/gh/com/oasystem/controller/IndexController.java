package gh.com.oasystem.controller;

import gh.com.oasystem.model.entity.Department;
import gh.com.oasystem.model.entity.Employee;
import gh.com.oasystem.model.entity.Node;
import gh.com.oasystem.service.DepartmentService;
import gh.com.oasystem.service.NodeService;
import gh.com.oasystem.service.UserService;
import gh.com.oasystem.service.EmployeeService;
import gh.com.oasystem.service.impl.DepartmentServiceImpl;
import gh.com.oasystem.service.impl.EmployeeServiceImpl;
import gh.com.oasystem.service.impl.NodeServiceImpl;
import gh.com.oasystem.service.impl.UserServiceImpl;
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

@WebServlet("/api/index")
public class IndexController extends HttpServlet {
    private UserService userService;
    private NodeService nodeService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        nodeService = new NodeServiceImpl();
        userService = new UserServiceImpl();
        employeeService = new EmployeeServiceImpl();
        departmentService = new DepartmentServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        ResponseUtils result = new ResponseUtils();
        String uid = req.getParameter("uid");
        String eid = req.getParameter("eid");
        System.out.println("uid:" + uid);
        System.out.println("eid:" + eid);
        Long roleId = userService.queryRoleIdByUserId(Long.parseLong(uid));
        List<Node> allNode = nodeService.queryAllNode(roleId);
        List<Map<String, Object>> nodeList = new ArrayList<>();
        Map<String, Object> NodeMap = new HashMap<>();

        for (Node node : allNode) {
            if (node.getParentId() == null) {
                NodeMap = new HashMap<>();
                NodeMap.put("node", node);
                NodeMap.put("children", new ArrayList<Node>());
                nodeList.add(NodeMap);
            } else {
                List<Node> nodes = (List<Node>) NodeMap.get("children");
                nodes.add(node);
            }

        }
        System.out.println(Long.parseLong(uid));
        Long employeeId=employeeService.queryEmployeeIdByUserId(Long.parseLong(uid));
        Employee employee = employeeService.queryEmployeeById(employeeId);
        System.out.println("employee:" + employee);
        Long departmentId=departmentService.queryDepartmentIdByEmployeeId(employeeId);
        Department department=departmentService.queryDepartmentById(departmentId);
        result=new ResponseUtils().put("nodeList",nodeList).put("employee",employee).put("department",department);
        System.out.println(result.toJsonString());
        resp.getWriter().write(result.toJsonString());    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


}
