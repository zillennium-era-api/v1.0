package com.eracambodia.era.model;

public class ErrorCode {
    boolean erroPhone;
    boolean erroEmail;
    boolean erroIdCard;

    public boolean isErroPhone() {
        return erroPhone;
    }

    public void setErroPhone(boolean erroPhone) {
        this.erroPhone = erroPhone;
    }

    public boolean isErroEmail() {
        return erroEmail;
    }

    public void setErroEmail(boolean erroEmail) {
        this.erroEmail = erroEmail;
    }

    public boolean isErroIdCard() {
        return erroIdCard;
    }

    public void setErroIdCard(boolean erroIdCard) {
        this.erroIdCard = erroIdCard;
    }

    @Override
    public String toString() {
        return "ErroCode{" +
                "erroPhone=" + erroPhone +
                ", erroEmail=" + erroEmail +
                ", erroIdCard=" + erroIdCard +
                '}';
    }
}
