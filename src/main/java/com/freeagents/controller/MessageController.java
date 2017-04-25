package com.freeagents.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeagents.model.Message;
import com.freeagents.model.User;
import com.freeagents.modelDAO.MessageDAO;
import com.freeagents.modelDAO.UserDAO;

@Controller
public class MessageController {
	
	@RequestMapping(value="/mymessages",method = RequestMethod.GET)
	public String myMessages(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			User u = (User) session.getAttribute("user");
			MessageDAO.getInstance();
			ArrayList<Message> received = MessageDAO.getReceived(u.getId());
			request.setAttribute("user", u);
			request.setAttribute("messages", received);
			return "mymessages";
		}
		else{
			return "login";
		}
		
	}
	
	@RequestMapping(value="/sendmessage", method=RequestMethod.GET)
	public String login(HttpSession session, HttpServletRequest req) {
		long id = Long.parseLong(req.getParameter("id"));
		req.setAttribute("id", id);
		req.setAttribute("receiver", UserDAO.getUserID(id));
		return "sendmessage";
	}
	
	@RequestMapping(value="/sendmessage",method = RequestMethod.POST)
	public String sendmessage(Model model, HttpServletRequest request, HttpSession session) {
		boolean valid = true;
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String date = request.getParameter("date");
		
		if (session.getAttribute("user") != null) {
			if(request.getParameter("title") != null && request.getParameter("content") != null){
				long id = Long.parseLong(request.getParameter("id"));
				User receiver = UserDAO.getUserID(id);
				User sender = (User) session.getAttribute("user");
				if(sender == null || receiver == null || title.isEmpty() || content.isEmpty()){
					valid = false;
				}
				if(valid){
					Message message = new Message(sender, receiver, title, content, date);
					MessageDAO.getInstance();
					MessageDAO.sendMessage(message);
					session.setAttribute("notification", "Message successfuly sent!");
					return "index";
				}
				else{
					session.setAttribute("notification", "There was a problem. Your message was not sent!");
					return "index";
				}
			}
			else{
				session.setAttribute("notification", "Message was not sent!");
				request.setAttribute("id", request.getParameter("id"));
				return "sendmessage";
			}
		}
		else{
			return "login";
		}
		
	}
}
