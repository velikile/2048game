package View;

import java.util.Observable;

import Model.Game2048Model.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Game2048View extends Observable implements View,Runnable {
	Display display;
	Shell shell;
	Board board;
	private Button newGameButton;
	private Button undoMoveButton;
	private Button SaveGameButton;
	private Button LoadGameButton;
	private Button AIButton;
	private Button HintButton;
	private String userCommand;
	
	public Game2048View() {
	}


	@Override
	public void displayData(final int[][] boardData) {
		board.SetBoard(boardData);
			board.redraw();
	}

	@Override
public  int getUserCommand() {
		int x=0;
			if(userCommand=="up"){
				 x=4;
				 userCommand="";
			}

			else if(userCommand=="down"){
				userCommand="";
				 x=3;
			}
			else if(userCommand=="left"){
				userCommand="";
				 x=1;
			}
			else if(userCommand=="right"){
				userCommand="";
				 x=2;}
			else if(userCommand=="loadGame"){
				userCommand="";
				 x=12;
			}else if(userCommand=="newGame"){
				userCommand="";
				 x=9;
			}else if(userCommand=="saveGame"){
				userCommand="";
				 x=11;
			}else if(userCommand=="undoMove"){
				userCommand="";
				 x=10;
			}
			else if(userCommand=="exit"){
				userCommand="";
				 x=13;
			}
			else if(userCommand=="AIGame"){
				userCommand="";
				 x=14;
			}
			else if(userCommand=="Hint"){
				userCommand="";
				 x=15;
			}

			
	return x;
}

		
			
	private void initComponents(){
		if(shell==null)
		shell = new Shell();
		display=Display.getDefault();
		shell.setLayout(new GridLayout(2, false));
		shell.setText("My 2048");
		Menu menuBar=new Menu(shell,SWT.BAR);
		MenuItem csFileMenu=new MenuItem(menuBar,SWT.CASCADE);
		csFileMenu.setText("File");
		Menu fileMenu=new Menu(shell,SWT.DROP_DOWN);
		MenuItem Exit=new MenuItem(menuBar,SWT.PUSH);
		Exit.setText("Exit");
//		
		Exit.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				userCommand="exit";
				Notify();
				// TODO Auto-generated method stub
				
			}});
		csFileMenu.setMenu(fileMenu);
		MenuItem load=new MenuItem(fileMenu,SWT.PUSH);
		load.setText("Load");
		MenuItem save=new MenuItem(fileMenu,SWT.PUSH);
		save.setText("Save");
		load.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				userCommand="loadGame";
				Notify();
				
				
			}});
		save.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				userCommand="saveGame";
				Notify();
				
				
			}});
		shell.setMenuBar(menuBar);
		if(board==null)
		board= new Board(shell, SWT.NONE);
		board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,2));
		newGameButton=new Button(shell, SWT.PUSH);
		newGameButton.setText("New game");
		newGameButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP, false, false, 1,1));
		newGameButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="newGame";
				Notify();
			}});

		undoMoveButton =new Button(shell, SWT.PUSH);
		undoMoveButton.setText("Undo Move");
		undoMoveButton.setLayoutData(new GridData(SWT.FILL,SWT.TOP, false, false, 1,1));
		undoMoveButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="undoMove";
				Notify();
			}});
		AIButton=new Button(shell, SWT.PUSH);
		AIButton.setText("Run AI");
		//AIButton.setLayoutData(new GridData(SWT.FILL,SWT.LEFT,false,false,1,1));
		//AIButton.setAlignment(SWT.CENTER);
		AIButton.addSelectionListener(new SelectionListener(){
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="AIGame";
				Notify();
			}});
		HintButton=new Button(shell, SWT.PUSH);
		HintButton.setText("Show A Hint");
		HintButton.addSelectionListener(new SelectionListener(){
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="Hint";
				Notify();
			}});
		
		SaveGameButton=new Button(shell, SWT.PUSH);
		SaveGameButton.setText("Save Game");
		SaveGameButton.addSelectionListener(new SelectionListener(){
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="saveGame";
				Notify();
			}});

		LoadGameButton=new Button(shell, SWT.PUSH);;
		LoadGameButton.setText("Load Game");
		LoadGameButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand="loadGame";
				Notify();
			}});

		board.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				
				
				 if(e.keyCode==SWT.ARROW_DOWN)
					userCommand="down";
				 if(e.keyCode==SWT.ARROW_UP)
					userCommand="up";
				if(e.keyCode==SWT.ARROW_LEFT)
					userCommand="left";
				if(e.keyCode==SWT.ARROW_RIGHT)
					userCommand="right";
				Notify();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				userCommand="";
				
			}});
		board.addMouseListener(new MouseListener(){
				int xenter,xexit,yenter,yexit;
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
				
					// TODO Auto-generated method stub
				
						xenter=e.x;
						yenter=e.y;
					
				}	

				@Override
				public void mouseUp(MouseEvent e) {
					// TODO Auto-generated method stub
					xexit=e.x;
					yexit=e.y;
					int xdif,ydif;
					if(xenter!=0&&yenter!=0){
					xdif=(xenter-xexit);
					ydif=(yenter-yexit);
					
					if(xdif>15)
					userCommand="left";
					else if(ydif>15)
						userCommand="up";
					else if(xdif<-15)
						userCommand="right";	
					else if(ydif<-15)
							userCommand="down";
					Notify();
				}}});	
		
		Notify();
	
	}

			private void Notify() {
				setChanged();
				notifyObservers();
				
			}

	@Override
	public void run() {
			initComponents();
			shell.open();
				while(!shell.isDisposed()){
					if(!display.readAndDispatch())
						display.sleep();}
					display.dispose();
			
		}
	public Board getBoard(){
		return board;
		
	}
	

	
	
		
	}


