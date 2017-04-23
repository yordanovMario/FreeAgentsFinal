package com.freeagents.util;

import java.util.Comparator;

import com.freeagents.model.Job;

public class CompBySponsored implements Comparator<Job>{

	public int compare(Job j1, Job j2) {
		if((j1.isSponsored() && j2.isSponsored()) || (!j1.isSponsored() && !j2.isSponsored())){
			return j1.getDate().toString().compareTo(j2.getDate().toString());
		}
		else{
			if(j1.isSponsored()){
				return 1;
			}
			else{
				return -1;
			}
		}
		
	}

}
