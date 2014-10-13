package View;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * @author Lev veliki
 * This is a class for the gui of the 2048 game 
 * uses SWT for the graphics component
 * 
 *
 */

public class Board extends Canvas {
public int [][] boardData;
protected int Score=0;
protected int MaxScore=0;
 int height;
 int width;
 
 /**
  * 
  * @param parent
  * @param style
  * start the whole thing 
  * 
  */
public Board(final Composite parent,int style){
	super(parent, style);
	final Font F=new Font(getDisplay(), "Arial",30,SWT.BOLD);
	parent.setSize(660,600);
	parent.setLocation(100,100);
addPaintListener(new PaintListener(){
	Image Tile2=new Image(getDisplay(),"src/2048Art/2.gif");
	Image Tile2new;
	Image Tile4=new Image(getDisplay(),"src/2048Art/4.gif");
	Image Tile4new;
	Image Tile8=new Image(getDisplay(),"src/2048Art/8.gif");
	Image Tile8new;
	Image Tile16=new Image(getDisplay(),"src/2048Art/16.gif");
	Image Tile16new;
	Image Tile32=new Image(getDisplay(),"src/2048Art/32.gif");
	Image Tile32new;
	Image Tile64=new Image(getDisplay(),"src/2048Art/64.gif");
	Image Tile64new;
	Image Tile128=new Image(getDisplay(),"src/2048Art/128.gif");
	Image Tile128new;
	Image Tile256=new Image(getDisplay(),"src/2048Art/256.gif");
	Image Tile256new;
	Image Tile512=new Image(getDisplay(),"src/2048Art/512.gif");
	Image Tile512new;
	Image Tile1024=new Image(getDisplay(),"src/2048Art/1024.gif");
	Image Tile1024new;
	Image Tile2048=new Image(getDisplay(),"src/2048Art/2048.gif");
	Image Tile2048new;
	Image Tile4096=new Image(getDisplay(),"src/2048Art/4096.gif");
	Image Tile4096new;
	Image Tile8192=new Image(getDisplay(),"src/2048Art/8192.gif");
	Image Tile8192new;
	Image Tile16384=new Image(getDisplay(),"src/2048Art/16384.gif");
	Image Tile16384new;
	@Override
	public void paintControl(PaintEvent e) {
		height=parent.getBounds().height;
		width=parent.getBounds().width;
		if(boardData!=null){
			int row=boardData.length;
			int col=boardData[0].length;
			for (int i=0;i<row;i++)
				for (int j=0;j<col;j++){
					e.gc.setBackground((getDisplay().getSystemColor(SWT.COLOR_BLUE)));
				//	e.gc.fillRectangle(i*width/(row+row),j*height/(col+col/2),width/(row+row)+2 , height/(col+col/2)+2);
					e.gc.fillRectangle(i*width/(row+row/2),j*height/(col+col/2), width/(row+row/2)-row*row , height/(col+col/2) -(col*col));
					if(row*row<width/(row+row/2)&&col*col<height/(col+col/2))
					switch (boardData[j][i]){
					case 2:{
						Tile2new=new Image(getDisplay(),Tile2.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile2new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
						
					case 4:{
						Tile4new=new Image(getDisplay(),Tile4.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile4new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					
					case 8:{
						Tile8new=new Image(getDisplay(),Tile8.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile8new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					case 16:{
						Tile16new=new Image(getDisplay(),Tile16.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile16new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
						
					case 32:{
						Tile32new=new Image(getDisplay(),Tile32.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));	
						e.gc.drawImage(Tile32new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					
					case 64:{
						Tile64new=new Image(getDisplay(),Tile64.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile64new,i*width/(row+row/2),j*height/(col+col/2));
						break;
						
					}
					case 128:{
						Tile128new=new Image(getDisplay(),Tile128.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile128new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
						
					case 256:{
						Tile256new=new Image(getDisplay(),Tile256.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile256new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					
					case 512:{
						Tile512new=new Image(getDisplay(),Tile512.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));

						e.gc.drawImage(Tile512new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					case 1024:{
						Tile1024new=new Image(getDisplay(),Tile1024.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile1024new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
						
					case 2048:{
						Tile2048new=new Image(getDisplay(),Tile2048.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile2048new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					case 4096:{
						Tile4096new=new Image(getDisplay(),Tile4096.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile4096new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					case 8192:{
						Tile8192new=new Image(getDisplay(),Tile8192.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile8192new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					case 16384:{
						Tile16384new=new Image(getDisplay(),Tile16384.getImageData().scaledTo(width/(row+row/2)-row*row,height/(col+col/2)-col*col));
						e.gc.drawImage(Tile16384new,i*width/(row+row/2),j*height/(col+col/2));
						break;
					}
					
					default:{
						
						break;
					}
					}
					
					
					e.gc.setForeground(new Color(getDisplay(), 50,50,50));
					e.gc.setBackground((getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND)));
					e.gc.setFont(F);
					e.gc.drawString("The Score is: "+Score,width/15, height-height/3);
					//e.gc.drawString("Heighest Score is: "+MaxScore, width/4,height-150);
					//e.gc.setForeground(new Color(getDisplay(), 255,255,255));
					
					
				}
			
			e.gc.dispose();
			if(Tile2new!=null)
			 Tile2new.dispose();
			if(Tile4new!=null)
			 Tile4new.dispose();
			if(Tile8new!=null)
			 Tile8new.dispose();
			if(Tile16new!=null)
			 Tile16new.dispose();
			if(Tile32new!=null)
			  Tile32new.dispose();
			if(Tile64new!=null)
			 Tile64new.dispose();
			if(Tile128new!=null)
			 Tile128new.dispose();
			if(Tile256new!=null)
			 Tile256new.dispose();
			if(Tile512new!=null)
			 Tile512new.dispose();
			if(Tile1024new!=null)
			 Tile1024new.dispose();
			if(Tile2048new!=null)
			 Tile2048new.dispose();
			if(Tile4096new!=null)
			 Tile4096new.dispose();
			if(Tile8192new!=null)
			 Tile8192new.dispose();
			if(Tile16384new!=null)
			Tile16384new.dispose();
				}
			}
			
		
		
	});


}
/**
 *
 * @param data
 * setting the game board data to the data passed
 */
public void SetBoard(int[][] data) {
	int row=data.length;
	int col=data[0].length;
	boardData=new int [row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++)
			boardData[i][j]=data[i][j];		
	
}
/**
 * 
 * @param Score
 * setting the score manually 
 */
public void SetScore(int Score){
	this.Score=Score;
	
	
}
/**
 * 
 * @param MaxScore
 * setting the best score so far manually
 */
public void SetMaxScore(int MaxScore){
	this.MaxScore=MaxScore;

}
/**
 * 
 * @return Score
 * returns the Score;
 */
public int GetScore(){
	return this.Score;
}
	/**
	 *@return MaxScore
	 * returns the best score so far
	 */
public int  GetMaxScore(){
	return this.MaxScore;
	
	
}

	
	
}

