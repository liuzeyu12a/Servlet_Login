package regsiter;


import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import userDao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
     1. 案例需求：
     1. 访问带有验证码的登录页面login.jsp
     2. 用户输入用户名，密码以及验证码。
         * 如果用户名和密码输入有误，跳转登录页面，提示:用户名或密码错误
         * 如果验证码输入有误，跳转登录页面，提示：验证码错误
         * 如果全部输入正确，则跳转到主页success.jsp，显示：用户名,欢迎您
 */
@WebServlet("/regsiterServlet")
public class RegsiterServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1.设置编码
        resp.setCharacterEncoding("utf-8");
        //2.获取参数Map(先获取输入的参数)
        Map<String, String[]> parameterMap = req.getParameterMap();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserDao userDao = new UserDao();
        String checkCode = req.getParameter("checkcode");

        //3.先判断验证码是否正确
        HttpSession session = req.getSession();
        String inputCode = (String)session.getAttribute("checkCode");
        //3.1删除存储在session中的验证码,如果登录成功返回，确保验证码不是同一个
        session.removeAttribute("checkCode");
        req.removeAttribute("regsiter_error");
        session.removeAttribute("success_user");
        //4.输入与显示的验证码相符
        if(checkCode != null && checkCode.equalsIgnoreCase(inputCode)){
            User regsiter = userDao.regsiter(loginUser.getUsername(), loginUser.getPassword());
            if(regsiter == null){ //注册失败
                req.setAttribute("regsiter_error","注册失败! 用户名或密码不能为空！！");
                req.getRequestDispatcher("/regsiter.jsp").forward(req,resp);
            }else {
                //注册成功
                session.setAttribute("success_user","注册成功，请登录!");
                resp.sendRedirect(req.getContextPath()+"/login.jsp");
            }
        }else{
            //跳转到注册界面  提示验证码错误
            req.setAttribute("rgs_error","验证码错误！注册失败");
            req.getRequestDispatcher("/regsiter.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
