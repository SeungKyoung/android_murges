package com.example.totproject.login;

import java.io.Serializable;

public class MbtiVO implements Serializable {

    String mbti_local;
    String member_id;
    int board_sn;
    String mbti_addr;
    int mbti_x, mbti_y;
    int mbti_tour,
            mbti_activity,
            mbti_festival,
            mbti_solo,
            mbti_couple,
            mbti_buddy,
            mbti_family,
            mbti_price,
            mbti_sd,
            mbti_io;
    int matchScore;


    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public String getMbti_local() {
        return mbti_local;
    }

    public void setMbti_local(String mbti_local) {
        this.mbti_local = mbti_local;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getBoard_sn() {
        return board_sn;
    }

    public void setBoard_sn(int board_sn) {
        this.board_sn = board_sn;
    }

    public String getMbti_addr() {
        return mbti_addr;
    }

    public void setMbti_addr(String mbti_addr) {
        this.mbti_addr = mbti_addr;
    }

    public int getMbti_x() {
        return mbti_x;
    }

    public void setMbti_x(int mbti_x) {
        this.mbti_x = mbti_x;
    }

    public int getMbti_y() {
        return mbti_y;
    }

    public void setMbti_y(int mbti_y) {
        this.mbti_y = mbti_y;
    }



    public int getMbti_tour() {
        return mbti_tour;
    }

    public void setMbti_tour(int mbti_tour) {
        this.mbti_tour = mbti_tour;
    }

    public int getMbti_activity() {
        return mbti_activity;
    }

    public void setMbti_activity(int mbti_activity) {
        this.mbti_activity = mbti_activity;
    }

    public int getMbti_festival() {
        return mbti_festival;
    }

    public void setMbti_festival(int mbti_festival) {
        this.mbti_festival = mbti_festival;
    }

    public int getMbti_solo() {
        return mbti_solo;
    }

    public void setMbti_solo(int mbti_solo) {
        this.mbti_solo = mbti_solo;
    }

    public int getMbti_couple() {
        return mbti_couple;
    }

    public void setMbti_couple(int mbti_couple) {
        this.mbti_couple = mbti_couple;
    }

    public int getMbti_buddy() {
        return mbti_buddy;
    }

    public void setMbti_buddy(int mbti_buddy) {
        this.mbti_buddy = mbti_buddy;
    }

    public int getMbti_family() {
        return mbti_family;
    }

    public void setMbti_family(int mbti_family) {
        this.mbti_family = mbti_family;
    }

    public int getMbti_price() {
        return mbti_price;
    }

    public void setMbti_price(int mbti_price) {
        this.mbti_price = mbti_price;
    }

    public int getMbti_sd() {
        return mbti_sd;
    }

    public void setMbti_sd(int mbti_sd) {
        this.mbti_sd = mbti_sd;
    }

    public int getMbti_io() {
        return mbti_io;
    }

    public void setMbti_io(int mbti_io) {
        this.mbti_io = mbti_io;
    }
}
