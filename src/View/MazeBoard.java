package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import Mazepack.Maze;
/**
 * 
 * @author Lev veliki
 * this class is for the maze game gui  
 *
 */

public class MazeBoard extends Board {
		public Maze M;
		 int mousex=-1;
		 int mousey=-1;
		 boolean mouseflag=false;
		 boolean mouseInBoundflag=false;
		protected boolean cheeseFlag=false;
		
	public MazeBoard(final Composite parent, int style) {
		super(parent, style);
		
		addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e ) {
				
				mouseflag=true;
				mousex=e.x;
				mousey=e.y;
				
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
				mousex=-1;
				mousey=-1;
				mouseflag=false;
				redraw();
				// TODO Auto-generated method stub
				
			}});
		addListener(SWT.MouseMove, new Listener(){

			@Override
			public void handleEvent(Event e) {
				mousex=e.x;
				mousey=e.y;
				if(mouseflag==true)
				redraw();
				
			}});
		addPaintListener(new PaintListener(){
			Image cheese=new Image(getDisplay(),"c:/Users/SAMSUNG/workspace/Game/src/cheese.gif");
			Image cheese2;
			
		
			Image mouse=new Image(getDisplay(),"c:/Users/SAMSUNG/workspace/Game/src/mouse.gif");
			Image mouse2;//=new Image(getDisplay(),mouse.getImageData().scaledTo(width/21-1,height/21-1));
			@Override
			public void paintControl(PaintEvent e) {
				int width=getParent().getBounds().width;
				int height=getParent().getBounds().height;
				mouse2=new Image(getDisplay(),mouse.getImageData().scaledTo(width/21-1,height/21-1));
				cheese2=new Image(getDisplay(),cheese.getImageData().scaledTo(width/21-1,height/21-1));
				int [][]data=M.GetData();
				   for(int i=0;i<data.length;i++)
					   for (int j=0;j<data[0].length;j++){
						   if(M.getValue(i, j)==-1){
							   e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK)); 
							   e.gc.fillRectangle(i*width/21+1,j*height/21+1,width/21-1,height/21-1);}
							   else if(M.getValue(i, j)==1){
								   e.gc.drawImage(mouse2,i*width/21 ,j*height/21+1);
							   if(mousex>i*width/23&&mousex<i*width/23+width/5&&mousey>j*height/23&&mousey<j*height/23+height/5)
								   mouseInBoundflag=true;
							   else if(mouseflag)
								   mouseInBoundflag=false;
							   
							  
							   }
							   else if(data[j][i]==2){
								   e.gc.drawImage(cheese2,i*width/21 ,j*height/21+1);
				
					if(mousex!=-1&&mousey!=-1&&mouseflag==true){
						if(mouse2!=null&&mouseInBoundflag){
						e.gc.drawImage(mouse2,mousex-10 ,mousey-10);
						
						}
					}}}
				   if(cheese2!=null&&mouse2!=null){
					   
				   cheese2.dispose();
				   mouse2.dispose();}
					 e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
					e.gc.drawText("The score is: "+Score , 30,22*height/29+10);
					e.gc.drawText("The BEST score is: "+ MaxScore , 150,22*height/29+10);
					//e.gc.dispose();
				
			}});
		
	}
	
	public void SetBoard(final int [][] data) {
		M=new Maze(data);
		

		
	}
		
	

		
	



}
