package Model.MazeModel;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Observable;
import java.util.Stack;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import Model.Model;
import Mazepack.Maze;
import Mazepack.Spot;
import Mazepack.State;
import Solver.Node;
public class GameMazeModel extends Observable implements Model {
	private Maze maze;
	private int Score=0;
	private int MaxScore=0;
	private State now; 
	private State goal;
	boolean Flag=true;
	private Stack<String> UndoList=new Stack<String>();
	private boolean WinFlag;
	public GameMazeModel(){
		try {
			maze=new Maze();
			now=maze.getSstate();
			goal=maze.getGstate();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		public GameMazeModel(GameMazeModel A){
			maze=new Maze(A.getData());
			now=new State(A.now);
			Score=A.Score;
			MaxScore=A.MaxScore;
			goal=new State(A.goal);
			this.Flag=A.Flag;
			UndoList=new Stack<String>();
			while(!A.UndoList.isEmpty()){
				this.UndoList.add(A.UndoList.pop());
				
			}
		
		
		
	}
	@Override
	public void moveUp() {
		
		if(now.getSpot().gety()-1>=0&&maze.getValue(now.getSpot().getx(), now.getSpot().gety()-1)!=-1){
					now.setSpot(new Spot(now.getSpot().getx(),now.getSpot().gety()-1));
					if(Flag==true){
					Score+=10;
					UndoList.push("moveUp");}
					else {Score-=10;}
					maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
					if(this.GameOver())
						if(MaxScore==0)
							MaxScore=Score;
						else
						MaxScore=Math.min(MaxScore, Score);
					
		}
			
				
	}

	@Override
	public void moveDown() {
		if(maze.getValue(now.getSpot().getx(), now.getSpot().gety()+1)!=-1){
			now.setSpot(new Spot(now.getSpot().getx(),now.getSpot().gety()+1));
			if(Flag==true){
				Score+=10;
				UndoList.push("moveDown");}
				else {Score-=10;}
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			}
	
		
	}

	@Override
	public void moveLeft() {
		if(maze.getValue(now.getSpot().getx()-1, now.getSpot().gety())!=-1){
			now.setSpot(new Spot(now.getSpot().getx()-1,now.getSpot().gety()));
			if(Flag==true){
				Score+=10;
				UndoList.push("moveLeft");}
				else {Score-=10;}
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
			}
	
		
	}

	@Override
	public void moveRight() {
		if(maze.getValue(now.getSpot().getx()+1, now.getSpot().gety())!=-1){
			now.setSpot(new Spot(now.getSpot().getx()+1,now.getSpot().gety()));
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(Flag==true){
				Score+=10;
				UndoList.push("moveRight");}
				else {Score-=10;}
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
		}
	}

	@Override
	public int[][] getData() {
		// TODO Auto-generated method stub
		return maze.GetData();
	}

	@Override
	public boolean GameOver() {
		if(maze.getSstate().isEqual(goal)){
			WinFlag=true;
			return true;}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void NewGame() {
		// TODO Auto-generated method stub
		try {
			maze=new Maze();
			maze.PrintMaze();
			now=maze.getSstate();
			goal=maze.getGstate();
			Score=0;
			this.UndoList=new Stack<String>();
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void Notify() {
		this.setChanged();
		this.notifyObservers();
		// TODO Auto-generated method stub

	}

	@Override
	public int GetScore() {
		// TODO Auto-generated method stub
		return Score;
	}
	public int getMaxScore(){
		return MaxScore;
		
	}
	@Override
	public boolean LoadGame() {
		FileDialog FD=new FileDialog(new Shell().getShell());
		FD.open();
		String path=FD.getFilterPath()+"\\"+FD.getFileName();
		File MazeModel=new File(path);
		this.NewGame();
		XStream xstream=new XStream(new DomDriver());
		try{
		
		GameMazeModel A=new GameMazeModel((GameMazeModel)xstream.fromXML(MazeModel));
		this.maze=new Maze(A.getData());
		this.now=new State(A.now);
		this.goal=new State(A.goal);
		this.Score=A.Score;
		this.Flag=A.Flag;
		this.MaxScore=A.MaxScore;
		this.UndoList=A.getUndoList();}
		catch(Exception e){}
		
		
		Notify();
		System.out.println("gameLoaded");
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void UndoMove() {
		String lastMove=new String();
		Flag=false;
		if(!UndoList.isEmpty()&&Flag==false){
			lastMove=UndoList.pop();
		}
		
			switch(lastMove){
			case "moveUp":
			{
				moveDown();
				break;
			}
			case "moveDown":
			{
				moveUp();
				break;
			}
			case "moveRight":
			{
				moveLeft();
				break;
			}
			case "moveLeft":
			{
				moveRight();
				break;
			}
			case "moveURight":
			{
				moveDLeft();
				break;
			}
			case "moveDRight":
			{
				moveULeft();
				break;
			}
			case "moveULeft":
			{
				moveDRight();
				break;
			}
			case "moveDLeft":
			{
				moveURight();
				break;
			}
			 default:{
		 
				break;
			}
		}
			Flag=true;}
			
	
	@Override
	public boolean SaveGame(String string) {
	
		XStream xstream=new XStream(new DomDriver());
		GameMazeModel A=new GameMazeModel(this);
		File MazeModel=new File(string);
	//	GameMazeModel A=new GameMazeModel(this);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(MazeModel);
			xstream.toXML(A,writer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//writer.print(xml);
		System.out.println("gameSaved");
		return true;
	}
	

public Stack<String> getUndoList() {
		return UndoList;
	}
	public void setUndoList(Stack<String> undoList) {
		UndoList = undoList;
	}

	public void moveURight() {
		if(maze.getValue(now.getSpot().getx()+1, now.getSpot().gety()-1)!=-1){
			now.setSpot(new Spot(now.getSpot().getx()+1,now.getSpot().gety()-1));
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(Flag==true){
				Score+=15;
				UndoList.push("moveURight");}
				else {Score-=15;}
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
		}
		
		
	}

	public void moveDRight() {
		if(maze.getValue(now.getSpot().getx()+1, now.getSpot().gety()+1)!=-1){
			now.setSpot(new Spot(now.getSpot().getx()+1,now.getSpot().gety()+1));
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(Flag==true){
				Score+=15;
				UndoList.push("moveDRight");}
				else {Score-=15;}
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
		}
		
		
	}

	public void moveULeft() {
		if(maze.getValue(now.getSpot().getx()-1, now.getSpot().gety()-1)!=-1){
			now.setSpot(new Spot(now.getSpot().getx()-1,now.getSpot().gety()-1));
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(Flag==true){
				Score+=15;
				UndoList.push("moveULeft");}
				else {Score-=15;}
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
		}
		
		
	}

	public void moveDLeft() {
		if(maze.getValue(now.getSpot().getx()-1, now.getSpot().gety()+1)!=-1){
			now.setSpot(new Spot(now.getSpot().getx()-1,now.getSpot().gety()+1));
			maze.setStartPoint(now.getSpot().getx(), now.getSpot().gety());
			if(Flag==true){
				Score+=15;
				UndoList.push("moveDLeft");}
				else {Score-=15;}
			if(this.GameOver())
				if(MaxScore==0)
					MaxScore=Score;
				else
				MaxScore=Math.min(MaxScore, Score);
		}
		
	}

	@Override
	public boolean isWinFlag() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setWinFlag(boolean winFlag) {
		WinFlag = winFlag;
	}

	@Override
	public String[] GetAvailableMoves() {
		
		return null;
	}
	
	
}
