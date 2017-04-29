package com.freeagents.modelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.freeagents.model.DBManager;
import com.freeagents.model.Feedback;
import com.freeagents.model.Message;
import com.freeagents.model.Notification;

public class FeedbackDAO {

	private static FeedbackDAO instance;
	//Hashmap with all received feedbacks with key - user id
	private static HashMap<Long, ArrayList<Feedback>> receivedUser = new HashMap<Long, ArrayList<Feedback>>();
	//Hashmap with all feedbacks with key - feedback id
	private static HashMap<Long, Feedback> feedbacks = new HashMap<Long, Feedback>();
		
	private FeedbackDAO(){
		reloadCache();
	}
	
	public static synchronized FeedbackDAO getInstance(){
		if(instance == null){
			instance = new FeedbackDAO();
		}
		return instance;
	}
	
	private void reloadCache(){
		if(receivedUser.isEmpty() ){
			String query = "SELECT feedback_id, content, rating, date, sender_id, receiver_id, is_read FROM feedbacks";
			java.sql.PreparedStatement st;
			try {
				st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
				ResultSet res = st.executeQuery();
				Feedback feedback;
				long sender;
				long receiver;
				while(res.next()){
					sender = res.getLong("sender_id");
					receiver = res.getLong("receiver_id");
					feedback = new Feedback(res.getLong("feedback_id"), res.getString("content"), UserDAO.getUserID(sender), UserDAO.getUserID(receiver), 
							res.getString("date"), res.getInt("rating"), res.getInt("is_read"));
					if(!feedback.isRead()){
						addNotification(feedback);
					}
					feedbacks.put(feedback.getId(), feedback);
					if(!receivedUser.containsKey(receiver)){
						receivedUser.put(receiver, new ArrayList<Feedback>());
					}
					receivedUser.get(receiver).add(feedback);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage() + " SQL Exception in relaodCache()");
				e.printStackTrace();
			}
			System.out.println("Feedback cache collections reloaded");
		}
	}
	
	public static synchronized void sendFeedback(Feedback feedback){
		String query = "INSERT INTO feedbacks (content, rating, date, sender_id, receiver_id, is_read) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement st;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement(query);
			st.setString(1, feedback.getContent());
			st.setInt(2, feedback.getRating());
			st.setString(3, feedback.getDate());
			st.setLong(4, feedback.getSender().getId());
			st.setLong(5, feedback.getReceiver().getId());
			st.setInt(6, 0);
			st.execute();
			ResultSet res = st.getGeneratedKeys();
			res.next();
			long id = res.getLong(1);
			feedback.setId(id);
			addNotification(feedback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		feedbacks.put(feedback.getId(), feedback);
		if(!receivedUser.containsKey(feedback.getReceiver().getId())){
			receivedUser.put(feedback.getReceiver().getId(), new ArrayList<Feedback>());
		}
		receivedUser.get(feedback.getReceiver().getId()).add(feedback);
		
	}
	
	private static void addNotification(Feedback feedback){
		feedback.getReceiver().addNotification(new Notification(feedback.getSender().getFirstName(), 2, feedback.getId()));
	}
	
	private static void removeNotification(long notificationId, long userId){
		UserDAO.getUserID(userId).removeNotification(notificationId);
	}	
	
	public static synchronized ArrayList<Feedback> getReceived(long id){
		return receivedUser.get(id);
	}
	
	public HashMap<Long, Feedback> getAllFeedbacks(){		
		return feedbacks;
	}
	
	public static synchronized Feedback readFeedback(long feedbackID, long notificationID){
		Feedback feedback = feedbacks.get(feedbackID);
		feedback.setSeen(true);
		String query = "UPDATE feedbacks SET is_read=1 WHERE feedback_id = ?";
		PreparedStatement st;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement(query);
			st.setLong(1, feedbackID);
			st.execute();
			if(notificationID != 0){
				removeNotification(notificationID, feedback.getReceiver().getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return feedback;
	}
}