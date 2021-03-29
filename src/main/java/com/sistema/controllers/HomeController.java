package com.sistema.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "register";
	}
	
	@GetMapping("/forgot-pass")
	public String forgot() {
		return "forgot-pass";
	}
	
	@GetMapping("/tables")
	public String tables() {
		return "tables";
	}
	
	@GetMapping("/forms")
	public String forms() {
		return "forms";
	}
	
	@GetMapping("/charts")
	public String charts() {
		return "charts";
	}
	
	
	

}
