package check;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int width = 100;
        int heigh = 50;

        //1.在内存中创建一验证码图片对象
        BufferedImage img = new BufferedImage(width,heigh,BufferedImage.TYPE_INT_RGB);

        //2.美化图片
        //2.1填充背景色
        Graphics g = img.getGraphics();  //获取画笔
        g.setColor(Color.PINK);          //设置颜色
        g.fillRect(0,0,width,heigh);

        //2.2设置画笔颜色
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,heigh-1);   //边框默认占用1px


        //设置随机验证码
        Random random = new Random();
        String checkcode = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 4; i++) {
            int index = random.nextInt(checkcode.length());
            char c = checkcode.charAt(index);
            sb.append(c);
            g.drawString(c+"",width/5*i,heigh/2);
        }
        //讲输入进去的验证码保存到session中
        HttpSession session = req.getSession();
        session.setAttribute("checkCode",sb.toString());

        //2.4画干扰线
        g.setColor(Color.GREEN);
        for (int i = 0; i < 10; i++) {
            //2.4.1定义起点和终点随机出现位置
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(heigh);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(heigh);
            //2.4.2 画线
            g.drawLine(x1,y1,x2,y2);
        }

        ImageIO.write(img,"jpg",resp.getOutputStream());

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
