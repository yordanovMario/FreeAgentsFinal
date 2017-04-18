package com.util;

import java.util.Comparator;

import com.freeagents.model.Job;

public class CompByLately implements Comparator<Job>{

	public int compare(Job j1, Job j2) {
		return j1.getDate().toString().compareTo((j2.getDate().toString()));
	}
	
}