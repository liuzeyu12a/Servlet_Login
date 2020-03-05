package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具类使用Druid连接池
 */
public class JDBCUtil {

    private static DataSource ds;

    //加载配置文件
    static {
        try {
            //使用Properties对象
            Properties pro = new Properties();
            //使用getClassLoader获取字节输入流
            InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(is);

            //初始化连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接池对象
    public static DataSource getDataSource(){
        return ds;
    }


    //获取Connection对象
    public static Connection getConnection(DataSource ds) throws SQLException {
        return ds.getConnection();
    }

}
