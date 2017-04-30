package com.freeagents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FooterController {
	
	@RequestMapping(value="/careers", method=RequestMethod.GET)
	public String careers(Model model){
		return "careers";
	}
	
	@RequestMapping(value="/privacy", method=RequestMethod.GET)
	public String privacy(Model model){
		return "privacy";
	}
	
	@RequestMapping(value="/terms", method=RequestMethod.GET)
	public String terms(Model model){
		return "terms";
	}
	
	@RequestMapping(value="/contact", method=RequestMethod.GET)
	public String contact(Model model){
		return "contact";
	}
	
}
