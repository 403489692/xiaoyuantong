package com.jlstudio.main.bean;

/**
 * Created by gzw on 2015/10/30.
 */
public class User {
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String classname;
    private String departmentname;

    public User(String username, String password, String nickname, String role, String classname, String departmentname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.classname = classname;
        this.departmentname = departmentname;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClassid() {
        return classname;
    }

    public void setClassid(String classid) {
        this.classname = classid;
    }

    public String getDepartmentid() {
        return departmentname;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentname = departmentid;
    }
}
