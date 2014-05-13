package Mazepack;




public class State// implements Comparable<State> 
{
	private double gscore;
	private double fscore;
	private State come_from;
	private String Data;
	
	
	public State(State s){
		Data=s.getData();
		come_from=s.getCome_from();
		gscore=s.getGscore();
		fscore=s.getF();

	}
	public State(){
		this.Data=new String();
		this.come_from=null;
		this.gscore=0;
		this.fscore=0;
	}
	public State(String string) {
		this.Data=new String(string);
		this.gscore=0;
		this.fscore=0;
		come_from=null;
	}
	public double getF(){
		return fscore;
		
	}
	public void setF(double x){
		this.fscore=x;
	}

	public double getGscore() {
		return gscore;
	}
	public void setGscore(double gscore) {
		this.gscore = gscore;
	}
	    @Override
	    public boolean equals(Object spot) {
	    	if(spot==null)
	    			return false;
	        if(spot instanceof State) {
	        	if(this.getData().equals(((State) spot).getData()))
	        		return true;
	        }

	        return false;
	    }
	public boolean isEqual(State state) {
		if(this.getData().equals(state.getData()))
		return true;
		else 
		return false;
	}
	
	public void setSpot(Spot s){
		 Data=new String(s.toString());
		this.come_from=null;
		fscore=0;
		gscore=0;
	}
	public Spot getSpot(){
		String [] strings=getData().split(",");
		int x=Integer.parseInt(strings[0].substring(1));
		int y=Integer.parseInt(strings[1].substring(0, strings[1].length()-1));
		Spot s=new Spot(x,y);
		return s;
	}
	public State getCome_from() {
		return this.come_from; 
	}
	public void setCome_from(State come_from) {
		this.come_from = new State (come_from);
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
    @Override
    public int hashCode() {
        return (Data.hashCode());
    }

		
	
	
}