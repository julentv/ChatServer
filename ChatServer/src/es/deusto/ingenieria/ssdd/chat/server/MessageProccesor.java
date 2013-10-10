package es.deusto.ingenieria.ssdd.chat.server;

import java.net.DatagramPacket;
import java.util.ArrayList;

import es.deusto.ingenieria.ssdd.chat.data.Message;

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

}
