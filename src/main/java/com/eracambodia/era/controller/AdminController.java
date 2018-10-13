package com.eracambodia.era.controller;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.model.Response;
import com.eracambodia.era.service.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value = "ADMIN",description = "respone json data to backen")
public class AdminController {
    @Autowired
    private Service service;

    @GetMapping("/users")
    public ResponseEntity listUser(@RequestParam("role")String role,@RequestParam(value = "name",required = false)String name,@RequestParam(value="page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10") int limit){
        Pagination pagination=new Pagination(page,limit);
        Response response=new Response(200,service.getUsers(role,name,pagination),pagination);
        return response.getResponseEntity("data","pagination");
    }
    @PostMapping("/user/upgrade_to_agent")
    public ResponseEntity upgradeToAgent(@RequestParam("userId") Integer userId,@RequestParam(value = "leaderId",required = false)Integer leaderId){
        service.upgradeToAgent(userId,leaderId);
        Response response=new Response(200);
        return response.getResponseEntity();
    }

    @GetMapping("/userId")
    public ResponseEntity getUserById(@RequestParam("uuid")String uuid){
        Response response=new Response(200,service.findUserById(uuid));
        return response.getResponseEntity("data");
    }
}
