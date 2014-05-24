package Controller;

import Client_Server.TCPServer;
/**
 * 
 * @author Lev veliki
 * this class runs the server
 *
 */
public class runServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new TCPServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
