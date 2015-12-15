package View;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import Client_Server.TCPClient;
import Mazepack.Maze;
import Model.MazeModel.GameMazeModel;

/**
 * 
 * 
 * @author Lev veliki
 * this is The view Side for the maze game
 *
 */
public class MazeView extends Observable implements View,Runnable {
	private Display display;
	private Shell shell;
	MazeBoard mazeboard;
	private Button newGameButton;
	private Button undoMoveButton;
	private Button SaveGameButton;
	private Button LoadGameButton;
	public volatile String userCommand;
	int key,keyTwo;
	private Timer T=new Timer();
	public volatile boolean timeflag=false;
	KeyEvent temp,tempTwo;
	boolean doublekeyFlag=false;
	static Stack <KeyEvent> keys=new Stack<KeyEvent>();

	/**
	 * Sets the data and redraw the gui
	 */
	@Override
	public void displayData(final int[][] data) {
		if(!mazeboard.isDisposed()){
		mazeboard.SetBoard(data);
			mazeboard.redraw();	}
			}

	/**
	 * @return userCommand
	 * this return an int which represent the user command
	 */
	@Override
	public int getUserCommand() {
		
			int x=0;
			if(userCommand=="exit"){
				x=13;
				
			}
		 	if(userCommand=="upleft"){
		 		
			 		x=7;
		 	}
			else if(userCommand=="downleft"){
				
				 	x=5;
			}
			else if(userCommand=="downright"){
				
				 x=6;
			}
			else if(userCommand=="upright"){
				
				 x=8;

			}
			else if(userCommand=="up"){
				
					 x=4;
			}
				else if(userCommand=="down"){
					
					 x=3;
				}
				else if(userCommand=="left"){
					
					 x=1;
				}
				else if(userCommand=="right"){
					
					 x=2;
				}else if(userCommand=="loadGame"){
					
					 x=12;
				}else if(userCommand=="newGame"){
					
					 x=9;
				}else if(userCommand=="saveGame"){
					
					 x=11;
				}else if(userCommand=="undoMove"){
					
					 x=10;
				}
		 	
		return x;
	}
/**
 * start the gui components
 */
	private void initComponents(){
		if(null==Display.getDefault().getActiveShell())
			shell =new Shell();
		else{
			
			shell=Display.getDefault().getActiveShell();
		}
			if(shell!=null){
			shell.setLayout(new GridLayout(2, false));
			shell.setSize(500, 500);
			shell.setText("My Maze");
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

			display=Display.getDefault();
			mazeboard= new MazeBoard(shell, SWT.DOUBLE_BUFFERED);
			mazeboard.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,2));
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

			LoadGameButton=new Button(shell, SWT.PUSH);
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

			mazeboard.addKeyListener(new KeyListener(){
				private long lastpress=0;

				@Override
				public void keyPressed(KeyEvent e) {
					keys.add(e);
					if(System.currentTimeMillis()-lastpress>10){
						lastpress=System.currentTimeMillis();
					T.schedule(new TimerTask(){

						@Override
						public void run() {
								Thread.currentThread().setName("KeyStrokeTimer");
							if(display.isDisposed()){
								
								T.cancel();
							}
								if(!keys.isEmpty()){
								temp=keys.pop();
								key=temp.keyCode;}
								
								

								if(!keys.isEmpty()){
								tempTwo=keys.pop();
								keyTwo=tempTwo.keyCode;}
								else if(key!=0)	 
									keyTwo=key;
								else if(keyTwo!=0)
									key=keyTwo;
								if(key==keyTwo){
								if(key==SWT.ARROW_DOWN)
									userCommand="down";
								if(key==SWT.ARROW_LEFT)
									userCommand="left";
								if(key==SWT.ARROW_UP)
									userCommand="up";
								if(key==SWT.ARROW_RIGHT)
									userCommand="right";
								}
									
								else {
										if((key==SWT.ARROW_DOWN&&keyTwo==SWT.ARROW_LEFT)||(keyTwo==SWT.ARROW_DOWN&&key==SWT.ARROW_LEFT)){
											userCommand="downleft";

										}
										else if((key==SWT.ARROW_UP&&keyTwo==SWT.ARROW_LEFT)||(keyTwo==SWT.ARROW_UP&&key==SWT.ARROW_LEFT)){
											userCommand="upleft";

										}
										else if((key==SWT.ARROW_DOWN&&keyTwo==SWT.ARROW_RIGHT)||(keyTwo==SWT.ARROW_DOWN&&key==SWT.ARROW_RIGHT)){
											userCommand="downright";}

										else if((key==SWT.ARROW_UP&&keyTwo==SWT.ARROW_RIGHT)||(keyTwo==SWT.ARROW_UP&&key==SWT.ARROW_RIGHT)){
											userCommand="upright";
										}
										
										keys.clear();
										
									}
								display.syncExec(new Runnable(){

									@Override
									public void run() {
										Notify();
									}});
														
						}},15);
						
					}
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}});
				mazeboard.addMouseListener(new MouseListener(){
				Point mouseloc=new Point(0, 0);
				int xenter,xexit,yenter,yexit;
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseDown(MouseEvent e) {
					userCommand="";
					int width=mazeboard.getParent().getBounds().width;
					int height=mazeboard.getParent().getBounds().height;
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
					if(xdif>15&&ydif>15)
						userCommand="upleft";
						else if(ydif>15&&xdif<-15)
							userCommand="upright";
						else if(xdif<-15&&ydif<-15)
							userCommand="downright";	
						else if(ydif<-15&&xdif>15)
								userCommand="downleft";
						else if(xdif>15)
					userCommand="left";
					else if(ydif>15)
						userCommand="up";
					else if(xdif<-15)
						userCommand="right";	
					else if(ydif<-15)
							userCommand="down";
					Notify();//This is for the user command 
					
				}	
					
				}

			});
			Notify();//This is made for the Display refresh 
	}
			
	}

	/**
	 * this is the Runnable method to run this gui in a new thread;
	 */
	@Override
	public void run() {
		Thread.currentThread().setName("ViewThread");
				initComponents();
				if(shell!=null){
				shell.open();
				while(!shell.isDisposed()){
					if(!display.readAndDispatch())
						display.sleep();	
				}
		
					T.cancel();	
				
			}}
	/**
	 * notifies the observers 				
	 */

	public void Notify(){
		this.setChanged();
		this.notifyObservers();
		
	}
	/**	
	 * @return Board 
	 * return the graphical component of the gui 
	 */
	@Override
	public Board getBoard() {
		return mazeboard;
	}

	@Override
	public void AIPlayer(TCPClient C,int numberOfMoves,int TreeDepth){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Refresh() {
		if(!display.isDisposed())
			mazeboard.redraw();
	}

	

	}


