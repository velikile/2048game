package Client_Server;

import Solver.Node;

public class Game2048H implements Huristics {
				
	@Override
	public int Evaluation(Node N) {
		if(N.getState().GameOver())
			return Integer.MIN_VALUE;
		else if(N.getState().GameWon())
			return Integer.MAX_VALUE;
		return (int) (N.getState().GetScore()+N.getState().CountEmptyCells())*VectorScore(N.getState().getData());
	}
	
	public int VectorScore(int Data[][]){
		int score=0;
		for(int i=0;i<Data.length-1;i++)
			for (int j=0;j<Data[0].length-1;j++){
					if(Data[i][j]<=Data[i+1][j])
						score++;
					else score--;
					if(Data[i][j]<=Data[i][j+1])
						score++;
					else score--;
					
					if(i>0&&i<Data.length-1&&j>0&&j<Data.length-1 &&GetMaxValue(Data)==Data[i][j]){
						score-=20;
						
					}
				
				
			}
		return score;
		
		
	}
	public int GetMaxValue(int Data[][]){
		int max=0;
		for(int i[]:Data)
			for (int j:i)
				if(max<=j)
					max=j;
		return max;
	}
	}
