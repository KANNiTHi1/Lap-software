package com.app.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LibraryMvcController {

    // Serve the index.html page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Let's get started with Java Academy");
        return "index"; // Refers to src/main/resources/templates/index.html
    }

    // Serve the registration.html page
    @GetMapping("/registration")
    public String registration() {
        return "registration"; // Refers to src/main/resources/templates/registration.html
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(String name, String email, Model model) {
        model.addAttribute("message", "Thank you for registering, " + name + "!");
        return "redirect:/"; // Redirect to home page
    }

    // Serve the contactus.html page
    @GetMapping("/contactus")
    public String contactUs() {
        return "contactus"; // Refers to src/main/resources/templates/contactus.html
    }

    // Handle contact form submission
    @PostMapping("/contact")
    public String contact(String name, String email, String message, Model model) {
        model.addAttribute("message", "Thank you for contacting us, " + name + "! We will get back to you soon.");
        return "redirect:/"; // Redirect to home page
    }
}
