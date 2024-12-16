<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: 27596
  Date: 2024/11/23
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>在线用户列表</title>
</head>
<body>
<h2>在线用户列表</h2>
<%
    Map<String,Object> sessionMap =(Map<String,Object>) application.getAttribute("sessionMap");
    response.setIntHeader("Refresh",5);
%>

<ul>
    <%
        for (Map.Entry<String,Object> entry : sessionMap.entrySet()){

    %>

    <li>
        <%=entry.getValue()%>

    </li>
    <%
        }
    %>
</ul>
</body>
</html>