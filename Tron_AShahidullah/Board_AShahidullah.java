import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;

public class Board_AShahidullah extends JPanel
{
	
	public final int NUM_ROW;
	public final int NUM_COL;
	private int SIZE;
	private int[][] mtx; 
	
	public Board_AShahidullah(int r, int c)
	{
		NUM_ROW = r;
		NUM_COL = c;
		SIZE = 10;
		mtx = new int[r][c];
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(NUM_ROW * SIZE, NUM_COL * SIZE));
	}
	
	public int getValue(int r, int c)
	{
		return mtx[r][c];
	}
	
	public void setValue(int r, int c, int v)
	{
		mtx[r][c] = v;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, NUM_ROW * SIZE, NUM_COL * SIZE);
		
		for(int i = SIZE; i < NUM_ROW * SIZE; i += SIZE)
			g.drawLine(i, 0, i, NUM_ROW * SIZE);
		
		for(int j = SIZE; j < NUM_COL * SIZE; j += SIZE)
			g.drawLine(0, j, NUM_COL * SIZE, j);
		
		int r = 0;
		int c = 0;
		
		for(int row = 0; row < NUM_ROW; row++)
			for(int col = 0; col < NUM_COL; col++)
				if(mtx[row][col] != 0)
				{
					r = row;
					c = col;
					
					switch(mtx[r][c])
					{
					case 1:
						g.setColor(Color.GREEN);
						g.fillRect(r * SIZE, c * SIZE, SIZE, SIZE);
						break;
					case 2:
						g.setColor(Color.RED);
						g.fillRect(r * SIZE, c * SIZE, SIZE, SIZE);
						break;
					case 3:
						g.setColor(Color.RED);
						g.fillRect(r * SIZE, c * SIZE, SIZE, SIZE);
						break;
					case 4:
						g.setColor(Color.GREEN);
						g.fillRect(r * SIZE, c * SIZE, SIZE, SIZE);
						break;
					}
				}
		
		Font font = new Font("Comic Sans", Font.ROMAN_BASELINE, 50);
		
		if(TronGame_AShahidullah.gameOver == true)
		{
			g.setFont(font);
			g.setColor(Color.CYAN.brighter());
			g.drawString("Tie!", 200, 50);
		}
		
		if(TronGame_AShahidullah.gameOver1 == true)
		{
			g.setFont(font);
			g.setColor(Color.CYAN.brighter());
			g.drawString("Player 1 Loses!", 80, 50);
		}
			
		if(TronGame_AShahidullah.gameOver2 == true)
		{
			g.setFont(font);
			g.setColor(Color.CYAN.brighter());
			g.drawString("Player 2 Loses!", 80, 50);
		}
	}
	
}
