package com.eracambodia.era.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping({"/","/index"})
    public String starter() {
        return "index";
    }
    @GetMapping("/doc")
    public String swagger(){
        return "redirect:/swagger-ui.html";
    }
}
