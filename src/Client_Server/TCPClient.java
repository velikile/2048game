package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.Game2048Model.Game2048Model;

public class TCPClient {
	static Game2048Model M=new Game2048Model();
	  public static void main(String argv[]) throws Exception  {  
		String sentence;  
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));  
		Socket clientSocket = new Socket("localhost", 6789); 
		ObjectOutputStream output=new ObjectOutputStream(clientSocket.getOutputStream());
		output.writeObject(M);
		output.flush();
//		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
		//sentence = inFromUser.readLine();   
		//modifiedSentence = inFromServer.readLine();   System.out.println("FROM SERVER: " + modifiedSentence); 
	clientSocket.close();  }
	
}
