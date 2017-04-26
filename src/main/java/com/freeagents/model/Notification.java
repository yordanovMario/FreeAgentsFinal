package com.freeagents.model;

public class Notification implements Comparable<Notification>{
	
	long id;
	String title;
	String link;
	static long counter = 1;
	int type;
	long objectID;
	
	public long getId() {
		return id;
	}

	public Notification(String text, int number, long objectID) {
		id = counter;
		counter++;
		this.objectID = objectID;
		this.type = number;
		switch (number) {
		case 1:
			this.title = "You have one new message from " + text;
			this.link = "readmessage?id=" + objectID;
			break;
		case 2:
			this.title = "You have new feedback posted from " + text;
			this.link = "readfeedback?id=" + objectID;
			break;
		case 3:
			this.title = "The employer of the job " + text + " has accepted your offer.";
			this.link = "jobsIWork";
			break;
		case 4:
			this.title = "You have new offer for your job " + text;
			this.link = "viewoffers?id="+objectID;
			break;
		default:
			this.title = "Bugged notification";
			this.link = "index";
		}
	}

	public long getObjectID() {
		return objectID;
	}

	public int getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	@Override
	public int compareTo(Notification n1) {
		if((n1.id - this.id) > 0){
			return 1;
		}
		else if((n1.id - this.id) < 0){
			return -1;
		}
		else{
			return 0;
		}
	}
	
}
