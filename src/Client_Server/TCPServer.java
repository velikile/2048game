package Client_Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
		 threadPool.shutdown();
	 }
	 
	private  String GetTheBestMove(Node N) {
		String move=null;
		double Valone=MiniMax(N,depth,true);
		//double Valtwo=alphaBeta(N, depth,Integer.MIN_VALUE,Integer.MAX_VALUE, true);
		//double Valthree=ExpectMax(N,depth,false);
	//	System.out.println(Valone+"**"+Valtwo+"***"+Valthree);
		if(N.getChildren()!=null)
		for(Node a:N.getChildren()){
			//System.out.println(a.getLastMove()+" Score is :"+a.getVal()+"alpahBeta "+Valthree);
			//System.out.println(a.getLastMove()+" Score is :"+a.getVal()+"minimax "+Valtwo	);
			if(Valone==a.getVal()){
				move=a.getLastMove();}
		}// the Decision will be here
		return move;
		
	}
	private double MiniMax(Node n, int depth, boolean maxPlayer) {
				
			if(n.TerminalNode()||depth==0){
				return H.Evaluation(n);}
			else{
				double BestVal=0;
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
	private double ExpectMax(Node n, int depth, boolean RandomEvent) {
		
		if(n.TerminalNode()||depth==0){
			return H.Evaluation(n);}
		
			double A=0;
			double Min=Integer.MIN_VALUE;
		if(!RandomEvent){
			A=Min;
			for(Node a:n.getChildren()){
				a.setVal(ExpectMax(a,depth-1,RandomEvent));
				A=Math.max(A,a.getVal());
				}
				
		}	
		else	{
			A=0;
			for(Node a:n.getChildren()){
			A+=(Probability(a)*ExpectMax(a, depth-1,!RandomEvent));
			System.out.println(A);
			}n.setVal(A);	
		}
		return A;
		
} 
	
	
	private double Probability(Node a) {
		if(a.getChildren()!=null)
		if(a.getChildren().length==0)
			return 1;
		else if(a.getLastMove()=="add2tile"){
			return 0.9*(1/a.getState().CountEmptyCells());
			
		}
		else if(a.getLastMove()=="add4tile"){
			
			return 0.1*(1/a.getState().CountEmptyCells());
			
		}
		System.out.println(0.1*(1/a.getState().CountEmptyCells()));
		return 1;
	}
	
	
	private double alphaBeta(Node n, int depth,double a,double b, boolean maxPlayer) {
		if(n.TerminalNode()||depth==0){
			return H.Evaluation(n);}
		if(maxPlayer)	{
			for (Node Child:n.getChildren()){
			a=Math.max(a,alphaBeta(Child,depth-1,a,b,false));
			Child.setVal(a);
			if(b<=a)
				break;
				
			}
			
			return a;
		}	
		else{
			for (Node Child:n.getChildren()){
			b=Math.min(b,alphaBeta(Child,depth-1,a,b,true));
			Child.setVal(b);
				if(b<=a)
				break;			
		}	
			return b;
}}
	
	
	
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
		 int [][] Data= (int[][]) input.readObject();
		 int Score=input.readInt();
		 Board theGame = new Board(Data,Score);
	        Direction hint = null;
			try {
				hint = AIsolver.findBestMove(theGame, depth);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//	 Node Sol=new Node(new Game2048Model(Data,Score),depth,true);
		 DataOutputStream outToClient = new DataOutputStream(Sock.getOutputStream());    
		 Hint = hint.toString() + '\n';      
		 outToClient.writeBytes(Hint);     
			Sock.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}}
	
	/* 
	 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
	 *
	 * This program is free software: you can redistribute it and/or modify
	 * it under the terms of the GNU General Public License as published by
	 * the Free Software Foundation, either version 3 of the License, or
	 * (at your option) any later version.
	 *
	 * This program is distributed in the hope that it will be useful,
	 * but WITHOUT ANY WARRANTY; without even the implied warranty of
	 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 * GNU General Public License for more details.
	 *
	 * You should have received a copy of the GNU General Public License
	 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
	 */
	/**
	 * The AIsolver class that uses Artificial Intelligence to estimate the next move.
	 * 
	 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
	 */
	class AIsolver {
	    
	    /**
	     * Player vs Computer enum class
	     */
	    public enum Player {
	        /**
	         * Computer
	         */
	        COMPUTER, 

	        /**
	         * User
	         */
	        USER
	    }
	    
	    /**
	     * Method that finds the best next move.
	     * 
	     * @param theBoard
	     * @param depth
	     * @return
	     * @throws CloneNotSupportedException 
	     */
	    public static Direction findBestMove(Board theBoard, int depth) throws CloneNotSupportedException {
	        //Map<String, Object> result = minimax(theBoard, depth, Player.USER);
	        
	        Map<String, Object> result = alphabeta(theBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);
	        
	        return (Direction)result.get("Direction");
	    }
	    
	    /**
	     * Finds the best move by using the Minimax algorithm.
	     * 
	     * @param theBoard
	     * @param depth
	     * @param player
	     * @return
	     * @throws CloneNotSupportedException 
	     */
	    private static Map<String, Object> minimax(Board theBoard, int depth, Player player) throws CloneNotSupportedException {
	        Map<String, Object> result = new HashMap<>();
	        
	        Direction bestDirection = null;
	        int bestScore;
	        
	        if(depth==0 || theBoard.isGameTerminated()) {
	            bestScore=heuristicScore(theBoard.getScore(),theBoard.getNumberOfEmptyCells(),calculateClusteringScore(theBoard.getBoardArray()));
	        }
	        else {
	            if(player == Player.USER) {
	                bestScore = Integer.MIN_VALUE;

	                for(Direction direction : Direction.values()) {
	                    Board newBoard = (Board) theBoard.clone();

	                    int points=newBoard.move(direction);
	                    
	                    if(points==0 && newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
	                    	continue;
	                    }

	                    Map<String, Object> currentResult = minimax(newBoard, depth-1, Player.COMPUTER);
	                    int currentScore=((Number)currentResult.get("Score")).intValue();
	                    if(currentScore>bestScore) { //maximize score
	                        bestScore=currentScore;
	                        bestDirection=direction;
	                    }
	                }
	            }
	            else {
	                bestScore = Integer.MAX_VALUE;

	                List<Integer> moves = theBoard.getEmptyCellIds();
	                if(moves.isEmpty()) {
	                    bestScore=0;
	                }
	                int[] possibleValues = {2, 4};

	                int i,j;
	                int[][] boardArray;
	                for(Integer cellId : moves) {
	                    i = cellId/Board.BOARD_SIZE;
	                    j = cellId%Board.BOARD_SIZE;

	                    for(int value : possibleValues) {
	                        Board newBoard = (Board) theBoard.clone();
	                        newBoard.setEmptyCell(i, j, value);

	                        Map<String, Object> currentResult = minimax(newBoard, depth-1, Player.USER);
	                        int currentScore=((Number)currentResult.get("Score")).intValue();
	                        if(currentScore<bestScore) { //minimize best score
	                            bestScore=currentScore;
	                        }
	                    }
	                }
	            }
	        }
	        
	        result.put("Score", bestScore);
	        result.put("Direction", bestDirection);
	        
	        return result;
	    }
	    
	    /**
	     * Finds the best move bay using the Alpha-Beta pruning algorithm.
	     * 
	     * @param theBoard
	     * @param depth
	     * @param alpha
	     * @param beta
	     * @param player
	     * @return
	     * @throws CloneNotSupportedException 
	     */
	    private static Map<String, Object> alphabeta(Board theBoard, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException {
	        Map<String, Object> result = new HashMap<>();
	        
	        Direction bestDirection = null;
	        int bestScore;
	        
	        if(theBoard.isGameTerminated()) {
	            if(theBoard.hasWon()) {
	                bestScore=Integer.MAX_VALUE; //highest possible score
	            }
	            else {
	                bestScore=Math.min(theBoard.getScore(), 1); //lowest possible score
	            }
	        }
	        else if(depth==0) {
	            bestScore=heuristicScore(theBoard.getScore(),theBoard.getNumberOfEmptyCells(),calculateClusteringScore(theBoard.getBoardArray()));
	        }
	        else {
	            if(player == Player.USER) {
	                for(Direction direction : Direction.values()) {
	                    Board newBoard = (Board) theBoard.clone();

	                    int points=newBoard.move(direction);
	                    
	                    if(points==0 && newBoard.isEqual(theBoard.getBoardArray(), newBoard.getBoardArray())) {
	                    	continue;
	                    }
	                    
	                    Map<String, Object> currentResult = alphabeta(newBoard, depth-1, alpha, beta, Player.COMPUTER);
	                    int currentScore=((Number)currentResult.get("Score")).intValue();
	                                        
	                    if(currentScore>alpha) { //maximize score
	                        alpha=currentScore;
	                        bestDirection=direction;
	                    }
	                    
	                    if(beta<=alpha) {
	                        break; //beta cutoff
	                    }
	                }
	                
	                bestScore = alpha;
	            }
	            else {
	                List<Integer> moves = theBoard.getEmptyCellIds();
	                int[] possibleValues = {2, 4};

	                int i,j;
	                abloop: for(Integer cellId : moves) {
	                    i = cellId/Board.BOARD_SIZE;
	                    j = cellId%Board.BOARD_SIZE;

	                    for(int value : possibleValues) {
	                        Board newBoard = (Board) theBoard.clone();
	                        newBoard.setEmptyCell(i, j, value);

	                        Map<String, Object> currentResult = alphabeta(newBoard, depth-1, alpha, beta, Player.USER);
	                        int currentScore=((Number)currentResult.get("Score")).intValue();
	                        if(currentScore<beta) { //minimize best score
	                            beta=currentScore;
	                        }
	                        
	                        if(beta<=alpha) {
	                            break abloop; //alpha cutoff
	                        }
	                    }
	                }
	                
	                bestScore = beta;
	                
	                if(moves.isEmpty()) {
	                    bestScore=0;
	                }
	            }
	        }
	        
	        result.put("Score", bestScore);
	        result.put("Direction", bestDirection);
	        
	        return result;
	    }
	    
	    /**
	     * Estimates a heuristic score by taking into account the real score, the
	     * number of empty cells and the clustering score of the board.
	     * 
	     * @param actualScore
	     * @param numberOfEmptyCells
	     * @param clusteringScore
	     * @return 
	     */
	    private static int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
	        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
	        return Math.max(score, Math.min(actualScore, 1));
	    }
	    
	    /**
	     * Calculates a heuristic variance-like score that measures how clustered the
	     * board is.
	     * 
	     * @param boardArray
	     * @return 
	     */
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
	
	/* 
	 * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
	 *
	 * This program is free software: you can redistribute it and/or modify
	 * it under the terms of the GNU General Public License as published by
	 * the Free Software Foundation, either version 3 of the License, or
	 * (at your option) any later version.
	 *
	 * This program is distributed in the hope that it will be useful,
	 * but WITHOUT ANY WARRANTY; without even the implied warranty of
	 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 * GNU General Public License for more details.
	 *
	 * You should have received a copy of the GNU General Public License
	 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
	 */
	/**
	 * Action Status enum.
	 * 
	 * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
	 */
	 enum ActionStatus {
	    /**
	     * Successful move, the game continues.
	     */
	    CONTINUE(0, "Successful move, the game continues."),
	    
	    /**
	     * Game completed successfully.
	     */
	    WIN(1, "You won, the game ended!"),
	    
	    /**
	     * No more moves, end of game.
	     */
	    NO_MORE_MOVES(2,"No more moves, the game ended!"),
	    
	    /**
	     * Invalid move, move can't be performed.
	     */
	    INVALID_MOVE(3,"Invalid move!");
	    
	    /**
	     * The numeric code of the status
	     */
	    private final int code;
	    
	    /**
	     * The description of the status
	     */
	    private final String description;
	    
	    /**
	     * Constructor
	     * 
	     * @param code
	     * @param description 
	     */
	    private ActionStatus(final int code, final String description) {
	        this.code = code;
	        this.description = description;
	    }
	    
	    /**
	     * Getter for code.
	     * 
	     * @return 
	     */
	    public int getCode() {
	        return code;
	    }
	 
	    /**
	     * Getter for description.
	     * 
	     * @return 
	     */
	    public String getDescription() {
	        return description;
	    }
	}
	 /* 
	  * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
	  *
	  * This program is free software: you can redistribute it and/or modify
	  * it under the terms of the GNU General Public License as published by
	  * the Free Software Foundation, either version 3 of the License, or
	  * (at your option) any later version.
	  *
	  * This program is distributed in the hope that it will be useful,
	  * but WITHOUT ANY WARRANTY; without even the implied warranty of
	  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	  * GNU General Public License for more details.
	  *
	  * You should have received a copy of the GNU General Public License
	  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
	  */


	 /**
	  * Direction enum.
	  * 
	  * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
	  */
	 enum Direction {
	     /**
	      * Move Up
	      */
	     UP(0,"Up"), 
	     
	     /**
	      * Move Right
	      */
	     RIGHT(1,"Right"), 
	     
	     /**
	      * Move Down
	      */
	     DOWN(2,"Down"), 
	     
	     /**
	      * Move Left
	      */
	     LEFT(3,"Left");
	     
	     
	     /**
	      * The numeric code of the status
	      */
	     private final int code;
	     
	     /**
	      * The description of the status
	      */
	     private final String description;
	     
	     /**
	      * Constructor
	      * 
	      * @param code
	      * @param description 
	      */
	     private Direction(final int code, final String description) {
	         this.code = code;
	         this.description = description;
	     }
	     
	     /**
	      * Getter for code.
	      * 
	      * @return 
	      */
	     public int getCode() {
	         return code;
	     }
	  
	     /**
	      * Getter for description.
	      * 
	      * @return 
	      */
	     public String getDescription() {
	         return description;
	     }
	     
	     /**
	      * Overloads the toString and returns the description of the move.
	      * @return 
	      */
	     @Override
	     public String toString() { 
	         return description;
	     }
	 }

	 /* 
	  * Copyright (C) 2014 Vasilis Vryniotis <bbriniotis at datumbox.com>
	  *
	  * This program is free software: you can redistribute it and/or modify
	  * it under the terms of the GNU General Public License as published by
	  * the Free Software Foundation, either version 3 of the License, or
	  * (at your option) any later version.
	  *
	  * This program is distributed in the hope that it will be useful,
	  * but WITHOUT ANY WARRANTY; without even the implied warranty of
	  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	  * GNU General Public License for more details.
	  *
	  * You should have received a copy of the GNU General Public License
	  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
	  */


	 /**
	  * The main class of the Game 2048.
	  * 
	  * @author Vasilis Vryniotis <bbriniotis at datumbox.com>
	  */
	  class Board implements Cloneable {
	     /**
	      * The size of the board
	      */
	     public static final int BOARD_SIZE = 4;
	     
	     /**
	      * The maximum combination in which the game terminates
	      */
	     public static final int TARGET_POINTS = 2048;
	     
	     /**
	      * The theoretical minimum win score until the target point is reached
	      */
	     public static final int MINIMUM_WIN_SCORE = 18432;
	     
	     /**
	      * The score so far
	      */
	     private int score=0;
	     
	     /**
	      * The board values
	      */
	     private int[][] boardArray;
	     
	     /**
	      * Random Generator which is used in the creation of random cells
	      */
	     private final Random randomGenerator;
	     
	     /**
	      * It caches the number of empty cells
	      */
	     private Integer cache_emptyCells=null;
	     
	     /**
	      * Constructor without arguments. It initializes randomly the Board
	      */
	     public Board() {
	         boardArray = new int[BOARD_SIZE][BOARD_SIZE];
	         randomGenerator = new Random(System.currentTimeMillis());
	         
	         addRandomCell();
	         addRandomCell();
	         
	     }
	     
	     
	     public Board(int[][]arr,int Score) {
	         boardArray = clone2dArray(arr);
	         randomGenerator = new Random(System.currentTimeMillis());
	         score=Score;
	         
	     }
	     
	     
	     /**
	      * Deep clone
	      * 
	      * @return
	      * @throws CloneNotSupportedException
	      */
	     @Override
	     public Object clone() throws CloneNotSupportedException {
	         Board copy = (Board)super.clone();
	         copy.boardArray = clone2dArray(boardArray);
	         return copy;
	     }
	     
	     /**
	      * Getter for score attribute
	      * 
	      * @return 
	      */
	     public int getScore() {
	         return score;
	     }
	     
	     /**
	      * Getter for BoardArray
	      * @return 
	      */
	     public int[][] getBoardArray() {
	         return clone2dArray(boardArray);
	     }
	     
	     /**
	      * Getter for RandomGenerator field
	      * 
	      * @return 
	      */
	     public Random getRandomGenerator() {
	         return randomGenerator;
	     }
	     
	     /**
	      * Performs one move (up, down, left or right).
	      * 
	      * @param direction
	      * @return 
	      */
	     public int move(Direction direction) {    
	         int points = 0;
	         
	         //rotate the board to make simplify the merging algorithm
	         if(direction==Direction.UP) {
	             rotateLeft();
	         }
	         else if(direction==Direction.RIGHT) {
	             rotateLeft();
	             rotateLeft();
	         }
	         else if(direction==Direction.DOWN) {
	             rotateRight();
	         }
	         
	         for(int i=0;i<BOARD_SIZE;++i) {
	             int lastMergePosition=0;
	             for(int j=1;j<BOARD_SIZE;++j) {
	                 if(boardArray[i][j]==0) {
	                     continue; //skip moving zeros
	                 }
	                 
	                 int previousPosition = j-1;
	                 while(previousPosition>lastMergePosition && boardArray[i][previousPosition]==0) { //skip all the zeros
	                     --previousPosition;
	                 }
	                 
	                 if(previousPosition==j) {
	                     //we can't move this at all
	                 }
	                 else if(boardArray[i][previousPosition]==0) {
	                     //move to empty value
	                     boardArray[i][previousPosition]=boardArray[i][j];
	                     boardArray[i][j]=0;
	                 }
	                 else if(boardArray[i][previousPosition]==boardArray[i][j]){
	                     //merge with matching value
	                     boardArray[i][previousPosition]*=2;
	                     boardArray[i][j]=0;
	                     points+=boardArray[i][previousPosition];
	                     lastMergePosition=previousPosition+1;
	                     
	                 }
	                 else if(boardArray[i][previousPosition]!=boardArray[i][j] && previousPosition+1!=j){
	                     boardArray[i][previousPosition+1]=boardArray[i][j];
	                     boardArray[i][j]=0;
	                 }
	             }
	         }
	         
	         
	         score+=points;
	         
	         //reverse back the board to the original orientation
	         if(direction==Direction.UP) {
	             rotateRight();
	         }
	         else if(direction==Direction.RIGHT) {
	             rotateRight();
	             rotateRight();
	         }
	         else if(direction==Direction.DOWN) {
	             rotateLeft();
	         }
	         
	         return points;
	     }
	     
	     /**
	      * Returns the Ids of the empty cells. The cells are numbered by row.
	      * 
	      * @return 
	      */
	     public List<Integer> getEmptyCellIds() {
	         List<Integer> cellList = new ArrayList<>();
	         
	         for(int i=0;i<BOARD_SIZE;++i) {
	             for(int j=0;j<BOARD_SIZE;++j) {
	                 if(boardArray[i][j]==0) {
	                     cellList.add(BOARD_SIZE*i+j);
	                 }
	             }
	         }
	         
	         return cellList;
	     }
	     
	     /**
	      * Counts the number of empty cells
	      * 
	      * @return 
	      */
	     public int getNumberOfEmptyCells() {
	         if(cache_emptyCells==null) {
	             cache_emptyCells = getEmptyCellIds().size();
	         }
	         return cache_emptyCells;
	     }
	     
	     /**
	      * Checks if any of the cells in the board has value equal or larger than the
	      * target.
	      * 
	      * @return 
	      */
	     public boolean hasWon() {
	         if(score<MINIMUM_WIN_SCORE) { //speed optimization
	             return false;
	         }
	         for(int i=0;i<BOARD_SIZE;++i) {
	             for(int j=0;j<BOARD_SIZE;++j) {
	                 if(boardArray[i][j]>=TARGET_POINTS) {
	                     return true;
	                 }
	             }
	         }
	         
	         return false;
	     }
	     
	     /**
	      * Checks whether the game is terminated
	      * 
	      * @return 
	      * @throws java.lang.CloneNotSupportedException 
	      */
	     public boolean isGameTerminated() throws CloneNotSupportedException {
	         boolean terminated=false;
	         
	         if(hasWon()==true) {
	             terminated=true;
	         }
	         else {
	             if(getNumberOfEmptyCells()==0) { //if no more available cells
	                 Board copyBoard = (Board) this.clone();
	                                 
	                 if(copyBoard.move(Direction.UP)==0 
	                    && copyBoard.move(Direction.RIGHT)==0 
	                    && copyBoard.move(Direction.DOWN)==0 
	                    && copyBoard.move(Direction.LEFT)==0) {
	                     terminated=true;
	                 }
	                 
	                 //copyBoard=null;
	             }
	         }
	         
	         return terminated;
	     }
	     
	     /**
	      * Performs an Up, Right, Down or Left move
	      * 
	      * @param direction
	      * @return 
	      * @throws java.lang.CloneNotSupportedException 
	      */
	     public ActionStatus action(Direction direction) throws CloneNotSupportedException {
	         ActionStatus result = ActionStatus.CONTINUE;
	         
	         int[][] currBoardArray = getBoardArray();
	         int newPoints = move(direction);
	         int[][] newBoardArray = getBoardArray();
	         
	         //add random cell
	         boolean newCellAdded = false;
	         
	         if(!isEqual(currBoardArray, newBoardArray)) {
	             newCellAdded = addRandomCell();
	         }
	         
	         if(newPoints==0 && newCellAdded==false) {
	             if(isGameTerminated()) {
	                 result = ActionStatus.NO_MORE_MOVES;
	             }
	             else {
	                 result = ActionStatus.INVALID_MOVE;
	             }
	         }
	         else {
	             if(newPoints>=TARGET_POINTS) {
	                 result = ActionStatus.WIN;
	             }
	             else {
	                 if(isGameTerminated()) {
	                     result = ActionStatus.NO_MORE_MOVES;
	                 }
	             }
	         }
	         
	         return result;
	     }
	     
	     /**
	      * Sets the value to an empty cell. 
	      * 
	      * @param i
	      * @param j
	      * @param value 
	      */
	     public void setEmptyCell(int i, int j, int value) {
	         if(boardArray[i][j]==0) {
	             boardArray[i][j]=value;
	             cache_emptyCells=null;
	         }
	     }
	     
	     /**
	      * Rotates the board on the left
	      */
	     private void rotateLeft() {
	         int[][] rotatedBoard = new int[BOARD_SIZE][BOARD_SIZE];
	         
	         for(int i=0;i<BOARD_SIZE;++i) {
	             for(int j=0;j<BOARD_SIZE;++j) {
	                 rotatedBoard[BOARD_SIZE-j-1][i] = boardArray[i][j];
	             }
	         }
	         
	         boardArray=rotatedBoard;
	     }
	     
	     /**
	      * Rotates the board on the right
	      */
	     private void rotateRight() {
	         int[][] rotatedBoard = new int[BOARD_SIZE][BOARD_SIZE];
	         
	         for(int i=0;i<BOARD_SIZE;++i) {
	             for(int j=0;j<BOARD_SIZE;++j) {
	                 rotatedBoard[i][j]=boardArray[BOARD_SIZE-j-1][i];
	             }
	         }
	         
	         boardArray=rotatedBoard;
	     }
	     
	     /**
	      * Creates a new Random Cell
	      */
	     private boolean addRandomCell() {
	         List<Integer> emptyCells = getEmptyCellIds();
	         
	         int listSize=emptyCells.size();
	         
	         if(listSize==0) {
	             return false;
	         }
	         
	         int randomCellId=emptyCells.get(randomGenerator.nextInt(listSize));
	         int randomValue=(randomGenerator.nextDouble()< 0.9)?2:4;
	         
	         int i = randomCellId/BOARD_SIZE;
	         int j = randomCellId%BOARD_SIZE;
	         
	         setEmptyCell(i, j, randomValue);
	         
	         return true;
	     }
	     
	     /**
	      * Clones a 2D array
	      * 
	      * @param original
	      * @return 
	      */
	     private int[][] clone2dArray(int[][] original) { 
	         int[][] copy = new int[original.length][];
	         for(int i=0;i<original.length;++i) {
	             copy[i] = original[i].clone();
	         }
	         return copy;
	     }
	     

	     
	     /**
	      * Checks whether the two input boards are same.
	      * 
	      * @param currBoardArray, newBoardArray
	      * @return 
	      */
	     public boolean isEqual(int[][] currBoardArray, int[][] newBoardArray) {

	     	boolean equal = true;
	         
	         for(int i=0;i<currBoardArray.length;i++) {
	             for(int j=0;j<currBoardArray.length;j++) {
	                 if(currBoardArray[i][j]!= newBoardArray[i][j]) {
	                     equal = false; //The two boards are not same.
	                     return equal;
	                 }
	             }
	         }
	         
	         return equal;
	     }
	 }

	

	
	

