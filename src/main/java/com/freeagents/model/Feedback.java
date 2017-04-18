package com.freeagents.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Feedback {
	
	private long id;
	private String content;
	private User sender;
	private User receiver;
	private String date;
	private int rating;
	private boolean isRead;
	
	public Feedback(User sender, User receiver, String content, int rating, String date) {
		if(content!=null && !content.isEmpty()){
			this.content = content;
		}
		if(sender!=null){
			this.sender = sender;
		}
		if(sender!=null){
			this.receiver = receiver;
		}
		if(date == null){
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			this.date = dateTime.format(formatter);
		}
		else{
			this.date = date;
		}
		if(rating >=1 && rating <= 5){
			this.rating = rating;
		}
		this.isRead = false;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public User getSender() {
		return sender;
	}
	
	public int getRating() {
		return rating;
	}
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setSeen(boolean isSeen) {
		this.isRead = isSeen;
	}
	
}
