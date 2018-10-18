package com.eracambodia.era.exception;

import com.eracambodia.era.model.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.transform.Result;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity registerUnauthorize(CustomException ex) {
        Object object = ex.getRegisterUniqueFields();
        if (object != null) {
            Response response = new Response(ex.getStatus(), ex.getRegisterUniqueFields());
            response.setMessage(ex.getMessage());
            return response.getResponseEntity("data");
        } else {
            Response response = new Response(ex.getStatus());
            response.setMessage(ex.getMessage());
            return response.getResponseEntity();
        }
    }

    //RequestParam exception
    @Override
    protected ResponseEntity handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response=new Response(400);
        response.setMessage(ex.getMessage());
        return response.getResponseEntity();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrity(Exception ex) {
            Response response = new Response(403);
            response.setMessage(ex.getMessage());
            return response.getResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response=new Response(400);
        response.setMessage(ex.getBindingResult().toString());
        return response.getResponseEntity();
    }

}
