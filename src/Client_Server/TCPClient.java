package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.Model;


public class TCPClient {
	 private String Hint=null;
	public TCPClient(Model M) throws Exception{
		setHint(ConnectAndGethint(M));
	}
	  public String ConnectAndGethint(Model M) throws Exception  {  
		String sentence;  
		String modifiedSentence;  
		Socket clientSocket = new Socket("localhost", 6789); 
		ObjectOutputStream output=new ObjectOutputStream(clientSocket.getOutputStream());
		output.writeObject(M);
		output.flush();
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));     
		modifiedSentence = inFromServer.readLine();  
		System.out.println("FROM SERVER: " + modifiedSentence); 
	clientSocket.close();
	return modifiedSentence;}
	  
	public String getHint() {
		return Hint;
	}
	public void setHint(String hint) {
		Hint = hint;
	}
	
}
