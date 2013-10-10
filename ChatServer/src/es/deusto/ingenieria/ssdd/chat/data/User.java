package es.deusto.ingenieria.ssdd.chat.data;

public class User {	
	private String nick;
	private String ip;
	private String port;
	
	public User(String nick, String ip, String port){
		this.nick=nick;
		this.ip=ip;
		this.port=port;
	}
	
	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
		
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {			
			return this.nick.equalsIgnoreCase(((User)obj).nick);				  
		} else {
			return false;
		}
	}
}