package com.example.mvcwebdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MvcwebdemoController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "Let's get started");
		return "index";
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/register")
	public String handleRegister(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			Model model) {

		model.addAttribute("name", name);
		model.addAttribute("email", email);
		return "registration_success";
	}
}
