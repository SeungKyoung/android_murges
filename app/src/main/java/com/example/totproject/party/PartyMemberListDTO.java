package com.example.totproject.party;

public class PartyMemberListDTO {
    private String member_id;
    private String picture_filepath;

    public PartyMemberListDTO(String member_id, String picture_filepath) {
        this.member_id = member_id;
        this.picture_filepath = picture_filepath;
    }

    public PartyMemberListDTO(String member_id) {
        this.member_id = member_id;
    }

    public String getMemberid() {
        return member_id;
    }

    public void setMemberid(String memberid) {
        this.member_id = memberid;
    }

    public String getPicture_filepath() {
        return picture_filepath;
    }

    public void setPicture_filepath(String picture_filepath) {
        this.picture_filepath = picture_filepath;
    }
}
