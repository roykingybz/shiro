package com.royking.shiro.dao.impl;

import com.royking.shiro.dao.UserDao;
import com.royking.shiro.entity.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author YIN
 * @Title: UserDaoImpl
 * @Package com.royking.shiro.dao.impl
 * @Description:
 * @date 2018/10/710:38
 */
@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public User getUserByUserName(String username) {
        String sql = "select user_name,pass_word from user where user_name = ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<User>() {
            @Nullable
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("pass_word"));
                return user;
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public List<String> getRolesByUserName(String username) {

        String sql = "select role_name from user_role where user_name = ?";
        List<String> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Nullable
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return  resultSet.getString("role_name");
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    public List<String> getPermissionByUserName(String username) {
        String sql = "select permission_text from role_permission where user_name = ?";
        List<String> list = jdbcTemplate.query(sql, new String[]{username}, new RowMapper<String>() {
            @Nullable
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return  resultSet.getString("permission_text");
            }
        });
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }
}
