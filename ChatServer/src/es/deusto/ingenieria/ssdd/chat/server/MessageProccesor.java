package es.deusto.ingenieria.ssdd.chat.server;

import java.net.DatagramPacket;
import java.util.ArrayList;

import es.deusto.ingenieria.ssdd.chat.data.Message;
import es.deusto.ingenieria.ssdd.chat.data.User;

public class MessageProccesor implements Runnable {

	private DatagramPacket messageToProcces;
	private UserList userList;
	private Message message;
	
	public MessageProccesor(DatagramPacket messageToProcces, UserList userList) {
		this.messageToProcces=messageToProcces;
		this.userList=userList;
		this.message=new Message();
		
	}

	@Override
	public void run() {
		

	}
	private void generateMessage(){
		String ip=messageToProcces.getAddress().getHostAddress();
		User user=userList.getUserByIp(ip);
		
		
	}

}
