package com.eracambodia.era.model.api_agent_account_update.request;

import javax.validation.constraints.*;

public class UpdateAgentAccount {
    private String name;
    private String phone;
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UpdateAgentAccount{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
