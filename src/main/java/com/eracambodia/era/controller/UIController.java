package com.eracambodia.era.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

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
