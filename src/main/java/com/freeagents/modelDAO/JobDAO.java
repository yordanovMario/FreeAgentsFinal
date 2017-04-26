package com.freeagents.modelDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

import com.freeagents.model.DBManager;
import com.freeagents.model.Job;
import com.freeagents.model.Message;
import com.freeagents.model.Notification;
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
			String query = "SELECT job_id, title, description, budget, category_id, user_employer_id, user_worker_id, status, accepted_offer_id, date, expire, visibility, required_exp FROM jobs";
			java.sql.PreparedStatement st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
			ResultSet res = st.executeQuery();
			User user;
			Job job;
			while(res.next()){
				user = UserDAO.getUserID(res.getLong("user_employer_id"));
				job = new Job(res.getLong("job_id"), user, UserDAO.getUserID(res.getLong("user_worker_id")), res.getString("title"), 
						res.getString("description"), res.getInt("budget"), res.getInt("category_id"), res.getInt("required_exp"), false, 
						(res.getInt("expire") > 0 ? 7 : res.getInt("expire")), res.getString("date"), res.getInt("status"), res.getInt("visibility"), 
						OfferDAO.getInstance().getOffer(res.getLong("accepted_offer_id")));
				long userID = user.getId();
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
	
	public HashMap<Long, Job> getAllJobs(){		
		return jobs;
	}
	
	public ArrayList<Job> getMyJobs(long id){
		return jobsUser.get(id);
	}
	
	public ArrayList<Job> getJobsIWork(long id){
		ArrayList<Job> working = new ArrayList<Job>();
		for(Job j : jobs.values()){
			System.out.println(j);
			if(j.getWorker() != null){
				if(j.getWorker().getId() == id){
					System.out.println(j);
					working.add(j);
				}
			}
		}
		return working;
	}
	
	public static HashMap<Integer, String> getStatuses() {
		return statuses;
	}
	
	public static Job getJob(long id){
		return jobs.get(id);
	}
	
	public void firstOffer(long id){
		jobs.get(id).setStatus(2);
	}
	
	public synchronized void acceptOffer(long jobID, long offerID) throws SQLException{
		String query1 = "UPDATE jobs SET accepted_offer_id = ?, status = ?, user_worker_id = ?, visibility = ? WHERE job_id = ?";
		Connection con = null;
		Offer offer = OfferDAO.getInstance().getOffer(offerID);
		PreparedStatement st1 = DBManager.getInstance().getConnection().prepareStatement(query1);
		st1.setLong(1, offerID);
		st1.setLong(2, 3);
		st1.setLong(3, offer.getSender());
		st1.setInt(4, 0);
		st1.setLong(5, jobID);
		
		String query2 = "INSERT INTO workersww (employer_id, worker_id) values (?, ?)";
		PreparedStatement st2 = DBManager.getInstance().getConnection().prepareStatement(query2);
		st2.setLong(1, getJob(jobID).getEmployer().getId());
		st2.setLong(2, offer.getSender());
		
		String query3 = "INSERT INTO employersww (employer_id, worker_id) values (?, ?)";
		PreparedStatement st3 = DBManager.getInstance().getConnection().prepareStatement(query3);
		st2.setLong(1, getJob(jobID).getEmployer().getId());
		st2.setLong(2, offer.getSender());
		
		try
		{
		  con.setAutoCommit(false);
		  st1.execute();
		  st2.execute();
		  st3.execute();
		  getJob(jobID).acceptOffer(OfferDAO.getInstance().getOffer(offerID));
		  addNotification(offer);
		  con.commit();
		}
		catch(SQLException e)
		{
		  System.out.println(e.getMessage());
		  con.rollback();
		}
		finally
		{
		   con.close();
		}

		
	}
	
	private static void addNotification(Offer offer){
		offer.getSenderUser().addNotification(new Notification("Your offer for job " + getJob(offer.getJob()).getTitle() + " was accepted from employer.", 3, offer.getId()));
	}
	
	private static void removeNotification(Offer offer){
		offer.getSenderUser().removeNotification(offer.getId(), 3);
	}
	
//	private synchronized ArrayList<Job> jobsIWork(long id) throws SQLException{
//		ArrayList<Job> jobsIWork = new ArrayList<Job>();
//		String query = "SELECT job_id FROM jobs WHERE user_worker_id = ?";
//		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(query);
//		st.setLong(1, id);
//		ResultSet res = st.executeQuery();
//		while(res.next()){
//			jobsIWork.add(getJob(res.getLong("job_id")));
//		}
//		return jobsIWork;
//	}
}
