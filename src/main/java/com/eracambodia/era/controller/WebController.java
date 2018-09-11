
package com.eracambodia.era.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/index")
public class WebController {
    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/doc")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}

