package Model.Game2048Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import Model.Model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * @author Lev veliki
 * The class stores the data associated with the Game
 */
public class Game2048Model extends Observable implements Model,Serializable{

	private static final long serialVersionUID = 1L;
	private int [][] Data;
	private int Score;
	private int MaxScore;
	private boolean WinFlag=false;
	private int Scores[]=new int [2048];
	private Stack<int [][]> UndoList=new Stack<int [][]>();
	public Game2048Model(int [][] Data){
		
		this.SetData(Data);
		Score=0;
	}
	/**
	 * Default Ctor
	 * 
	 */
	public Game2048Model(){
		Data=new int[4][4];//this is the size of the Board
		int row=Data.length;
		int col=Data[0].length;
		Random r=new Random();
		int x=new Random().nextInt(10);
		if(x==9){
			Data[r.nextInt(row)][r.nextInt(col)]=4;
			Data[r.nextInt(row)][r.nextInt(col)]=2;}
		else{
			Data[r.nextInt(row)][r.nextInt(col)]=2;
			Data[r.nextInt(row)][r.nextInt(col)]=2;
		}
		
		
	}
	/**
	 * 
	 * @return Board 
	 *	return the Game Data as a String 
	 * 
	 */
	
	
	@Override
	public String toString(){
	String Board=new String();
	boolean first=true;
	for(int i[]:Data){
		if(!first){
		Board+="\n";
		}
		for(int j:i){
			Board+=j;
			first=false;}
	}
		return Board;
	}
	/**
	 * 
	 * @param boardData
	 * @param score
	 * A simply Ctor for a slim version of the Model
	 */
	
	public Game2048Model(int[][] boardData, int score) {
		SetData(boardData);
		Score=score;
	}
	/**
	 * 
	 * @param Game2048Model
	 * A full Copy Ctor 
	 */
	
	public Game2048Model(Game2048Model A) {
		this.SetData(A.Data);
		this.Score=A.GetScore();
		this.UndoList=A.getUndoList();
		this.Scores=A.getScores();
		this.MaxScore=A.getMaxScore();
	}
		
	/**
	 * The method is Moving all of the Tiles Up 
	 */
	
