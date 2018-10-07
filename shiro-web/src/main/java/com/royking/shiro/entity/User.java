package com.royking.shiro.entity;

/**
 * @author YIN
 * @Title: User
 * @Package com.royking.shiro.entity
 * @Description:
 * @date 2018/10/621:35
 */
public class User {

    private String username;
    private String password;
    private boolean rememberMe;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
