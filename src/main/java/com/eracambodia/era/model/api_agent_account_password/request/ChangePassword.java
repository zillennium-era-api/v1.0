package com.eracambodia.era.model.api_agent_account_password.request;

import javax.validation.constraints.*;

public class ChangePassword {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String oldPassword;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
