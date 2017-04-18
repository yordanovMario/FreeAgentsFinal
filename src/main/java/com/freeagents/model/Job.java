package com.freeagents.model;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
	private ArrayList<Offer> offers;
	private Offer acceptedOffer;
	private boolean sponsored;
	private String date;
	private boolean visible;
	private int expire;

	public Job(User employer, String title, String description, int budget, int category, int requiredExp, boolean sponsored, int expire, String date) {
		if(employer!=null){
			this.employer = employer;
		}
		if(title!=null && !title.isEmpty()){
			this.title = title;
		}
		if(description!=null && !description.isEmpty()){
			this.description = description;
		}
		if(budget>0){
			this.budget = budget;
		}
		if(category!=0 && category <= 16){
			this.category = category;
		}
		this.sponsored = sponsored;
		this.requiredExp = requiredExp;
		this.status = 1;
		this.expire = expire;
		this.offers = new ArrayList<Offer>();
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		if(date == null){
			this.date = dateTime.format(formatter);
		}
		else{
			this.date = date;
		}
		this.visible = true;
	}
	public User getWorker() {
		return worker;
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

	public ArrayList<Offer> getOffers() {
		return offers;
	}

	public void setOffers(ArrayList<Offer> offers) {
		this.offers = offers;
	}

	public Offer getAcceptedOffer() {
		return acceptedOffer;
	}

	public void setAcceptedOffer(Offer acceptedOffer) {
		this.acceptedOffer = acceptedOffer;
	}

	public void setEmployer(User employer) {
		this.employer = employer;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setCategory(int category) {
		this.category = category;
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
		
	public void printOffers(){
		System.out.println("Offers for Job *" + this.title + "* :");
		for (int i = 0; i < offers.size(); i++) {
			System.out.println(offers.get(i));
		}
	}
	
	public void acceptOffer(Offer offer){
		if(offers.contains(offer)){
			this.acceptedOffer = offer;
		}
		this.status = 3;
	}
	
	public void inProgress(){
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
	
	
}
