/**
 * Contains the fields and methods to create a Tetronimo.
 * @author Archie Shahidullah <<a href = "mailto:archie.s@outlook.com">archie.s@outlook.com</a>>
 * @version 1.2
 * @since 1.0
 */

public class Tetris_AShahidullah 
{

	/**
	 * The board of the game.
	 */
	public static Board_AShahidullah myBoard;
	
	/**
	 * Contains the coordinates to make each tetronimo.
	 */
	private final int[][] pieces = { {0, 0, 1, 0, 0, 1, 0,-1},   // T Piece: 1 - Magenta
									 {0, 0, 0, 1, 0,-1, 1, 1},   // L Piece: 2 - Orange
									 {0, 0, 0, 1, 0,-1,-1, 1},   // J Piece: 3 - Blue
									 {0, 0, 0,-1, 1, 0, 1, 1},   // Z Piece: 4 - Red
									 {0, 0, 0, 1, 1, 0, 1,-1},   // S Piece: 5 - Green
									 {0, 0, 0,-1, 0, 1, 0, 2},   // I Piece: 6 - Cyan
									 {0, 0, 1, 0, 0, 1, 1, 1} }; // O Piece: 7 - Yellow
	
	/**
	 * Identifies whether a piece can move without colliding or not (only in use for {@code fallToBottom()} method).
	 */
	private boolean canMove;
	
	/**
	 * Contains the coordinates of the current piece.
	 */
	public int[] piece = new int[8];
	
	/**
	 * Contains the new coordinates of a point that has been rotated.
	 */
	public int[] rotatedPoint = new int[2];
	
	/**
	 * Integer specifying the color a tile will be painted.
	 * @see {@code Board_AShahidullah.paintComponent(Graphics g)}
	 */
	public int color;
	
	/**
	 * Integer denoting the row the center of the tetronimo is in.
	 */
	public int posRow;
	
	/**
	 * Integer denoting the column the center of the tetronimo is in.
	 */
	public int posCol;
	
	/**
	 * Integer denoting the piece to be selected.
	 */
	private int selection;
	
	/**
	 * Constructs the tetronimo in accordance to what is selected.
	 * <p>
	 * This constructor takes in a row and column position, along with a selection number plus the board itself to create a tetronimo.
	 * Using the selection number, it draws the correct set of coordinates from {@code pieces} and puts them in the one-dimensional array
	 * {@code piece}. It then assigns a position for the center of the piece, initializes the board, then sets the value of the piece on the
	 * board.
	 * </p>
	 * @param r row the center of the piece is placed at.
	 * @param c column the center of the piece is placed at.
	 * @param s selection number of the piece.
	 * @param b the board of the game in; must be {@code Board_AShahidullah}.
	 */
	public Tetris_AShahidullah(int r, int c, int s, Board_AShahidullah b)
	{
		selection = s;
		color = selection + 1;
		
		/* Loads the coordinates of the correct piece into the concurrent piece array. */
		for(int i = 0; i < piece.length; i++)
			piece[i] = pieces[selection][i];
		
		posRow = r;
		posCol = c;
		myBoard = b;
		
		/* Sets the initial position of the piece. */
		for(int j = 0; j < piece.length; j += 2)
			myBoard.setValue(piece[j] + posRow, piece[j + 1] + posCol, color);
	}
	
	/**
	 * Checks whether the tetronimo will collide based on the next move.
	 * <p>
	 * This method takes in the direction that the tetronimo is intended to move in. It then creates temporary variables to simulate
	 * this movement without actually moving the piece. If the new position of the piece is not occupied by another piece or is not the bottom
	 * of the board, the piece may move; otherwise it cannot.
	 * </p>
	 * @param  direction the direction in which the piece intends to move in
	 * @return true if there is an obstacle in the way; otherwise false
	 */
	public boolean checkCollision(int direction)
	{
		int r = posRow;
		int c = posCol;
		
		switch(direction)
		{
		case 0: // Down
			r++;
			break;
		case 1: // Right
			c++;
			break;
		case 2: // Left
			c--;
			break;
		}
		
		for(int i = 0; i < piece.length; i += 2)
			if(myBoard.getValue(piece[i] + r, piece[i + 1] + c) > 0 || (piece[i] + r) > myBoard.NUM_ROWS - 1 || piece[i + 1] + c < 0 || piece[i + 1] + c > myBoard.NUM_COLS - 1)
			{
				/* Sets the position of the piece to the board as it as collided. */
				for(int j = 0; j < piece.length ; j += 2)
					myBoard.setValue(piece[j] + posRow, piece[j + 1] + posCol, color);
				return true;
			}
		
		return false;
	}
	
