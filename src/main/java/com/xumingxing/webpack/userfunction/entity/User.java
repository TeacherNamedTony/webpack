package com.xumingxing.webpack.userfunction.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2914:24
 * Description: Version 1.0
 * Location: webpack
 */
@Component
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Field
    private int type;

    @Field
    private String content;

    @Field
    private LocalDateTime operateTime;

    @Field
    private int delete = 0;

    public User() {
    }

    public User(String id, int type, String content, LocalDateTime operateTime, int delete) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.operateTime = operateTime;
        this.delete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "user{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", operateTime=" + operateTime +
                ", delete=" + delete +
                '}';
    }
}
