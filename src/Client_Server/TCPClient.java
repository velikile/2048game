package Client_Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPClient {
	private String name=null;
	private String Hint=null;
	int [][]Data=null;
	int Score=0;
	public boolean isServerUp=true;
	Socket clientSocket=null;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	public TCPClient(final int [][] Data,final int Score,final boolean wantsHint) throws Exception{
		 this.Data=Data;
		 this.Score=Score;	
		 
	}
	public boolean Connect(){
		try {
			clientSocket = new Socket("localhost", 6789);
			if(clientSocket.isBound()){
				isServerUp=true;
			}
		} catch (UnknownHostException e) {
			isServerUp=false;
			
		} catch (IOException e) {
			isServerUp=false;
		}
		if(isServerUp){
			try {
				output=new ObjectOutputStream(clientSocket.getOutputStream());
				input=new ObjectInputStream(clientSocket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			
		}
		return isServerUp;
	}

	public String Send2Server(int [][]Data,int Score,int SearchDepth){
		String InfromServer=null;
		try {
			 
			output.writeObject(Data);
			output.writeObject(Score);
			output.writeObject(SearchDepth);
			InfromServer=(String)input.readObject();
			//System.out.println(InfromServer);
		} catch (IOException | ClassNotFoundException | NullPointerException e) {
			System.out.println("Too many Clients Cant get Hint");
		
		}
		return InfromServer;
		
	}
	
	
	public String getHint() {
		return Hint;
	}
	public void setHint(String hint) {
		Hint = hint;
	}
	  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public boolean isServerUp() {
		return isServerUp;
	}

	
}
