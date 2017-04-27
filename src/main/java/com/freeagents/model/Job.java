package com.freeagents.model;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.freeagents.modelDAO.UserDAO;

public class Job {
	
	private long id;
	private User worker;
	private User employer;
	private String title;
	private String description;
	private int budget;
	private int category;
	private ArrayList<File> files;
	private int requiredExp;
	private int status;
	private long acceptedOffer;
	private boolean sponsored;
	private String date;
	private boolean visible;
	private int expire;

	public Job(User employer, String title, String description, int budget, int category, int requiredExp, boolean sponsored, int expire, String date) {
		setEmployer(employer);
		setTitle(title);
		setDescription(description);
		setBudget(budget);
		setCategory(category);
		this.sponsored = sponsored;
		this.requiredExp = requiredExp;
		this.status = 1;
		this.expire = expire;
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		this.date = dateTime.format(formatter);
		this.visible = true;
	}
	
	public Job(long id, User employer, User worker, String title, String description, int budget, int category, int requiredExp, boolean sponsored, int expire, String date, int status, int visibility, long acceptedOffer){
		this.id = id;
		this.employer = employer;
		this.title = title;
		this.description = description;
		this.budget = budget;
		this.category = category;
		this.requiredExp = requiredExp;
		this.sponsored = sponsored;
		this.expire = expire;
		this.date = date;
		this.status = status;
		this.visible = (visibility == 0 ? false : true);
		this.worker = (worker != null ? worker : null);
		this.acceptedOffer = (acceptedOffer > 0 ? acceptedOffer : 0);
	}

	public void setWorker(User worker) {
		this.worker = worker;
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public int getRequiredExp() {
		return requiredExp;
	}

	public void setRequiredExp(int requiredExp) {
		this.requiredExp = requiredExp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getAcceptedOffer() {
		return acceptedOffer;
	}

	public void setEmployer(User employer) {
		if(employer!=null){
			this.employer = employer;
		}
	}

	public void setTitle(String title) {
		if(title != null && !title.isEmpty()){
			if(title.length() < 99){
				this.title = title;
			}
			else{
				this.title = title.substring(0, 98);
			}
		}
	}

	public void setDescription(String description) {
		if(description != null && !description.isEmpty()){
			if(description.length() < 800){
				this.description = description;
			}
			else{
				this.description = description.substring(0, 799);
			}
		}
	}

	public void setBudget(int budget) {
		if(budget>0){
			this.budget = budget;
		}
		else{
			this.budget = 0;
		}
		if(budget > 1000000){
			this.budget = 1000000;
		}
	}

	public void setCategory(int category) {
		if(category > 0 && category!=0 && category <= 16){
			this.category = category;
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public int getExpire() {
		return expire;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isSponsored() {
		return sponsored;
	}

	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public int getBudget() {
		return budget;
	}
		
	public void acceptOffer(Offer offer){
		this.worker = UserDAO.getUserID(offer.getSender());
		this.acceptedOffer = offer.getId();
		this.visible = false;
		this.status = 3;
	}
	
	public void receiveDeposit(){
		this.status = 4;
	}
	
	public void jobDone(){
		this.status = 5;
	}
	
	public User getEmployer() {
		return employer;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getCategory() {
		return category;
	}

	public void openDispute(){
		this.status = 6;
	}
	public User getWorker() {
		return worker;
	}
	
	
}
