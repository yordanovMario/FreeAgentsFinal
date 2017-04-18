package com.freeagents.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.User;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class UserController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model, HttpSession session){
		boolean logged = (Boolean) session.getAttribute("logged");
		
		if (session.getAttribute("logged") != null && logged){
			User user = UserDAO.getProfile((User) session.getAttribute("username"));
			model.addAttribute("user", user);
		}
		return "index";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model, HttpSession session){
		boolean logged = (Boolean) session.getAttribute("logged");
		if (session.getAttribute("logged") != null && logged){
			User user = UserDAO.getProfile((User)session.getAttribute("user"));
			HashMap<Integer, String> levels = UserDAO.getLevels();
			HashMap<Integer, String> countries = UserDAO.getCountries();
			model.addAttribute("user", user);
			model.addAttribute("countries", countries);
			model.addAttribute("levels", levels);
			session.setAttribute("user", user);
			return "profile";
		}
		else{
			return "index";
		}
	
	}
	
	
	
}
