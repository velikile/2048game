package Controller;
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

		//GameMazeModel gm=new GameMazeModel();
		//MazeView gmb=new MazeView();
		 Game2048Model gm=new Game2048Model();
		 Game2048View gmb=new Game2048View();
		 Presenter P=new Presenter(gmb,gm);
		 gmb.addObserver(P);
		 gm.addObserver(P);
	}}		