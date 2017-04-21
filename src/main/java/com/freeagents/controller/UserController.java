package com.freeagents.controller;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	public String index(Model model, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		boolean logged = (Boolean) session.getAttribute("logged");
		
		if (session.getAttribute("logged") != null && logged){
			User user = UserDAO.getProfile((User) session.getAttribute("username"));
//			HashMap<Integer, String> levels = UserDAO.getLevels();
//			HashMap<Integer, String> countries = UserDAO.getCountries();
			request.setAttribute("user", user);
//			request.setAttribute("countries", countries);
//			request.setAttribute("levels", levels);
//			session.setAttribute("username", user);
		}
		return "index";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		boolean logged = (Boolean) session.getAttribute("logged");
		if (session.getAttribute("logged") != null && logged){
			User user = UserDAO.getProfile((User)session.getAttribute("user"));
			HashMap<Integer, String> levels = UserDAO.getLevels();
			HashMap<Integer, String> countries = UserDAO.getCountries();
			request.setAttribute("user", user);
			request.setAttribute("countries", countries);
			request.setAttribute("levels", levels);
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
			return "redirect:LogIn.html";
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
			return "redirect:LogIn.html";
			//return "login";
		}
	}
	
	@RequestMapping(value="/viewprofile",method = RequestMethod.GET)
	public String viewProfile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			long id = Long.parseLong(request.getParameter("id"));
			User temp = UserDAO.getUserID(id);
			User user = UserDAO.getProfile(temp);
			request.setAttribute("userprofile", user);
			String country = UserDAO.getCountry(user.getCountry());
			request.setAttribute("country", country);
			return "viewprofile";
		}
		else{
			return "redirect:LogIn.html";
			//return "login"; 
		}
	}

	@RequestMapping(value="/editdata",method = RequestMethod.POST)
	public String editProfile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			String firstname = request.getParameter("firstname");
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
			return "redirect:LogIn.html";
			//return "login";
		}
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();  
		session.setAttribute("logged", false);
		session.invalidate();
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		return "index";
	}
	
	@RequestMapping(value="/signup",method = RequestMethod.POST)
	public String signup(Model model, HttpServletRequest request) {
		boolean valid = true;
		String page = "redirect:SignUpFailed.html";
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		String passconf	= request.getParameter("password2");
		
		if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || user.isEmpty() || pass.isEmpty() || passconf.isEmpty()){
			valid = false;
		}
	
		boolean second = true;
		boolean third = true;
		if(!pass.equals(passconf)){
			page = "redirect:SignUpPasswords.html";
			second = false;
		}
		if(UserDAO.getInstance().checkUser(user, email)){
			page = "redirect:SignUpDuplicates.html";
			third = false;
		}
		if(valid && second && third){
			page = "redirect:LogInSuccess.html";
			try {
				User u = new User(user, pass, email, fname, lname);
				System.out.println(u);
				UserDAO.getInstance().registerUser(u);
			} catch (SQLException e) {
				System.out.println("SignUp error - " + e.getMessage());
			}
			//Email sending code:
			new com.freeagents.util.MailSender(email, "Welcome to FreeAgents!", 
					"Hi, " + fname + "!" + System.lineSeparator() + System.lineSeparator() +
					"Welcome to FreeAgents! Thanks so much for joining us." + System.lineSeparator() +
					System.lineSeparator() +
					"You are now part of our community of curated freelance talent " + System.lineSeparator() +
					"available to work for you remotely at the click of a button." + System.lineSeparator() + 
					"Have any questions? Just shoot us an email! We’re always here to help." + System.lineSeparator() + 
					System.lineSeparator() +
					"Cheerfully yours," + System.lineSeparator() +
					"The Freeagents Team"
					);
		}
		return page;
		//rq.forward(req, resp);
	}

}
