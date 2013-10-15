package es.deusto.ingenieria.ssdd.chat.server;

import java.net.DatagramPacket;
import java.util.ArrayList;

import es.deusto.ingenieria.ssdd.chat.data.Message;
import es.deusto.ingenieria.ssdd.chat.data.User;
import es.deusto.ingenieria.ssdd.chat.exceptions.IncorrectMessageException;

public class MessageProccesor extends Thread {

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
		//controlar errores aqui (mensaje desconocido y tal)
		try {
			generateMessage();
			treatMessage();
			
		} catch (IncorrectMessageException e) {
			//SI EL MENSAJE RECIBIDO NO ES CORRECTO ENVIAR AL EMISOR DE ESTE MENSAJE EL 305
			//TRATAR ESTO !!
			e.printStackTrace();
		}
		//reaccion al mensaje aqui

	}
	/**
	 * generates a message from the data of the datagram
	 * @throws IncorrectMessageException If the message cannot be correctly created.
	 */
	private void generateMessage() throws IncorrectMessageException{
		
		//obtain all the params from the datagram data
		String ip=messageToProcces.getAddress().getHostAddress();
		User userFrom=userList.getUserByIp(ip);
		User userTo=userList.getUserByIp(ip);
		String content=new String(messageToProcces.getData());
		int id=Integer.parseInt(content.substring(0, 3));
		if(content.length()>4){
			//also eliminate the first &
			content=content.substring(4);	
		}else{
			content=null;
		}
		
		
		//obtain the destination user if the message should have it
		if(Message.hasDestination(id)){
			try{
				String[] splited=content.split("&");
				String nickTo=splited[0];
				userTo=userList.getUserByNick(nickTo);
				if(splited.length>1){
					content=content.substring(nickTo.length()+1);
				}
			}catch(RuntimeException e){
				throw new IncorrectMessageException("Incorrect message. No destination user found");
			}
		}
		
		//pass all the params to the message object
		message.setFrom(userFrom);
		message.setTo(userTo);
		message.setText(content);
		message.setMessageType(id);
	}
	/**
	 * Dispatcher for the different message types
	 * @throws IncorrectMessageException
	 */
	private void treatMessage() throws IncorrectMessageException{
		if(message.isCrossMessage()){
			//todos los cross messages
			crossMessagesTreatment();
		}else{
			switch (message.getMessageType()){
				case Message.CLIENT_MESSAGE_LOGIN:
					//mensaje de login
					this.loginMessageTreatment();
					break;
				case Message.CLIENT_MESSAGE_CLOSE_CONNECTION:
					//cerrar conexion de un usuario
					this.closeConnectionTreatment();
					break;
				case Message.CLIENT_MESSAGE_GET_USERS:
					//devolver lista
					this.getConnectedUsersTreatment();
					break;
				default: throw new IncorrectMessageException("The message type code does not exist");
			}
		}
	}
	
	
	//Methods for the treatment of the different message types
	private void loginMessageTreatment() throws IncorrectMessageException{
		String content=message.getText();
		if(content!=null && !content.contains("&")){
			if(this.userList.getUserByNick(content)!=null){
				//The nick already exist (send 301)
				String response=new Integer(Message.ERROR_MESSAGE_EXISTING_NICK).toString();
				sendDatagram(message.getFrom(),response);
				//Comprobamos el nick pero, ¿Y si ya hay un usuario en esa IP???
			}else{
				//Everithing correct, send ACK
				String response=new Integer(Message.SERVER_MESSAGE_CONNECTED).toString()+this.userList.toString();
				sendDatagram(message.getFrom(),response);
			}
		}else{
			throw new IncorrectMessageException("The Login message is not correct: '"+content.toString()+"'");
		}
	}
	private void crossMessagesTreatment(){
		String response=message.getSimpleResponse();
		if(response!=null){
			this.sendDatagram(this.message.getTo(), response);
		}else{
			//User disconnected
			response=new Integer(Message.ERROR_MESSAGE_USER_IS_DISCONNECTED).toString()+this.userList.toString();
			this.sendDatagram(this.message.getFrom(), response);
		}
	}
	private void closeConnectionTreatment() throws IncorrectMessageException{
		if(this.userList.deleteByIp(this.message.getFrom().getIp())){
			sendDatagram(this.message.getFrom(), new Integer(Message.SERVER_MESSAGE_DISCONNECTED).toString());
		}else{
			throw new IncorrectMessageException("The user to disconect is not connected");
		}
	}
	private void getConnectedUsersTreatment(){
		String listOfUsers=this.userList.toString();
		sendDatagram(message.getFrom(),listOfUsers);
	}
	private void sendDatagram(User destinationUser, String message){
		//Que este controle lo de la longitud!!!!!
		
	}
}
