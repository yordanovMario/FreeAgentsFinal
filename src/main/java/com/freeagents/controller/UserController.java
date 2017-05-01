package com.freeagents.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.freeagents.model.Feedback;
import com.freeagents.model.Notification;
import com.freeagents.model.User;
import com.freeagents.modelDAO.FeedbackDAO;
import com.freeagents.modelDAO.UserDAO;

@SessionAttributes("filename")
@MultipartConfig
@Controller
public class UserController {
	
	private static final String FILE_LOCATION = "D:\\uploadedpics";
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpSession session){
		if(session.getAttribute("user") != null){
			request.removeAttribute("notification");
			User user = (User) session.getAttribute("user");
			request.setAttribute("user", user);
		}
		return "index";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model, HttpServletRequest request, HttpSession session){
		if(session.getAttribute("user") != null){
			session.removeAttribute("notification");
			User user = (User)session.getAttribute("user");
			User userprofile = UserDAO.getProfile(user.getId());
			HashMap<Integer, String> levels = UserDAO.getLevels();
			HashMap<Integer, String> countries = UserDAO.getCountries();
			request.setAttribute("user", userprofile);
			request.setAttribute("countries", countries);
			request.setAttribute("levels", levels);
			session.setAttribute("user", user);
			return "profile";
		}
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login1(HttpSession session, HttpServletRequest request) {
		if (session.getAttribute("user") != null) {
			session.setAttribute("notification", "You are already logged in.");
			return "index";
		}
		else{
			session.removeAttribute("notification");
			session.removeAttribute("notifsignup");
			request.setAttribute("url", request.getParameter("url"));
			return "login";
		}	
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
//			if(u.getLevel() == 7){
//				session.setAttribute("user", u);
//				return "admin/index";
//			}
			
			session.setAttribute("user", u);
			System.out.println(req.getParameter("url"));
			if(req.getParameter("url") != null){
				return req.getParameter("url");
			}
	        return "index";
		}		
		else{
			session.setAttribute("notifsignup", "Wrong username or password. Try again!");
			return "login";
		}
	}
	
	@RequestMapping(value="/viewprofile",method = RequestMethod.GET)
	public String viewProfile(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			long id = Long.parseLong(request.getParameter("id"));
			User userprofile = UserDAO.getProfile(id);
			ArrayList<Feedback> feedbacks = FeedbackDAO.getReceived(id);
			double rating = 0;
			if(feedbacks != null){
				double sum = 0;
				for(Feedback f : feedbacks){
					sum += f.getRating();
				}
				rating = sum/feedbacks.size();
				request.setAttribute("rating", rating);
			}
			request.setAttribute("userprofile", userprofile);
			String country = UserDAO.getCountry(userprofile.getCountry());
			request.setAttribute("country", country);
			request.setAttribute("feedbacks", feedbacks);
			return "viewprofile";
		}
		else{
			return "login";
		}
	}

	@RequestMapping(value="/editdata",method = RequestMethod.POST)
	public String editProfile(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			User user = (User) session.getAttribute("user");
//			user.setFirstName(request.getParameter("firstname"));
//			user.setLastName(request.getParameter("lastname"));
			user.setJobTitle(request.getParameter("jobtitle"));
			user.setPhone(request.getParameter("phone"));
			user.setPerHourRate(request.getParameter("perhourrate") == "" ? 0 : Integer.parseInt(request.getParameter("perhourrate")));
			user.setAboutMe(request.getParameter("aboutme"));
			user.setPortfolio(request.getParameter("portfolio"));
			user.setCountry(request.getParameter("country") == "" ? 3 : Integer.parseInt(request.getParameter("country")));
			try {
				UserDAO.updateProfile(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			HashMap<Integer, String> countries = UserDAO.getCountries();
			session.setAttribute("user", user);
			session.setAttribute("countries", countries);
			return "profile";
		}
		else{
			return "login";
			
		}
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
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
		session.removeAttribute("notifisignup");
		if(session.getAttribute("user") != null) {
			session.setAttribute("notification", "You are already logged in.");
			return "index";
		}
		return "signup";
	}
	
	@RequestMapping(value="/signup",method = RequestMethod.POST)
	public String signup(HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("user") != null) {
			session.setAttribute("notification", "You are already logged in.");
			return "index";
		}
		else{
			boolean valid = true;
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
				session.setAttribute("notifsignup", "Passwords don't match! Please try again.");
				second = false;
				return "signup";
			}
			if(UserDAO.getInstance().checkUser(user, email)){
				session.setAttribute("notifsignup", "Either username or email address is already taken. Please try again.");
				third = false;
				return "signup";
			}
			if(valid && second && third){
				try {
					User u = new User(user, pass, email, fname, lname);
					System.out.println(u);
					UserDAO.getInstance().registerUser(u);
				} catch (SQLException e) {
					System.out.println("SignUp error - " + e.getMessage());
					session.setAttribute("notifsignup", "Registration failed. Please try again.");
					return "signup";
				}
				//Email sending code:
				try{
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
				} catch(NoClassDefFoundError e){
					System.out.println("ERROR in mailsending for registration in UserController");
				}
			}
			session.setAttribute("notifsignup", "Registration successfull. Please log in to continue:");
			return "login";
		}
	}
	
	@RequestMapping(value="/uploadpic",method = RequestMethod.POST)
	public String uploadPicture(@RequestParam("failche") MultipartFile multiPartFile, Model model, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("user");
		long id = user.getId();
		java.io.File fileOnDisk = new java.io.File(FILE_LOCATION + "/" + id + ".jpg");
		Files.copy(multiPartFile.getInputStream(), fileOnDisk.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return "profile";
	}
	
	@RequestMapping(value="image/{id}", method=RequestMethod.GET)
	@ResponseBody
	public void viewPicture(@PathVariable("id") Long id, HttpServletResponse resp, Model model, HttpSession session) throws IOException{
		java.io.File file = new java.io.File(FILE_LOCATION + "/" + id + ".jpg");
		Files.copy(file.toPath(), resp.getOutputStream());
	}
	
	@RequestMapping(value="image/0", method=RequestMethod.GET)
	@ResponseBody
	public void viewDefaultPicture(HttpServletResponse resp, Model model, HttpSession session) throws IOException{
		java.io.File file = new java.io.File(FILE_LOCATION + "/0.jpg");
		Files.copy(file.toPath(), resp.getOutputStream());
	}
	
	
	@RequestMapping(value="/notifications",method = RequestMethod.GET)
	public String notification(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			ArrayList<Notification> notifications = UserDAO.getNotifications((User) session.getAttribute("user"));
			request.setAttribute("notifications", notifications);
			return "notifications";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/forgotpassword", method=RequestMethod.GET)
	public String forgotPass() {
		return "forgotpassword";
	}
	
	@RequestMapping(value="/forgotpassword",method = RequestMethod.POST)
	public String forgotPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		User u = UserDAO.getInstance().checkEmail(email);
		if(u != null){
			String password = User.generateNewPass();
			System.out.println(password);
			//TODO: update password code
			String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
			try {
				UserDAO.getInstance().setPassword(pw_hash, u);
				new com.freeagents.util.MailSender(email, "Password Reset", "Your new password is " + password + " .");
				session.setAttribute("notification", "Check your email. We have sent you new password.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				session.setAttribute("notification", "Check your email. We have sent you new password.");
			}
			return "login";
		}
		else{
			return "index";
		}
	}
	
	//TODO: Change password

}