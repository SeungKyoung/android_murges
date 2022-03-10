package com.example.totproject.party_plan;

import java.io.Serializable;

    public class PlanlistDTO implements Serializable {
        private int plan_sn;
        private String picture_filepath;
        private int party_sn;
        private String plan_name;
        private String plan_writer;
        private String plan_startdate;
        private String plan_enddate;
        private String plan_location;
        private String plan_startpoint;
        private String plan_hotel;
        private String plan_cost;
        private String member_id;
        private String plan_starttime;
        private String plan_endtime;

        public PlanlistDTO(int plan_sn, String picture_filepath, int party_sn, String plan_name, String plan_writer, String plan_startdate, String plan_enddate, String plan_location, String plan_startpoint, String plan_hotel, String plan_cost, String member_id, String plan_starttime, String plan_endtime) {
            this.plan_sn = plan_sn;
            this.picture_filepath = picture_filepath;
            this.party_sn = party_sn;
            this.plan_name = plan_name;
            this.plan_writer = plan_writer;
            this.plan_startdate = plan_startdate;
            this.plan_enddate = plan_enddate;
            this.plan_location = plan_location;
            this.plan_startpoint = plan_startpoint;
            this.plan_hotel = plan_hotel;
            this.plan_cost = plan_cost;
            this.member_id = member_id;
            this.plan_starttime = plan_starttime;
            this.plan_endtime = plan_endtime;
        }

        public int getPlan_sn() {
            return plan_sn;
        }

        public void setPlan_sn(int plan_sn) {
            this.plan_sn = plan_sn;
        }

        public String getPicture_filepath() {
            return picture_filepath;
        }

        public void setPicture_filepath(String picture_filepath) {
            this.picture_filepath = picture_filepath;
        }

        public int getParty_sn() {
            return party_sn;
        }

        public void setParty_sn(int party_sn) {
            this.party_sn = party_sn;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getPlan_writer() {
            return plan_writer;
        }

        public void setPlan_writer(String plan_writer) {
            this.plan_writer = plan_writer;
        }

        public String getPlan_startdate() {
            return plan_startdate;
        }

        public void setPlan_startdate(String plan_startdate) {
            this.plan_startdate = plan_startdate;
        }

        public String getPlan_enddate() {
            return plan_enddate;
        }

        public void setPlan_enddate(String plan_enddate) {
            this.plan_enddate = plan_enddate;
        }

        public String getPlan_location() {
            return plan_location;
        }

        public void setPlan_location(String plan_location) {
            this.plan_location = plan_location;
        }

        public String getPlan_startpoint() {
            return plan_startpoint;
        }

        public void setPlan_startpoint(String plan_startpoint) {
            this.plan_startpoint = plan_startpoint;
        }

        public String getPlan_hotel() {
            return plan_hotel;
        }

        public void setPlan_hotel(String plan_hotel) {
            this.plan_hotel = plan_hotel;
        }

        public String getPlan_cost() {
            return plan_cost;
        }

        public void setPlan_cost(String plan_cost) {
            this.plan_cost = plan_cost;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getPlan_starttime() {
            return plan_starttime;
        }

        public void setPlan_starttime(String plan_starttime) {
            this.plan_starttime = plan_starttime;
        }

        public String getPlan_endtime() {
            return plan_endtime;
        }

        public void setPlan_endtime(String plan_endtime) {
            this.plan_endtime = plan_endtime;
        }
}


