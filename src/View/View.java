package View;

import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Canvas;

import Model.Game2048Model.Board;
public interface View {

public void displayData(int[][] data);
public Board getBoard();
public int getUserCommand();
public void AIPlayer();
}