	@Override
	public void moveUp() {
		int row=Data.length;
		int col=Data[0].length;
		int counter=0;
		int [][]temp=new int[row][col];
		int ScoreToAdd=0;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
			temp[a][b]=Data[a][b];	
			}
			
		
		Queue<Integer> q =new LinkedList<Integer>();
 		for(int a=0;a<row;a++){
			for (int b=0;b<col;b++){
				if(Data[b][a]!=0){
					q.add(Data[b][a]);
			
					Data[b][a]=0;}
					if(b==col-1){
						counter=0;
				 		while(!q.isEmpty()&&counter<4){
			 					Data[counter][a]=q.poll();
			 					if(q.peek()!=null){
			 						if(Data[counter][a]==q.peek()){
			 							Data[counter][a]=q.poll()*2;
			 							Data[counter+1][a]=0;
			 							ScoreToAdd+=Data[counter][a];
			 						}
			 						
			 					}
	
			 					counter++;}
								 }}
					}
 		if(!isEqual(temp)){
 			UndoList.add(temp);
 			Scores[UndoList.size()-1]=ScoreToAdd;
 			Score+=ScoreToAdd;
 			if(Score>MaxScore)
 				MaxScore=Score;
 			Notify();
 	}
					
					}
	

	/**
	 * The method is Moving all of the Tiles Down 
	 *   
	 */
	@Override
	public void moveDown() {
		int row=Data.length;
		int col=Data[0].length;
		int counter=0;
		int [][]temp=new int[row][col];
		int ScoreToAdd=0;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
			temp[a][b]=Data[a][b];}
		Stack<Integer> q =new Stack<Integer>();
 		for(int a=0;a<row;a++){
			for (int b=0;b<col;b++){
				if(Data[b][a]!=0){
					q.add(Data[b][a]);
			
					Data[b][a]=0;}
					if(b==col-1){
						counter=b;
				 		while(!q.isEmpty()&&counter>=0){
			 					Data[counter][a]=q.pop();
			 					if(!q.isEmpty()){
			 						if(Data[counter][a]==q.peek()){
			 							Data[counter][a]=q.pop()*2;
			 							Data[counter-1][a]=0;
			 							ScoreToAdd+=Data[counter][a];
			 						}
			 						
			 					}
			 					counter--;}
								 }}
					}
 		if(!isEqual(temp)){
 			UndoList.add(temp);
 			Score+=ScoreToAdd;
 			Scores[UndoList.size()-1]=ScoreToAdd;
 			if(Score>MaxScore)
 				MaxScore=Score;
 			Notify();

		}
		
	}


	/**
	 * The method is Moving all of the Tiles left  
	 */
	
	@Override
	public void moveLeft() {
		int row=Data.length;
		int col=Data[0].length;
		int counter=0;
		int [][]temp=new int[row][col];
		int ScoreToAdd=0;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
			temp[a][b]=Data[a][b];}
		
		Queue<Integer> q =new LinkedList<Integer>();
 		for(int a=0;a<row;a++){
			for (int b=0;b<col;b++){
				if(Data[a][b]!=0){
					q.add(Data[a][b]);
			
					Data[a][b]=0;}
					if(b==col-1){
						counter=0;
				 		while(!q.isEmpty()&&counter<col){
			 					Data[a][counter]=q.poll();
			 					if(!q.isEmpty()){
			 						if(Data[a][counter]==q.peek()){
			 							Data[a][counter]=q.poll()*2;
			 							Data[a][counter+1]=0;
			 							ScoreToAdd+=Data[a][counter];
			 						}}
			 					
			 					counter++;}
								 }}
					}
 		if(!isEqual(temp)){
 			UndoList.add(temp);
 			Score+=ScoreToAdd;
 			Scores[UndoList.size()-1]=ScoreToAdd;
 			if(Score>MaxScore)
 				MaxScore=Score;
 			Notify();

 	 	}
	}
	/**
	 * The method is Moving all of the Tiles right
	 */
	@Override
	public void moveRight() {
	
		int row=Data.length;
		int col=Data[0].length;
		int counter=0;
		int [][]temp=new int[row][col];
		int ScoreToAdd=0;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
			temp[a][b]=Data[a][b];}
		Stack<Integer> q =new Stack<Integer>();
 		for(int a=0;a<row;a++){
			for (int b=0;b<col;b++){
				if(Data[a][b]!=0){
					q.add(Data[a][b]);
			
					Data[a][b]=0;}
					if(b==col-1){
						counter=row-1;
				 		while(!q.isEmpty()&&counter>=0){
			 					Data[a][counter]=q.pop();
			 					if(!q.isEmpty()){
			 						if(Data[a][counter]==q.peek()){
			 							Data[a][counter]=q.pop()*2;
			 							Data[a][counter-1]=0;
			 							ScoreToAdd+=Data[a][counter];
			 						}}
			 					counter--;}
								 }}
					}

 		if(!isEqual(temp)){
 			UndoList.add(temp);
 			Score+=ScoreToAdd;
 			Scores[UndoList.size()-1]=ScoreToAdd;
 			if(Score>MaxScore)
 				MaxScore=Score;
 			Notify();
 		}
	
			
		

	}
	
	/**
	 * @return temp 
	 * returns a Copy Data of the game board 
	 * 
	 */

	@Override
	public int[][] getData() {
		int[][] temp=new int[Data.length][Data[0].length] ;
		int row=Data.length;
		int col=Data[0].length;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
				temp[a][b]=Data[a][b];}
		return temp;
	}
	
	/**
	 * 
	 * @return ArrayList<Interger>
	 * returns an array with the indices of the free cells in modulo of the row and col
	 */
	
	public ArrayList<Integer> FreeSpaces(){
		
		int row=Data.length;
		int col=Data[0].length;
		
		ArrayList<Integer> free=new ArrayList<Integer>();
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){//checking for close matchups
				if(Data[a][b]==0){
					free.add(a*row+b);}
				
				}
		return free;
	

}
	/**
	 * @return void
	 * @param void 
	 * Adds a Random Tile to the Game Board
	 */
	
	@Override
	public void AddRandom(){ 	
		int row=Data.length;
		int col=Data[0].length;
		int x=new Random().nextInt(10);
		ArrayList<Integer> FreeCells=FreeSpaces();
		if(FreeCells.isEmpty())
			return;
		int free=new Random().nextInt(FreeCells.size());
		if(x==9)
			x=4;
		else 
			x=2;
		int a=(int)FreeCells.toArray()[free]%col;
		int b=(int)FreeCells.toArray()[free]/row;
		Data[b][a]=x;
			}
	/**
	 * 
	 * @return boolean
	 * Check if The Game is Over,only returns a true when you lose the game  
	 */

		
	public boolean GameOver(){
	int row=Data.length;
	int col=Data[0].length;
	int counter=0;
	int numberOfValues=0;
	for(int a=0;a<row;a++){
		for (int b=0;b<col;b++){
			if(Data[a][b]==2048){
				setWinFlag(true);
			}
			if(Data[a][b]!=0)
			numberOfValues++;
			if(a+1<row&&b+1<col){
			if(Data[a][b]==Data[a+1][b]){
				counter++;
				}
			if(Data[a][b]==Data[a][b+1]){
				counter++;
			}}
			 if(b+1==col&&a<row-1){
				if(Data[a][b]==Data[a+1][b]){
					counter++;
					}
		}
			 if(a+1==row&&b<col-1){
				if(Data[a][b]==Data[a][b+1]){
					counter++;
					}
		}
			if(a+1==row&&b+1==col){
				if(Data[a][b]==Data[a][b-1]||Data[a][b]==Data[a-1][b]){
					counter++;
				}
		}
	}}
if (counter==0&&numberOfValues==row*col||WinFlag){
	return true;}
return false;
}
/**
 * 
 * 
 * @param data
 * @return boolean 
 * Checks whether the game board is equal to the data passed
 */
	
