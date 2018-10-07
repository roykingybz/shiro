package com.royking.shiro.realm;

import com.royking.shiro.dao.UserDao;
import com.royking.shiro.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author YIN
 * @Title: CustomerRealm
 * @Package com.royking.shiro
 * @Description:
 * @date 2018/10/617:41
 */
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;


    {
        super.setName("CustomerRealm");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();

        // 查询数据库得到角色集合
        Set<String> setRole = getRolesByUserName(username);

        // 查询数据库得到权限集合
        Set<String> setP = getPermissionByUserName(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(setP);
        simpleAuthorizationInfo.setRoles(setRole);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟查询数据库权限集合
     * @param username
     * @return
     */
    private Set<String> getPermissionByUserName(String username) {
        List<String> list = userDao.getPermissionByUserName(username);
        Set<String> setP = new HashSet<String>(list);
        return setP;
    }

    /**
     * 模拟查询数据库角色集合
     * @param username
     * @return
     */
    private Set<String> getRolesByUserName(String username) {
        System.out.println("从数据库中取角色");
        List<String> list = userDao.getRolesByUserName(username);
        Set<String> setRole = new HashSet<String>(list);
        return setRole;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String username = (String) authenticationToken.getPrincipal();
        // 通过数据库查询密码
        String password = getPassWordByUserName(username);

        if (password == null) {
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,getName());
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟从数据库中查询用户密码
     * @return
     */
    private String getPassWordByUserName(String username) {
        User user = userDao.getUserByUserName(username);
        if (user == null) {
            return null;
        }
        return user.getPassword();
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","yin");
        System.out.println(md5Hash);
    }
}
