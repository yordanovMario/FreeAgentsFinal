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
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
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
	
	@RequestMapping(value="/sentmessages",method = RequestMethod.GET)
	public String sent(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			User u = (User) session.getAttribute("user");
			MessageDAO.getInstance();
			ArrayList<Message> received = MessageDAO.getSent(u.getId());
			request.setAttribute("user", u);
			request.setAttribute("messages", received);
			return "sentmessages";
		}
		else{
			return "login";
		}
		
	}
	
	@RequestMapping(value="/sendmessage", method=RequestMethod.GET)
	public String sendmessage(HttpSession session, HttpServletRequest req) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			if(req.getParameter("id") != null){
				if(req.getParameter("title") != null){
					req.setAttribute("title", req.getParameter("title"));
					req.setAttribute("type", 1);
				}
				else{
					
				}
				long id = Long.parseLong(req.getParameter("id"));
				req.setAttribute("id", id);
				req.setAttribute("receiver", UserDAO.getUserID(id));
				req.setAttribute("type", 2);
			}
			else{
				req.setAttribute("type", 3);
			}
			return "sendmessage";
		}
		else{
			return "login";
		}
	}
	
	@RequestMapping(value="/sendmessage",method = RequestMethod.POST)
	public String sendmessage(HttpServletRequest request, HttpSession session) {
		boolean valid = true;
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String date = request.getParameter("date");
		int type = Integer.parseInt(request.getParameter("type"));
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			if(request.getParameter("title") != null && request.getParameter("content") != null){
				User receiver;
				if(type == 1){
					long id = Long.parseLong(request.getParameter("id"));
					receiver = UserDAO.getUserID(id);
				}
				else{
					receiver = UserDAO.getUser(request.getParameter("username"));
					if(receiver == null){
						session.setAttribute("notification", "No such user in our site.");
						request.setAttribute("username", request.getParameter("username"));
						request.setAttribute("content", content);
						request.setAttribute("title", title);
						request.setAttribute("type", 3);
						request.setAttribute("id", request.getParameter("id"));
						return "sendmessage";
					}
				}
				User sender = (User) session.getAttribute("user");
				if(sender == null || receiver == null || title.isEmpty() || content.isEmpty()){
					valid = false;
				}
				if(valid){
					Message message = new Message(sender, receiver, title, content, date);
					MessageDAO.getInstance();
					MessageDAO.sendMessage(message);
					session.setAttribute("notification", "Message successfully sent!");
					return "index";
				}
				else{
					session.setAttribute("notification", "The data you have entered is not correct. Please correct it and try again!");
					request.setAttribute("content", content);
					request.setAttribute("title", title);
					request.setAttribute("id", request.getParameter("id"));
					return "sendmessage";
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

	@RequestMapping(value="/readmessage", method=RequestMethod.GET)
	public String readmessage(HttpSession session, HttpServletRequest req) {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("notification");
			session.setAttribute("notifications", UserDAO.getNotifications((User) session.getAttribute("user")));
			long id = Long.parseLong(req.getParameter("id"));
			long notification = (req.getParameter("notifID") != null ? Long.parseLong(req.getParameter("notifID")) : 0);
			Message message = MessageDAO.readMessage(id, notification);
			req.setAttribute("message", message);
			return "readmessage";
		}
		else{
			return "login";
		}
	}
}
