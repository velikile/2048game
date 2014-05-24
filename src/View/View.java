package View;

import Client_Server.TCPClient;

/**
 * 
 * @author Lev veliki
 *
 *this defines the methods for each view side of the game each Game must implement these methods
 *
 */

public interface View {
public void displayData(int[][] data);
public Board getBoard();
public void Refresh();
public int getUserCommand();
public void AIPlayer(TCPClient C,int numberOfMoves, int TreeDepth);

}
