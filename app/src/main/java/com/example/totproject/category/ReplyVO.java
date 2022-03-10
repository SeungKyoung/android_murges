package com.example.totproject.category;

import java.io.Serializable;

public class ReplyVO implements Serializable {
    private int reply_sn;
    private String reply_content;
    private int board_sn;
    private String member_id, member_nick;
    private String reply_writedate;
    private int picture_file_count;
    private String picture_filepath;

    public int getReply_sn() {
        return reply_sn;
    }

    public void setReply_sn(int reply_sn) {
        this.reply_sn = reply_sn;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public int getBoard_sn() {
        return board_sn;
    }

    public void setBoard_sn(int board_sn) {
        this.board_sn = board_sn;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getReply_writedate() {
        return reply_writedate;
    }

    public void setReply_writedate(String reply_writedate) {
        this.reply_writedate = reply_writedate;
    }

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }

    public int getPicture_file_count() {
        return picture_file_count;
    }

    public void setPicture_file_count(int picture_file_count) {
        this.picture_file_count = picture_file_count;
    }

	public String getPicture_filepath() {
		return picture_filepath;
	}

	public void setPicture_filepath(String picture_filepath) {
		this.picture_filepath = picture_filepath;
	}
    
    
}
