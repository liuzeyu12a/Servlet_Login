<%--
  Created by IntelliJ IDEA.
  User: lzy
  Date: 2020/3/4
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>

    <script>
        window.onload=function (ev) {
            var img = document.getElementById("checkCode");
            img.onclick=function (ev2) {
                var time = new Date().getTime();
                img.src="/day10/checkCode?"+time;
            }

            var change = document.getElementById("change");
            change.onclick=function (ev2) {
                var time = new Date().getTime();
                img.src="/day10/checkCode?"+time;
            }
        }
    </script>

    <style>
        #cc{
            color: red;
        }
        .up{
            color: red;
        }
    </style>
</head>
<body>

<form action="/day10/loginServlet" method="post">

    <h1>登录界面</h1>
    <table>
        <tr>
            <td> 账号：</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>验证码：</td>
            <td><input type="text" name="checkcode"></td>
        </tr>
        <tr>
            <td> <img src="/day10/checkCode" id="checkCode"></td>
            <td> <a href="#" id="change">看不清...换一张</a></td>
        </tr>
        <tr>
            <td ><input type="submit" value="登录"></td>
            <td><a href="./regsiter.jsp">注册</a></td>
            
        </tr>
    </table>
</form>

<div class="up"><%= request.getAttribute("up_error")==null ? "" : request.getAttribute("up_error") %></div>
<div id="cc"><%= request.getAttribute("cc_error")==null ? "" : request.getAttribute("cc_error") %></div>
<div id="su"><%= request.getSession().getAttribute("success_user")==null ? "" : request.getSession().getAttribute("success_user") %></div>
</body>
</html>
