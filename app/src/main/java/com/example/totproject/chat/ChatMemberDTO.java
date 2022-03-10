package com.example.totproject.chat;

import java.io.Serializable;

public class ChatMemberDTO implements Serializable {

    private int chat_index;
    private int member_index;
    private String picture_filepath;       //프사
    private String member_nick;         // 닉네임?
    private String chat_member_statmsg;     // ??
    private String chat_member_host;    // ??

    public int getChat_index() {
        return chat_index;
    }

    public void setChat_index(int chat_index) {
        this.chat_index = chat_index;
    }

    public int getMember_index() {
        return member_index;
    }

    public void setMember_index(int member_index) {
        this.member_index = member_index;
    }

    public String getPicture_filepath() {
        return picture_filepath;
    }

    public void setPicture_filepath(String picture_filepath) {
        this.picture_filepath = picture_filepath;
    }

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }

    public String getChat_member_statmsg() {
        return chat_member_statmsg;
    }

    public void setChat_member_statmsg(String chat_member_statmsg) {
        this.chat_member_statmsg = chat_member_statmsg;
    }

    public String getChat_member_host() {
        return chat_member_host;
    }

    public void setChat_member_host(String chat_member_host) {
        this.chat_member_host = chat_member_host;
    }
}