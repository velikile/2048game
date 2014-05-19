package Controller;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import Solver.Node;
import View.MazeView;
import Client_Server.Game2048H;
import Client_Server.TCPClient;
import Client_Server.TCPServer;
import Mazepack.Maze;
import Model.Game2048Model.Board;
import Model.Game2048Model.Game2048Model;
import Model.MazeModel.GameMazeModel;
import Presenter.Presenter;
import View.Game2048View;
























import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class run {
	public static void main(String[] args) throws InterruptedException {

		//GameMazeModel gm=new GameMazeModel();
		//MazeView gmb=new MazeView();
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//			
//				try {
//					TS.StartServer();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//			}}).start();
		
		 Game2048Model gm=new Game2048Model();
		 Game2048View gmb=new Game2048View();
		Presenter P=new Presenter(gmb,gm);
		gmb.addObserver(P);
		gm.addObserver(P);
		//		 Node N=new Node();
//		 boolean first=false;
//		  System.out.println(N.getState()+"\n");
//		  while(!N.TerminalNode()){
//		 while(N.getChildren()!=null){
//			 	N=N.getChildren()[0];
//			System.out.println(N.getState()); 
//			System.out.println(N.getLastMove());
//		 }
//		 N=new Node(N.getState(),5,first);
//		 first=!first;}
//		System.out.println(N.getState());
		 
	}}		