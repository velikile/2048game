package Mazepack;

import java.util.Random;

public class Maze  {
	
	
public int [][] M;//this hold the data for the maze board
 private int row; // the height of the maze
 private int col;//the width of the maze 
 private State sst=new State();//start spot
 private State gst=new State();//goal spot 
 
@SuppressWarnings("unused")
public Maze() throws InterruptedException{
	
	if(Global.CheeseX>Global.Col_maze||Global.CheeseY>Global.Row_maze)
		throw new InterruptedException("Invalid Cheese Spot"); 
	if(Global.MiceX>Global.Col_maze||Global.MiceY>Global.Row_maze)
		throw new InterruptedException("Invalid Mice Spot"); 
	this.row=Global.Row_maze;
	this.col=Global.Col_maze;
	M=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++)
		{   if(i==Global.MiceY && j==Global.MiceX){
			M[i][j]=1;
			sst.setSpot(new Spot(j,i));
			sst.setData(sst.getSpot().toString());
			}
			if(i==2&&j>0&&j<col-2&&j!=5)//creating a wall 
				M[i][j]=-1;
			if(i==0 &&j!=Global.CheeseX+1 )
				M[i][j]=-1;
		if(j==0&&i>0&&i<row)
			M[i][j]=-1;
		if(j==15)//creating a wall 
			M[i][j]=-1;
		if(i==15)//creating a wall 
			M[i][j]=-1;
		if(j==10&&i<row&&i>=4&&i!=8&&i!=6)//creating a wall 
			M[i][j]=-1;
		if(j>=8&&i==5)//creating a wall 
			M[i][j]=-1;
		if(j>=9&&i==row-3&&j<col-1&&j!=col-3)//creating a wall 
			M[i][j]=-1;
		if(j>=9&&i==row-3&&j<col-1&&j!=col-3)//creating a wall 
			M[i][j]=-1;
		if(j>=9&&i==row-5&&j<col-1&&j!=col-5)//creating a wall 
			M[i][j]=-1;
		if(j>=9&&i==row-7&&j<col-1&&j!=col-2)//creating a wall 
			M[i][j]=-1;
		if(j>=9&&i==row-9&&j<col-1&&j!=col-3)//creating a wall 
			M[i][j]=-1;
		if(j==7&&i<row-2&&i>=5&&i!=7)//creating a wall 
			M[i][j]=-1;
		if(j==5&&i<9&&i>4)//creating a wall 
			M[i][j]=-1;
		if(j>2&&j<6&&i==13)//creating a wall 
			M[i][j]=-1;
		
		if(j==6&&i==8)//creating a wall 
			M[i][j]=-1;
		if(i==4&&j==5)//creating a wall 
			M[i][j]=-1;
		
		if(j==4&&i==7)//creating a wall 
			M[i][j]=-1;
		if(j==3&&i<8&&i>=5)//creating a wall 
			M[i][j]=-1;
		if(i==3&&j%2!=0)//creating a wall 
		M[i][j]=-1;
		if(i==3&&j==6)//creating a wall 
			M[i][j]=-1;
		if(i==4&&j==6)//creating a wall 
			M[i][j]=-1;
		if(j==5&&i<row-3&&i>9)//creating a wall 
			M[i][j]=-1;
		if(j==3&&i<row-3&&i>8)//creating a wall 
				M[i][j]=-1;
		if(j==1&&i<row-3&&i>4)//creating a wall 
			M[i][j]=-1;
	
		if(i==Global.CheeseY&&j==Global.CheeseX+1)
		{
			gst.setSpot(new Spot(j,i));
			gst.setData(gst.getSpot().toString());
			M[i][j]=2;}
	}

}
	

