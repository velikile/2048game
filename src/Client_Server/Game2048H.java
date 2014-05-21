package Client_Server;

import java.util.ArrayList;
import java.util.Collections;

import Model.Game2048Model.Game2048Model;
import Solver.Node;

public class Game2048H implements Huristics {
				
	@Override
	public double Evaluation(Node N) {
		if(N.getState().GameOver())
			return Double.MIN_VALUE;
		else if(N.getState().GameWon())
			return Double.MAX_VALUE;
		return heuristicScore(N.getState().GetScore(), N.getState().CountEmptyCells(),this.calculateClusteringScore(N.getState().getData()));
	}
	
	public double VectorScore(int Data[][]){
		double score=0;
		int row=Data.length;
		int col=Data[0].length;
		//checking to see if there are misplaced tiles and setting them with a lower score
		ArrayList<Integer> Vector =new ArrayList<Integer>();
		
		for(int i=0;i<row-1;i++)
			for (int j=0;j<col-1;j++){
					if(Data[i][j]!=0&&i==j)
						Vector.add(Data[i][j]);
					if(Data[i][j]<=Data[i+1][j])
						score+=10;
					else score-=10;
					if(Data[i][j]<=Data[i][j+1])
						score+=10;
					else score-=10;
					if(i+1==row&&j+1==col&&GetMaxValue(Data)==Data[i+1][j+1])
						score+=1000;
					if(i>0&&i<row&&j>0&&j<col-1 &&GetMaxValue(Data)==Data[i][j]){
						score-=100;	
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
	private static int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
	private static int calculateClusteringScore(int[][] boardArray) {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }
                
                //clusteringScore-=boardArray[i][j];
                
                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }
                        
                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(boardArray[i][j]-boardArray[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }
	
	}
