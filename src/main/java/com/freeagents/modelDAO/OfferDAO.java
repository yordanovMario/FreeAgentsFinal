package com.freeagents.modelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.freeagents.model.DBManager;
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
			while(res.next()){
				offer = new Offer(res.getLong("sender_id"), res.getLong("job_id"), res.getString("content"), Integer.parseInt(res.getString("price")), false);
				long jobID = Long.parseLong(res.getString("job_id"));
				offer.setId(Long.parseLong(res.getString("offer_id")));
				offer.setDate(res.getString("date"));
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
		offersID.put(offer.getId(), offer);
		if(!offers.containsKey(jobID)){
			JobDAO.getJob(offer.getJob()).setStatus(2);
			offers.put(jobID, new ArrayList<Offer>());
		}
		offers.get(jobID).add(offer);
	}
	
	public ArrayList<Offer> getJobOffers(long id){
		return offers.get(id);
	}
	
	public HashMap<Long, Offer> getAllOffers(){		
		return offersID;
	}
	
	public Offer getOffer(long id){
		return offersID.get(id);
	}
	
	public boolean hasOffers(long id){
		return offersID.containsKey(id);
	}
}
