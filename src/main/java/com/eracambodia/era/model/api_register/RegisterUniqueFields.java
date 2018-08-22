package com.eracambodia.era.model.api_register;

public class RegisterUniqueFields {
    private boolean phoneExist;
    private boolean emailExist;
    private boolean idCardExist;
    public RegisterUniqueFields(){
        phoneExist=false;
        phoneExist=false;
        idCardExist=false;
    }
    public boolean isPhoneExist() {
        return phoneExist;
    }

    public void setPhoneExist(boolean phoneExist) {
        this.phoneExist = phoneExist;
    }

    public boolean isEmailExist() {
        return emailExist;
    }

    public void setEmailExist(boolean emailExist) {
        this.emailExist = emailExist;
    }

    public boolean isIdCardExist() {
        return idCardExist;
    }

    public void setIdCardExist(boolean idCardExist) {
        this.idCardExist = idCardExist;
    }

    @Override
    public String toString() {
        return "RegisterUniqueFields{" +
                "phoneExist=" + phoneExist +
                ", emailExist=" + emailExist +
                ", idCardExist=" + idCardExist +
                '}';
    }
}
