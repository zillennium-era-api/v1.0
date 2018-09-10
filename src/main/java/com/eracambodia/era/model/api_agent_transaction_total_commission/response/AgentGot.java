package com.eracambodia.era.model.api_agent_transaction_total_commission.response;

public class AgentGot {
    private Integer buildingId;
    private boolean isIncludeVat;
    private Double commission;
    private Double projectPrice;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Double getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(Double projectPrice) {
        this.projectPrice = projectPrice;
    }

    public boolean isIncludeVat() {
        return isIncludeVat;
    }

    public void setIncludeVat(boolean includeVat) {
        isIncludeVat = includeVat;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "AgentGot{" +
                "buildingId=" + buildingId +
                ", isIncludeVat=" + isIncludeVat +
                ", commission=" + commission +
                ", projectPrice=" + projectPrice +
                '}';
    }
}
