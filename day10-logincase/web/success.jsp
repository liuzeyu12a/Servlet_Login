<%--
  Created by IntelliJ IDEA.
  User: lzy
  Date: 2020/3/4
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<h1>恭喜<%= request.getSession().getAttribute("user")%>，登录成功！！</h1>
</body>
</html>
