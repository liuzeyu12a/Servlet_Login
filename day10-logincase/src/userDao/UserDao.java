package userDao;


import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtil;

import java.util.List;

/**
 * 操作数据库的User表
 */
public class UserDao {
    //使用JDBCTemplate对象公用
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());
    /**
     * 登录方法
     * @param loginUser  只用用户名和密码
     * @return  User包含用户全部数据
     */
    public User login(User loginUser){

        //1.编写sql语句
        /**
         * 注意：数据库的实体类属性一定要和查询的字段名称一一对应，因为查询到后要创建实体类对象
         * 如果对于不上的话，将不一致的属性将被赋值为null
         */
        try{
            String sql = "select * from login_user where username= ? and password= ? ";
            User user = jdbcTemplate.
                    queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                            loginUser.getUsername(), loginUser.getPassword());
            return user;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 注册用户
     * @param username
     * @param password
     */
    public User regsiter(String username,String password){
        if(username != null && !username.equals("") && password != null && !password.equals("")){
            String sql_query = "SELECT * from login_user where username= ?";
            List<User> query = jdbcTemplate.query(sql_query, new BeanPropertyRowMapper<User>(User.class), username);
            if( query.size() > 0){
                return null;   //查到数据库中已经存在的用户名
            }

            //没重复部分
            String sql = "insert into login_user(username,password)value(?,?)";
            int zs = jdbcTemplate.update(sql, username,password);
            System.out.println(zs);

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }
            return null;
    }
}