public boolean isEqual(int [][]data){
	if(data!=null){
		int row=Data.length;
		int col=Data[0].length;
		int counter=0;
		for(int a=0;a<row;a++)
			for (int b=0;b<col;b++){
				if(data[a][b]==Data[a][b])
					counter++;
			}
		if (counter==row*col){
			return true;}
		else 
			return false;
		
		
	}
	return false;
	
	
}
/**
 * 
 * @return sum 
 * this is summing the the number of cubes 
 */
public int getSum()
{	int sum=0;
	for (int a[]:this.Data)
		for(int b:a){
			if(b!=0)
			sum+=b;}
	return sum;
}

/**
 * 
 * @param int score
 * Setting the score manually 
 */
public void setScore(int score) {
	Score = score;
}
/**
 * 
 * @param M
 * @return boolean
 * return true if The game Board is equal to the objects Board
 */
public boolean equals(Game2048Model M){
	if(isEqual(M.getData()))
		return true;
	else return false;	
	}
/**
 * 
 * Starts a new Game
 */

@Override
public void NewGame() {
	int row=Data.length;
	int col=Data[0].length;
	Data=new int[row][col];
	Random r=new Random();
	int x=r.nextInt(10);
	if(x==9){
	Data[r.nextInt(row)][r.nextInt(col)]=4;
	Data[r.nextInt(row)][r.nextInt(col)]=2;}
	else{
		Data[r.nextInt(row)][r.nextInt(col)]=2;
		Data[r.nextInt(row)][r.nextInt(col)]=2;
	}
	
	if(MaxScore<Score)
		MaxScore=Score;
	Score=0;
	UndoList.clear();
	Scores=new int [2048];
	WinFlag=false;
}
/**
 * A small useful method to Notify Observes
 */

