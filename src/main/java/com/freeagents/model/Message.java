package com.freeagents.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.freeagents.modelDAO.UserDAO;

public class Message{
	
	private long id;
	private String content;
	private String title;
	private User sender;
	private User receiver;
	private String date;
	private boolean isRead;

	public Message(User sender, User receiver, String title, String content, String date) {
		setTitle(title);
		setContent(content);
		setDate(date);
		this.sender = sender;
		this.receiver = receiver;
		this.isRead = false;
	}
	
	public Message(long id, long sender, long receiver, String content, String title, String date, int isRead) {
		this.id = id;
		this.content = content;
		this.title = title;
		setSender(sender);
		setReceiver(receiver);
		this.date = date;
		this.isRead = (isRead == 0 ? false : true);
	}

	public boolean isRead() {
		return isRead;
	}

	private void setTitle(String title) {
		if(title != null && !title.isEmpty()){
			if(title.length() < 45){
				this.title = title;
			}
			else{
				this.title = title.substring(0, 44);
			}
		}
	}
	

	public void setContent(String content) {
		if(content != null && !content.isEmpty()){
			if(content.length() < 1000){
				this.content = content;
			}
			else{
				this.content = content.substring(0, 999);
			}
		}
	}

	public void setSender(long sender) {
		if(sender > 0){
			this.sender = UserDAO.getUserID(sender);
		}
	}

	public void setReceiver(long receiver) {
		if(receiver > 0){
			this.receiver = UserDAO.getUserID(receiver);
		}
	}

	public void setDate(String date) {
		if(date == null){
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			this.date = dateTime.format(formatter);
		}
		else{
			this.date = date;
		}
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
