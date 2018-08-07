package com.eracambodia.era.controller;

import com.eracambodia.era.model.User;
import com.eracambodia.era.model.UserValidation;
import com.eracambodia.era.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @ApiOperation(value = "please input email,password")
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data=new HashMap<>();
        if(UserValidation.checkUserExistOrNot(user,userService)!=null) {
            Map<String, Object> map = new HashMap<>();

            String clientCredential = "client:123";
            String basicAuth = new String(Base64.encodeBase64(clientCredential.getBytes()));
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", "Basic " + basicAuth);
            HttpEntity<String> request = new HttpEntity<>(headers);

            String grant_type = "password";
            String email = user.getEmail();
            String password = user.getPassword();
            String url = "https://eraapi.herokuapp.com/oauth/token";
            String access_token_url = url + "?grant_type=" + grant_type + "&username=" + email + "&password=" + password;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

            map.put("user", responseEntity.getBody());
            JsonParser springParser = JsonParserFactory.getJsonParser();
            map = springParser.parseMap(responseEntity.getBody());
            data.put("code", "200");
            data.put("message", "Successful");
            data.put("data", map);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }else {
            data.put("code","401");
            data.put("message","email or password not match");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

    }

    @ApiOperation(value = "please input username,password,email,idcard,phonenumber,gender,dob")
    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody User user){
        Map<String,Object> data=new HashMap<>();
        if(UserValidation.checkUserFields(user)!=null && UserValidation.checkUserExistOrNot(user,userService)==null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            data.put("data", user);
            data.put("code", "200");
            data.put("message", "Successful");
            userService.register(user);
            return new ResponseEntity<>(data,HttpStatus.OK);
        }else {
            data.put("code","401");
            data.put("message","user already exits or field is invalid.");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
    }

}
