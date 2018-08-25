package com.eracambodia.era.controller;

import com.eracambodia.era.model.api_agent_account_password.request.ChangePassword;
import com.eracambodia.era.model.api_agent_account_update.request.UpdateAgentAccount;
import com.eracambodia.era.model.api_agent_favorite_add.request.AgentAddFavorite;
import com.eracambodia.era.model.api_agent_favorite_delete.request.AgentDeleteFavorite;
import com.eracambodia.era.model.api_building_available.response.BuildingAvailable;
import com.eracambodia.era.model.api_building_status_update.request.BuildingStatusUpdate;
import com.eracambodia.era.model.api_user.response.User;
import com.eracambodia.era.setting.Default;
import com.eracambodia.era.exception.CustomException;
import com.eracambodia.era.model.*;
import com.eracambodia.era.model.api_building.response.Buildings;
import com.eracambodia.era.model.api_building_uuid.response.BuildingUUID;
import com.eracambodia.era.model.api_login.request.Login;
import com.eracambodia.era.model.api_register.request.Register;
import com.eracambodia.era.service.FileStorageService;
import com.eracambodia.era.service.Service;
import com.eracambodia.era.validate.ImageValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.awt.print.Pageable;
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

        String access_token_url = Default.oauthTokenUrl + "?grant_type=" + grant_type + "&username=" + email + "&password=" + password;
        RestTemplate restTemplate = new RestTemplate();
        //request token from /oauth/token
        ResponseEntity<String> responseEntity = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        //oauth string body response convert to json
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String, Object> token = springParser.parseMap(responseEntity.getBody());
        Response response = new Response<Map>(200, token);
        return  response.getResponseEntity("data");
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

        String refresh_token_url = Default.oauthTokenUrl + "?grant_type=" + grant_type + "&client_id=" + client_id + "&refresh_token=" + refresh_token;
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
        register.setPassword(passwordEncoder.encode(register.getPassword()));
        service.register(register);
        Response response=new Response(201);
        return response.getResponseEntity();
    }

    @GetMapping("/user")
    public ResponseEntity viewUserInformation(@ApiIgnore Principal principal){
        User user=service.findUserByUsernameOfUser(principal.getName());
        Response response=new Response(200,user);
        return response.getResponseEntity("data");
    }

    @PostMapping("/agent/profile/upload")
    public ResponseEntity uploadProfileImage(@RequestParam("file")MultipartFile file,@ApiIgnore Principal principal) {
        ImageValidator imageValidator=new ImageValidator();
        boolean checkImageExtenstion=imageValidator.validate(file.getOriginalFilename());
        if(!checkImageExtenstion){
            throw new CustomException(401,"File type invalid");
        }
        //remove old image file
        String imagePath=service.findImageByUsernameOfUploadProfileAgent(principal.getName());
        if(imagePath!=null){
            fileStorageService.deleteFile(imagePath);
        }

        //store image in directory uploads
        String fileName=fileStorageService.storeFile(file);

        //provide url for view image
        String downloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user/image/")
                .path(fileName)
                .toUriString();

        service.updateImageProfileOfUploadProfileAgent(fileName, principal.getName());
        Response response = new Response(200, downloadUri);
        return response.getResponseEntity("data");
    }

    @GetMapping("/image/user/{fileName:.+}")
    public ResponseEntity<Resource> viewImage(@PathVariable String fileName,HttpServletRequest request){
        //load image
        Resource resource=fileStorageService.loadFileAsResource(fileName,"user");
        if(resource==null){
            throw new CustomException(404 , "File not found.");
        }
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

    @GetMapping("/image/building/{fileName:.+}")
    public ResponseEntity imageBuilding(@PathVariable String fileName,HttpServletRequest request){
        Resource resource=fileStorageService.loadFileAsResource(fileName,"building");
        if(resource==null){
            throw new CustomException(404 , "File not found.");
        }
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

    @PutMapping("/agent/account/password")
    public ResponseEntity updateAgentPassword(@RequestBody ChangePassword changePassword, @ApiIgnore Principal principal){
        String password=service.getUserPasswordByEmail(principal.getName());
        if(!passwordEncoder.matches(changePassword.getOldPassword(),password)){
            throw new CustomException(401,"password not match.");
        }
        String resetPassword=passwordEncoder.encode(changePassword.getNewPassword());
        service.updateUserPassword(resetPassword,principal.getName());

        Response response=new Response(200);
        return response.getResponseEntity();
    }

    @PutMapping("/agent/account/update")
    public ResponseEntity updateAccountInformation(@RequestBody UpdateAgentAccount updateAgentAccount, @ApiIgnore Principal principal){
        service.updateUserInformation(updateAgentAccount,principal.getName());
        Response response = new Response(201,service.findUserByUsernameOfUser(principal.getName()));
        return response.getResponseEntity("data");
    }

    @GetMapping("/building")
    public ResponseEntity buildings(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        List<Buildings> buildings=service.findBuildings(pagination);

        Response response = new Response(200, buildings,pagination);
        return response.getResponseEntity("data","pagination");

    }

    @GetMapping("/building/{uuid}")
    public ResponseEntity buildingDetail(@PathVariable("uuid")String uuid,@ApiIgnore Principal principal){
        BuildingUUID buildingUUID=service.findBuildingByUUID(uuid,principal.getName());

        Response response = new Response(200, buildingUUID);
        return response.getResponseEntity("data");
    }

    @PostMapping("/building/status/update")
    public ResponseEntity updateBuildingStatus(@RequestBody BuildingStatusUpdate buildingStatusUpdate){
        service.updateBuildingStatus(buildingStatusUpdate);
        Response response=new Response(200);
        return response.getResponseEntity();
    }

    @GetMapping("/building/available")
    public ResponseEntity buildingAvailable(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        List<BuildingAvailable> buildingAvailable=service.findBuildingAvailable(pagination);
        Response response = new Response(200, buildingAvailable,pagination);
        return response.getResponseEntity("data","pagination");

    }

    @GetMapping("/building/held")
    public ResponseEntity buildingHeld(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.findBuildingHeld(pagination),pagination);
        return response.getResponseEntity("data","pagination");

    }

    @GetMapping("/agent/transaction")
    public ResponseEntity agentTransaction(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit,@ApiIgnore Principal principal){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.findAgentsTransaction(principal.getName(),pagination),pagination);
        return response.getResponseEntity("data","pagination");
    }

    @GetMapping("/agent/booking")
    public ResponseEntity agentBooking(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit,@ApiIgnore Principal principal){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.findAgentsBooking(principal.getName(),pagination),pagination);
        return response.getResponseEntity("data","pagination");
    }

    @GetMapping("/agent/favorite")
    public ResponseEntity agentFavorite(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit,@ApiIgnore Principal principal){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.findAgentFavorite(principal.getName(),pagination),pagination);
        return response.getResponseEntity("data","pagination");
    }

    @PostMapping("/agent/favorite/add")
    public ResponseEntity agentAddFovorite(@RequestBody AgentAddFavorite agentAddFavorite, @ApiIgnore Principal principal){
        service.addFavorite(agentAddFavorite,principal.getName());
        Response response=new Response(201);
        return response.getResponseEntity();
    }

    @PostMapping("/agent/favorite/delete")
    public ResponseEntity agentDeleteFavorite(@RequestBody AgentDeleteFavorite agentDeleteFavorite,@ApiIgnore Principal principal){
        service.deleteAgentFavorite(agentDeleteFavorite,principal.getName());
        Response response=new Response(200);
        return response.getResponseEntity();
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit,@RequestParam("keyword")String keyword){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.search(keyword,pagination),pagination);
        return response.getResponseEntity("data","pagination");
    }
}