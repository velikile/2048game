package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.Model;


public class TCPClient {
	 private String Hint=null;
	public TCPClient(int [][] Data,int Score) throws Exception{
		setHint(ConnectAndGethint(Data,Score));
	}
	  public String ConnectAndGethint(int [][]Data,int Score) throws Exception  {  
		String sentence;  
		String modifiedSentence;  
		Socket clientSocket = new Socket("localhost", 6789); 
		ObjectOutputStream output=new ObjectOutputStream(clientSocket.getOutputStream());
		output.writeObject(Data);
		output.writeInt(Score);
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
