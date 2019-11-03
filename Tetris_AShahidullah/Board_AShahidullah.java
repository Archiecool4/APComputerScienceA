import java.awt.*;
import javax.swing.JPanel;

/**
 * Contains the fields and methods in order to construct the board of Tetris - along with the necessary graphics.
 * @author Archie Shahidullah <<a href = "mailto:archie.s@outlook.com">archie.s@outlook.com</a>>
 * @version 1.2
 * @since 1.0
 */
public class Board_AShahidullah extends JPanel
{
	
	/**
	 * The number of rows in the board array - determined in the {@code Tetris_AShahidullah()} constructor.
	 */
	public final int NUM_ROWS;
	
	/**
	 * The number of columns in the board array - determined in the {@code Tetris_AShahidullah()} constructor.
	 */
	public final int NUM_COLS;
	
	/**
	 * Size of the tiles (in pixels) to be drawn on the board.
	 */
	public final int SIZE;
	
	/**
	 * The array used as the board for Tetris.
	 */
	private int[][] board;
	
	/**
	 * Integer variable representing the red component in the color of the grid lines on the board.
	 */
	private int r; 
	
	/**
	 * Integer variable representing the green component in the color of the grid lines on the board.
	 */
	private int gr;
	
	/**
	 * Integer variable representing the blue component in the color of the grid lines on the board.
	 */
	private int b;
	
	/**
	 * Identifies whether to increase the RGB values of the grid lines on the board.
	 */
	private boolean increase = true;
	
	/**
	 * Constructs the board by setting initial parameters.
	 * <p>
	 * This constructor takes in the number of rows and columns to be entered on the board, then sets the array {@code board} with those
	 * conditions. It also takes in a value for the {@code SIZE} for the scale of the tiles on the board. Lastly, it sets the background
	 * color of the JPanel to a dark gray and the preferred size to {@code ((NUM_COLS + 9) * SIZE)} by {@code ((NUM_ROWS + 2) * SIZE)}.
	 * </p>
	 * @param r number of rows in the board.
	 * @param c number of columns in the board.
	 * @param s size factor of the tiles on the board.
	 */
	public Board_AShahidullah(int r, int c, int s)
	{
		NUM_ROWS = r;
		NUM_COLS = c;
		SIZE = s;
		board = new int[r][c];
		setBackground(Color.GRAY.darker().darker().darker().darker());
		setPreferredSize(new Dimension((NUM_COLS + 9) * SIZE, (NUM_ROWS + 2) * SIZE));
	}
	
	/**
	 * Returns the value of the board at a specified location.
	 * <p>
	 * The method checks whether the requested coordinates exist, and then returns the value - else it returns 0.
	 * </p>
	 * @param r row the position is on.
	 * @param c column the position is on.
	 * @return  the value of the position.
	 */
	public int getValue(int r, int c)
	{
		if(r >= 0 && r < NUM_ROWS && c >= 0 && c < NUM_COLS)
			return board[r][c];
		return 0;
	}
	
