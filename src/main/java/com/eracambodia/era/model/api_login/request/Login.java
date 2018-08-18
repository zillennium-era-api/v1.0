package com.eracambodia.era.model.api_login.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Login {
    @Size(max=2,message = "AAAAAAAAAAAAAAAAAAA")
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
