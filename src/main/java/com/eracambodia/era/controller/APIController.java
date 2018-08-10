package com.eracambodia.era.controller;

import com.eracambodia.era.model.*;
import com.eracambodia.era.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLogin userLogin) {
        Response response = null;
        if (UserValidation.checkUserLoginFields(userLogin) != null) {
            User user = new User();
            user.setEmail(userLogin.getEmail());
            user.setPassword(userLogin.getPassword());
            if (UserValidation.checkUserExistOrNot(user, userService) != null) {
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
                JsonParser springParser = JsonParserFactory.getJsonParser();
                Map<String, Object> token = springParser.parseMap(responseEntity.getBody());
                response = new Response<Map>(200, token);
                return new ResponseEntity<>(response.getResponse(), HttpStatus.OK);
            } else {
                response = new Response<>(401, null);
                return new ResponseEntity<>(response.getResponse(), HttpStatus.UNAUTHORIZED);
            }
        } else {
            response = new Response(401, null);
            return new ResponseEntity<>(response.getResponse(), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRegister userRegister) {
        Response<User> response = null;
        if (UserValidation.checkUserRegisterFields(userRegister) != null) {
            User user = new User();
            user.setEmail(userRegister.getEmail());
            user.setPassword(userRegister.getPassword());
            user.setDob(userRegister.getDob());
            user.setGender(userRegister.getGender());
            user.setIdcard(userRegister.getIdcard());
            user.setPhonenumber(userRegister.getPhonenumber());
            user.setUsername(userRegister.getUsername());
            if (UserValidation.checkUserFields(user) != null && UserValidation.checkUserExistOrNot(user, userService) == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                response = new Response<User>(200, userService.register(user));
                return new ResponseEntity<Map<String, Object>>(response.getResponse(), HttpStatus.OK);
            } else {
                response = new Response<>(401, null);
                return new ResponseEntity<>(response.getResponse(), HttpStatus.UNAUTHORIZED);
            }
        } else {
            response = new Response<>(401, null);
            return new ResponseEntity<>(response.getResponse(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity userByToken(@ApiIgnore Principal principal){
        Response response=new Response(200,userService.findUserByEmail(principal.getName()));
        return new ResponseEntity(response.getResponse(),HttpStatus.OK);
    }
}