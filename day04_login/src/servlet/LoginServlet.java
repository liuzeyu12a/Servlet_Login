package servlet;

import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import userDao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet  extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");

//        //2.获取请求参数
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        //3.创建User对象
//        User loginUser = new User();
//        //4.封装User对象
//        loginUser.setUsername(username);
//        loginUser.setPassword(password);

        //以上注释部分可以使用BeanUtils替换，如下
        Map<String, String[]> map = req.getParameterMap();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser,map);  //populate封装
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用userDao方法
        UserDao dao = new UserDao();
        User user = dao.login(loginUser);

        //判断数据库有没有查找到
        if(user == null){
            //向FaliServlet转发消息
            req.getRequestDispatcher("/failServlet").forward(req,resp);
        }else{
            //发送共享消息
            req.setAttribute("user",user);
            req.getRequestDispatcher("/successServlet").forward(req,resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
