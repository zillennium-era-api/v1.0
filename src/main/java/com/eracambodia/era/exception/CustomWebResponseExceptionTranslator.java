package com.eracambodia.era.exception;

import com.eracambodia.era.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        Response response = new Response(401);
        response.setMessage(e.getMessage());
        return response.getResponseEntity();
    }
}
