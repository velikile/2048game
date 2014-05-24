package Model;

/**
 * 
 * @author Lev veliki
 * interface for the 2048 game and the maze game
 * this defines the main mechanics of the GAME each game must implement those methods 
 *
 */
public interface Model {
	/**
	 * Movement Methods
	 */
	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();
	/**
	 * @return Data
	 * return the Game Board Data
	 */
	int [][] getData();
	/**
	 * 
	 * @return Boolean 
	 * true if the game is finished false otherwise
	 */
	boolean GameOver();
	/**
	 * Start A new Game
	 */
	void NewGame();
	/**
	 * An Observer pattern method to setChanged() and notifyObservers() 
	 */
	void Notify();
	/**
	 * 
	 * @return Score 
	 * returns the current Score
	 */
	int GetScore();
	/**
	 * 
	 * @return BestScore
	 * The Best So far
	 */
	int getMaxScore();
	/**
	 * loads the game
	 * @return Boolean 
	 * true if success false otherwise
	 */
	boolean LoadGame();
	/**
	 * Undo one move at a time 
	 */
	void UndoMove();
	/**
	 * save the game to the file path string
	 * @param string
	 * @return Boolean 
	 * true if success false otherwise
	 */
	boolean SaveGame(String string);
	/**
	 * 
	 * @return Moves
	 * returns the available moves from the current State
	 */
	String [] GetAvailableMoves();
	/**
	 * Add a random piece to the game
	 * you can specify what kind of element you would like to add 
	 */
	public void AddRandom();
	/**
	 * Checks if you win the game
	 * @return Boolean 
	 */
	boolean GameWon();
	
	
	
}
