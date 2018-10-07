package com.royking.shiro;

import com.sun.xml.internal.ws.wsdl.parser.MemberSubmissionAddressingWSDLParserExtension;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author YIN
 * @Title: CustomerRealm
 * @Package com.royking.shiro
 * @Description:
 * @date 2018/10/617:41
 */
public class CustomerRealm extends AuthorizingRealm {

    Map<String,Object> map = new HashMap<String, Object>(16);

    {
        map.put("yin","68f69ec2c8225c01f8401948e878e336");
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
        Set<String> setP = new HashSet<String>();
        setP.add("user:delete");
        setP.add("user:add");
        return setP;
    }

    /**
     * 模拟查询数据库角色集合
     * @param username
     * @return
     */
    private Set<String> getRolesByUserName(String username) {
        Set<String> setRole = new HashSet<String>();
        setRole.add("admin");
        setRole.add("user");
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
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("yin"));
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟从数据库中查询用户密码
     * @return
     */
    private String getPassWordByUserName(String username) {
        return (String) map.get(username);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","yin");
        System.out.println(md5Hash);
    }
}
