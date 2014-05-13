package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CustomDialog extends Dialog
{
    protected String message="" ;
    private Shell shell;
	protected boolean newGameFlag;
	private boolean undoGameFlag;
	protected boolean LoadGameFlag;
	protected boolean ExitFlag;
	public boolean isNewGameFlag() {
		return newGameFlag;
	}
	
	public void setNewGameFlag(boolean newGameFlag) {
		this.newGameFlag = newGameFlag;
	}

	public boolean isLoadGameFlag() {
		return LoadGameFlag;
	}

	public void setLoadGameFlag(boolean loadGameFlag) {
		LoadGameFlag = loadGameFlag;
	}

	public boolean isExitFlag() {
		return ExitFlag;
	}

	public void setExitFlag(boolean exitFlag) {
		ExitFlag = exitFlag;
	}


    public CustomDialog(Shell parent)
    {
        // Pass the default styles here
        this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell = new Shell(parent.getDisplay());
    }

    public CustomDialog(Shell parent, int style)
    {
        // Let users override the default styles
        super(parent, style);
        shell = parent;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void open()
    {
        shell.setText(getText());
        createContents(shell);
        shell.pack();
        shell.open();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }

    private void createContents(final Shell shell)
    {
        shell.setLayout(new GridLayout(3, true));
        shell.setText("Choose");
//         Show the message
        Label label = new Label(shell, SWT.NONE);
        label.setText(message);
        GridData data = new GridData();
        data.horizontalSpan = 3;
        label.setLayoutData(data);

        // Display the input box
        Label image = new Label(shell, SWT.NONE);
        image.setImage(shell.getDisplay().getSystemImage(SWT.ICON_QUESTION));
        
        data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        data.horizontalSpan = 4;
        image.setLayoutData(data);

        Button newGame = new Button(shell, SWT.PUSH);
        newGame.setText("newGame");
        data = new GridData(SWT.FILL, SWT.END, true, true);
        newGame.setLayoutData(data);
        newGame.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent event)
            {
                newGameFlag=true;
                shell.close();
            }
        });

        Button Load = new Button(shell, SWT.PUSH);
        Load.setText("LoadGame");
        data = new GridData(SWT.FILL, SWT.END, true, true);
        Load.setLayoutData(data);
        Load.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent event)
            {	
              LoadGameFlag=true;
              shell.close();
            }	
        });
        Button Undo = new Button(shell, SWT.PUSH);
        Undo.setText("UndoMove");
        data = new GridData(SWT.FILL, SWT.END, true, true);
        Undo.setLayoutData(data);
        Undo.addSelectionListener(new SelectionAdapter()
        {
            

			public void widgetSelected(SelectionEvent event)
            {	
              setUndoGameFlag(true);
              shell.close();
            }	
        });
        
        
        

        Button Exit = new Button(shell, SWT.PUSH);
        Exit.setText("Exit");
        data = new GridData(SWT.FILL, SWT.END, true, true);
        Exit.setLayoutData(data);
        Exit.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent event)
            {	ExitFlag=true;
                shell.close();
            }
        });

        shell.setDefaultButton(Exit);
    }

	public boolean isUndoGameFlag() {
		return undoGameFlag;
	}

	public void setUndoGameFlag(boolean undoGameFlag) {
		this.undoGameFlag = undoGameFlag;
	}


}
