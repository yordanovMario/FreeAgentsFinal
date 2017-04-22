package com.freeagents.controller;


import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Offer;
import com.freeagents.model.User;
import com.freeagents.modelDAO.JobDAO;
import com.freeagents.modelDAO.OfferDAO;

@Controller
public class OfferController {
	
	@RequestMapping(value="/viewoffers",method = RequestMethod.GET)
	public String viewoffers(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
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
	
	@RequestMapping(value="/postoffer",method = RequestMethod.POST)
	public String sendoffer(HttpServletRequest request, HttpSession session) {
		User u = (User) session.getAttribute("user");
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			if(request.getParameter("content") != null){
				long id = Long.parseLong(request.getParameter("id"));
				String content = (String) request.getParameter("content");
				int price = Integer.parseInt(request.getParameter("price"));
				Offer offer = new Offer(u.getId(), id, content, price, false);
				try {
					OfferDAO.getInstance().postOffer(offer);
				} catch (SQLException e) {
					System.out.println("Offer sending error - " + e.getMessage());
				}
				return "index";
			}
			else{
				long id = Long.parseLong(request.getParameter("id"));
				request.setAttribute("id", id);
				return "postoffer";
			}
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/acceptoffer",method = RequestMethod.POST)
	public String acceptoffer(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			long id = Long.parseLong(request.getParameter("id"));
			long jobID = Long.parseLong(request.getParameter("jobID"));
			try {
				JobDAO.getInstance().acceptOffer(jobID, id);
				request.setAttribute("id", jobID);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				request.setAttribute("notification", "An error occured during accepting your offer.");
			}
			return "viewoffers";
		}
		else{
			return "login";
		}
	}
	
}
