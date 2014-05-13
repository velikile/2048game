package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Model.Game2048Model.Game2048Model;

public class TCPServer {
	 public static void main(String argv[]) throws Exception     
	 {  
		 String clientSentence;          
		 String capitalizedSentence;     
		 ServerSocket welcomeSocket = new ServerSocket(6789);        
		 while(true)          {
			 Socket connectionSocket = welcomeSocket.accept();      
			 ObjectInputStream  input =  new ObjectInputStream(connectionSocket.getInputStream());
			 Game2048Model M=(Game2048Model) input.readObject();
			 System.out.println(M);
			 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());    
			 //clientSentence = inFromClient.readLine();    
			 //System.out.println("Received: " + clientSentence);    
			 //capitalizedSentence = clientSentence.toUpperCase() + '\n';      
			 //outToClient.writeBytes(capitalizedSentence);     
			 }  
		 } 
}
