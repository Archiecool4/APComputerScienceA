import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.io.*;
import java.net.URISyntaxException;

import javax.imageio.*;

/**
 * Contains the fields and methods to create the JFrame Tetris runs in, the key listener, and the methods to run the game itself.
 * @author Archie Shahidullah <<a href = "mailto:archie.s@outlook.com">archie.s@outlook.com</a>>
 * @version 1.2
 * @since 1.0
 */

public class TetrisGame_AShahidullah extends JFrame
{
	
	/**
	 * The grid of the game in accordance to the {@code Board_AShahidullah} class.
	 */
	public static Board_AShahidullah myBoard;
	
	/**
	 * The current tetronimo (tetris piece).
	 */
	public static Tetris_AShahidullah tetris;
	
	/**
	 * The background music of the game.
	 */
	public static Sound_AShahidullah background;
	
	/**
	 * The music that plays when game over.
	 */
//	public static Sound_AShahidullah gameOverMusic;
	
	/**
	 * The current score of the player.
	 */
	public static int score;
	
	/**
	 * The identification number of the next tetronimo.
	 */
	public static int nextPiece;
	
	/**
	 * The speed the tetronimo moves at. Adjustable from 100 to 500 milliseconds.
	 */
	public static int speed = 300;
//	public static int time = 83000;
	
	/**
	 * Identifies when the player has lost the game.
	 */
	public static boolean gameOver;
	
	/**
	 * Identifies if the game is paused or not.
	 */
	public static boolean pause;
	
	/**
	 * Identifies whether SFX are toggled.
	 */
//	private static boolean sounds = true;
	
	/**
	 * Identifies whether a piece can rotate without collision.
	 */
	private static boolean canRotate;
	
	/**
	 * Identifies whether the player wishes to restart the game - only after game over.
	 */
	private static boolean restart;
	
	/**
	 * Creates the Tetris window and pieces.
	 * <p>
	 * This constructor creates the JFrame used for the game. It then creates the grid in accordance to the {@code Board_AShahidullah} class.
	 * It then creates a tetronimo in accordance to the {@code Tetris_AShahidullah} class before selecting the identification 
	 * number of the next piece.
	 * </p>
	 * <p>
	 * The constructor then sets the default close operation to exit and bars resizing; it adds the JPanel {@code myBoard} to the JFrame, 
	 * centers it in the screen, and sets it to visible. Lastly, it calls the {@code keyListenter()} and {@code runGame()} method.
	 * </p>
	 */
	
