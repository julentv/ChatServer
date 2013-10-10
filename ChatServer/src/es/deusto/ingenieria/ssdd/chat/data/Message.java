package es.deusto.ingenieria.ssdd.chat.data;

import java.net.DatagramPacket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	//Client message types
	private static final int CLIENT_MESSAGE_LOGIN=101;
	private static final int CLIENT_MESSAGE_ESTABLISH_CONNECTION=102;
	private static final int CLIENT_MESSAGE_ACCEPT_INVITATION=103;
	private static final int CLIENT_MESSAGE_REJECT_INVITATION=104;
	private static final int CLIENT_MESSAGE_CLOSE_CONVERSATION=105;
	private static final int CLIENT_MESSAGE_CLOSE_CONNECTION=106;
	private static final int CLIENT_MESSAGE_GET_USERS=107;
	private static final int CLIENT_MESSAGE=108;
	
	//Server message types
	private static final int SERVER_MESSAGE_CONNECTED=201;
	private static final int SERVER_MESSAGE_INVITATTION=202;
	private static final int SERVER_MESSAGE_INVITATTION_ACCEPTED=203;
	private static final int SERVER_MESSAGE_INVITATTION_REJECTED=204;
	private static final int SERVER_MESSAGE_CONVERSATION_CLOSED=205;
	private static final int SERVER_MESSAGE_DISCONNECTED=206;
	private static final int SERVER_MESSAGE_LIST_OF_USERS=207;
	private static final int SERVER_MESSAGE=208;
	
	//ERROR MESSAGES
	private static final int ERROR_MESSAGE_EXISTING_NICK=301;
	private static final int ERROR_MESSAGE_CONNECTION_FAILED=302;
	private static final int ERROR_MESSAGE_USER_ALREADY_CHATTING=303;
	private static final int ERROR_MESSAGE_USER_IS_DISCONNECTED=304;
	private static final int ERROR_MESSAGE_MESSAGE_ERROR=305;
	
	//MESSAGES WITH DESTINATION NICK AS SECON PARAMETER
	private static int[]MESSAGES_WITH_DESTINATION={CLIENT_MESSAGE_ESTABLISH_CONNECTION,CLIENT_MESSAGE_ACCEPT_INVITATION,CLIENT_MESSAGE_REJECT_INVITATION,CLIENT_MESSAGE};
	
	
	private long timestamp;
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	private String text;
	private int messageType;
	private User from;
	private User to;
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
	
	public Message(){
		
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public User getFrom() {
		return from;
	}
	
	public void setFrom(User from) {
		this.from = from;
	}
	
	public User getTo() {
		return to;
	}
	
	public void setTo(User to) {
		this.to = to;
	}
	
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			Message otherMsg = (Message)obj;
			
			return this.timestamp == otherMsg.timestamp &&
				   this.text.equals(otherMsg.text) &&
				   this.from.equals(otherMsg.from) &&
				   this.to.equals(otherMsg.to);
		} else {
			return false;
		}
	}
	
	public String toString() {
		return "[" + dateFormatter.format(new Date(this.timestamp)) + "] '" + 
	           this.from + " -> " + this.to + " : " + this.text; 
				
	}
	public static boolean hasDestination(int code){
		for(int i:Message.MESSAGES_WITH_DESTINATION){
			if(i==code)return true;
		}
		return false;
	}
}