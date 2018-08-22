package com.eracambodia.era.exception;

import com.eracambodia.era.model.api_register.RegisterUniqueFields;

public class CustomException extends RuntimeException {
    private int status;
    private RegisterUniqueFields registerUniqueFields;
    public CustomException(int status, String message){
        super(message);
        this.status=status;
    }
    public CustomException(int status, String message, RegisterUniqueFields registerUniqueFields){
        super(message);
        this.status=status;
        this.registerUniqueFields=registerUniqueFields;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RegisterUniqueFields getRegisterUniqueFields() {
        return registerUniqueFields;
    }

    public void setRegisterUniqueFields(RegisterUniqueFields registerUniqueFields) {
        this.registerUniqueFields = registerUniqueFields;
    }
}
