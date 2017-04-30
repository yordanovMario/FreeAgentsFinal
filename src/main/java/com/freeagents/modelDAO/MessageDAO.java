package com.freeagents.modelDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.freeagents.model.DBManager;
import com.freeagents.model.Message;
import com.freeagents.model.Notification;


public class MessageDAO {
	
	private static MessageDAO instance;
	//Hashmap with all received messages with key - user id
	private static HashMap<Long, ArrayList<Message>> receivedUser = new HashMap<Long, ArrayList<Message>>();
	//Hashmap with all sent messages with key - user id
	private static HashMap<Long, ArrayList<Message>> sentUser = new HashMap<Long, ArrayList<Message>>();
	//Hashmap with all messages wit key - message id
	private static HashMap<Long, Message> messages = new HashMap<Long, Message>();
	
	private MessageDAO(){
		reloadCache();
	}
	
	public static synchronized MessageDAO getInstance(){
		if(instance == null){
			instance = new MessageDAO();
		}
		return instance;
	}
	
	private void reloadCache(){
		if(receivedUser.isEmpty() ){
			String query = "SELECT message_id, title, content, date, sender_id, receiver_id, is_read FROM messages";
			java.sql.PreparedStatement st;
			try {
				st = DBManager.getInstance().getConnection().clientPrepareStatement(query);
				ResultSet res = st.executeQuery();
				Message message;
				long sender;
				long receiver;
				while(res.next()){
					sender = res.getLong("sender_id");
					receiver = res.getLong("receiver_id");
					message = new Message(res.getLong("message_id"), sender, receiver, res.getString("title"), 
							res.getString("content"), res.getString("date"), res.getInt("is_read"));
					if(!message.isRead()){
						addNotification(message);
					}
					messages.put(message.getId(), message);
					if(!receivedUser.containsKey(receiver)){
						receivedUser.put(receiver, new ArrayList<Message>());
					}
					receivedUser.get(receiver).add(0, message);
					
					if(!sentUser.containsKey(sender)){
						sentUser.put(sender, new ArrayList<Message>());
					}
					sentUser.get(sender).add(0, message);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage() + " SQL Exception in relaodCache()");
				e.printStackTrace();
			}
			System.out.println("Message cache collections reloaded");
		}
	}
	
	public static synchronized void sendMessage(Message message){
		String query = "INSERT INTO messages (title, content, date, sender_id, receiver_id, is_read) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement st;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement(query);
			st.setString(1, message.getTitle());
			st.setString(2, message.getContent());
			st.setString(3, message.getDate());
			st.setLong(4, message.getSender().getId());
			st.setLong(5, message.getReceiver().getId());
			st.setInt(6, 0);
			st.execute();
			ResultSet res = st.getGeneratedKeys();
			res.next();
			long id = res.getLong(1);
			message.setId(id);
			addNotification(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		messages.put(message.getId(), message);
		
		if(!receivedUser.containsKey(message.getReceiver().getId())){
			receivedUser.put(message.getReceiver().getId(), new ArrayList<Message>());
		}
		receivedUser.get(message.getReceiver().getId()).add(0, message);
		
		if(!sentUser.containsKey(message.getSender().getId())){
			sentUser.put(message.getSender().getId(), new ArrayList<Message>());
		}
		sentUser.get(message.getSender().getId()).add(0, message);
	}
	
	private static void addNotification(Message message){
		message.getReceiver().addNotification(new Notification(message.getSender().getFirstName(), 1, message.getId()));
	}
	
	private static void removeNotification(long notificationId, long userId){
		UserDAO.getUserID(userId).removeNotification(notificationId);
	}
	
	public static synchronized ArrayList<Message> getReceived(long id){
		return receivedUser.get(id);
	}
	
	public static synchronized ArrayList<Message> getSent(long id){
		return sentUser.get(id);
	}
	
	public HashMap<Long, Message> getAllMessages(){		
		return messages;
	}
	
	public static synchronized Message readMessage(long messageID, long notificationID){
		Message message = messages.get(messageID);
		System.out.println(message);
		System.out.println(message.isRead());
		message.setRead(true);
		
		String query = "UPDATE messages SET is_read=1 WHERE message_id = ?";
		PreparedStatement st;
		try {
			st = DBManager.getInstance().getConnection().prepareStatement(query);
			st.setLong(1, messageID);
			st.execute();
			if(notificationID != 0){
				removeNotification(notificationID, message.getReceiver().getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}
	
}
