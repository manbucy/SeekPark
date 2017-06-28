package net.manbucy.seekpark.model.user;

/**
 * 用户登录信息 用户名 密码 是否记住密码
 * Created by yang on 2017/6/23.
 */

public class UserLoginInfo {
    private String username;
    private String password;
    private boolean isRemember;

    public UserLoginInfo() {
    }

    public UserLoginInfo(String username, String password, boolean isRemember) {
        this.username = username;
        this.password = password;
        this.isRemember = isRemember;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRemember() {
        return isRemember;
    }
}
