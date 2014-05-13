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

import View.MazeView;
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
		 Game2048Model gm=new Game2048Model();
		 Game2048View gmb=new Game2048View();
		Presenter P=new Presenter(gmb,gm);
		gmb.addObserver(P);
		gm.addObserver(P);

	}}		