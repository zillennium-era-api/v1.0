package com.eracambodia.era.model;

import org.bouncycastle.math.ec.ScaleYPointMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Response<T> {
    private T[] data;
    private String message;
    private int code;
    private HttpStatus httpStatus;
    private String role;

    public Response(int code, T... data) {
        this.code = code;
        this.data = data;
        statusCode(code);
    }

    public T[] getData() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setData(T[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ResponseEntity<T> getResponseEntity(String... type) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", getCode());
        map.put("message", getMessage());
        if(role!=null) {
            map.put("role", getRole());
        }
        if (getData() != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                map.put(type[i], data[i]);
            }
        }
        httpStatus(getCode());
        return new ResponseEntity(map, getHttpStatus());
    }

    private void httpStatus(int code){
        switch (code) {
            case 200:
                setHttpStatus(HttpStatus.OK);
                break;
            case 201:
                setHttpStatus(HttpStatus.CREATED);
                break;
            case 400:
                setHttpStatus(HttpStatus.BAD_REQUEST);
                break;
            case 401:
                setHttpStatus(HttpStatus.UNAUTHORIZED);
                break;
            case 403:
                setHttpStatus(HttpStatus.BAD_REQUEST);
                break;
            case 404:
                setHttpStatus(HttpStatus.NOT_FOUND);
                break;
            case 409:
                setHttpStatus(HttpStatus.CONFLICT);
                break;
            case 500:
                setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                break;
        }
    }

    private void statusCode(int code){
        switch (code) {
            case 200:
                setMessage("Successful");
                break;
            case 201:
                setMessage("Create Successful");
                break;
            case 400:
                setMessage("Bad Request.");
                break;
            case 401:
                setMessage("Unauthorized");
                break;
            case 403:
                setHttpStatus(HttpStatus.BAD_REQUEST);
                break;
            case 404:
                setMessage("Not Found");
                break;
            case 409:
                setMessage("Data already exit.");
                break;
            case 500:
                setMessage("Internal server error");
                break;
            default:
                setMessage("status s'ey ke a nhop.");
        }
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + Arrays.toString(data) +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", httpStatus=" + httpStatus +
                ", role='" + role + '\'' +
                '}';
    }
}