	public TetrisGame_AShahidullah()
	{
		super("Tetris");
		
		/*
		 * The following code creates the board, the initial piece, and the identification number of the next piece.
		 */
		myBoard = new Board_AShahidullah(20, 10, 30);
		tetris = new Tetris_AShahidullah(0, myBoard.NUM_COLS/2, (int) (Math.random() * 7), myBoard); 
		nextPiece = (int) (Math.random() * 7); /* Generates random integer between 0 and 6. */
		/*
		 * End of code.
		 */
		
//		try {
//		BufferedImage img = ImageIO.read(new File(getClass().getResource("history.jpg").toURI()));
		
		/*
		 * The following code sets the parameters of the JFrame.
		 */
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
//		setContentPane(new JLabel(new ImageIcon(img)));
		add(myBoard, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
//		} catch(IOException ex) {
//			ex.printStackTrace();
//		} catch(URISyntaxException ex) {
//			ex.printStackTrace();
//		}
		/*
		 * End of code.
		 */
		
		/* Initializes the key listener and the game. */
		keyListener();
		runGame();
	}
	
	/**
	 * Changes the speed the tetronimo moves at, between a range of 100 to 500 milliseconds.
	 * @param s the speed the tetronimo moves at; 0 decreases the speed while 1 increases it.
	 */
	public static void changeSpeed(int s)
	{
		switch(s)
		{
		case 0:
			if(speed >= 125)
				speed -= 25;
			break;
		case 1:
			if(speed <= 475)
				speed += 25;
			break;
		default:
			break;
		}
	}
	
	/**
	 * Adds a new tetronimo to the board after the last one has collided as well as selects the next piece.
	 */
	public static void addPiece()
	{
		tetris = new Tetris_AShahidullah(0, myBoard.NUM_COLS/2, nextPiece, myBoard);
		/* To prevent collisions from successive spawning. */
		for(int i = 0; i < tetris.piece.length; i += 2)
			myBoard.setValue(tetris.piece[i] + tetris.posRow, tetris.piece[i + 1] + tetris.posCol, 0);
		
		nextPiece = (int) (Math.random() * 7);
	}
	
	/**
	 * Deletes a row from the board once it is filled.
	 * <p>
	 * The method creates two temporary variables to record how many spaces are filled and which row to delete. It then loops through the whole board
	 * and if the number of filled spaces equals the number of columns on the board, it deletes that row and shifts all rows above it down. This is
	 * reflected when {@code myBoard.repaint()} is called. If a row is deleted it increments {@code score} by 100 and plays a sound clip.
	 * </p>
	 */
	public static void rowDeletion()
	{
		int filledSpaces = 0;
		int rowToDelete;
		
		/* Loops through the board. */
		for(int row = 0; row < myBoard.NUM_ROWS; row++)
		{
			rowToDelete = row;
			if(myBoard.getValue(row, 0) != 0)
				for(int i = 0; i < myBoard.NUM_COLS; i++)
				{
					if(myBoard.getValue(row, i) != 0)
					{
						filledSpaces++;
						
						if(filledSpaces == myBoard.NUM_COLS)
						{
//							if(sounds)
								new Sound_AShahidullah("rowDelete.wav");
							
							for(int j = rowToDelete; j >= 1; j--)
								for(int k = 0; k < myBoard.NUM_COLS; k++)
									myBoard.setValue(j, k, myBoard.getValue(j - 1, k));
							for(int l = 0; l < myBoard.NUM_COLS; l++)
								myBoard.setValue(0, l, 0);
						}
						
					}
					else 
					{
						filledSpaces = 0;
						break;
					}
				}
			
			/* Increments the score. */
			if(filledSpaces == myBoard.NUM_COLS)
				score += 100;
		}
	}
	
	/**
	 * Allows the user to enter keyboard input.
	 * <p>
	 * The key listener allows inputs of WASD and arrow keys (to the same effect) - where W/Up rotates. Other than the W/Up keys, the keys call
	 * {@code tetris.move(int d)} where {@code d} is some integer signifying direction. When W/Up is selected, the rotation is checked to make 
	 * sure a collision is not imminent - and if not calls {@code tetris.rotate(int r, int c)} on each point of the tetronimo - where {@code r} 
	 * and {@code c} are the coordinates of said point. The space key calls {@code tetris.fallToBottom()} and places the tetronimo at the bottom
	 * of the board.
	 * </p>
	 * <p>
	 * The Z and X keys control slowing and increasing the speed (by a factor of 25 milliseconds) respectively via the {@code changeSpeed()} method.
	 * P pauses the game ({@code pause ^= true}), R restarts the game once it has ended ({@code restart = true}), M toggles music 
	 * ({@code background.toggle()}), and F toggles SFX ({@code sounds ^= true}). Lastly, the escape key terminates the application 
	 * ({@code System.exit(0)}).
	 * </p>
	 */
	public void keyListener()
	{
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP: /* Checks if the piece can rotate. */
					if(!gameOver)
					{
						for(int i = 0; i < tetris.piece.length; i += 2)
							myBoard.setValue(tetris.piece[i] + tetris.posRow, tetris.piece[i + 1] + tetris.posCol, 0);

						for(int j = 0; j < tetris.piece.length; j += 2)
						{
							tetris.rotate(tetris.piece[j], tetris.piece[j + 1]);
							canRotate = false;
							if(tetris.rotatedPoint[0] + tetris.posRow >= 0 && tetris.rotatedPoint[0] + tetris.posRow < myBoard.NUM_ROWS 
							&& tetris.rotatedPoint[1] + tetris.posCol >= 0 && tetris.rotatedPoint[1] + tetris.posCol < myBoard.NUM_COLS
							&& myBoard.getValue(tetris.rotatedPoint[0] + tetris.posRow, tetris.rotatedPoint[1] + tetris.posCol) == 0)
							{
								canRotate = true;
							}

							if(!canRotate)
								break;
						}

						if(canRotate)
							for(int k = 0; k < tetris.piece.length; k += 2)
							{
								tetris.rotate(tetris.piece[k], tetris.piece[k + 1]);
								tetris.piece[k] = tetris.rotatedPoint[0];
								tetris.piece[k + 1] = tetris.rotatedPoint[1];
							}

						for(int l = 0; l < tetris.piece.length; l += 2)
							myBoard.setValue(tetris.piece[l] + tetris.posRow, tetris.piece[l + 1] + tetris.posCol, tetris.color);
					}
					else
						new Sound_AShahidullah("gameover.wav");
					break;
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if(!gameOver)
					{
						tetris.move(1);
						myBoard.repaint();
					}
					else
						new Sound_AShahidullah("gameover.wav");
					break;
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if(!gameOver)
					{
						tetris.move(0);
						myBoard.repaint();
					}
					else
						new Sound_AShahidullah("gameover.wav");
					break;
				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if(!gameOver)
					{
						tetris.move(2);
						myBoard.repaint();
					}
					else
						new Sound_AShahidullah("gameover.wav");
					break;
				case KeyEvent.VK_SPACE:
					tetris.fallToBottom();
					break;
				case KeyEvent.VK_Z:
					changeSpeed(0);
					break;
				case KeyEvent.VK_X:
					changeSpeed(1);
					break;
				case KeyEvent.VK_P:
					pause ^= true;
					if(pause)
						new Sound_AShahidullah("gameover.wav");
					else
						new Sound_AShahidullah("rowDelete.wav");
					break;
				case KeyEvent.VK_R:
					if(gameOver)
					{
//						background.clip.setMicrosecondPosition(0);
//						background.stop();
//						gameOverMusic.stop();
						restart = true;
						dispose();
					}
					else
						new Sound_AShahidullah("gameover.wav");
					break;
//				case KeyEvent.VK_M:
//					background.toggle();
//					gameOverMusic.toggle();
//					break;
//				case KeyEvent.VK_F:
//					sounds ^= true;
//					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
				}
			}
		});
	}
	
	/**
	 * Controls the progression of the game.
	 * <p>
	 * The game runs while {@code gameOver} is false. It starts the background music, then periodically calls {@code myBoard.repaint()} to refresh
	 * the screen. It calls {@code tetris.move(0)} to move the tetronimo down and sleeps each iteration for the amount specified in {@code speed}.
	 * </p>
	 */
	public void runGame()
	{	
		while(!gameOver)
		{				
//			background.start();
			myBoard.repaint();
			tetris.move(0);
//			background.checkSound();
			
			while(pause == true)
			{
				myBoard.repaint();
//				background.checkSound();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
//				time += 100;
			} 
			
			try {
				Thread.sleep(speed);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
//			time += speed;
		
			myBoard.repaint();
		}
	}
	
	/**
	 * Initiates the entire game in a do-while loop to allow for restarting.
	 * @param args default Java input
	 */
	public static void main(String[] args)
	{
		/* Displays a pop-up explaining the direction of the game. */
		JOptionPane.showMessageDialog(null,"Directions for Tetris:\n\nWASD or Arrow Keys - move the piece (W/UP rotates)\nSPACE - piece falls to bottom"
				+ "\nZ and X - slow and speed up respectively\nP - pause\nR - restart (only when game over)"
				+ "\nESC - exit","Tetris Directions",JOptionPane.PLAIN_MESSAGE);
		
		background = new Sound_AShahidullah("Tetris.wav");
		
		/* Ensures the game runs at least once. */
		do
		{
			restart = false;
			gameOver = false;
			
			/* Resets initial conditions. */
			pause = false; canRotate = false; score = 0; speed = 300;
			
			new TetrisGame_AShahidullah();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			while(!restart)
			{
				myBoard.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			} 
		} while(restart);
		
		myBoard.repaint();
	}
	
}
