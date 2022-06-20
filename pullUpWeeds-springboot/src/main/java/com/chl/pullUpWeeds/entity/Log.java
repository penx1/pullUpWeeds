package com.chl.pullUpWeeds.entity;


import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

@Data
public class Log {
    private int id;
    private String username;
    private String content;
    private Timestamp time;

    public Log(String username, String content, Timestamp time) {
        this.username = username;
        this.content = content;
        this.time = time;
    }

    public Log() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
