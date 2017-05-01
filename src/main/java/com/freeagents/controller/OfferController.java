package com.freeagents.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Job;
import com.freeagents.model.Offer;
import com.freeagents.model.User;
import com.freeagents.modelDAO.JobDAO;
import com.freeagents.modelDAO.OfferDAO;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class OfferController {
	
	@RequestMapping(value="/viewoffers",method = RequestMethod.GET)
	public String viewoffers(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			long id = Long.parseLong(request.getParameter("id"));
			ArrayList<Offer> offers = OfferDAO.getInstance().getJobOffers(id);
			if(offers != null){
				System.out.println(id);
				request.setAttribute("offers", offers);
				request.setAttribute("jobID", id);
			}
			return "viewoffers";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/postoffer", method=RequestMethod.GET)
	public String postoffer(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			request.setAttribute("id", request.getParameter("id"));
			return "postoffer";
		}
		else{
			session.removeAttribute("notification");
			session.removeAttribute("notifsignup");
			return "login";
		}	
	}
	
	@RequestMapping(value="/postoffer",method = RequestMethod.POST)
	public String sendoffer(HttpServletRequest request, HttpSession session) {
		User u;
		long id;
		if (session.getAttribute("user") != null) {
			if(request.getParameter("content") != null){
				session.removeAttribute("notification");
				session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
				id = Long.parseLong(request.getParameter("id"));
				String content = (String) request.getParameter("content");
				int price = Integer.parseInt(request.getParameter("price"));
				u = (User) session.getAttribute("user");
				Offer offer = new Offer(u.getId(), id, content, price, false);
				try {
					OfferDAO.getInstance().postOffer(offer);
					session.setAttribute("notification", "Your offer was successfully sent to the employer");
				} catch (SQLException e) {
					System.out.println("Offer sending error - " + e.getMessage());
					session.setAttribute("notification", "There was an error while processing your request. Please try again.");
					
				}
				return "index";
			}
			else{
				id = Long.parseLong(request.getParameter("id"));
				request.setAttribute("id", id);
				session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
				session.setAttribute("notification", "The content of your offer is empty. Please try again.");
				return "postoffer";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/acceptoffer",method = RequestMethod.POST)
	public String acceptoffer(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			User u = (User) session.getAttribute("user");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			long id = Long.parseLong(request.getParameter("id"));
			long jobID = Long.parseLong(request.getParameter("jobID"));
			try {
				JobDAO.getInstance().acceptOffer(jobID, id);
				session.setAttribute("notification", "You have successfully accepted the offer");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				session.setAttribute("notification", "An error occured during accepting the offer you wanted. Please try again");
			}
			ArrayList<Job> jobs = new ArrayList<Job>();
			jobs = JobDAO.getInstance().getMyJobs(u.getId());
			HashMap<Long, Boolean> offers = new HashMap<Long, Boolean>();
			if(jobs != null){
				for(Job j : jobs){
					offers.put(j.getId(), OfferDAO.getInstance().hasOffers(j.getId()));
				}
				request.setAttribute("user", u);
				request.setAttribute("jobs", jobs);
				request.setAttribute("offers", offers);
				request.setAttribute("statuses", JobDAO.getStatuses());
			}
			return "myjobs";
		}
		else{
			return "login";
		}
	}
	
}
