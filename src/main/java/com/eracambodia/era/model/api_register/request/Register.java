package com.eracambodia.era.model.api_register.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.UUID;

public class Register {
    @ApiModelProperty(position = 2)
    private String name;
    @ApiModelProperty(position = 4)
    private String gender;
    @ApiModelProperty(position = 7)
    private Date dateOfBirth;
    @ApiModelProperty(position = 3)
    private String phone;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 5)
    private String password;
    @ApiModelProperty(position = 6)
    private String idCard;
    @JsonIgnore
    private String uuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUuid() {
        return UUID.randomUUID() + "";
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Register{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", idCard='" + idCard + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
