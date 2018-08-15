package com.eracambodia.era.controller;

import com.eracambodia.era.model.Pagination;
import com.eracambodia.era.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {
    @GetMapping("/")
    public String starter(){
        return "redirect:/index";
    }
}
