package com.freeagents.controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.freeagents.model.User;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class UserController {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model, HttpSession session){
		//boolean logged = (Boolean) session.getAttribute("logged");
		
		//if (session.getAttribute("logged") != null && logged){
		if (session.getAttribute("logged") != null){
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
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {
		session.removeAttribute("notification");
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			return new ModelAndView("index", "Login", new User());
		}
		return new ModelAndView("login", "Login", new User());
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("Login") User user, HttpSession session) {
		
		if(session.isNew()){
			session.invalidate();
			return "redirect:login";
		}
		
		String email = user.getEmail();
		String password = user.getPassword();
		if(UserDAO.getInstance().validLogin(email, password)){
			session.setAttribute("logged", true);
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(60);
			return "index";
		}
		else{
			session.setAttribute("logged", false);
			session.setAttribute("notification", "The username or password you entered is wrong. Please try again.");
			return "login";
		}
	}
	
	@RequestMapping(value="/viewprofile",method = RequestMethod.GET)
	public String viewProfile(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			long id = Long.parseLong(request.getParameter("id"));
			User temp = UserDAO.getUserID(id);
			User user = UserDAO.getProfile(temp);
			model.addAttribute("userprofile", user);
			String country = UserDAO.getCountry(user.getCountry());
			model.addAttribute("country", country);
			return "viewprofile";
		}
		else{
			return "login"; 
		}
	}

	@RequestMapping(value="/editdata",method = RequestMethod.POST)
	public String editProfile(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			String firstname = model.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String jobtitle = request.getParameter("jobtitle");
			String phone = request.getParameter("phone");
			int perhourrate = Integer.parseInt(request.getParameter("perhourrate"));
			String aboutme = request.getParameter("aboutme");
			String portfolio = request.getParameter("portfolio");
			int country = Integer.parseInt(request.getParameter("country"));
			user.setFirstName(firstname);
			user.setLastName(lastname);
			user.setJobTitle(jobtitle);
			user.setPhone(phone);
			user.setPerHourRate(perhourrate);
			user.setAboutMe(aboutme);
			user.setPortfolio(portfolio);
			user.setCountry(country);
			try {
				UserDAO.updateProfile(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			HashMap<Integer, String> countries = UserDAO.getCountries();
			session.removeAttribute("user");
			session.setAttribute("user", user);
			session.setAttribute("countries", countries);
			return "profile";
		}
		else{
			return "login";
		}
	}

	
	
}
