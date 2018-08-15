
package com.eracambodia.era.controller;

import com.eracambodia.era.repository.BuildingRepository;
import com.eracambodia.era.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/index")
public class WebController {
    @GetMapping()
    public String index(){
        return "index";
    }

    @GetMapping("/doc")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}

