package com.freeagents.model;

public class Notification implements Comparable<Notification>{
	
	String title;
	String link;
	static int counter = 1;
	int number;
	
	long messageID;
	long feedbackID;
	long offerID;
	long jobID;
	
	public Notification(String title, int number, long id) {
		this.title = title;
		
		number = counter;
		counter++;
		switch (number) {
		case 1:
			messageID = id;
			this.link = "mymessages";
			break;
		case 2:
			feedbackID = id;
			this.link = "myfeedbacks";
			break;
		case 3:
			offerID = id;
			this.link = "jobsIWork";
			break;
		case 4:
			jobID = id;
			this.link = "viewoffers?id="+id;
			break;
		default:
			messageID = id;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	@Override
	public int compareTo(Notification n1) {
		// TODO Auto-generated method stub
		return n1.number - this.number;
	}
	
}
