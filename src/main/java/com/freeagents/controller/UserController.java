package com.freeagents.controller;

import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.freeagents.model.User;
import com.freeagents.modelDAO.UserDAO;


@Controller
public class UserController {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model, HttpServletRequest request, HttpSession session){
		boolean logged;
		if(session.getAttribute("logged") != null && session.getAttribute("user") != null){
			logged = (Boolean) session.getAttribute("logged");
			if (session.getAttribute("logged") != null && logged){
				User user = UserDAO.getProfile((User) session.getAttribute("username"));
				request.setAttribute("user", user);
			}
		}
		return "index";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(HttpServletRequest request, HttpSession session){
		if(session.getAttribute("logged") != null && session.getAttribute("user") != null){
			if ((Boolean) session.getAttribute("logged")){
				User user = UserDAO.getProfile((User)session.getAttribute("user"));
				HashMap<Integer, String> levels = UserDAO.getLevels();
				HashMap<Integer, String> countries = UserDAO.getCountries();
				request.setAttribute("user", user);
				request.setAttribute("countries", countries);
				request.setAttribute("levels", levels);
				session.setAttribute("user", user);
				return "profile";
			}
			return "login";
		}
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpSession session) {
		session.removeAttribute("notification");
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, HttpServletRequest req) {
		
		if(session.isNew()){
			session.invalidate();
			return "login";
		}
		String user = req.getParameter("username");
		String pass = req.getParameter("password");
		User u = UserDAO.getInstance().validLogin(user, pass);
		if(u != null){
			if(u.getLevel() == 7){
				session.setAttribute("user", u);
		        session.setAttribute("logged", true);
				return "admin/index";
			}
			session.setAttribute("user", u);
	        session.setAttribute("logged", true);
	        return "index";
		}		
		else{
			session.setAttribute("logged", false);
			session.setAttribute("notification", "Wrong username or password. Try again!");
			return "login";
		}
	}
	
	
	@RequestMapping(value="/viewprofile",method = RequestMethod.GET)
	public String viewProfile(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			long id = Long.parseLong(request.getParameter("id"));
			User temp = UserDAO.getUserID(id);
			User user = UserDAO.getProfile(temp);
			request.setAttribute("userprofile", user);
			String country = UserDAO.getCountry(user.getCountry());
			request.setAttribute("country", country);
			return "viewprofile";
		}
		else{
			return "login";
			
		}
	}

	@RequestMapping(value="/editdata",method = RequestMethod.POST)
	public String editProfile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
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
			return "login";
			
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
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(HttpSession session) {
		session.removeAttribute("notification");
		return "signup";
	}
	
	@RequestMapping(value="/signup",method = RequestMethod.POST)
	public String signup(Model model, HttpServletRequest request, HttpSession session) {
		boolean valid = true;
		String page = "signup";
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
			session.setAttribute("notification", "Passwords don't match! Please try again.");
			second = false;
			return page;
		}
		if(UserDAO.getInstance().checkUser(user, email)){
			session.setAttribute("notification", "Either username or email address is already taken. Please try again.");
			third = false;
			return page;
		}
		if(valid && second && third){
			page = "login";
			session.setAttribute("notification", "Registration successfull. Please log in to continue:");
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
	}
	
	@RequestMapping(value="/contact", method=RequestMethod.GET)
	public String contact(Model model){
		return "contact";
	}

}
