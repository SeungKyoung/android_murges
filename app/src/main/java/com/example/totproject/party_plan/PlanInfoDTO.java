package com.example.totproject.party_plan;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanInfoDTO implements Serializable {

    private String plandetail_day;
    private String plandetail_time;
    private int plan_sn;
    private int plandetail_sn;
   private String plandetail_content;
    private String plandetail_content_detail;
    private ArrayList<PlanInfoDTO> subList ;

    public ArrayList<PlanInfoDTO> getSubList() {
        return subList;
    }

    public void setSubList() {
        this.subList = new ArrayList<>();
    }

    public PlanInfoDTO() {
    }

    public PlanInfoDTO(int plandetail_sn) {
        this.plandetail_sn = plandetail_sn;
    }

    public PlanInfoDTO(String plandetail_date, String plandetail_time, int plan_sn, String plandetail_content, String plandetail_content_detail) {
        this.plandetail_day = plandetail_date;
        this.plandetail_time = plandetail_time;
        this.plan_sn = plan_sn;
        this.plandetail_content = plandetail_content;
        this.plandetail_content_detail = plandetail_content_detail;
    }

    public int getPlandetail_sn() {
        return plandetail_sn;
    }

    public void setPlandetail_sn(int plandetail_sn) {
        this.plandetail_sn = plandetail_sn;
    }

    public String getPlandetail_date() {
        return plandetail_day;
    }

    public void setPlandetail_date(String plandetail_day) {
        this.plandetail_day = plandetail_day;
    }

    public String getPlandetail_time() {
        return plandetail_time;
    }

    public void setPlandetail_time(String plandetail_time) {
        this.plandetail_time = plandetail_time;
    }

    public int getPlan_sn() {
        return plan_sn;
    }

    public void setPlan_sn(int plan_sn) {
        this.plan_sn = plan_sn;
    }

    public String getPlandetail_content() {
        return plandetail_content;
    }

    public void setPlandetail_content(String plandetail_content) {
        this.plandetail_content = plandetail_content;
    }

    public String getPlandetail_content_detail() {
        return plandetail_content_detail;
    }

    public void setPlandetail_content_detail(String plandetail_content_detail) {
        this.plandetail_content_detail = plandetail_content_detail;
    }
}
