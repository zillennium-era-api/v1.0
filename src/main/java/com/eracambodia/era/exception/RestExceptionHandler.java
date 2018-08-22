package com.eracambodia.era.exception;

import com.eracambodia.era.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity registerUnauthorize(CustomException ex){
        Object object=ex.getRegisterUniqueFields();
        if(object!=null) {
            Response response = new Response(ex.getStatus(), ex.getRegisterUniqueFields());
            response.setMessage(ex.getMessage());
            return response.getResponseEntity("data");
        }else {
            Response response = new Response(ex.getStatus());
            response.setMessage(ex.getMessage());
            return response.getResponseEntity();
        }
    }
}
