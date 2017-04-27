package com.freeagents.modelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.freeagents.model.DBManager;
import com.freeagents.model.Message;
import com.freeagents.model.Notification;
import com.freeagents.model.Offer;

public class OfferDAO {
	
	private static OfferDAO instance;
	//HashMap with all jobs and their posted offers
	private static HashMap<Long, ArrayList<Offer>> offers = new HashMap<Long, ArrayList<Offer>>();
	//HashMap with all offers by ID
	private static HashMap<Long, Offer> offersID = new HashMap<Long, Offer>();
	private OfferDAO(){
		try {
			reloadCache();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized OfferDAO getInstance(){
		if(instance == null){
			instance = new OfferDAO();
		}
		return instance;
	}
	
	private void reloadCache() throws SQLException{
		if(offers.isEmpty()){
			String query = "SELECT offer_id, content, price, date, job_id, sender_id, is_read FROM offers;";
			java.sql.PreparedStatement st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
			ResultSet res = st.executeQuery();
			Offer offer;
			long jobID;
			while(res.next()){
				jobID = res.getLong("job_id");
				offer = new Offer(res.getLong("offer_id"), res.getLong("sender_id"), jobID, res.getString("content"), res.getInt("price"), 
						res.getInt("is_read"), res.getString("date"));
				if(!offer.isRead()){
					addNotification(offer);
				}
				offersID.put(offer.getId(), offer);
				if(!offers.containsKey(jobID)){
					offers.put(jobID, new ArrayList<Offer>());
				}
				offers.get(jobID).add(offer);
			}
		}
	}
	
	public synchronized void postOffer(Offer offer) throws SQLException{
		String query = "INSERT INTO offers (content, price, date, job_id, sender_id, is_read) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(query);
		st.setString(1, offer.getContent());
		st.setInt(2, offer.getPrice());
		st.setString(3, offer.getDate());
		st.setLong(4, offer.getJob());
		st.setLong(5, offer.getSender());
		st.setInt(6, 0);
		st.execute();
		ResultSet res = st.getGeneratedKeys();
		res.next();
		long id = res.getLong(1);
		offer.setId(id);
		long jobID = offer.getJob();
		addNotification(offer);
		offersID.put(offer.getId(), offer);
		if(!offers.containsKey(jobID)){
			JobDAO.getJob(offer.getJob()).setStatus(2);
			query = "UPDATE jobs SET status=2 WHERE job_id=?";
			PreparedStatement st2 = DBManager.getInstance().getConnection().prepareStatement(query);
			st2.setLong(1, jobID);
			st2.execute();
			offers.put(jobID, new ArrayList<Offer>());
		}
		offers.get(jobID).add(offer);
	}
	
	private static void addNotification(Offer offer){
//		System.out.println(offer.getJob());
//		System.out.println(1);
//		System.out.println(offer.getId());
//		System.out.println(2);
//		System.out.println(offer.getJob());
//		System.out.println(3);
//		System.out.println(JobDAO.getJob(offer.getJob()).getEmployer());
//		System.out.println(4);
//		System.out.println(offer.getSenderUser().getFirstName());
//		System.out.println(5);
		JobDAO.getJob(offer.getJob()).getEmployer().addNotification(new Notification(offer.getSenderUser().getFirstName(), 4, offer.getId()));
	}
	
	private static void removeNotification(long notificationId, long userId){
		UserDAO.getUserID(userId).removeNotification(userId, notificationId);
	}
	
	public ArrayList<Offer> getJobOffers(long id){
		removeNotification(id, JobDAO.getJob(id).getEmployer().getId());
		return offers.get(id);
	}
	
	public HashMap<Long, Offer> getAllOffers(){		
		return offersID;
	}
	
	public Offer getOffer(long id){
		if(id > 0){
			return offersID.get(id);
		}
		else{
			return null;
		}
		
	}
	
	public boolean hasOffers(long id){
		return offersID.containsKey(id);
	}
	
}
