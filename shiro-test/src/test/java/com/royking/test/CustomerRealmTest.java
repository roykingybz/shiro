package com.royking.test;

import com.royking.shiro.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author YIN
 * @Title: CustomerRealmTest
 * @Package com.royking.test
 * @Description:
 * @date 2018/10/617:57
 */
public class CustomerRealmTest {

    @Test
    public void loginTest() {
        CustomerRealm customerRealm = new CustomerRealm();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customerRealm.setCredentialsMatcher(matcher);

        // 1、新建一个SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customerRealm);

        // 2、得到主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("yin","123456");
        subject.login(token);

        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("isAuthenticated:"+isAuthenticated);

        subject.checkRole("admin");

        subject.checkPermission("user:add");

        subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