public Maze(int row,int col,Spot Cheese,Spot Mice)throws InterruptedException{
	this.col=col;
	this.row=row;
	gst=new State();
	sst=new State();
	M=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++){
			this.M[i][j]=0;

			if(i==1&&j<col-1&&j>=1)
				this.M[i][j]=-1;
			if(j==1&&i<=col-1&&i>=1)
				this.M[i][j]=-1;
			if(new Spot(i,j).isEqual(Cheese))
				this.M[i][j]=2;	
			if(new Spot(i,j).isEqual(Mice))
				this.M[i][j]=1;
			}
	Setlocations();
}
public Maze(Maze maze){//Copyctor

	this.col=maze.getCol();
	this.row=maze.getRow();
	this.gst=maze.getGstate();
	this.sst=maze.getSstate();
	this.M=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++){
			this.M[i][j]=maze.getValue(j,i);
			}	
}
public Maze(Maze maze,State start,State goal){//ctor

	this.col=maze.getCol();
	this.row=maze.getRow();
	this.gst=new State(start);
	this.sst=new State(goal);
	this.M=new int[row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++){
			this.M[i][j]=maze.getValue(j,i);
			}	
}




	public Maze(int[][] data) {
		row=data.length;
		col=data[0].length;
		M=new int[data.length][data[0].length];
		for(int i=0;i<this.row;i++)
			for (int j=0;j<col;j++){
				M[i][j]=data[i][j];
				if (this.M[i][j]==1){
				sst.setSpot(new Spot(j,i));
				}
				else if (this.M[i][j]==2){
					gst.setSpot(new Spot(j,i));
				}
}
	}



	public void Setlocations(){//sets the locations of the current position and the target position
		for(int i=0;i<this.row;i++)
			for (int j=0;j<col;j++){
				if (this.M[i][j]==1){
				sst.setSpot(new Spot(j,i));
				}
				else if (this.M[i][j]==2){
					gst.setSpot(new Spot(j,i));
				}
			}
	
	
	}	
	public void PrintMaze(){//prints the maze as is 
		for(int i=0;i<this.row;i++)
			for (int j=0;j<col;j++){
				if(M[i][j]==-1)
				System.out.print("*");
				else if(M[i][j]!=0)
				System.out.print(M[i][j]);
				else if(M[i][j]==0)
						System.out.print("0");
				if(j==col-1)
					System.out.println("");
			
			}}
	
	public boolean setStartPoint(int x,int y){
		int x1=this.getSstate().getSpot().getx();
		int y1=this.getSstate().getSpot().gety();
		M[y1][x1]=0;
		if(getValue(x,y)!=-1)
			M[y][x]=1;
		Setlocations();
		
			return true;	
		}
	public boolean setGoalPoint(int x,int y){
		int x1=this.getGstate().getSpot().getx();
		int y1=this.getGstate().getSpot().gety();
		M[y1][x1]=0;
		if(getValue(x,y)!=-1)
			M[y][x]=2;
		Setlocations();
		
			return true;	
		}
	
	
	public Maze RandomWalls(){
		for(int i=0;i<this.getRow();i++)
			for (int j=0;j<this.getCol();j++)
				if(new Random().nextInt()%5==0&&M[i][j]!=1&&M[i][j]!=2)
					M[i][j]=-1;
		return this;
		
	}
	
	public int getRow(){
			
		return row;
		}
	
	public int getCol(){
			
			return col;
		}
	
	public int getValue(int x,int y){
		
		if(x<col&&x>=0&&y<row&&y>=0){
		return M[y][x];}
		else
		return -1;
		
		
	}
	public State getSstate(){
		return new State(sst);
		
	}
	
	public State getGstate(){
		return new State(gst);
		
	}

		public int[][] GetData(){
			int [][]temp=M.clone();
			
			return temp;
			
		}


		public void setMaze(int[][] data) {
			row=data.length;
			col=data[0].length;
			M=new int[data.length][data[0].length];
			for(int i=0;i<this.row;i++)
				for (int j=0;j<col;j++){
					M[i][j]=data[i][j];
					if (this.M[i][j]==1){
					sst.setSpot(new Spot(j,i));
					}
					else if (this.M[i][j]==2){
						gst.setSpot(new Spot(j,i));
					}
		}
	
	
		}}
	
	


