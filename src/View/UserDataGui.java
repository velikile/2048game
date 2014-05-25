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
 * 
 * this class is made to get the tree depth and numberofmoves 
 * Ai Solution That the presented activates
 *
 */

public class UserDataGui {
  Display display =null;
  Shell shell =null;
  Text textTreeDepth;
  Text textNumberOfMoves;
  int Treeheight;
  int NumberOfmoves;
  boolean HeightSet=false ;
  private void init(final String type) {
	  if(type=="AIsolve"){
    (new Label(shell, SWT.NULL)).setText("Tree Depth");
    textTreeDepth = new Text(shell, SWT.SINGLE | SWT.BORDER);
    textTreeDepth.setTextLimit(2);
    (new Label(shell, SWT.NULL)).setText("NumberOfMoves");
    textNumberOfMoves = new Text(shell, SWT.SINGLE | SWT.BORDER);
	  }else{
		  (new Label(shell, SWT.NULL)).setText("Tree Depth");
		    textTreeDepth = new Text(shell, SWT.SINGLE | SWT.BORDER);
		    textTreeDepth.setTextLimit(2);
	  }
    
    Button OK = new Button(shell,SWT.PUSH);
    OK.setText("Done");
    if(type=="AIsolve")
    new Label(shell,SWT.DOWN).setText("Enter the Tree height And\n The Number of Moves you want to get");
    else{new Label(shell,SWT.DOWN).setText("Enter the Tree height for the hint calculation ");
    	
    }
    OK.addSelectionListener(new SelectionListener(){

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {}
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			try{
			Treeheight=Integer.parseInt(textTreeDepth.getText());
			if(type=="AIsolve")
			NumberOfmoves=Integer.parseInt(textNumberOfMoves.getText());
			else {
				NumberOfmoves=1;
			}
			}
			catch(NumberFormatException e){
				MessageBox B=new MessageBox(shell,SWT.NO_REDRAW_RESIZE);
				B.setMessage("Enter Integers please");
				B.open();
			}
			shell.dispose();
		}});
  }  
  
  public UserDataGui(Display display,String type) {
	  this.display=display;
	  this.shell=new Shell(display);
    shell.setLayout(new GridLayout(2, false));
    shell.setBounds(200, 200, 400, 170);
    init(type);
    shell.setText("Control");
    textTreeDepth.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    if(type=="AIsolve")
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

public void SetTreeHeight(int Treeheight) {
	this.Treeheight=Treeheight;
	HeightSet=true;
	
}

}