package com.sistema.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sistema.models.ErrorAuth;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		ErrorAuth.setErro(false);
		return "index";
	}

	@GetMapping("/forgot-pass")
	public String forgot() {
		ErrorAuth.setErro(false);
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

	@GetMapping("/tradutor")
	public String tradutor() {
		return "tradutor";
	}

}
