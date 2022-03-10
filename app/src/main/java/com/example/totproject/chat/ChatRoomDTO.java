package com.example.totproject.chat;

import java.io.Serializable;

public class ChatRoomDTO implements Serializable {
    private String msg;
    private String nickname;
    private String date;
    private String picture_filepath;

    public String getPicture_filepath() {
        return picture_filepath;
    }

    public void setPicture_filepath(String picture_filepath) {
        this.picture_filepath = picture_filepath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
