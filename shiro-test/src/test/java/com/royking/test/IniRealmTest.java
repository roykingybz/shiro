package com.royking.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author YIN
 * @Title: IniRealmTest
 * @Package com.royking.test
 * @Description:
 * @date 2018/10/616:43
 */
public class IniRealmTest {

    @Test
    public void loginTest() {

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        // 1、新建一个SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2、得到主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("yin","123");
        subject.login(token);

        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("isAuthenticated:"+isAuthenticated);

        subject.checkRole("admin");

        subject.checkPermission("user:delete");

        subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
