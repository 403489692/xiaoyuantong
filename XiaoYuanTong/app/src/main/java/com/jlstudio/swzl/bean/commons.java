package com.jlstudio.swzl.bean;

import java.io.Serializable;

/**
 * Created by Joe on 2015/10/27.
 */
public class commons implements Serializable {
    private int id_commons;

    public int getId_commons() {
        return id_commons;
    }

    public void setId_commons(int id_commons) {
        this.id_commons = id_commons;
    }

    private String content;
    private String nickname;
    private String time;
    public commons() {
    }
    public commons(String nickname, String content, String time) {
        this.nickname = nickname;
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
