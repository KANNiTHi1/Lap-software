package com.example.academy.controller;

import com.example.academy.service.RegistrantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrantController {

    private final RegistrantService registrantService;

    public RegistrantController(RegistrantService registrantService) {
        this.registrantService = registrantService;
    }

    @GetMapping("/registrants")
    public String list(Model model) {
        model.addAttribute("activeTop", "registrants"); // ไฮไลต์เมนูบน
        model.addAttribute("activeLeft", "registrants"); // ไฮไลต์เมนูซ้าย
        model.addAttribute("pageTitle", "Registrants - Java Academy");
        model.addAttribute("registrants", registrantService.findAll());
        return "registrants";
    }
}