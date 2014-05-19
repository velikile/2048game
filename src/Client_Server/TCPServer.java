package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import Model.Model;
import Model.Game2048Model.Game2048Model;
import Solver.Node;

public class TCPServer {
		String Hint;
	    static final int SEC=1000;
		int depth;
		Huristics H;
		ExecutorService threadPool=Executors.newFixedThreadPool(10);
	public TCPServer(int depth,Huristics H) throws Exception{
		this.H=H;
		this.depth=depth;
		StartServer();
		
	}			
	 public  void StartServer() throws Exception   {       
		 ServerSocket welcomeSocket = new ServerSocket(6789);        
		 threadPool.execute(new ServerThread(welcomeSocket));
		 
		 }
	private  String GetTheBestMove(Node N) {
		int Valone=MiniMax(N,depth,true);
		int Valtwo=alphaBeta(N, depth,Integer.MIN_VALUE,Integer.MAX_VALUE, true);
		//int Valthree=ExpectMax(N,depth,false);
		//System.out.println(Valone+"**"+Valtwo);
		int BestScore=Integer.MIN_VALUE;
		Node BestNode=null;
		if(N.getChildren()!=null)
		for(Node a:N.getChildren()){
			System.out.println(a.getLastMove()+" Score is :"+a.getVal()+"alpahBeta "+Valtwo	);
			if(BestScore<a.getVal()){
				BestNode=a;
				BestScore=a.getVal();}
		}// the Decision will be here
		return BestNode.getLastMove();
		
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
	private int ExpectMax(Node n, int depth, boolean RandomEvent) {
		
		if(n.TerminalNode()||depth==0){
			return H.Evaluation(n);}
		else{
			int BestVal=0;
			int Min=Integer.MIN_VALUE;
			int Max=Integer.MAX_VALUE;
		if(!RandomEvent){
			BestVal=Min;
			for(Node a:n.getChildren()){
			a.setVal(ExpectMax(a,depth-1,!RandomEvent));
			BestVal=Math.max(BestVal,a.getVal());}
			return BestVal;
		}	
		else	{
			BestVal=0;
			for(Node a:n.getChildren()){
			a.setVal( (int)Probability(a)*ExpectMax(a, depth-1,!RandomEvent));
			BestVal+=a.getVal();
			
		}	n.setVal(BestVal/n.getChildren().length);
			return BestVal/n.getChildren().length;
		}}
		
} 
	private double Probability(Node a) {
		if(a.getChildren()!=null)
		if(a.getChildren().length==0)
			return 0.2;
		else if(a.getLastMove()=="add2tile")
			return 0.9*(1/a.getState().CountEmptyCells());
		else if(a.getLastMove()=="add4tile")
			return 0.1*(1/a.getState().CountEmptyCells());
		return 0;
	}
	private int alphaBeta(Node n, int depth,int a,int b, boolean maxPlayer) {
		if(n.TerminalNode()||depth==0){
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
			n.setVal(a);
			return b;
		}	

}
	class ServerThread extends Thread{
	ServerSocket welcomeSock;
	String H=Hint;
	ServerThread(ServerSocket welcomeSock){
		this.welcomeSock=welcomeSock;
		
	}
	public void run(){
	
		while(true)
		try {
		Socket Sock = welcomeSock.accept();
		ObjectInputStream  input =  new ObjectInputStream(Sock.getInputStream());
		 Node Sol=new Node((Model) input.readObject(),depth,true);
		 DataOutputStream outToClient = new DataOutputStream(Sock.getOutputStream());    
		 Hint = GetTheBestMove(Sol) + '\n';      
		 outToClient.writeBytes(Hint);     
			Sock.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
	
}
