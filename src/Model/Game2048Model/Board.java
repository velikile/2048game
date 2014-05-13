package Model.Game2048Model;

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

public class Board extends Canvas {
int [][] boardData;
protected int Score=0;
protected int MaxScore=0;
 int height;
 int width;
public Board(final Composite parent,int style){
	super(parent, style);
	parent.setSize(660,600);
	parent.setLocation(100,100);
addPaintListener(new PaintListener(){

	@Override
	public void paintControl(PaintEvent e) {
		height=parent.getBounds().height;
		width=parent.getBounds().width;
		
		 Color[] colors=new Color[10];
				colors[0]=getDisplay().getSystemColor(SWT.COLOR_GRAY);
				colors[1]=getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
				colors[2]=getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW);
				colors[3]=getDisplay().getSystemColor(SWT.COLOR_RED);
				colors[4]=getDisplay().getSystemColor(SWT.COLOR_CYAN);
				colors[5]=getDisplay().getSystemColor(SWT.COLOR_GREEN);
				colors[6]=new Color(getDisplay(), 255,165,0);
				colors[7]=getDisplay().getSystemColor(SWT.COLOR_DARK_CYAN);
				colors[8]=getDisplay().getSystemColor(SWT.COLOR_DARK_RED);
				colors[9]=getDisplay().getSystemColor(SWT.COLOR_MAGENTA);
				
				Font font = new Font(getDisplay(),"ariel",25,SWT.BOLD );  
		if(boardData!=null){
			int row=boardData.length;
			int col=boardData[0].length;
			for (int i=0;i<row;i++)
				for (int j=0;j<col;j++){
					
					
					switch (boardData[j][i]){
					case 2:{
						e.gc.setForeground(new Color(getDisplay(), 0,0,0));
						e.gc.setBackground(colors[0]);
						break;
					}
						
					case 4:{
						e.gc.setForeground(new Color(getDisplay(), 0,0,0));
						e.gc.setBackground(colors[1]);
						break;
					}
					
					case 8:{
						e.gc.setForeground(new Color(getDisplay(), 0,0,0));
						e.gc.setBackground(colors[2]);
						break;
					}
					case 16:{
						e.gc.setBackground(colors[3]);
						break;
					}
						
					case 32:{
						e.gc.setForeground(new Color(getDisplay(), 125,125,125));
						e.gc.setBackground(colors[4]);
						break;
					}
					
					case 64:{
						e.gc.setForeground(new Color(getDisplay(), 80,80,80));
						e.gc.setBackground(colors[5]);
						break;
					}
					case 128:{
						e.gc.setBackground(colors[6]);
						break;
					}
						
					case 256:{
						e.gc.setBackground(colors[7]);
						break;
					}
					
					case 512:{
						e.gc.setBackground(colors[8]);
						break;
					}
					case 1024:{
						e.gc.setBackground(colors[9]);
						break;
					}
						
					case 2048:{
						e.gc.setBackground(new Color(getDisplay(),0,0,0));
						e.gc.setForeground(new Color(getDisplay(),0,0,0));
						break;
					}
					
					default:{
						e.gc.setBackground(new Color(getDisplay(), 255,255,255));
						break;
					}
					}
					e.gc.fillRoundRectangle(i*width/6,j*height/6,width/6-15,height/6-15, 20,20 );
					e.gc.setFont(font);
					if(boardData[j][i]!=0){
						if(boardData[j][i]!=1024&&boardData[j][i]!=512&&boardData[j][i]!=2&&boardData[j][i]!=32&&boardData[j][i]!=64)
					e.gc.setForeground(new Color(getDisplay(), 255,255,255));
						if(boardData[j][i]<10)
							e.gc.drawString(""+boardData[j][i], i*width/6+width/17, j*height/6+height/22);
						else if(boardData[j][i]<1000&&boardData[j][i]>100)
							e.gc.drawString(""+boardData[j][i], i*width/6+width/25, j*height/6+height/22);
						else if(boardData[j][i]>1000)
					e.gc.drawString(""+boardData[j][i], i*width/6+width/50, j*height/6+height/22);
						else
						e.gc.drawString(""+boardData[j][i], i*width/6+width/20, j*height/6+height/22);
					}
					
					e.gc.setForeground(new Color(getDisplay(), 0,0,0));
					e.gc.setBackground(new Color(getDisplay(), 255,255,255));
					
					e.gc.drawString("The Score is: "+Score,width/4, height-190);
					e.gc.drawString("Heighest Score is: "+MaxScore, width/4,height-150);
					//e.gc.setForeground(new Color(getDisplay(), 255,255,255));
					
					
				}
			e.gc.dispose();
				}
			}
			
		
		
	});


}
public void SetBoard(int[][] data) {
	int row=data.length;
	int col=data[0].length;
	boardData=new int [row][col];
	for(int i=0;i<row;i++)
		for (int j=0;j<col;j++)
			boardData[i][j]=data[i][j];		
	
}
public void SetScore(int Score){
	this.Score=Score;
	
	
}
public void SetMaxScore(int MaxScore){
	this.MaxScore=MaxScore;
	
	
}
public int GetScore(){
	return this.Score;
	
	
}
public int  GetMaxScore(){
	return this.MaxScore;
	
	
}

	
	
}

