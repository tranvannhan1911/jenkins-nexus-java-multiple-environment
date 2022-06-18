package com.example.multiple_environment.multipleenvironment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HelloController {
	@Value("${app.title}")
	private String title;
	
	@GetMapping("")
	public String hello(Model model) {
		model.addAttribute("title", title);
		return "index";
	}
}
