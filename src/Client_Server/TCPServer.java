package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Model.Model;
import Model.Game2048Model.Game2048Model;

public class TCPServer {
	
	
		private static boolean timeOutFlag=false;
	 public static void main(String argv[]) throws Exception     
	 {  
		 String clientSentence;          
		 String capitalizedSentence;     
		 ServerSocket welcomeSocket = new ServerSocket(6789);        
		 while(!timeOutFlag)          {
			 Socket connectionSocket = welcomeSocket.accept();
			 connectionSocket.setSoTimeout(10);
			 
			 ObjectInputStream  input =  new ObjectInputStream(connectionSocket.getInputStream());
			 Model M=(Model) input.readObject();
			 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());    
			 capitalizedSentence = GetTheBestMove(M) + M+ '\n';      
			 outToClient.writeBytes(capitalizedSentence);     
			 if(connectionSocket.isClosed())
				 timeOutFlag=true;
		 }  
		 }
	private static String GetTheBestMove(Model m) {
		// TODO Auto-generated method stub
		return null;
	} 
}
