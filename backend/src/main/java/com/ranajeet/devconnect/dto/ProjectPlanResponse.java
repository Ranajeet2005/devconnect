package com.ranajeet.devconnect.dto;

public class ProjectPlanResponse {

    private String plan;

    public ProjectPlanResponse() {
    }

    public ProjectPlanResponse(String plan) {
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}