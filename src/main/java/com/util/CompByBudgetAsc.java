package com.util;

import java.util.Comparator;

import com.freeagents.model.Job;

public class CompByBudgetAsc implements Comparator<Job>{

	public int compare(Job j1, Job j2) {
		return j1.getBudget() - j2.getBudget();
		
	}
	
}
