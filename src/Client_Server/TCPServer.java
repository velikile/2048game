package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Model.Model;
import Model.Game2048Model.Game2048Model;
import Solver.Node;

public class TCPServer {
				int depth=4;
				
	 public  void StartServer() throws Exception     
	 {  
		 String clientSentence;          
		 String capitalizedSentence;     
		 ServerSocket welcomeSocket = new ServerSocket(6789);        
		 while(true){
			 Socket connectionSocket = welcomeSocket.accept();
			 connectionSocket.setSoTimeout(10);
			 ObjectInputStream  input =  new ObjectInputStream(connectionSocket.getInputStream());
			 Model M=(Model) input.readObject();
			 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());    
			 capitalizedSentence = GetTheBestMove(M) + M+ '\n';      
			 outToClient.writeBytes(capitalizedSentence);     
			
		 }  
		 }
	private  String GetTheBestMove(Model m) {
		String move=null;// the Decision will be here
		Node DTree=new Node(m,depth,true);//Decision Tree for the Model
		
		return move;
		
		
	} 
}
