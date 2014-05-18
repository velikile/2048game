package Model.Game2048Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.sun.media.sound.DataPusher;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import Client_Server.TCPClient;
import Mazepack.Maze;
import Mazepack.State;
import Model.Model;
import Model.MazeModel.GameMazeModel;
import Solver.Node;

public class Game2048Model extends Observable implements Model,Serializable{
	private int [][] Data;
	private int Score;
	private int MaxScore;
	private boolean WinFlag=false;
	private int Scores[]=new int [1024];
	private Stack<int [][]> UndoList=new Stack<int [][]>();
	private String Hint = null;
	public Game2048Model(int [][] Data){
		
		this.SetData(Data);
		Score=0;
	}
	public Game2048Model(){
		Data=new int[4][4];
		int row=Data.length;
		int col=Data[0].length;
		Random r=new Random();
		int x=new Random().nextInt(10);
		if(x==9)
			x=4;
		else
			x=2;
		Data[r.nextInt(row)][r.nextInt(col)]=x;
		Data[r.nextInt(row)][r.nextInt(col)]=x-2;
		
	}
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
	public Game2048Model(int[][] boardData, int score) {
		SetData(boardData);
		Score=score;
	}
	public Game2048Model(Game2048Model A) {
		this.SetData(A.Data);
		this.Score=A.getScore();
		this.UndoList=A.getUndoList();
		this.Scores=A.getScores();
		this.MaxScore=A.getMaxScore();
	}
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
				return false;}
			if(Data[a][b]==Data[a][b+1]){
				counter++;
			return false;}}
			 if(b+1==col&&a<row-1){
				if(Data[a][b]==Data[a+1][b]){
					counter++;
					return false;}
		}
			 if(a+1==row&&b<col-1){
				if(Data[a][b]==Data[a][b+1]){
					counter++;
					return false;}
		}
			if(a+1==row&&b+1==col){
				if(Data[a][b]==Data[a][b-1]||Data[a][b]==Data[a-1][b]){
					counter++;
					return false;}
		}
	}}
if (counter==0&&numberOfValues==row*col){
	return true;}
return false;
}
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

public int getSum()
{	int sum=0;
	for (int a[]:this.Data)
		for(int b:a)
			sum+=b;
	return sum;
}
public int getScore() {
	return Score;
}
public void setScore(int score) {
	Score = score;
}

public boolean equals(Model M){
	if(isEqual(M.getData()))
		return true;
	else return false;	
	}
@Override
public void NewGame() {
	int row=Data.length;
	int col=Data[0].length;
	Data=new int[row][col];
	Random r=new Random();
	int x=r.nextInt(10);
	if(x==9)
		x=4;
	else
		x=2;
	Data[r.nextInt(row)][r.nextInt(col)]=x-2;
	Data[r.nextInt(row)][r.nextInt(col)]=x;
	if(MaxScore<Score)
		MaxScore=Score;
	Score=0;
	UndoList.clear();
	Scores=new int [2048];
	WinFlag=false;
}
@Override
public void Notify() {
	this.setChanged();
	notifyObservers();
	
}
@Override
public int GetScore() {
	// TODO Auto-generated method stub
	return Score;
}
@Override
public int getMaxScore() {
	// TODO Auto-generated method stub
	return MaxScore;
}
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
	this.MaxScore=A.MaxScore;}catch(Exception e){}
	Notify();
	return true;
}
@Override
public void UndoMove() {
	WinFlag=false;
	if(!UndoList.isEmpty())
	Data=this.UndoList.pop();
	if(UndoList.size()!=0)
	Score=Score-Scores[UndoList.size()];
	Notify();
}
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
	}
	return true;
}

public Stack<int[][]> getUndoList() {
	// TODO Auto-generated method stub
	return UndoList;
}
public int[] getScores() {
	return Scores;
}
public void setScores(int scores[]) {
	Scores = scores;
}

public void setWinFlag(boolean winFlag) {
	WinFlag = winFlag;
}
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
public void SetData(int[][] data2) {
	int row=data2.length;
	int col=data2[0].length;
	Data=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++)
			Data[i][j]=data2[i][j];
		
}
@Override
public boolean GameWon() {
if(WinFlag)
	return true;
return false;
}
@Override
public void AIPlayer() {
	
	final Game2048Model TempModel=this;
	 new Thread(new Runnable(){

		@Override
		public void run() {
			 while(!GameOver()){
				 	try {
					Hint = new TCPClient(TempModel).getHint();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				 	try {
				 		Thread.sleep(1000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 	System.out.println("after 1 seconds");
					
		}
			
		}}).start();
	 
	 try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	 switch(Hint){
		case "right":{moveRight();
		break;}
		case "left":{moveLeft();
		break;}
		case "up":{moveUp();
		break;}
		case "down":{moveDown();
		break;}
		}
	 }
}
@Override
public int CountEmptyCells() {
	int counter=0;
	for(int a[]:Data)
		for(int b:a){
			if(b==0)
				counter++;
			
		}
	return counter;
}
				
	

	
}



