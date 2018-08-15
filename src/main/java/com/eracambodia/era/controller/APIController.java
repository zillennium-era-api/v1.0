package com.eracambodia.era.controller;

import com.eracambodia.era.model.*;
import com.eracambodia.era.model.user.swagger.ChangePassword;
import com.eracambodia.era.model.user.swagger.UpdateUserInfo;
import com.eracambodia.era.model.user.swagger.UserLogin;
import com.eracambodia.era.model.user.swagger.UserRegister;
import com.eracambodia.era.model.user.User;
import com.eracambodia.era.service.FileStorageService;
import com.eracambodia.era.service.building.BuildingService;
import com.eracambodia.era.service.user.UserService;
import com.eracambodia.era.util.UserValidation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.SecurityScheme;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FileStorageService fileStorageService;

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
                String url="https://eraapi.herokuapp.com/oauth/token";
                //String url = "http://localhost:8080/oauth/token";
                String access_token_url = url + "?grant_type=" + grant_type + "&username=" + email + "&password=" + password;
                RestTemplate restTemplate = new RestTemplate();
                //request token from /oauth/token
                ResponseEntity<String> responseEntity = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

                //oauth string body response convert to json
                JsonParser springParser = JsonParserFactory.getJsonParser();
                Map<String, Object> token = springParser.parseMap(responseEntity.getBody());

                response = new Response<Map>(200, token);
                return response.getResponseEntity();
            } else {
                response = new Response<>(401, null);
                return response.getResponseEntity();
            }
        } else {
            response = new Response(401, null);
            return response.getResponseEntity();
        }
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(@RequestBody RefreshToken refreshToken){

        String clientCredential = "client:123";
        String basicAuth = new String(Base64.encodeBase64(clientCredential.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + basicAuth);
        HttpEntity<String> request = new HttpEntity<>(headers);

        String grant_type = "refresh_token";
        String client_id="client";
        String refresh_token=refreshToken.getRefreshToken();
        String url = "http://localhost:8080/oauth/token";
        String refresh_token_url = url + "?grant_type=" + grant_type + "&client_id=" + client_id + "&refresh_token=" + refresh_token;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(refresh_token_url, HttpMethod.POST, request, String.class);

        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> token = springParser.parseMap(responseEntity.getBody());
        Response response=new Response(200,token);
        return response.getResponseEntity();

    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRegister userRegister) {
        Response response = null;
        if (UserValidation.checkUserRegisterFields(userRegister) != null) {
            User user = new User();
            user.setEmail(userRegister.getEmail());
            user.setPassword(userRegister.getPassword());
            user.setDob(userRegister.getDob());
            user.setGender(userRegister.getGender());
            user.setIdcard(userRegister.getIdcard());
            user.setPhonenumber(userRegister.getPhonenumber());
            user.setUsername(userRegister.getUsername());
            user.setUuid(UUID.randomUUID()+"");
            if (UserValidation.checkUserFields(user) != null && UserValidation.checkUserExistOrNot(user, userService) == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                response = new Response<User>(200, userService.register(user));
                return response.getResponseEntity();
            } else {
                response = new Response<>(401, null);
                return response.getResponseEntity();
            }
        } else {
            response = new Response<>(401, null);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/user")
    public ResponseEntity viewUserInformation(@ApiIgnore Principal principal){
        Response response=new Response(200,userService.findUserByEmail(principal.getName()));
        return response.getResponseEntity();
    }

    @PostMapping("/agent/profile/upload")
    public ResponseEntity uploadProfileImage(@RequestParam("file")MultipartFile file,@ApiIgnore Principal principal){
        //remove old image file
        User user=userService.findUserByEmail(principal.getName());
        if(user!=null){
            fileStorageService.deleteFile(user.getImage());
        }

        //store image in directory uploads
        String fileName=fileStorageService.storeFile(file);

        //provide url for view image
        if(fileName!=null) {
            String downloadUri=ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/user/image/")
                    .path(fileName)
                    .toUriString();
            userService.updateImageProfile(fileName, principal.getName());
            Response response = new Response(200, downloadUri);
            return response.getResponseEntity();
        }else {
            Response response = new Response(401, null);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/user/image")
    public ResponseEntity<Resource> viewImage(@ApiIgnore Principal principal,HttpServletRequest request){
        User user=userService.findUserByEmail(principal.getName());

        //load image
        Resource resource=fileStorageService.loadFileAsResource(user.getImage());

        //content type as image for view
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        //return image view on brownser
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @DeleteMapping("/user/image/delete")
    public ResponseEntity deleteImage(@ApiIgnore Principal principal){
        User user= userService.findUserByEmail(principal.getName());

        //delete image file and set column image to null
        if(fileStorageService.deleteFile(user.getImage())){
            user.setImage(null);
            userService.updateImageProfile(null,principal.getName());
            Response response=new Response(200,null);
            return  response.getResponseEntity();
        }else {
            Response response = new Response(401, null);
            return response.getResponseEntity();
        }
    }

    @PutMapping("/agent/account/password")
    public ResponseEntity updateAgentPassword(@RequestBody ChangePassword changePassword, @ApiIgnore Principal principal){
        User user=userService.findUserByEmail(principal.getName());
        if(passwordEncoder.matches(changePassword.getOldpassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
            userService.updateUserPassword(user);
            Response response=new Response(200,null);
            return response.getResponseEntity();
        }else {
            Response response=new Response(401,null);
            return response.getResponseEntity();
        }
    }

    @PutMapping("/agent/account/update")
    public ResponseEntity updateAccountInformation(@RequestBody UpdateUserInfo updateUserInfo,@ApiIgnore Principal principal){
        if(!UserValidation.checkUpdateUserInfo(updateUserInfo)) {
            User user = userService.findUserByEmail(principal.getName());
            user.setUsername(updateUserInfo.getUsername());
            user.setPhonenumber(updateUserInfo.getPhonenumber());
            userService.updateUserInformation(user);
            Response response = new Response(200, userService.findUserByEmail(principal.getName()));
            return response.getResponseEntity();
        }else{
            Response response = new Response(401, null);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/building")
    public ResponseEntity buildings(@RequestParam(value = "page",defaultValue = "1")int page){
        int count=buildingService.countBuildingRecord();
        Pagination pagination=new Pagination(page,10,count);

        List<Building> buildings=buildingService.findBuildings(pagination);

        if(buildings!=null && buildings.size()>0) {
            Response response = new Response(200, buildings,pagination);
            return response.getResponseEntity();
        }else {
            Response response = new Response(404, null);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/building/{uuid}")
    public ResponseEntity buildingDetail(@PathVariable("uuid")String uuid){
        Building building=buildingService.findBuildingByUUID(uuid);
        if(building!=null) {
            Response response = new Response(200, building);
            return response.getResponseEntity();
        }else {
            Response response = new Response(404, null);
            return response.getResponseEntity();
        }
    }

    @PostMapping("/building/status/update")
    public ResponseEntity updateBuildingFromAvailableToHold(@RequestBody BuildingStatusUpdate buildingStatusUpdate){
        if(buildingService.findBuildingIdById(buildingStatusUpdate.getOwnerId())!=null){
            buildingService.updateBuildingStatus(buildingStatusUpdate);
            Response response=new Response(200,null);
            return response.getResponseEntity();
        }
        else {
            Response response=new Response(404,null);
            return response.getResponseEntity();
        }
    }
}