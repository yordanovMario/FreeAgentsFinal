package com.freeagents.modelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import com.freeagents.model.DBManager;
import com.freeagents.model.Job;
import com.freeagents.model.Offer;
import com.freeagents.model.User;

public class JobDAO {
	
	private static JobDAO instance;
	//hashmap with all userID and arraylist with the jobs they posted;
	private static HashMap<Long, ArrayList<Job>> jobsUser = new HashMap<Long, ArrayList<Job>>();
	
	private static HashMap<Long, Job> jobs = new HashMap<Long, Job>();
	
	private static HashMap<Integer, String> statuses = new HashMap<Integer, String>();
	
	private JobDAO(){
		try {
			reloadCache();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized JobDAO getInstance(){
		if(instance == null){
			instance = new JobDAO();
		}
		return instance;
	}
	
	private void reloadCache() throws SQLException{
		if(jobsUser.isEmpty()){
			String query = "SELECT j.job_id, j.title, j.description, j.budget, j.category_id, j.user_worker_id, j.status, j.accepted_offer_id, j.date, j.expire, j.visibility, j.required_exp, u.username FROM jobs j JOIN users u ON j.user_employer_id = u.user_id";
			java.sql.PreparedStatement st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
			ResultSet res = st.executeQuery();
			User temp;
			Job job;
			while(res.next()){
				temp = UserDAO.getUser(res.getString("username"));
				job = new Job(temp, res.getString("title"), res.getString("description"), 
						Integer.parseInt(res.getString("budget")), Integer.parseInt(res.getString("category_id")), 
						res.getInt("required_exp"), false, (res.getString("expire") == null ? 7 : Integer.parseInt(res.getString("expire"))), 
						res.getString("date"));
				job.setId(Long.parseLong(res.getString("job_id")));
				long userID = temp.getId();
				if(res.getString("user_worker_id") != null){
					job.setWorker(UserDAO.getUserID(Long.parseLong(res.getString("user_worker_id"))));
				}
				if(res.getString("accepted_offer_id") != null){
					job.setAcceptedOffer(OfferDAO.getInstance().getOffer(Long.parseLong(res.getString("accepted_offer_id"))));
				}
				if(res.getString("date") != null){
					job.setDate(res.getString("date"));
				}
				if(res.getString("visibility") != null && Integer.parseInt(res.getString("visibility")) == 0){
					job.setVisible(false);
				}
				job.setStatus(res.getInt("status"));
				jobs.put(job.getId(), job);
				if(jobsUser.containsKey(userID)){
					jobsUser.get(userID).add(job);
				}
				else{
					jobsUser.put(userID, new ArrayList<Job>());
					jobsUser.get(userID).add(job);
				}
			}
		}
		if(statuses.isEmpty()){
			String query = "SELECT * FROM statuses";
			java.sql.PreparedStatement st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()){
				statuses.put((Integer) res.getInt("status_id"), res.getString("name"));
			}
		}
	}
	
	public synchronized void postJob(Job job) throws SQLException{
		String query = "INSERT INTO jobs (title, description, budget, category_id, status, user_employer_id, date, expire, visibility, required_exp) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(query);
		st.setString(1, job.getTitle());
		st.setString(2, job.getDescription());
		st.setInt(3, job.getBudget());
		st.setInt(4, job.getCategory());
		st.setInt(5, 1);
		st.setLong(6, job.getEmployer().getId());
		st.setString(7, job.getDate());
		st.setInt(8, job.getExpire());
		st.setInt(9, job.isVisible() ? 1 : 0);
		st.setInt(10, job.getRequiredExp());
		st.execute();
		ResultSet res = st.getGeneratedKeys();
		res.next();
		long id = res.getLong(1);
		long employerID = job.getEmployer().getId();
		job.setId(id);
		jobs.put(job.getId(), job);
		if(jobsUser.containsKey(employerID)){
			jobsUser.get(employerID).add(job);
		}
		else{
			jobsUser.put(employerID, new ArrayList<Job>());
			jobsUser.get(employerID).add(job);
		}
	}
	
	public TreeSet<Job> getAllJobs(Comparator<Job> comp, int category, int experience){
		TreeSet<Job> temp = new TreeSet<Job>(comp);
		if(category == 0 && experience == 0){
			 for(Job j : jobs.values()){
				 if(j.isVisible()){
					 temp.add(j);
				 }
			 }
			 return temp;
		}
		if(category != 0 && experience == 0){
			for (Job j : jobs.values()) {
				if(j.getCategory() == category && j.isVisible()){
					temp.add(j);
				}
			}
			return temp;
		}
		if(category == 0 && experience != 0){
			for (Job j : jobs.values()) {
				if(j.getRequiredExp() == experience && j.isVisible()){
					temp.add(j);
				}
			}
			return temp;
		}
		if(category != 0 && experience != 0){
			for (Job j : jobs.values()) {
				if(j.getRequiredExp() == experience && j.isVisible() && j.getCategory() == category){
					temp.add(j);
				}
			}
		}
		return temp;
	}
	
	public ArrayList<Job> getMyJobs(long id){
		return jobsUser.get(id);
	}
	
	public static HashMap<Integer, String> getStatuses() {
		return statuses;
	}
	
	public Job getJob(long id){
		return jobs.get(id);
	}
	
	public void firstOffer(long id){
		jobs.get(id).setStatus(2);
	}
	
	public synchronized void acceptOffer(long jobID, long offerID) throws SQLException{
		String query = "UPDATE jobs SET accepted_offer_id = ? WHERE job_id = ?";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(query);
		st.setLong(1, offerID);
		st.setLong(2, jobID);
		st.execute();
		getJob(jobID).acceptOffer(OfferDAO.getInstance().getOffer(offerID));
	}
	
}
