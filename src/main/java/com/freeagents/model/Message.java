package com.freeagents.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message{
	
	private long id;
	private String content;
	private String title;
	private User sender;
	private User receiver;
	private String date;
	private boolean isRead;

	public Message(User sender, User receiver, String title, String content, String date) {
		if(title!=null && !title.isEmpty()){
			this.title = title;
		}
		if(date == null){
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			this.date = dateTime.format(formatter);
		}
		else{
			this.date = date;
		}
		if(sender!=null){
			this.sender = sender;
		}
		if(sender!=null){
			this.receiver = receiver;
		}
		if(content!=null){
			this.content = content;
		}
		this.isRead = false;
	}
	
	public String getTitle() {
		return title;
	}
	
	public long getId() {
		return id;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public User getSender() {
		return sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public String getDate() {
		return date;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
