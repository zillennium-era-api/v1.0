package com.eracambodia.era.model.api_login.request;



import javax.validation.constraints.*;

public class Login {
    @Email(message = "email not valid.")
    private String email;
    @Size(min = 8,message = "password's length required > 7")
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
