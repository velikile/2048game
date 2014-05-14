package Presenter;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Model.Model;
import Model.Game2048Model.Game2048Model;
import Model.MazeModel.GameMazeModel;
import View.CustomDialog;
import View.View;

public class Presenter implements Observer{
	View ui;
	Model model;
	public Presenter(View v,Model m) {
		 ui=v;
		 model=m;
		 new Thread((Runnable) ui).start();		  
		 
		
	}
	
	@Override
	public void update(Observable subject, Object arg1) {
	
	
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
			ui.getBoard().setFocus();
		
		}
		 else if(UserCommand==10){//undomove
				model.UndoMove();
				ui.getBoard().SetScore(model.GetScore());
				ui.getBoard().setFocus();	
				
				
		}
		 else if(UserCommand==11){//save game
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
		 else if(UserCommand==12){//load game
			 model.LoadGame();
			 ui.getBoard().SetScore(model.GetScore());
			 ui.getBoard().SetMaxScore(model.getMaxScore());
			 ui.getBoard().setFocus();
		}
		 
		 ui.displayData(model.getData());
		 if(model.GameOver()||model.isWinFlag()){
			
			CustomDialog D=new CustomDialog(ui.getBoard().getShell()){};
			if(model.isWinFlag())
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
			 ui.getBoard().setFocus();}
			 else if(D.isNewGameFlag()){
				 model.NewGame();
				ui.getBoard().SetBoard(model.getData());
				ui.getBoard().SetScore(0);
				ui.getBoard().redraw();
				ui.getBoard().setFocus();}
			 else if(D.isUndoGameFlag()){
				 model.UndoMove();
				ui.getBoard().SetBoard(model.getData());
				ui.getBoard().SetScore(model.GetScore());
				ui.getBoard().redraw();
				ui.getBoard().setFocus();}
		}
			ui.displayData(model.getData());	
	
	
	}
		
		}
	
		
		
	


