package com.eracambodia.era.model.api_agent_transaction_total_commission.response;

import java.text.DecimalFormat;

public class AgentCommission {
    private Integer count;
    private Double buildingCompleted;
    private Integer fromYear;
    private Integer toYear;
    private Double agentGot;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getBuildingCompleted() {
        return buildingCompleted;
    }

    public void setBuildingCompleted(Double buildingCompleted) {
        DecimalFormat df2 = new DecimalFormat(".##");
        Double amount = Double.parseDouble(df2.format(buildingCompleted));
        this.buildingCompleted = amount;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public Double getAgentGot() {
        return agentGot;
    }

    public void setAgentGot(Double agentGot) {
        DecimalFormat df2 = new DecimalFormat(".##");
        Double amount = Double.parseDouble(df2.format(agentGot));
        this.agentGot = amount;
    }

    @Override
    public String toString() {
        return "api_agent_transaction_total_commission{" +
                "count=" + count +
                ", buildingCompleted=" + buildingCompleted +
                ", fromYear=" + fromYear +
                ", toYear=" + toYear +
                ", agentGot=" + agentGot +
                '}';
    }
}
