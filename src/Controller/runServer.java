package Controller;

import Client_Server.Game2048H;
import Client_Server.TCPServer;

public class runServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TCPServer TS=new TCPServer(7,new Game2048H());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
