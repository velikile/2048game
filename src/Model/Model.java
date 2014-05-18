package Model;

import java.util.Stack;

import Solver.Node;

public interface Model {
	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();
	int [][] getData();
	boolean GameOver();
	void NewGame();
	void Notify();
	int GetScore();
	int getMaxScore();
	boolean LoadGame();
	void UndoMove();
	boolean SaveGame(String string);
	String [] GetAvailableMoves();
	public void AddRandom();
	boolean GameWon();
	void AIPlayer();
	int CountEmptyCells();
	
	
}
