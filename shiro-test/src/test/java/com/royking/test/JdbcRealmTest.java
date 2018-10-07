package com.royking.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author YIN
 * @Title: JdbcRealmTest
 * @Package com.royking.test
 * @Description:
 * @date 2018/10/616:48
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://192.168.241.128:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

    }

    @Test
    public void loginTest() {

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql = "select pass_word from user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String preSql = "select permission_text from role_permission where role_name = ?";
        jdbcRealm.setPermissionsQuery(preSql);

        // 1、新建一个SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2、得到主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("yin","123456");
        subject.login(token);

        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("isAuthenticated:"+isAuthenticated);

        subject.checkRole("admin");

        subject.checkPermission("select");

        subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
