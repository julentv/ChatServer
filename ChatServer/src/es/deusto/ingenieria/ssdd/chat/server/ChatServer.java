package es.deusto.ingenieria.ssdd.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import es.deusto.ingenieria.ssdd.chat.data.User;


public class ChatServer {
	
	//Connected Users list
	private UserList userList;
	private int port;
	
	public ChatServer(int port){
		this.userList=new UserList();
		this.port=port;
	}
	public void start(){
		try {
			DatagramSocket udpSocket = new DatagramSocket(this.port);
			DatagramPacket request = null;
			byte[] buffer = new byte[1024];
			
			System.out.println(" - Server started '" + 
			                       udpSocket.getLocalAddress().getHostAddress() + ":" + 
					               port + "' ...");
			
			while(true){
				request = new DatagramPacket(buffer, buffer.length);
				udpSocket.receive(request);
				System.out.println(" - Received a request from '" + request.getAddress().getHostAddress() + ":" + request.getPort() + 
		                   "' -> " + new String(request.getData()));
				//procesar el mensaje en nuevo hilo con Message Processor
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void proccesMessage(DatagramPacket datagram){
		
	}
	public void sendDatagram(String message){
		
	}


}
