package Presenter;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Client_Server.TCPClient;
import Model.Model;
import Model.MazeModel.GameMazeModel;
import View.CustomDialog;
import View.UserDataGui;
import View.View;
/**
 * 
 * @author Lev veliki
 * This class coordinates between the model and the view class 
 * using the observer design pettern 
 */
public class Presenter implements Observer{
	View ui;
	Model model;
	String Hint=null;
	TCPClient client=null;
	int TreeHeight=-1; 
	boolean AiSolving=false;
	/**
	 * 
	 * 
	 * @param v
	 * @param m
	 * @throws Exception
	 * initiates the model and the view start a view thread
	 * starts the client thread;  
	 * 
	 */
	public Presenter(View v, Model m) throws Exception {
		 ui=v;
		 model=m;
		 new Thread((Runnable) ui).start();
		 new Thread(new Runnable(){

			@Override
			public void run() {
				 try {
					client=new TCPClient(model.getData(),model.GetScore(),false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 client.Connect();
				 
				
			}}).start();
		
	}
	/**
	 * each time the Observable is changed and notifies the observers this method is being activated 
	 * the updates are sent from the view and model 
	 * 
	 */
	@Override
	public void update(Observable subject, Object arg1) {
		if(subject.getClass().toString().contains("Model"))
			if(arg1==null)
			model.AddRandom();
		int UserCommand=ui.getUserCommand();
		if(UserCommand==13){
			System.exit(1);
			
		}
		if(UserCommand==2){//right
			model.moveRight();
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
			}
		else if(UserCommand==1){//left
			
			model.moveLeft();
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
		}
		else if(UserCommand==3){//down
		
		model.moveDown();
		ui.getBoard().SetScore(model.GetScore());
		ui.getBoard().SetMaxScore(model.getMaxScore());
		}
		else if(UserCommand==4){//up
			model.moveUp();
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
		 }
		else if(UserCommand==8&&model.getClass()==GameMazeModel.class){//UPright
				((GameMazeModel)model).moveURight();
				ui.getBoard().SetScore(model.GetScore());
				ui.getBoard().SetMaxScore(model.getMaxScore());
				}
		else if(UserCommand==7&&model.getClass()==GameMazeModel.class){//Upleft
			((GameMazeModel)model).moveULeft();
				ui.getBoard().SetScore(model.GetScore());
				ui.getBoard().SetMaxScore(model.getMaxScore());
			}
		else if(UserCommand==6&&model.getClass()==GameMazeModel.class){//downRight
			((GameMazeModel)model).moveDRight();
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
			}
		else if(UserCommand==5){//downLeft
			((GameMazeModel)model).moveDLeft();
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
			 }
		 else if(UserCommand==9){//new game
			 model.NewGame();
			ui.getBoard().SetScore(0);
			ui.Refresh();
		
		}
		 else if(UserCommand==10){//undomove
				model.UndoMove();
				ui.getBoard().SetScore(model.GetScore());
				ui.Refresh();	
				
				
		}
		 else if(UserCommand==11){//save game
			 Save();}
		 else if(UserCommand==12){//load game
			 Load();
		}
		 else if(UserCommand==15){//Give me A hint
			 try {	
				 if(!client.isServerUp())
					 client.Connect();
				 
				 if(client.isServerUp()&&!model.GameOver()&&!AiSolving){
					 AiSolving =true;
					 if(TreeHeight==-1){
					 UserDataGui Select=new UserDataGui(ui.getBoard().getDisplay(),"AIhint");
					 TreeHeight=(Select.getTreeHeight());}
				 	Hint=client.Send2Server(model.getData(),model.GetScore(),TreeHeight);
				 	if(Hint!=null)
					switch(Hint){
					case "RIGHT":{model.moveRight();
					model.AddRandom();break;}
					case "LEFT":{model.moveLeft();
					model.AddRandom();break;}
					case "UP":{model.moveUp();
					model.AddRandom();break;}
					case "DOWN":{model.moveDown();
					model.AddRandom();break;}
					}
				 	
					ui.getBoard().SetScore(model.GetScore());
					ui.Refresh();
				 }
				 else{
					 MessageBox Box=new MessageBox(new Shell());
					 if(!model.GameOver())
					 Box.setMessage("Server is down at the moment try later please");
					 else if(model.GameOver())
						 Box.setMessage("Game is Over Cant to that");
					 else if(AiSolving){
						 Box.setMessage("Currently Solving cant do that now");
					 }
					 Box.open();
					 
				 }
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			 
			 else if(UserCommand==14){
				 //just play the solution
				 if(!client.isServerUp())//if server down 
					 client.Connect();	//try to connect
				 if(client.isServerUp&&!model.GameOver()){
				 ui.getBoard().SetScore(model.GetScore());
				 UserDataGui Select=new UserDataGui(ui.getBoard().getDisplay(),"AIsolve");
				 ui.AIPlayer(client,Select.getTreeHeight(),Select.getNumberOfMoves());}
				 else{
					 MessageBox Box=new MessageBox(new Shell());
					 if(!model.GameOver())
					 Box.setMessage("Server is down at the moment try later please");
					 else
						 Box.setMessage("Game is Over Cant do that");
					 Box.open();
					 
				 }
				 
			 }
		ui.displayData(model.getData());
		 if(model.GameOver()){
			CustomDialog D=new CustomDialog(ui.getBoard().getShell());
			if(model.GameWon())
				D.setMessage("You Won \nyour Score is "+ model.GetScore());
			else
			D.setMessage("Game Over \nyour Score is "+ model.GetScore());
			 D.open();
			 if(D.isExitFlag())
			 System.exit(1);
			 else if(D.isLoadGameFlag()){
				 model.LoadGame();
			 ui.getBoard().SetScore(model.GetScore());
			 ui.getBoard().SetMaxScore(model.getMaxScore());
			 ui.Refresh();}
			 else if(D.isNewGameFlag()){
				 model.NewGame();
				ui.getBoard().SetBoard(model.getData());
				ui.getBoard().SetScore(0);
				ui.Refresh();
				ui.getBoard().setFocus();}
			 else if(D.isUndoGameFlag()){
				 model.UndoMove();
				ui.getBoard().SetBoard(model.getData());
				ui.getBoard().SetScore(model.GetScore());
				ui.Refresh();
				ui.getBoard().setFocus();}
		}
		 ui.Refresh();
	
	
	
	
		}
	/**
	 * 
	 * opens a file dialog and lets you pick the file you want to save to
	 */
		
	private void Save() {
		 String [] Extensions=new String[1];
			Extensions[0]=".xml";
			FileDialog FD=new FileDialog(ui.getBoard().getShell());
			FD.setFilterExtensions(Extensions);
			FD.open();
			model.SaveGame(FD.getFilterPath()+"\\"+FD.getFileName());
			ui.getBoard().SetScore(model.GetScore());
			ui.getBoard().SetMaxScore(model.getMaxScore());
			ui.getBoard().setFocus();
	}
	/**
	 * opens a file dialog and lets you pick the file you want to load from 
	 */
		public void Load(){
			model.LoadGame();
			 ui.getBoard().SetScore(model.GetScore());
			 ui.getBoard().SetMaxScore(model.getMaxScore());
			 ui.getBoard().setFocus();
			
		}
	}
	
		
		
	


