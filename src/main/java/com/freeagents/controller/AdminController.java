package com.freeagents.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Feedback;
import com.freeagents.model.Job;
import com.freeagents.model.Message;
import com.freeagents.model.Offer;
import com.freeagents.model.User;
import com.freeagents.modelDAO.FeedbackDAO;
import com.freeagents.modelDAO.JobDAO;
import com.freeagents.modelDAO.MessageDAO;
import com.freeagents.modelDAO.OfferDAO;
import com.freeagents.modelDAO.UserDAO;

public class AdminController {

	@RequestMapping(value="admin/index",method = RequestMethod.GET)
	public String adminSummary(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				request.setAttribute("user", user);
				return "admin/index";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="admin/messages",method = RequestMethod.GET)
	public String adminMessages(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				HashMap <Long, Message> messages = MessageDAO.getInstance().getAllMessages();
				request.setAttribute("user", user);
				request.setAttribute("messages", messages);
				return "admin/messages";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="admin/feedbacks",method = RequestMethod.GET)
	public String adminFeedbacks(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				HashMap <Long, Feedback> feedbacks = FeedbackDAO.getInstance().getAllFeedbacks();
				request.setAttribute("user", user);
				request.setAttribute("feedbacks", feedbacks);
				return "admin/feedbacks";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="admin/jobs",method = RequestMethod.GET)
	public String adminJobs(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				HashMap <Long, Job> jobs = JobDAO.getInstance().getAllJobs();
				request.setAttribute("user", user);
				request.setAttribute("jobs", jobs);
				return "jobs";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="admin/users",method = RequestMethod.GET)
	public String adminUsers(HttpServletRequest request, HttpSession session, @PathVariable("page") String page) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				HashMap <Long, User> users = UserDAO.getInstance().getAllUsers();
				request.setAttribute("users", users);
				request.setAttribute("user", user);
				return "admin/users";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="admin/offers",method = RequestMethod.GET)
	public String adminOffers(HttpServletRequest request, HttpSession session, @PathVariable("page") String page) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(user.getLevel() == 7){
				HashMap <Long, Offer> offers = OfferDAO.getInstance().getAllOffers();
				request.setAttribute("offers", offers);
				request.setAttribute("user", user);
				return "admin_offers";
			}
			else{
				request.setAttribute("notification", "You don't have permission to access this page!");
				return "index";
			}
		}
		else{
			return "login";
		}
	}
	
}
