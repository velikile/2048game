package Solver;

import java.util.ArrayList;

import Model.Model;

public class Node {
private int depth;
private boolean MaxPlayer;
private Node[] Children;
private Model State;
public  Node(){
	
	
	
	
}
public Node(Model State,int depth, boolean MaxPlayer){
	this.State=State;
	this.depth=depth;
	SetChildren(!MaxPlayer); 
}

private void SetChildren(boolean MaxPlayer) {
		if(depth>0)
		if(MaxPlayer){
			String[]Move=State.GetAvailableMoves();
			ArrayList<String> Moves=new ArrayList<String>();
			int counter=0;
			for(String a:Move)
			if(a!=null)
				Moves.add(a);
			Children=new Node[Moves.size()];
			for(String a:Moves){
				switch(a){
				case "right":{
					State.moveRight();
					Children[counter]=new Node(State,depth-1,!MaxPlayer);
				break;
				}
				case "left":{
					State.moveLeft();
					
					break;
				}
				case "up":{
					State.moveUp();
					break;
				}
				case "down":{
					State.moveDown();
					break;
				}
				
				}	
				
			}
			}
		
		
}

	
	
	

}
