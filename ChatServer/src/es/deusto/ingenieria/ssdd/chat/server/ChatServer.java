package es.deusto.ingenieria.ssdd.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


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
			System.out.println("________ Server started '" + 
			                       udpSocket.getLocalAddress().getHostAddress() + ":" + 
					               port + "' ...");
			
			while(true){
				byte[] buffer = new byte[1024];
				request = new DatagramPacket(buffer, buffer.length);
				udpSocket.receive(request);
				System.out.println(" - Received a request from '" + request.getAddress().getHostAddress() + ":" + request.getPort() + 
		                   "' -> " + new String(request.getData()));
				MessageProccesor procesor= new MessageProccesor(request, userList);
				
				//procesar el mensaje en nuevo hilo con MessageProcessor
				procesor.start();
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
