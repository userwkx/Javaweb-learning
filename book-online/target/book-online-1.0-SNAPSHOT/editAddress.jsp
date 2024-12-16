<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="top.soft.bookonline.entity.User" %>
<%@ page import="top.soft.bookonline.service.UserService" %>
<%@ page import="top.soft.bookonline.service.impl.UserServiceImpl" %>
<%
    String userId = request.getParameter("userId");
    UserService userService = new UserServiceImpl();
    User user = userService.findRepeatUser(userId);
%>

<h2>编辑常居地址</h2>
<form action="<c:url value="/UpdateUserServlet"/>" method="post">
    <input type="hidden" name="userId" value="${user.id}">
    <label for="address">常居地址:</label>
    <input type="text" id="address" name="address" value="${user.address}" required>
    <button type="submit">保存</button>
</form>
