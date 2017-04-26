package com.freeagents.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Feedback;
import com.freeagents.model.Notification;
import com.freeagents.model.User;
import com.freeagents.modelDAO.FeedbackDAO;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class FeedbackController {
	
	@RequestMapping(value="/sendfeedback", method=RequestMethod.GET)
	public String login(HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			long id = Long.parseLong(req.getParameter("id"));
			req.setAttribute("id", id);
			req.setAttribute("receiver", UserDAO.getUserID(id));
			return "sendfeedback";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/sendfeedback",method = RequestMethod.POST)
	public String sendfeedback(HttpServletRequest req, HttpSession session) {
		boolean valid = true;
		String content = req.getParameter("content");
		int rating = Integer.parseInt(req.getParameter("rating"));
		String date = req.getParameter("date");
		
		if (session.getAttribute("user") != null) {
			if(req.getParameter("content") != null && req.getParameter("rating") != null){
				session.removeAttribute("notification");
				long id = Long.parseLong(req.getParameter("id"));
				User receiver = UserDAO.getUserID(id);
				User sender = (User) session.getAttribute("user");
				if(sender == null || receiver == null || content.isEmpty() || rating < 1 || rating >5){
					valid = false;
				}
				if(valid){
					Feedback feedback = new Feedback(sender, receiver, content, rating, date);
					FeedbackDAO.getInstance();
					FeedbackDAO.sendFeedback(feedback);
					
					session.setAttribute("notification", "Feedback successfuly sent!");
					return "index";
				}
				else{
					session.setAttribute("notification", "The data you have entered is not correct. Please correct it and try again!");
					req.setAttribute("content", content);
					req.setAttribute("rating", rating);
					req.setAttribute("id", req.getParameter("id"));
					return "sendfeedback";
				}
			}
			else{
				long id = Long.parseLong(req.getParameter("id"));
				System.out.println(id);
				req.setAttribute("id", id);
				return "sendfeedback";
			}
		}
		return "sendfeedback";
	}
	
	@RequestMapping(value="/myfeedbacks",method = RequestMethod.GET)
	public String myfeedbacks(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			User u = (User) session.getAttribute("user");
			FeedbackDAO.getInstance();
			ArrayList<Feedback> received = FeedbackDAO.getReceived(u.getId());
			request.setAttribute("user", u);
			request.setAttribute("feedbacks", received);
			return "myfeedbacks";
		}
		else{
			return "login";
		}
	}
		
}
