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
		setContent(content);
		setRating(rating);
		setDate(date);
		setReceiver(receiver);
		setSender(sender);
		this.isRead = false;
	}
	
	public Feedback(long id, String content, User sender, User receiver, String date, int rating, int isRead) {
		this(sender, receiver, content, rating, date);
		this.id = id;
		this.isRead = (isRead == 0 ? false : true);
	}

	private void setContent(String content) {
		if(content != null && !content.isEmpty()){
			if(content.length() < 400){
				this.content = content;
			}
			else{
				this.content = content.substring(0,399);
			}
		}
	}
	
	public boolean isRead() {
		return isRead;
	}
	
	private void setSender(User sender) {
		if(sender != null){
			this.sender = sender;
		}
	}

	private void setReceiver(User receiver) {
		if(receiver != null){
			this.receiver = receiver;
		}
	}

	private void setRating(int rating) {
		if(rating >=1 && rating <= 5){
			this.rating = rating;
		}
		else{
			this.rating = 3;
		}
	}

	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.date = dateTime.format(formatter);
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
