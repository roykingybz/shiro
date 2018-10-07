package com.royking.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author YIN
 * @Title: AuthenticationTest
 * @Package src.test.java.com.royking.test
 * @Description:
 * @date 2018/10/615:37
 */
public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("yin","123","admin");
    }

    @Test
    public void loginTest() {

        // 1、新建一个SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // 2、得到主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("yin","123");
        subject.login(token);

        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("isAuthenticated:"+isAuthenticated);

        subject.checkRole("admin");

        subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
