package com.example.totproject.common.VO;

public class PictureVO {
	String picture_filepath;
	int board_sn, reply_sn;
	String member_id;
	public String getPicture_filepath() {
		return picture_filepath;
	}

	public void setPicture_filepath(String picture_filepath) {
		this.picture_filepath = picture_filepath;
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

	public int getReply_sn() {
		return reply_sn;
	}

	public void setReply_sn(int reply_sn) {
		this.reply_sn = reply_sn;
	}
}
