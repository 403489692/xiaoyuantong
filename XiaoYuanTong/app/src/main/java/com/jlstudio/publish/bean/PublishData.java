package com.jlstudio.publish.bean;

import java.io.Serializable;

/**
 * Created by gzw on 2015/10/16.
 */
public class PublishData implements Serializable{
    private String title;
    private String time;
    private String id;
    private String content;
    private String replyedCount;
    private String unReplyCount;
    private String flag;

    public PublishData() {
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public PublishData(String title, String time,String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }
    public PublishData(String title, String time,String content,String id) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.id = id;
    }

    public PublishData(String title, String time, String content, String replyedCount, String unReplyCount) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.replyedCount = replyedCount;
        this.unReplyCount = unReplyCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyedCount() {
        return replyedCount;
    }

    public void setReplyedCount(String replyedCount) {
        this.replyedCount = replyedCount;
    }

    public String getUnReplyCount() {
        return unReplyCount;
    }

    public void setUnReplyCount(String unReplyCount) {
        this.unReplyCount = unReplyCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
