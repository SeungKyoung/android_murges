package com.example.totproject.common.VO;

import java.io.Serializable;

public class BoardCommonVO implements Serializable {
    private int board_sn, board_read_cnt, board_reviewepath;
    private String board_title, board_content, board_is_updated, board_private, board_replly_able, board_class, member_id;
    private String board_date_create, picture_filepath, member_nick;
    private int picture_file_count;
    private int board_cnt_reply;
    private int function_like;
    private int list_cnt_many;
    private int matchScore;
    private String member_grade; //글쓴이등급
    private int whose_cnt_like;

    public int getWhose_cnt_like() {
        return whose_cnt_like;
    }

    public void setWhose_cnt_like(int whose_cnt_like) {
        this.whose_cnt_like = whose_cnt_like;
    }

    public String getMember_grade() {
        return member_grade;
    }

    public void setMember_grade(String member_grade) {
        this.member_grade = member_grade;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }


    public int getList_cnt_many() {
        return list_cnt_many;
    }

    public void setList_cnt_many(int list_cnt_many) {
        this.list_cnt_many = list_cnt_many;
    }

    public int getBoard_sn() {
        return board_sn;
    }

    public void setBoard_sn(int board_sn) {
        this.board_sn = board_sn;
    }

    public int getBoard_read_cnt() {
        return board_read_cnt;
    }

    public void setBoard_read_cnt(int board_read_cnt) {
        this.board_read_cnt = board_read_cnt;
    }

    public int getBoard_reviewepath() {
        return board_reviewepath;
    }

    public void setBoard_reviewepath(int board_reviewepath) {
        this.board_reviewepath = board_reviewepath;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_is_updated() {
        return board_is_updated;
    }

    public void setBoard_is_updated(String board_is_updated) {
        this.board_is_updated = board_is_updated;
    }

    public String getBoard_private() {
        return board_private;
    }

    public void setBoard_private(String board_private) {
        this.board_private = board_private;
    }

    public String getBoard_replly_able() {
        return board_replly_able;
    }

    public void setBoard_replly_able(String board_replly_able) {
        this.board_replly_able = board_replly_able;
    }

    public String getBoard_class() {
        return board_class;
    }

    public void setBoard_class(String board_class) {
        this.board_class = board_class;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBoard_date_create() {
        return board_date_create;
    }

    public void setBoard_date_create(String board_date_create) {
        this.board_date_create = board_date_create;
    }

    public String getPicture_filepath() {
        return picture_filepath;
    }

    public void setPicture_filepath(String picture_filepath) {
        this.picture_filepath = picture_filepath;
    }

    public int getPicture_file_count() {
        return picture_file_count;
    }

    public void setPicture_file_count(int picture_file_count) {
        this.picture_file_count = picture_file_count;
    }

    public int getBoard_cnt_reply() {
        return board_cnt_reply;
    }

    public void setBoard_cnt_reply(int board_cnt_reply) {
        this.board_cnt_reply = board_cnt_reply;
    }

    public int getFunction_like() {
        return function_like;
    }

    public void setFunction_like(int function_like) {
        this.function_like = function_like;
    }

    public String getMember_nick() {
        return member_nick;
    }

    public void setMember_nick(String member_nick) {
        this.member_nick = member_nick;
    }
}