package Mazepack;
public class Spot {
private int x;
private int y;
public Spot(int x,int y){
	this.x=x;
	this.y=y;
}	

public Spot(Spot now) {
	this.x=now.getx();
	this.y=now.gety();
	
}
public int getx(){
return x;
}

public int gety(){
return y;
}

public void setSpot(int X,int Y){
	this.x=X;
	this.y=Y;
}

public String toString(){
	return "("+x+","+y+")";
}

public boolean isEqual(Spot spot) {
	if(this.getx()==spot.getx()&&this.gety()==spot.gety())
	return true;
	else 
	return false;
}

}
