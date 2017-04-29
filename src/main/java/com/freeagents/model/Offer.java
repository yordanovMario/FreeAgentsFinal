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
		setContent(content);
		setPrice(price);
		this.isRead = isRead;
		setDate("newDate");
		this.isRead = false;
	}
	
	public Offer(long id, long sender, long job_id, String content, int price, int isRead, String date) {
		this.id = id;
		this.sender = sender;
		this.job = job_id;
		this.senderUser = UserDAO.getUserID(sender);
		setContent(content);
		setPrice(price);
		this.isRead = (isRead == 0 ? false : true);
		setDate(date);
	}
	
	private void setPrice(int price) {
		if(price > 0 && price < 10000){
			this.price = price;
		}
		else if(price <= 0){
			this.price = 0;
		}
		else{
			price = 10000;
		}
	}

	public void setDate(String date) {
		if(date == "newDate"){
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			this.date = dateTime.format(formatter);
		}
		else{
			this.date = date;
		}
	}

	public long getId() {
		return id;
	}
	
	public boolean isRead() {
		return isRead;
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
	
	public void setContent(String content) {
		if(content!=null && !content.isEmpty()){
			if(content.length() < 500){
				this.content = content;
			}
			else{
				this.content = content.substring(0, 499);
			}
		}
	}

	public int getPrice(){
		return price;
	}

	public long getSender() {
		return sender;
	}
	
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public User getSenderUser() { 
		return senderUser;
	}
		
}
