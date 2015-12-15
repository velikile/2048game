package Controller;
import java.util.Collection;
import java.util.Set;

import Model.Game2048Model.Game2048Model;
import Model.MazeModel.GameMazeModel;
import Presenter.Presenter;
import View.Game2048View;
import View.MazeView;
/**
 * the app main function
 * @author Lev veliki
 *
 */
public class run {
	public static void main(String[] args) throws Exception {

		GameMazeModel gm=new GameMazeModel();
		final MazeView gmb=new MazeView();
		//Game2048Model gm=new Game2048Model();
		//Game2048View gmb=new Game2048View();
		 final Presenter P=new Presenter(gmb,gm);
		 gmb.addObserver(P);
		 gm.addObserver(P);
		 new Thread(new Runnable(){

			@Override
			public void run() {
				Thread.currentThread().setName("TestThread");
				if(!Thread.currentThread().isInterrupted())
				while(P.ViewThreadUp()){
					
					if(gmb!=null&&gmb.getBoard()!=null&&gmb.getBoard().isDisposed()){
						System.exit(0);
						
					}
							
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Set<Thread> Tset=Thread.getAllStackTraces().keySet();
					System.out.println(Tset);
					
				}
				
			}}).start();
	}}		