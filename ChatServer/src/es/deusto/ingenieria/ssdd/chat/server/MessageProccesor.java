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
	//controlar errores al llamar a este metodo
	//lanzar error de mensaje desconocido
	private void generateMessage(){
		
		//obtain all the params from the datagram data
		String ip=messageToProcces.getAddress().getHostAddress();
		User userFrom=userList.getUserByIp(ip);
		User userTo=userList.getUserByIp(ip);
		String content=new String(messageToProcces.getData());
		int id=Integer.parseInt(content.substring(0, 3));
		content=content.substring(3);
		
		//obtain the destination user if the message has it
		if(Message.hasDestination(id)){
			String nickTo=content.split("&")[0];
			userTo=userList.getUserByNick(nickTo);
			content=content.substring(nickTo.length());
		}
		
		//pass all the params to the message object
		message.setFrom(userFrom);
		message.setTo(userTo);
		message.setText(content);
		message.setMessageType(id);
	}

}
