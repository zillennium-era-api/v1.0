package com.eracambodia.era.model;

import org.springframework.transaction.annotation.Transactional;

public class TransactionCal {
    private Double totalCommission;
    private Double agentCommission;
    private Double leaderCommission;
    private Double companyCommission;

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Double getAgentCommission() {
        return agentCommission;
    }

    public void setAgentCommission(Double agentCommission) {
        this.agentCommission = agentCommission;
    }

    public Double getLeaderCommission() {
        return leaderCommission;
    }

    public void setLeaderCommission(Double leaderCommission) {
        this.leaderCommission = leaderCommission;
    }

    public Double getCompanyCommission() {
        return companyCommission;
    }

    public void setCompanyCommission(Double companyCommission) {
        this.companyCommission = companyCommission;
    }

    @Override
    public String toString() {
        return "TransactionCal{" +
                "totalCommission=" + totalCommission +
                ", agentCommission=" + agentCommission +
                ", leaderCommission=" + leaderCommission +
                ", companyCommission=" + companyCommission +
                '}';
    }
}
