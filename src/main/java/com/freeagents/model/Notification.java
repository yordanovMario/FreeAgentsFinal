package com.freeagents.model;

import org.mockito.internal.matchers.CompareTo;

public class Notification {
	
	String title;
	String link;
	static int counter = 1;
	int number;
	
	public Notification(String title, String link) {
		this.title = title;
		this.link = link;
		number = counter;
		counter++;
		
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}
	
}
