package test;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class UserDaoTest {

    @Test
    public void test() throws Exception {

        User user = new User();
        user.setUsername("liuzeyu12a");
        user.setHehe("44");
        String username = BeanUtils.getProperty(user, "username");
        //String gender1 = BeanUtils.getProperty(user, "gender");  报错：未找到属性
        //System.out.println("gender:"+gender1);
        System.out.println(username);
        System.out.println("------获取hehe的值对应到gender的值------");
        String gender2 = BeanUtils.getProperty(user, "hehe");
        System.out.println("gender:"+gender2);
    }
}
