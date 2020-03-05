import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        //2.1
        g.setColor(Color.BLUE);
        g.drawRect(0,0,width-1,heigh-1);   //边框默认占用1px

        //2.3生成随机验证码
        Random random = new Random();
        String checkcode = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int j = 1; j <= 4; j++) {
            //2.3.1生成随机标
            int index = random.nextInt(checkcode.length());
            //2.3.2定位
            char c = checkcode.charAt(index);
            //2.3.3 在图上画出
            g.drawString(c+"",width/5*j,heigh/2);
        }

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

        //3.将图片输出到页面展示
        ImageIO.write(img,"jpg",resp.getOutputStream());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
