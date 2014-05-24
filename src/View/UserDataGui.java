package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author Lev veliki
 * this class is made to get the tree depth and numberofmoves 
 * for the Ai Solution for the presenter
 *
 */

public class UserDataGui {
  Display display =null;
  Shell shell =null;
  Text textTreeDepth;
  Text textNumberOfMoves;
  int Treeheight;
  int NumberOfmoves;

  private void init() {
    (new Label(shell, SWT.NULL)).setText("Tree Depth");
    
    
    textTreeDepth = new Text(shell, SWT.SINGLE | SWT.BORDER);
    textTreeDepth.setText("7");
    textTreeDepth.setTextLimit(16);

    (new Label(shell, SWT.NULL)).setText("NumberOfMoves");
    
    textNumberOfMoves = new Text(shell, SWT.SINGLE | SWT.BORDER);
    Button OK = new Button(shell,SWT.PUSH);
    OK.setText("Done");
    new Label(shell,SWT.DOWN).setText("Pick the Tree height And\n The Number of Moves you want to get");
    OK.addSelectionListener(new SelectionListener(){

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {}
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try{
			Treeheight=Integer.parseInt(textTreeDepth.getText());
			NumberOfmoves=Integer.parseInt(textNumberOfMoves.getText());}
			catch(NumberFormatException e){
				MessageBox B=new MessageBox(shell,SWT.NO_REDRAW_RESIZE);
				B.setMessage("Enter Integers please");
				B.open();
			}
			//System.out.println(Treeheight+" "+NumberOfmoves);
			shell.dispose();
		}});
  }  
  
  public UserDataGui(Display display) {
	  this.display=display;
	  this.shell=new Shell(display);
    shell.setLayout(new GridLayout(2, false));
    shell.setBounds(200, 200, 400, 170);
    
    init();
    shell.setText("Control");
    textTreeDepth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    textNumberOfMoves.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    shell.open();
    // Set up the event loop.
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        // If no more entries in event queue
        display.sleep();
      }}
      shell.dispose();
    
  }

  public int getTreeHeight() {
		return Treeheight;
	}

  public int getNumberOfMoves(){
		return NumberOfmoves;
	}

}