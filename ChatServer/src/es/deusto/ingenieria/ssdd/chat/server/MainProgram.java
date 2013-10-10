package es.deusto.ingenieria.ssdd.chat.server;

public class MainProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChatServer server= new ChatServer(6789);
		server.start();

	}

}
