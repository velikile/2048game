package Mazepack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MazeGen {

public class Cell implements Comparable<Object> {
	int x;
	int y;
public Cell(int x, int y) {
		this.x=x;
		this.y=y;
}

public Cell(Cell b) {
	this.x=b.x;
	this.y=b.y;
}

@Override
public int compareTo(Object O) {
	Cell temp=(Cell)O;
	if(temp.x==x&&temp.y==y)
	return 0;
	
	else return -1;
}
public String toString(){
	String Temp=new String();
	Temp=Temp.concat("("+x+","+y+")");
	return Temp;	
}

public boolean equals(Object O){
	Cell temp=(Cell)O;
	if(temp.compareTo(this)==0)
		return true;
	else return false;
	}

public Cell goleft() {
	Cell left=new Cell(this);
	left.y=y-1;
	return left;
}
public Cell goright() {
	Cell right=new Cell(this);
	right.y=y+1;
	return right;
}
public Cell godown() {
	Cell down=new Cell(this);
	down.x=x+1;
	return down;
	
}
public Cell goup() {
	Cell up=new Cell(this);
	up.x=x-1;
	return up;
}
}

	public interface MazeGenerator {
		Maze GenerateMaze(Maze M);
	}
	public class Prim_alg implements MazeGenerator {

		Maze M;
		volatile Integer size=4;
		volatile int x=0;
		volatile int y=0;


		@Override
		public Maze GenerateMaze(final Maze M) {
			M.Data[0][0]=2;
			List<Cell> Walls=new LinkedList<Cell>();
			Walls.addAll(M.getWalls(new Cell(0,0)));
			while(!Walls.isEmpty()){
				Cell B=(Cell) Walls.get(new Random().nextInt(Walls.size()));
				if(M.OpositeSide(B)){
					Cell newCell=M.addOposite(B);
					M.Data[B.x][B.y]=3;
					Walls.addAll(M.getWalls(newCell));
				}
				else if(M.notOposite(B))
				Walls.remove(B);	
			}
			return M;
			
		}

		

		protected void DecreaseSize(int i) {
			size-=i;
			
		}

		protected void IncreaseSize(int i) {
			size+=i;
			
		}}

	public class Maze {
		int [][] Data;
		int row;
		int col;
		MazeGenerator MazeGen;

		public Maze(int row,int col){
		this.row=row*2-1;
		this.col=col*2-1;
		Data=new int[this.row][this.col];
		for(int i=0;i<this.row;i++)
			for(int j =0; j<this.col;j++){
				if(i%2==0){
					if(j%2==1)
						Data[i][j]=1;
					else Data[i][j]=0;}
				else {
					Data[i][j]=1;
					
				}
				
			}
		MazeGen=new Prim_alg();
		//MazeGen=new Kruskal_alg();
		}
		public Maze(Maze M){
			this.row=M.row;
			this.col=M.col;
			this.Data=new int[row][col];
			for(int a=0;a<row;a++)
				for(int b=0;b<col;b++){
					this.Data[a][b]=M.Data[a][b];
				}
					
			
		}
		public int [][] GetArray(){
			for(int i=0;i<Data.length;i++)
				for(int j=0;j<Data[0].length;j++){
				
				
					if(Data[i][j]==3){
						Data[i][j]=0;
						}
					if(Data[i][j]==1){
						Data[i][j]=-1;
						}
					if(Data[i][j]==2){
						Data[i][j]=0;
						}
					
					
				}
			Data[Data.length-1][Data[0].length-1]=1;
			Data[0][0]=2;
			
			//System.out.println(this);
			
			return Data;
			
		}

		public void GenerateMaze(){	
			Maze newMaze=MazeGen.GenerateMaze(this);
			Init(newMaze);
		}



		private void Init(Maze M) {
			this.row=M.row;
			this.col=M.col;
			for(int a=0;a<row;a++)
				for(int b=0;b<col;b++){
					this.Data[a][b]=M.Data[a][b];
				}
			
		}
		
		public String toString(){
			boolean first=true;
			 String TempString =new String();
			 
			for (int a[]:Data){
				if(first){
					first=false;}
				else TempString=TempString.concat("\n");
				for (int b:a){
					TempString=TempString.concat(Integer.toString(b)+"  ");
					
				}}
			return TempString;
			
		}
		public boolean isInbound(Cell C){
			if(C.x>=row||C.x<0)
				return false;
			if(C.y>=col||C.y<0)
				return false;
			return true;
			
		}


		public List<Cell> getWalls(Cell b) {
			Cell temp=new Cell(b);
			List <Cell> neighbors=new LinkedList<Cell>();
			if(isInbound(temp.goleft()))//going left
				if(Data[temp.goleft().x][temp.goleft().y]==1)
					neighbors.add(temp.goleft());
			if(isInbound(temp.goright()))//going right
				if(Data[temp.goright().x][temp.goright().y]==1)
				neighbors.add(temp.goright());
			if(isInbound(temp.goup()))//going up
				if(Data[temp.goup().x][temp.goup().y]==1)
				neighbors.add(temp.goup());
			if(isInbound(temp.godown()))//going down
				if(Data[temp.godown().x][temp.godown().y]==1)
				neighbors.add(temp.godown());
			
			return neighbors;
		}


		public boolean OpositeSide(Cell wall) {//checks if the opposite side of the wall is free compared to the other side of the wall 
			if(!isInbound(wall))
				return false;
			if(wall.x%2==0&&wall.y%2==1){
				if(Data[wall.goright().x][wall.goright().y]==2){
					if(Data[wall.goleft().x][wall.goleft().y]==0)
					return true;
				}else if(Data[wall.goright().x][wall.goright().y]==0)
					if(Data[wall.goleft().x][wall.goleft().y]==2)
					return true;
				
				else if(Data[wall.goleft().x][wall.goleft().y]==0){
					if(Data[wall.goright().x][wall.goright().y]==2)
						return true;
				}	
				else if(Data[wall.goleft().x][wall.goleft().y]==2)
					if(Data[wall.goright().x][wall.goright().y]==0)
					return true;
				
			}
			
			if(wall.x%2==1&&wall.y%2==0){
				if(Data[wall.goup().x][wall.goup().y]==2){
					if(Data[wall.godown().x][wall.godown().y]==0)
					return true;}
				else if(Data[wall.goup().x][wall.goup().y]==0)
					if(Data[wall.godown().x][wall.godown().y]==2)
					return true;
				if(Data[wall.goup().x][wall.goup().y]==0)
					if(Data[wall.godown().x][wall.godown().y]==2)
						return true;
				if(Data[wall.goup().x][wall.goup().y]==2)
					if(Data[wall.godown().x][wall.godown().y]==0)
						return true;
			}
			return false;
		}
		public boolean notOposite(Cell wall){
			if(!isInbound(wall))
				return false;
			if(wall.x%2==0&&wall.y%2==1){
				if(Data[wall.goright().x][wall.goright().y]==2){
					if(Data[wall.goleft().x][wall.goleft().y]==2)
					return true;
				}else if(Data[wall.goright().x][wall.goright().y]==2)
					if(Data[wall.goleft().x][wall.goleft().y]==2)
					return true;
				
				else if(Data[wall.goleft().x][wall.goleft().y]==2){
					if(Data[wall.goright().x][wall.goright().y]==2)
						return true;
				}	
				else if(Data[wall.goleft().x][wall.goleft().y]==2)
					if(Data[wall.goright().x][wall.goright().y]==2)
					return true;
				
			}
			
			if(wall.x%2==1&&wall.y%2==0){
				if(Data[wall.goup().x][wall.goup().y]==2){
					if(Data[wall.godown().x][wall.godown().y]==2)
					return true;}
				else if(Data[wall.goup().x][wall.goup().y]==2)
					if(Data[wall.godown().x][wall.godown().y]==2)
					return true;
				if(Data[wall.goup().x][wall.goup().y]==0)
					if(Data[wall.godown().x][wall.godown().y]==2)
						return true;
				if(Data[wall.goup().x][wall.goup().y]==2)
					if(Data[wall.godown().x][wall.godown().y]==2)
						return true;
			}
			return false;
			
			
			
		}
		public Cell addOposite(Cell wall) {
			if(wall.x%2==0&&wall.y%2==1){
				if(Data[wall.goright().x][wall.goright().y]==2){
					Data[wall.goleft().x][wall.goleft().y]=2;
					return wall.goleft();
				}
				if(Data[wall.goleft().x][wall.goleft().y]==2){
					Data[wall.goright().x][wall.goright().y]=2;
					return wall.goright();
				}
			}
			else if(wall.x%2==1&&wall.y%2==0){
				if(Data[wall.goup().x][wall.goup().y]==2){
					Data[wall.godown().x][wall.godown().y]=2;
					return wall.godown();
				}
				if(Data[wall.godown().x][wall.godown().y]==2){
					Data[wall.goup().x][wall.goup().y]=2;
					return wall.goup();	
				}
			}
			// TODO Auto-generated method stub
			return wall;
			
		}

		}
	

}
