package com.eracambodia.era.controller;

import com.eracambodia.era.model.*;
import com.eracambodia.era.model.api_agent_account_password.request.ChangePassword;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.service.FileStorageService;
import com.eracambodia.era.service.Service;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@ControllerAdvice
@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private Service service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody Login login) {
        service.checkLogin(login);

        String clientCredential = "client:123";
        String basicAuth = new String(Base64.encodeBase64(clientCredential.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + basicAuth);
        HttpEntity<String> request = new HttpEntity<>(headers);

        String grant_type = "password";
        String email = login.getEmail();
        String password = login.getPassword();
        String url="https://eraapi.herokuapp.com/oauth/token";
        //String url = "http://localhost:8080/oauth/token";
        String access_token_url = url + "?grant_type=" + grant_type + "&username=" + email + "&password=" + password;
        RestTemplate restTemplate = new RestTemplate();
        //request token from /oauth/token
        ResponseEntity<String> responseEntity = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        //oauth string body response convert to json
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> token = springParser.parseMap(responseEntity.getBody());
        Response response = new Response<Map>(200, token);
        return  response.getResponseEntity("tokenResponse");
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
        ResponseEntity<String> responseEntity=null;
        try {
            responseEntity = restTemplate.exchange(refresh_token_url, HttpMethod.POST, request, String.class);
        }catch (HttpClientErrorException ex) {
            Response response=new Response(401);
            response.setMessage("access token not correct.");
            return response.getResponseEntity();
        }
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> token = springParser.parseMap(responseEntity.getBody());
        Response response=new Response(200,token);
        return response.getResponseEntity("refreshTokenResponse");
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Register register) {
        service.getEmail(register.getEmail());//check email available
        service.register(register);
        Response response=new Response(200);
        return response.getResponseEntity();
    }

    @GetMapping("/user")
    public ResponseEntity viewUserInformation(@ApiIgnore Principal principal){
        Response response=new Response(200,service.findUserByUsernameOfUser(principal.getName()));
        return response.getResponseEntity();
    }

    @PostMapping("/agent/profile/upload")
    public ResponseEntity uploadProfileImage(@RequestParam("file")MultipartFile file,@ApiIgnore Principal principal){
        //remove old image file
        String imagePath=service.findImageByUsernameOfUploadProfileAgent(principal.getName());
        if(imagePath!=null){
            fileStorageService.deleteFile(imagePath);
        }

        //store image in directory uploads
        String fileName=fileStorageService.storeFile(file);

        //provide url for view image
        if(fileName!=null) {
            String downloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/user/image/")
                    .path(fileName)
                    .toUriString();
            service.updateImageProfileOfUploadProfileAgent(fileName, principal.getName());
            Response response = new Response(200, downloadUri);
            return response.getResponseEntity();
        }else {
            Response response = new Response(401);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/user/image")
    public ResponseEntity<Resource> viewImage(@ApiIgnore Principal principal,HttpServletRequest request){

        String imagePath=service.findImageByUsernameOfUploadProfileAgent(principal.getName());

        //load image
        Resource resource=fileStorageService.loadFileAsResource(imagePath);

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
        String imagePath=service.findImageByUsernameOfUploadProfileAgent(principal.getName());

        //delete image file and set column image to null
        if(fileStorageService.deleteFile(imagePath)){
            service.updateImageProfileOfUploadProfileAgent(null, principal.getName());
            Response response=new Response(200,null);
            return  response.getResponseEntity();
        }else {
            Response response = new Response(401, null);
            return response.getResponseEntity();
        }
    }

    @PutMapping("/agent/account/password")
    public ResponseEntity updateAgentPassword(@RequestBody ChangePassword changePassword, @ApiIgnore Principal principal){
        String password=service.getUserPasswordByEmail(principal.getName());
        if(passwordEncoder.matches(changePassword.getOldPassword(),password)){
            String resetPassword=passwordEncoder.encode(changePassword.getNewPassword());
            service.updateUserPassword(resetPassword,principal.getName());
            Response response=new Response(200,null);
            return response.getResponseEntity();
        }else {
            Response response=new Response(401,null);
            return response.getResponseEntity();
        }
    }

    @PutMapping("/agent/account/update")
    public ResponseEntity updateAccountInformation(@RequestBody UpdateAgentAccount updateAgentAccount, @ApiIgnore Principal principal){
        service.updateUserInformation(updateAgentAccount,principal.getName());
        Response response = new Response(200);
        return response.getResponseEntity();
    }

    @GetMapping("/building")
    public ResponseEntity buildings(@RequestParam(value = "page",defaultValue = "1")int page){
        int count=service.countBuildingsRecord();
        Pagination pagination=new Pagination(page,10,count);

        List<Buildings> buildings=service.findBuildings(pagination);
        if(buildings!=null && buildings.size()>0) {
            Response response = new Response(200, buildings,pagination);
            return response.getResponseEntity("building","pagination");
        }else {
            Response response = new Response(404);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/building/{uuid}")
    public ResponseEntity buildingDetail(@PathVariable("uuid")String uuid){
        BuildingUUID buildingUUID=service.findBuildingByUUID(uuid);
        if(buildingUUID!=null) {
            Response response = new Response(200, service.findBuildingByUUID(uuid));
            return response.getResponseEntity("building");
        }else {
            Response response = new Response(404);
            return response.getResponseEntity();
        }
    }

    @PostMapping("/building/status/update")
    public ResponseEntity updateBuildingFromAvailableToHold(@RequestBody BuildingStatusUpdate buildingStatusUpdate){
        if(service.findBuildingIdByIdOfBuildingStatusUpdate(buildingStatusUpdate.getOwnerId())!=null){
            service.updateBuildingStatus(buildingStatusUpdate);
            Response response=new Response(200);
            return response.getResponseEntity();
        }
        else {
            Response response=new Response(404);
            return response.getResponseEntity();
        }
    }

    @GetMapping("/building/available")
    public ResponseEntity buildingAvailable(@RequestParam(value = "page",defaultValue = "1")int page){
        Pagination pagination=new Pagination(page,10);
        List<BuildingAvailable> buildingAvailable=service.findBuildingAvailable(pagination);
        if(buildingAvailable!=null && buildingAvailable.size()>0) {
            Response response = new Response(200, buildingAvailable);
            return response.getResponseEntity();
        }else {
            Response response = new Response(404);
            return response.getResponseEntity();
        }
    }
}