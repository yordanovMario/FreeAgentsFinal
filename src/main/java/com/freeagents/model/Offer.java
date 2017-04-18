package com.freeagents.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.freeagents.modelDAO.UserDAO;

public class Offer {
	
	private long id;
	private long sender;
	private User senderUser;
	private long job;
	private String content;
	private int price;
	private String date;
	private boolean isRead;
	
	public Offer(long sender, long job_id, String content, int price, boolean isRead) {
		this.sender = sender;
		this.job = job_id;
		this.senderUser = UserDAO.getUserID(sender);
		if(content!=null && !content.isEmpty()){
			this.content = content;
		}
		this.price = price;
		this.isRead = isRead;
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.date = dateTime.format(formatter);
		this.isRead = false;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getJob() {
		return job;
	}

	public String getDate() {
		return date;
	}

	public String getContent(){
		return content;
	}
	
	public int getPrice(){
		return price;
	}

	public long getSender() {
		return sender;
	}
	
	public User getSenderUser() { 
		return senderUser;
	}
		
}
