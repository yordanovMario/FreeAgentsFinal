package com.freeagents.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Feedback;
import com.freeagents.model.User;
import com.freeagents.modelDAO.FeedbackDAO;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class FeedbackController {
	
	@RequestMapping(value="/sendfeedback",method = RequestMethod.POST)
	public String sendfeedback(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
	
		boolean valid = true;
		
		String content = req.getParameter("content");
		int rating = Integer.parseInt(req.getParameter("rating"));
		String date = req.getParameter("date");
		
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			if(req.getParameter("content") != null && req.getParameter("rating") != null){
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
					req.setAttribute("notification", "Feedback successfuly sent!");
					return "index";
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
	public String myfeedbacks(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
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
