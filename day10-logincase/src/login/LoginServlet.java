package login;


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
     1. 访问带有验证码的登录页面login.jsp + 注册页面
     2. 用户输入用户名，密码以及验证码。
         * 如果用户名和密码输入有误，跳转登录页面，提示:用户名或密码错误
         * 如果验证码输入有误，跳转登录页面，提示：验证码错误
         * 如果全部输入正确，则跳转到主页success.jsp，显示：用户名,欢迎您
 *   3. 用户输入用户名，密码以及重复密码+验证码。
         * 如果用户名和密码输入有误（有空），重新刷新注册页面，提示:注册失败! 用户名或密码不能为空！！
         * 如果验证码输入有误，跳转登录页面，提示：验证码错误！注册失败
         * 如果全部输入正确，则跳转到主页login.jsp，显示：注册成功
 *
 */
@WebServlet("/loginServlet")
public class LoginServlet  extends HttpServlet{
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
        User user = userDao.login(loginUser);

        String checkCode = req.getParameter("checkcode");

        //3.先判断验证码是否正确
        HttpSession session = req.getSession();
        String inputCode = (String)session.getAttribute("checkCode");
        //3.1删除存储在session中的验证码,如果登录成功返回，确保验证码不是同一个
        session.removeAttribute("checkCode");
        session.removeAttribute("success_user");
        //4.输入与显示的验证码相符
        if(checkCode !=null && checkCode.equalsIgnoreCase(inputCode)){
            //4.1再次判断账号和密码是否正确
            //4.1.1如果相同
            if(user != null){
                resp.setStatus(302);
                session.setAttribute("user",user.getUsername());
                resp.sendRedirect(req.getContextPath()+"/success.jsp");
            }else{  //不相同
                //跳转到登录界面，提示账号或密码错误
                req.setAttribute("up_error","账号或密码错误!");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }else{ //4.1.2不符
            //跳转到登录界面  提示验证码错误
            req.setAttribute("cc_error","验证码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
