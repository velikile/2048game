package Solver;

import java.util.ArrayList;

import Model.Model;
import Model.Game2048Model.Game2048Model;

public class Node {
private int Val;
private int depth;
private boolean MaxPlayer;
private Node[] Children;
private String lastMove;
private Game2048Model State;
public  Node(){
	State=new Game2048Model();
	depth=5;
	MaxPlayer=true;
	SetChildren(MaxPlayer);
	
}
public Node(Model State,int depth, boolean MaxPlayer){
	this.State=new Game2048Model(State.getData(),State.GetScore());
	this.depth=depth;
	this.MaxPlayer=MaxPlayer;
	
	SetChildren(MaxPlayer); 
}

private void SetChildren(boolean MaxPlayer) {
		if(depth>0&&!TerminalNode())
		if(MaxPlayer){
			Model newState=new Game2048Model(State);
			String[]Move=State.GetAvailableMoves();
			int counter=0;
			for(String a:Move){
			if(a!=null)
				counter++;}
			Children=new Node[counter];
			counter=0;
			for(String a:Move){
				if(a!=null)
				switch(a){
				case "right":{
					newState.moveRight();
					Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove(a);
					
					break;
				}
				case "left":{
					newState.moveLeft();
					Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove(a);
					
					break;
				}
				case "up":{
					newState.moveUp();
					Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove(a);
					
					break;
				}
				case "down":{
					newState.moveDown();
					Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove(a);
					
					break;
				}
				
				}
				 }
				 
			}else {
				Game2048Model newState=new Game2048Model(State);
				int[][] Data=State.getData();
				int counter=0;
				int row=Data.length;
				int col=Data[0].length;
				 ArrayList<Integer> Free=State.FreeSpaces();
				 Children=new Node[Free.size()*2];
				 for(int a:Free){
					 Data=State.getData();
					 Data[a/row][a%col]=2;
					 newState.SetData(Data);
					 Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove("add2");
					 Data[a/row][a%col]=4;
					 newState.SetData(Data);
					 Children[counter++]=new Node(newState,depth-1,!MaxPlayer).setMove("add4");
						 
				 }
				 
					 
				 
				
				
			}
		
}
public boolean TerminalNode() {
	if(State.GameOver()||State.GameWon())
		return true;
	return false;
}
private Node setMove(String a) {

	lastMove=a;
	return this;
}
public Node[] getChildren(){
	return Children;
}

public int Huristic(){
	return State.getScore();
	
	
}
public Model getState(){
	return State;
	
}
public String getLastMove(){
	return lastMove;
	
	
}
public int getDepth(){
	return depth;
}
public void setVal(int Val) {
	this.Val=Val;
	
}
public int getVal() {
return Val;
}
	
	
	
	
	

}
