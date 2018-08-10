
package com.eracambodia.era.controller;

import com.eracambodia.era.model.User;
import com.eracambodia.era.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class WebController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/doc")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}

