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
	    static final int SEC=1000;
		int depth;
		Huristics H;
	public TCPServer(int depth,Huristics H){
		this.H=H;
		this.depth=depth;
	}			
	 public  void StartServer() throws Exception     
	 {  
		 String Hint;     
		 ServerSocket welcomeSocket = new ServerSocket(6789);        
		 while(true){
			 Socket connectionSocket = welcomeSocket.accept();
			 connectionSocket.setSoTimeout(60*SEC);
			 ObjectInputStream  input =  new ObjectInputStream(connectionSocket.getInputStream());
			 Node Sol=new Node((Model) input.readObject(),depth,true);
			 DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());    
			 Hint = GetTheBestMove(Sol) + '\n';      
			 outToClient.writeBytes(Hint);     
			
		 }  
		 }
	private  String GetTheBestMove(Node N) {
		String move=null;
		//int Valone=MiniMax(N,depth,true);
		int Valtwo=alphaBeta(N, depth,Integer.MIN_VALUE,Integer.MAX_VALUE, true);
		//System.out.println(Valone+"**"+Valtwo);
		if(N.getChildren()!=null)
		for(Node a:N.getChildren()){
			//System.out.println(a.getLastMove()+" Score is :"+a.getVal()+"alpahBeta "+Valtwo	);
			if(Valtwo==a.getVal()){
				move=a.getLastMove();}
		}// the Decision will be here
		return move;
		
	}
	private int MiniMax(Node n, int depth, boolean maxPlayer) {
				
			if(n.TerminalNode()||depth==0){
				n.setVal(H.Evaluation(n));
				return H.Evaluation(n);}
			else{
				int BestVal=0;
				int Min=Integer.MIN_VALUE;
				int Max=Integer.MAX_VALUE;
			if(maxPlayer){
				BestVal=Min;
				for(Node a:n.getChildren()){
				a.setVal(MiniMax(a,depth-1,!maxPlayer));
				BestVal=Math.max(BestVal,a.getVal());}
				return BestVal;
			}	
			else	{
				BestVal=Max;
				for(Node a:n.getChildren()){
				a.setVal(MiniMax(a,depth-1,!maxPlayer));
				BestVal=Math.min(BestVal,a.getVal());}
				return BestVal;
			}	
			}
	} 
	private int alphaBeta(Node n, int depth,int a,int b, boolean maxPlayer) {
		if(n.TerminalNode()||depth==0){
			n.setVal(H.Evaluation(n));
			return H.Evaluation(n);}
		if(maxPlayer)	{
			for (Node Child:n.getChildren()){
			a=Math.max(a,alphaBeta(Child,depth-1,a,b,false));
			if(b<=a)
				break;}
			n.setVal(a);
			return a;
		}	
		else{
			for (Node Child:n.getChildren()){
			b=Math.min(b,alphaBeta(Child,depth-1,a,b,true));
				if(b<=a)
				break;}
			n.setVal(b);
			return b;
		}	

} 
}