	/**
	 * Sets the board to a value at a specified location.
	 * <p>
	 * The method checks whether the location requested exists, and if so then sets the value at that location to the inputted value.
	 * @param r row the position is on.
	 * @param c column the position is on.
	 * @param v value the position is to be set to.
	 */
	public void setValue(int r, int c, int v)
	{
		if(r >= 0 && r < NUM_ROWS && c >= 0 && c < NUM_COLS)
			board[r][c] = v;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(SIZE, SIZE, NUM_COLS * SIZE, NUM_ROWS * SIZE);
		
		/*
		 * Paints the board according to the values on it.
		 */
		for(int row = 0; row < NUM_ROWS; row++)
			for(int col = 0; col < NUM_COLS; col++)
				if(board[row][col] != 0)
					switch(board[row][col])
					{
					case 1:
						g.setColor(Color.MAGENTA);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 2:
						g.setColor(Color.ORANGE);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 3:
						g.setColor(Color.BLUE);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 4:
						g.setColor(Color.RED);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 5:
						g.setColor(Color.GREEN);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 6:
						g.setColor(Color.CYAN);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					case 7:
						g.setColor(Color.YELLOW);
						g.fillRect(col * SIZE + SIZE, row * SIZE + SIZE, SIZE, SIZE);
						break;
					}
		
		/*
		 * The following snippet incrementally increases and decreases the RGB values of the grid lines of the board.
		 */
		if(increase)
		{
			if(r <= 235)
				r += 20;
			else if(gr <= 235)
				gr += 20;
			else if(b <= 235)
				b += 20;
			if(r >= 235 && gr >= 235 && b >= 235)
				increase = false;
		}
		if(!increase)
		{
			if(r >= 20)
				r -= 20;
			else if(gr >= 20)
				gr -= 20;
			else if(b >= 20)
				b -= 20;
			if(r <= 20 && gr <= 20 && b <= 20)
				increase = true;
		}
		/*
		 * End of snippet.
		 */
		
		/*
		 * This snippet draws the grid lines on the board.
		 */
		g.setColor(new Color(r, gr, b));
		
		g.drawRect(SIZE, SIZE, NUM_COLS * SIZE, NUM_ROWS * SIZE);
		
		for(int i = 0; i < NUM_ROWS * SIZE; i += SIZE)
			g.drawLine(SIZE, i + SIZE, NUM_COLS * SIZE + SIZE, i + SIZE);
		
		for(int j = 0; j < NUM_COLS * SIZE; j += SIZE)
			g.drawLine(j + SIZE, SIZE, j + SIZE, NUM_ROWS * SIZE + SIZE);
		/*
		 * End of snippet.
		 */
		
		/*
		 * The following code draws the score counter, name of the game, status of the game, the next piece, and the speed.
		 */
		Font gameStatus = new Font("Comic Sans", Font.ROMAN_BASELINE, (2 * SIZE)/3);
		Font header = new Font("Comic Sans", Font.BOLD, SIZE/2);
		Font score = new Font("Comic Sans", Font.ROMAN_BASELINE, SIZE);
		
		g.setColor(Color.BLACK);
		g.fillRect((NUM_COLS + 2) * SIZE, SIZE, 6 * SIZE, 3 * SIZE);
		g.setColor(Color.WHITE);
		g.drawRect((NUM_COLS + 2) * SIZE, SIZE, 6 * SIZE, 3 * SIZE);
		g.drawLine((NUM_COLS + 2) * SIZE, 2 * SIZE, (NUM_COLS + 8) * SIZE, 2 * SIZE);
		
		g.setFont(header);
		g.drawString("SCORE", (NUM_COLS + 4) * SIZE, (int) (1.75 * SIZE));
		g.setFont(score);
		g.drawString(String.valueOf(TetrisGame_AShahidullah.score), (int) ((NUM_COLS + 2.5) * SIZE), (int) (3.25 * SIZE));
		
		g.setColor(Color.BLACK);
		g.fillRect((NUM_COLS + 2) * SIZE, 5 * SIZE, 6 * SIZE, 3 * SIZE);
		g.setColor(Color.WHITE);
		g.drawRect((NUM_COLS + 2) * SIZE, 5 * SIZE, 6 * SIZE, 3 * SIZE);
		
		g.setFont(header);
		g.drawString("TETRIS", (NUM_COLS + 4) * SIZE, (int) (6.2 * SIZE));
		g.drawString("BY ARCHIE", (int) ((NUM_COLS + 3.6) * SIZE), (int) (7.2 * SIZE));
		
		g.setColor(Color.BLACK);
		g.fillRect((NUM_COLS + 2) * SIZE, 9 * SIZE, 6 * SIZE, 3 * SIZE);
		g.setColor(Color.WHITE);
		g.drawRect((NUM_COLS + 2) * SIZE, 9 * SIZE, 6 * SIZE, 3 * SIZE);
		g.drawLine((NUM_COLS + 2) * SIZE, 10 * SIZE, (NUM_COLS + 8) * SIZE, 10 * SIZE);
		
		g.setFont(header);
		g.drawString("GAME STATUS", (int) ((NUM_COLS + 3.25) * SIZE), (int) (9.75 * SIZE));
		g.setFont(gameStatus);
		
		if(TetrisGame_AShahidullah.gameOver == true)
			g.drawString("GAME OVER!", (int) ((NUM_COLS + 2.85) * SIZE), (int) (11.25 * SIZE));
		else if(TetrisGame_AShahidullah.pause == true)
			g.drawString("PAUSED", (int) ((NUM_COLS + 3.7) * SIZE), (int) (11.25 * SIZE));
		else
			g.drawString("KEEP GOING", (NUM_COLS + 3) * SIZE, (int) (11.25 * SIZE));
		
		g.setColor(Color.BLACK);
		g.fillRect((NUM_COLS + 2) * SIZE, 13 * SIZE, 6 * SIZE, 5 * SIZE);
		g.setColor(Color.WHITE);
		g.drawRect((NUM_COLS + 2) * SIZE, 13 * SIZE, 6 * SIZE, 5 * SIZE);
		g.drawLine((NUM_COLS + 2) * SIZE, 14 * SIZE, (NUM_COLS + 8) * SIZE, 14 * SIZE);
		
		g.setFont(header);
		g.drawString("NEXT PIECE", (int) ((NUM_COLS + 3.5) * SIZE), (int) (13.75 * SIZE));
		
		switch(TetrisGame_AShahidullah.nextPiece) /* Draws the next piece on the screen. */
		{
		case 0:
			g.setColor(Color.MAGENTA);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 15 * SIZE, SIZE, SIZE);
			break;
		case 1:
			g.setColor(Color.ORANGE);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 16 * SIZE, SIZE, SIZE);
			break;
		case 2:
			g.setColor(Color.BLUE);
			g.fillRect((NUM_COLS + 4) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 15 * SIZE, SIZE, SIZE);
			break;
		case 3:
			g.setColor(Color.RED);
			g.fillRect((NUM_COLS + 3) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 16 * SIZE, SIZE, SIZE);
			break;
		case 4:
			g.setColor(Color.GREEN);
			g.fillRect((NUM_COLS + 3) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 15 * SIZE, SIZE, SIZE);
			break;
		case 5:
			g.setColor(Color.CYAN);
			g.fillRect((NUM_COLS + 3) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 6) * SIZE, 15 * SIZE, SIZE, SIZE);
			break;
		case 6:
			g.setColor(Color.YELLOW);
			g.fillRect((NUM_COLS + 4) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 4) * SIZE, 16 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 15 * SIZE, SIZE, SIZE);
			g.fillRect((NUM_COLS + 5) * SIZE, 16 * SIZE, SIZE, SIZE);
			break;
		}
		
		g.setColor(Color.WHITE);
		
		for(int k = 0; k < 3; k++)
			g.drawLine((NUM_COLS + 2) * SIZE, (15 + k) * SIZE, (NUM_COLS + 8) * SIZE, (15 + k) * SIZE);
		
		for(int l = 0; l < 6; l++)
			g.drawLine((NUM_COLS + 3 + l) * SIZE, 14 * SIZE, (NUM_COLS + 3 + l) * SIZE, 18 * SIZE);
		
		g.setColor(Color.BLACK);
		g.fillRect((NUM_COLS + 2) * SIZE, 19 * SIZE, 6 * SIZE, 2 * SIZE);
		g.setColor(Color.WHITE);
		g.drawRect((NUM_COLS + 2) * SIZE, 19 * SIZE, 6 * SIZE, 2 * SIZE);
		g.drawLine((NUM_COLS + 2) * SIZE, 20 * SIZE, (NUM_COLS + 8) * SIZE, 20 * SIZE);
		
		g.setFont(header);
		g.drawString("SPEED", (int) ((NUM_COLS + 4.2) * SIZE), (int) (19.75 * SIZE));
		g.drawString(String.valueOf(TetrisGame_AShahidullah.speed), (int) ((NUM_COLS + 2.5) * SIZE), (int) (20.75 * SIZE));
		/*
		 * End of code.
		 */
	}
	
}
