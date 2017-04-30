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
import com.freeagents.modelDAO.OfferDAO;
import com.freeagents.modelDAO.UserDAO;
import com.freeagents.util.CompByBudgetAsc;
import com.freeagents.util.CompByBudgetDesc;
import com.freeagents.util.CompByLately;
import com.freeagents.util.CompBySponsored;

@Controller
public class JobController {
	
	@RequestMapping(value="/myjobs",method = RequestMethod.GET)
	public String myjobs(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			User u = (User) session.getAttribute("user");
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
	
	@RequestMapping(value="/postjob",method = RequestMethod.GET)
	public String postjob(Model model, HttpServletRequest request, HttpSession session){
		if (session.getAttribute("user") != null){
			session.removeAttribute("notification");
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
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			User user = (User) session.getAttribute("user");
			if(title.isEmpty() || desc.isEmpty() || budget.isEmpty() || category.isEmpty() || reqExp.isEmpty() || expire.isEmpty()){
				valid = false;
			}
			if(valid){
				Job job = new Job(user, title, desc, Integer.parseInt(budget), Integer.parseInt(category), Integer.parseInt(reqExp), isSponsored, Integer.parseInt(expire), null);
				try {
					JobDAO.getInstance().postJob(job);
					req.setAttribute("notification", "Your job was posted successfully.");
				} catch (SQLException e) {
					System.out.println("Job posting error - " + e.getMessage());
					HashMap<Integer, String> categories = UserDAO.getCategories();
					req.setAttribute("categories", categories);
					req.setAttribute("notification", "There was an error with your job. Please try again.");
					page = "postjob";
				}
			}
			else{
				HashMap<Integer, String> categories = UserDAO.getCategories();
				req.setAttribute("categories", categories);
				req.setAttribute("notification", "One ore more fields were empty. Please try again.");
				page = "postjob";
			}
		}
		return page;
	}
	
	@RequestMapping(value="/browsejobs",method = RequestMethod.GET)
	public String browsejobs(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		int sorter;
		int category;
		int experience;
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
		
		if(request.getParameter("experience") == null || request.getParameter("experience") == ""){
			experience=0;
		}
		else{
			experience = Integer.parseInt(request.getParameter("experience"));
			request.setAttribute("experience", Integer.parseInt(request.getParameter("experience")));
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
		TreeSet<Job> jobs = JobDAO.getInstance().getAllJobs(comp, category, experience);
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			User u = (User) session.getAttribute("user");
			request.setAttribute("user", u);
			request.setAttribute("jobs", jobs);
			HashMap<Integer, String> categories = UserDAO.getCategories();
			request.setAttribute("categories", categories);
			return "browsejobs";
		}
		else{
			request.setAttribute("url", "browsejobs");
			return "login";
		}
		
	}
	
	@RequestMapping(value="/jobsIwork",method = RequestMethod.GET)
	public String jobsIwork(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			long id;
			if(request.getParameter("id") != null){
				id = Long.parseLong(request.getParameter("id"));
			}
			else{
				id = 0;
			}
			User u = (User) session.getAttribute("user");
			request.setAttribute("jobsIwork", JobDAO.getInstance().getJobsIWork(u.getId(), id));
			request.setAttribute("statuses", JobDAO.getStatuses());
			request.setAttribute("user", u);
			return "jobsIwork";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/viewjob",method = RequestMethod.GET)
	public String viewjob(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			long id = Long.parseLong(request.getParameter("id"));
			User u = (User) session.getAttribute("user");
			request.setAttribute("job", JobDAO.getJob(id));
			request.setAttribute("statuses", JobDAO.getStatuses());
			request.setAttribute("user", u);
			HashMap<Integer, String> categories = UserDAO.getCategories();
			request.setAttribute("categories", categories);
			return "viewjob";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/viewjobfrombrowsejobs",method = RequestMethod.GET)
	public String viewJobFromBrowseJobs(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			long id = Long.parseLong(request.getParameter("id"));
			User u = (User) session.getAttribute("user");
			request.setAttribute("job", JobDAO.getJob(id));
			request.setAttribute("statuses", JobDAO.getStatuses());
			request.setAttribute("user", u);
			HashMap<Integer, String> categories = UserDAO.getCategories();
			request.setAttribute("categories", categories);
			return "viewjobfrombrowsejobs";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/finishjob",method = RequestMethod.POST)
	public String finishJob(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			User u = (User) session.getAttribute("user");
			long id = Long.parseLong(request.getParameter("id"));
			try {
				JobDAO.getInstance().finishJob(id);
				session.setAttribute("notification", "You have successfully finished the job!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				session.setAttribute("notification", "An error occured during finishing the job. Please try again!");
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