	/**
	 * Moves the piece in the specified direction while checking for collisions and game over.
	 * <p>
	 * This method first erases the piece from the board to check for collisions. Then it calls {@code checkCollision(int direction} in order to
	 * see if there are any obstacles and if there are none, it moves the tetronimo. It resets the piece on the board. 
	 * </p>
	 * <p>
	 * Should the piece not be able to move down, the method checks whether the game is over by calling {@code isGameOver()}. If the game is not over
	 * it calls {@code TetrisGame_AShahidullah.rowDeletion()} and adds a piece via {@code TetrisGame_AShahidullah.addpiece()}.
	 * @param direction the direction the tetronimo will move in
	 */
	public void move(int direction)
	{			
		/* Erases piece from board. */
		for(int i = 0; i < piece.length; i += 2)
			myBoard.setValue(piece[i] + posRow, piece[i + 1] + posCol, 0);
		
		/*
		 * The following code checks if there are any collisions and moves the piece accordingly. 
		 */
		if(!checkCollision(0))
		{
			switch(direction)
			{
			case 0: // Down
				posRow++;
				canMove = true;
				break;
			case 1: // Right
				if(!checkCollision(1))
					posCol++;
				break;
			case 2: // Left
				if(!checkCollision(2))
					posCol--;
				break;
			}
			
			for(int j = 0; j < piece.length ; j += 2)
				myBoard.setValue(piece[j] + posRow, piece[j + 1] + posCol, color);
		}
		/*
		 * End of code.
		 */
		
		else
		{
			canMove = false;
			
			/* 
			 * The following snippet checks whether the game is over.
			 */
			if(isGameOver())
			{
				for(int l = 0; l < piece.length; l += 2)
					myBoard.setValue(piece[l] + posRow, piece[l + 1] + posCol, color);
//				TetrisGame_AShahidullah.background.stop();
//				TetrisGame_AShahidullah.gameOverMusic = new Sound_AShahidullah("music.wav");
				new Sound_AShahidullah("gameover.wav");
				TetrisGame_AShahidullah.gameOver = true;
			}
			/*
			 * End of snippet.
			 */
			
			/*
			 * The following snippet calls the row deletion and add piece methods since the game has not been terminated.
			 */
			else
			{
				for(int m = 0; m < piece.length; m += 2)
					myBoard.setValue(piece[m] + posRow, piece[m + 1] + posCol, color);
				TetrisGame_AShahidullah.rowDeletion();
				TetrisGame_AShahidullah.addPiece();
			}
			/*
			 * End of snippet.
			 */
		}
	}
	
	/**
	 * Moves the piece down until it cannot move anymore.
	 */
	public void fallToBottom()
	{
		while(canMove == true)
			move(0);
	}
	
	/**
	 * Rotates a point 90 degrees clockwise.
	 * @param r the row the point is in
	 * @param c the column the point is in
	 * @return  the coordinates of the rotates point, represented in the array {@code rotatedPoint}.
	 */
	public int[] rotate(int r, int c)
	{
		rotatedPoint = new int[2];
		
		rotatedPoint[0] = c;
		rotatedPoint[1] = -r;
		
		return rotatedPoint;
	}
	
	/**
	 * Checks whether the game is over by seeing if the piece is at the top of the board.
	 * @return true if the piece is at the top of the board; otherwise false
	 */
	public boolean isGameOver()
	{
		for(int i = 0; i < piece.length; i += 2)
			if(piece[i] + posRow <= 1)
			{
				TetrisGame_AShahidullah.gameOver = true;
				return true;
			}
		
		return false;
	}
	
}
