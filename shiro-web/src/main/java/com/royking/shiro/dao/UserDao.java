package com.royking.shiro.dao;

import com.royking.shiro.entity.User;

import java.util.List;

/**
 * @author YIN
 * @Title: UserDao
 * @Package com.royking.shiro.dao
 * @Description:
 * @date 2018/10/710:38
 */
public interface UserDao {
    User getUserByUserName(String username);

    List<String> getRolesByUserName(String username);

    List<String> getPermissionByUserName(String username);
}
