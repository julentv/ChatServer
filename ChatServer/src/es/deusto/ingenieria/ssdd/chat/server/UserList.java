package es.deusto.ingenieria.ssdd.chat.server;

import java.util.ArrayList;

import es.deusto.ingenieria.ssdd.chat.data.User;

public class UserList {
	private ArrayList<User> listOfUsers;

	public UserList() {
		listOfUsers=new ArrayList<User>();
	}
	/**
	 * 
	 * @param ip
	 * @return the user if exists null if not.
	 */
	public User getUserByIp (String ip){
		for(User u:listOfUsers){
			if(u.getIp().equals(ip)) return u;
		}
		return null;
	}
	
	/**
	 * 
	 * @param nick
	 * @return the user if exists null if not.
	 */
	public User getUserByNick (String nick){
		for(User u:listOfUsers){
			if(u.getNick().equals(nick)) return u;
		}
		return null;
	}

}