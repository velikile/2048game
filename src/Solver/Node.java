package Solver;

import Model.Model;

public class Node {
Node[] Children;
Model State;

public Node(Model State,int depth, boolean MaxPlayer){
	this.State=State;
	while (depth>=0){
		depth-=1;
	SetChildren(!MaxPlayer);} 
}

private void SetChildren(boolean MaxPlayer) {
		Children=State.GetAvailableMoves(MaxPlayer);
		
}

	
	
	

}
