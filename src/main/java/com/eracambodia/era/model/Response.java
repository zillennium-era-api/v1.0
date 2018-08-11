package com.eracambodia.era.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

public class Response<T> {
    private T data;
    private String message;
    private int code;

    public Response(int code,T data){
        this.code=code;
        this.data=data;
        switch (code){
            case 200 : setMessage("Successful");break;
            case 401 : setMessage("Unauthorized");break;
            default : setMessage("status s'ey ke a nhop.");
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }

    public ResponseEntity<T> getResponseEntity(HttpStatus httpStatus){
        Map<String,Object> map=new HashMap<>();
        map.put("code",getCode());
        map.put("message",getMessage());
        if(getData()!=null) {
            map.put("data", getData());
        }
        return new ResponseEntity(map, httpStatus);
    }
}
