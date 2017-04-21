package com.freeagents.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Job;
import com.freeagents.model.User;
import com.freeagents.modelDAO.JobDAO;
import com.freeagents.modelDAO.UserDAO;
import com.freeagents.util.CompByBudgetAsc;
import com.freeagents.util.CompByBudgetDesc;
import com.freeagents.util.CompByLately;
import com.freeagents.util.CompBySponsored;

@Controller
public class JobController {
	
	@RequestMapping(value="/myjobs",method = RequestMethod.GET)
	public String myjobs(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
			User u = (User) session.getAttribute("user");
			ArrayList<Job> jobs = JobDAO.getInstance().getMyJobs(u.getId());
			request.setAttribute("user", u);
			request.setAttribute("jobs", jobs);
			return "myjobs";
		}
		else{
			return "login";
		}
	
	}
	
	@RequestMapping(value="/postjob",method = RequestMethod.GET)
	public String postjob(Model model, HttpServletRequest request, HttpSession session){
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null){
			HashMap<Integer, String> categories = UserDAO.getCategories();
			request.setAttribute("categories", categories);
			return "postjob";
		}
		return "login";
	}
	
	@RequestMapping(value="/postjob",method = RequestMethod.POST)
	public String postjob(HttpServletRequest req, HttpSession session){
		boolean valid = true;
		String title = req.getParameter("title");
		String desc = req.getParameter("description");
		String budget  = req.getParameter("budget");
		String category = req.getParameter("category");
		String reqExp = req.getParameter("reqExp");
		String expire = req.getParameter("expire");
		boolean isSponsored = false;
		String page = "index";
		if (session.getAttribute("logged") != null || session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			if(title.isEmpty() || desc.isEmpty() || budget.isEmpty() || category.isEmpty() || reqExp.isEmpty() || expire.isEmpty()){
				valid = false;
			}
			if(valid){
				System.out.println(user+" in postjobservlet");
				Job job = new Job(user, title, desc, Integer.parseInt(budget), Integer.parseInt(category), Integer.parseInt(reqExp), isSponsored, Integer.parseInt(expire), null);
				try {
					JobDAO.getInstance().postJob(job);
				} catch (SQLException e) {
					System.out.println("Job posting error - " + e.getMessage());
					page = "postjob";
				}
			}
		}
		return page;
	}
	
	@RequestMapping(value="/browsejobs",method = RequestMethod.GET)
	public String browsejobs(Model model, HttpServletRequest request, HttpServletResponse response){
		
		int sorter;
		int category;
		if(request.getParameter("sort") == null || request.getParameter("sort") == ""){
			sorter=2;
		}
		else{
			sorter = Integer.parseInt(request.getParameter("sort"));
			request.setAttribute("sortID", Integer.parseInt(request.getParameter("sort")));
		}
		if(request.getParameter("category") == null || request.getParameter("category") == ""){
			category=0;
		}
		else{
			category = Integer.parseInt(request.getParameter("category"));
			request.setAttribute("categoryID", Integer.parseInt(request.getParameter("category")));
		} 
		Comparator<Job> comp;
		switch (sorter) {
		case 1:
			comp = new CompByLately();
			break;
		case 2:
			comp = new CompByBudgetAsc();
			break;
		case 3:
			comp = new CompByBudgetDesc();
			break;
		case 4:
			comp = new CompBySponsored();
		default:
			comp = new CompByBudgetDesc();
		}
		TreeSet<Job> jobs = JobDAO.getInstance().getAllJobs(comp, category);
		HttpSession session = request.getSession(false);
		if (session.getAttribute("logged") != null && session.getAttribute("user") != null) {
				User u = (User) session.getAttribute("user");
				request.setAttribute("user", u);
				request.setAttribute("jobs", jobs);
				HashMap<Integer, String> categories = UserDAO.getCategories();
				request.setAttribute("categories", categories);
				return "browsejobs";
		}
		return "login";
	}
	
}