@Override
public void Notify() {
	this.setChanged();
	notifyObservers();
	
}

/**
 * @return Score
 * returns the Score of the game
 */
@Override
public int GetScore() {
	return Score;
}

/**
 * @return MaxScore 
 * returns the Best Score so far 
 */
@Override
public int getMaxScore() {
	// TODO Auto-generated method stub
	return MaxScore;
}

/**
 * @return boolean  
 * Load A Game File from Hard drive returns true if the load is successful 
 */
@Override
public boolean LoadGame() {
	FileDialog FD=new FileDialog(new Shell().getShell());
	FD.open();
	String path=FD.getFilterPath()+"\\"+FD.getFileName();
	
	File Model=new File(path);
	XStream xstream=new XStream(new DomDriver());
	try{
	Game2048Model A=new Game2048Model((Game2048Model)xstream.fromXML(Model));
	this.Data=(A.getData()).clone();
	this.UndoList=A.getUndoList();
	this.Score=A.Score;
	this.setScores(A.getScores());
	WinFlag=A.GameWon();
	if(A.MaxScore>this.MaxScore)
	this.MaxScore=A.MaxScore;}
	catch(Exception e){
		return false;
	}
	this.setChanged();
	this.notifyObservers("Load");
	
	return true;
}
/**
 * 
 * @param Scores
 * setting the Scores array to the one passed
 */
private void setScores(int[] Scores) {
	this.Scores=Scores;
	
}
/** 
 * Undo the last move made and notifies observes
 */
@Override
public void UndoMove() {
	WinFlag=false;
	if(!UndoList.isEmpty())
	Data=this.UndoList.pop();
	if(UndoList.size()!=0)
	Score=Score-Scores[UndoList.size()];
	this.setChanged();
	this.notifyObservers("Undo");
}
/**
 * @return boolean 
 * @param String 
 * Taking the Name of the file and saving the current state of the game as an XML type file
 * return true if Successful
 */
@Override
public boolean SaveGame(String string) {
	XStream xstream=new XStream(new DomDriver());
	Game2048Model A=new Game2048Model(this);
	File Model=new File(string);

	PrintWriter writer = null;
	try {
		writer = new PrintWriter(Model);
		xstream.toXML(A,writer);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;
}
/**
 * 
 * @return UndoList
 * returns all of the game States so far in a Stack organized fashion
 */
public Stack<int[][]> getUndoList() {
	// TODO Auto-generated method stub
	return UndoList;
}
/**
 * @return Scores
 * returns the Score array for the game 
 */
public int[] getScores() {
	return Scores;
}

/**
 * 
 * @param boolean 
 * Setting the winFlag Manually
 */
public void setWinFlag(boolean winFlag) {
	WinFlag = winFlag;
}
/**
 * @return Moves
 * returns a String array with all of the available moves from the current state
 */
@Override
public String []GetAvailableMoves() {
	Game2048Model Model=new Game2048Model(this);
	String[] Moves=new String[4];
		Model.moveDown(); 
	if(!isEqual(Model.getData())){
		Model=new Game2048Model(this);
		Moves[0]="down";}
	Model.moveUp();
	if(!isEqual(Model.getData())){
		Model=new Game2048Model(this);
		Moves[1]="up";}
	Model.moveLeft();
	if(!isEqual(Model.getData())){
		Model=new Game2048Model(this);
		Moves[2]="left";}
	
	Model.moveRight();
	if(!isEqual(Model.getData())){
		Model=new Game2048Model(this);
		Moves[3]="right";}
		return Moves;
		}
/**
 * 
 * @param data2
 * Setting the Game Board to the data passed
 */
public void SetData(int[][] data2) {
	int row=data2.length;
	int col=data2[0].length;
	Data=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++)
			Data[i][j]=data2[i][j];
		
}
/**
 * @return boolean
 * checks if the Game is in Win State
 */
@Override
public boolean GameWon() {
if(WinFlag)
	return true;
return false;
}
	
}



