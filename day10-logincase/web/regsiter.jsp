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
    <title>regsiter</title>

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

<script>
    function check() {
        var password = document.getElementById("password").value;
        var repassword = document.getElementById("repassword").value;
        if(password == repassword){
            return true;
        }else{
            window.alert("两次密码不同，请重新输入");
            return false;
        }
    }

</script>

<form action="/day10/regsiterServlet" method="post">

    <h1>注册界面</h1>
    <table>
        <tr>
            <td> 账号：</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
        <tr>
            <td>再次输入密码：</td>
            <td><input type="password" name="passpword2" id="repassword"></td>
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
            <td colspan="2"><input type="submit" value="注册" onclick="return check()"></td>
        </tr>
    </table>
</form>


<div class="up"><%= request.getAttribute("regsiter_error")==null ? "" : request.getAttribute("regsiter_error") %></div>
<div class="up"><%= request.getAttribute("rgs_error")==null ? "" : request.getAttribute("rgs_error") %></div>


</body>
</html>
