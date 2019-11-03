import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import java.net.URL;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Board extends JPanel
{
	public final int NUM_ROWS;
	public final int NUM_COLS;
	private final int SIZE;
	private int[][] board;
	private int pacCounter = 0;
	private int powerCounter = 0;
	private int ghostCounter = 0;
	private int deathCounter = 0;
	private boolean blinkyAnimation;
	private boolean pinkyAnimation;
	private boolean inkyAnimation;
	private boolean clydeAnimation;
	private boolean ghostAnimation;
	private boolean ghostTime;
	public boolean displayIntro = true;
	private BufferedImage spriteSheet;
	private BufferedImage gameBoard;
	private BufferedImage pacdotSprite;
	private BufferedImage eyeSprite;
	private URL resource;
	private final int WIDTH = 24;
	private final int HEIGHT = 24;
	private BufferedImage[] pacSprites = new BufferedImage[21];
	private BufferedImage[] blinkySprites = new BufferedImage[8];
	private BufferedImage[] pinkySprites = new BufferedImage[8];
	private BufferedImage[] inkySprites = new BufferedImage[8];
	private BufferedImage[] clydeSprites = new BufferedImage[8];
	private BufferedImage[] pacPowerSprites = new BufferedImage[2];
	private BufferedImage[] numberSprites = new BufferedImage[10];
	private BufferedImage[] ghostSprites = new BufferedImage[4];
	private Font game;
	
	public Board(int r, int c, int s)
	{
		NUM_ROWS = r;
		NUM_COLS = c;
		SIZE = s;
		setBoard(new int[r][c]);
		
		game = new Font("Comic Sans", Font.BOLD, SIZE);
		
		resource = getClass().getResource("Spritesheet.png");
		
		try {
			spriteSheet = ImageIO.read(resource);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		resource = getClass().getResource("gameBoard.jpg");
		
		try {
			gameBoard = ImageIO.read(resource);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		for(int i = 0; i < 8; i++)
			pacSprites[i] = spriteSheet.getSubimage(i * WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);
		
		pacSprites[8] = spriteSheet.getSubimage(0, 7 * HEIGHT, WIDTH, HEIGHT);
		
		for(int j = 4; j < 16; j++)
			pacSprites[5 + j] = spriteSheet.getSubimage(j * WIDTH, 7 * HEIGHT, WIDTH, HEIGHT);
		
		for(int l = 0; l < 8; l++)
			blinkySprites[l] = spriteSheet.getSubimage(l * WIDTH, 6 * HEIGHT, WIDTH, HEIGHT);
		
		for(int m = 0; m < 8; m++)
			pinkySprites[m] = spriteSheet.getSubimage(m * WIDTH, 8 * WIDTH, WIDTH, HEIGHT);

		for(int n = 8; n < 16; n++)
			inkySprites[n - 8] = spriteSheet.getSubimage(n * WIDTH, 8 * WIDTH, WIDTH, HEIGHT);
		
		for(int o = 0; o < 8; o++)
			clydeSprites[o] = spriteSheet.getSubimage(o * WIDTH, 9 * WIDTH, WIDTH, HEIGHT);
		
		for(int p = 0; p < 10; p++)
			numberSprites[p] = spriteSheet.getSubimage(p * WIDTH/2, 0, WIDTH/2, HEIGHT/2);
		
		for(int q = 6; q < 10; q++)
			ghostSprites[q - 6] = spriteSheet.getSubimage(q * WIDTH, 4 * WIDTH, WIDTH, HEIGHT);
		
		eyeSprite = spriteSheet.getSubimage(8 * WIDTH, 9 * WIDTH, WIDTH, HEIGHT);
		
		pacdotSprite = spriteSheet.getSubimage(16 * WIDTH/2, 0, WIDTH/2, HEIGHT/2);
		
		pacPowerSprites[0] = spriteSheet.getSubimage(21 * WIDTH/2, 0, WIDTH/2, HEIGHT/2);
		pacPowerSprites[1] = spriteSheet.getSubimage(22 * WIDTH/2, 0, WIDTH/2, HEIGHT/2);
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(NUM_COLS * SIZE, NUM_ROWS * SIZE));
	}
	
	public int getValue(int r, int c)
	{
		if(r >= 0 && r < NUM_ROWS && c >= 0 && c < NUM_COLS)
			return getBoard()[r][c];
		return 0;
	}
	
	public void setValue(int r, int c, int v)
	{
		if(r >= 0 && r < NUM_ROWS && c >= 0 && c < NUM_COLS)
			getBoard()[r][c] = v;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.GRAY);
		
		for(int a = 3; a < NUM_ROWS - 2; a++)
			if(a != 17)
				getBoard()[a][0] = -1;
		
		for(int b = 3; b < NUM_ROWS - 2; b++)
			if(b != 17)
				getBoard()[b][NUM_COLS - 1] = -1;
			
		for(int c = 0; c < NUM_COLS; c++)
			getBoard()[3][c] = -1;
		
		for(int d = 0; d < NUM_COLS; d++)
			getBoard()[NUM_ROWS - 3][d] = -1;
		
		for(int e = 5; e < 8; e++)
		{
			for(int f = 2; f < 6; f++)
			{
				getBoard()[e][f] = -1;
				getBoard()[e][NUM_COLS - f - 1] = -1;
			}
			
			for(int h = 7; h < 12; h++)
			{
				getBoard()[e][h] = -1;
				getBoard()[e][NUM_COLS - h - 1] = -1;
			}
		}
		
		for(int k = 13; k < 15; k++)
			for(int m = 3; m < 8; m++)
				getBoard()[m][k] = -1;
		
		for(int n = 9; n < 11; n++)
			for(int o = 2; o < 6; o++)
			{
				getBoard()[n][o] = -1;
				getBoard()[n][NUM_COLS - o - 1] = -1;
			}
		
		for(int p = 9; p < 17; p++)
			for(int q = 7; q < 9; q++)
			{
				getBoard()[p][q] = -1;
				getBoard()[p][NUM_COLS - q - 1] = -1;
			}
		
		for(int r = 12; r < 14; r++)
			for(int s = 9; s < 12; s++)
			{
				getBoard()[r][s] = -1;
				getBoard()[r][NUM_COLS - s - 1] = -1;
			}
		
		for(int t = 15; t < 20; t += 4)
			for(int u = 10; u < 18; u++)
				getBoard()[t][u] = -1;
		
		for(int v = 16; v < 19; v++)
			for(int w = 10; w < 18; w += 7)
				getBoard()[v][w] = -1;
		
		for(int x = 18; x < 23; x++)
			for(int y = 7; y < 9; y++)
			{
				getBoard()[x][y] = -1;
				getBoard()[x][NUM_COLS - y - 1] = -1;
			}
		
		for(int a1 = 12; a1 < 17; a1++)
			for(int b1 = 0; b1 < 6; b1++)
			{
				getBoard()[a1][b1] = -1;
				getBoard()[a1][NUM_COLS - b1 - 1] = -1;
			}
				
		for(int c1 = 13; c1 < 16; c1++)
			for(int d1 = 0; d1 < 5; d1++)
			{
				getBoard()[c1][d1] = 0;
				getBoard()[c1][NUM_COLS - d1 - 1] = 0;
			}
		
		for(int e1 = 18; e1 < 23; e1++)
			for(int f1 = 0; f1 < 6; f1++)
			{
				getBoard()[e1][f1] = -1;
				getBoard()[e1][NUM_COLS - f1 - 1] = -1;
			}
		
		for(int g1 = 19; g1 < 22; g1++)
			for(int h1 = 0; h1 < 5; h1++)
			{
				getBoard()[g1][h1] = 0;
				getBoard()[g1][NUM_COLS - h1 - 1] = 0;
			}
		
		for(int i1 = 21; i1 < 23; i1++)
			for(int j1 = 10; j1 < 18; j1++)
				getBoard()[i1][j1] = -1;
		
		for(int l1 = 23; l1 < 26; l1++)
			for(int m1 = 13; m1 < 15; m1++)
				getBoard()[l1][m1] = -1;
		
		for(int n1 = 24; n1 < 26; n1++)
		{
			for(int o1 = 7; o1 < 12; o1++)
			{
				getBoard()[n1][o1] = -1;
				getBoard()[n1][NUM_COLS - o1 - 1] = -1;
			}
			
			for(int p1 = 2; p1 < 6; p1++)
			{
				getBoard()[n1][p1] = -1;
				getBoard()[n1][NUM_COLS - p1 - 1] = -1;
			}
		}
		
		for(int q1 = 26; q1 < 29; q1++)
			for(int r1 = 4; r1 < 6; r1++)
			{
				getBoard()[q1][r1] = -1;
				getBoard()[q1][NUM_COLS - r1 - 1] = -1;
			}
		
		for(int s1 = 27; s1 < 29; s1++)
		{
			for(int t1 = 10; t1 < 18; t1++)
				getBoard()[s1][t1] = -1;
			
			for(int u1 = 1; u1 < 3; u1++)
			{
				getBoard()[s1][u1] = -1;
				getBoard()[s1][NUM_COLS - u1 - 1] = -1;
			}
		}
		
		for(int v1 = 29; v1 < 32; v1++)
			for(int w1 = 13; w1 < 15; w1++)
				getBoard()[v1][w1] = -1;
		
		for(int x1 = 30; x1 < 32; x1++)
			for(int y1 = 2; y1 < 12; y1++)
			{
				getBoard()[x1][y1] = -1;
				getBoard()[x1][NUM_COLS - y1 - 1] = -1;
			}
		
		for(int a2 = 27; a2 < 30; a2++)
			for(int b2 = 7; b2 < 9; b2++)
			{
				getBoard()[a2][b2] = -1;
				getBoard()[a2][NUM_COLS - b2 - 1] = -1;
			}
		
		for(int c2 = 9; c2 < 11; c2++)
			for(int d2 = 10; d2 < 18; d2++)
				getBoard()[c2][d2] = -1;
		
		for(int e2 = 11; e2 < 14; e2++)
			for(int f2 = 13; f2 < 15; f2++)
				getBoard()[e2][f2] = -1;
		
		g.drawImage(gameBoard, 0, 3 * SIZE, (int) (gameBoard.getWidth() * (NUM_COLS * SIZE)/736.0), (int) (gameBoard.getHeight() * ((NUM_ROWS - 5) * SIZE)/813.0), this);
		
		for(int row = 0; row < NUM_ROWS; row++)
			for(int col = 0; col < NUM_COLS; col++)
				if(getBoard()[row][col] != 0)
					switch(getBoard()[row][col])
					{
					case -4:
						g.drawImage(eyeSprite, col * SIZE, row * SIZE, this);
						break;
					case -3:
						switch(powerCounter)
						{
						case 0:
							g.drawImage(pacPowerSprites[0], col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
							powerCounter++;
							break;
						case 1:
							g.drawImage(pacPowerSprites[0], col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
							powerCounter++;
							break;
						case 2:
							g.drawImage(pacPowerSprites[0], col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
							powerCounter++;
							break;
						case 3:
							g.drawImage(pacPowerSprites[0], col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
							powerCounter++;
							break;
						case 4:
							g.drawImage(pacPowerSprites[0], col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
							powerCounter++;
							break;
						case 5:
							g.drawImage(pacPowerSprites[1], col * SIZE, row * SIZE, this);
							powerCounter++;
							break;
						case 6:
							g.drawImage(pacPowerSprites[1], col * SIZE, row * SIZE, this);
							powerCounter++;
							break;
						case 7:
							g.drawImage(pacPowerSprites[1], col * SIZE, row * SIZE, this);
							powerCounter++;
							break;
						case 8:
							g.drawImage(pacPowerSprites[1], col * SIZE, row * SIZE, this);
							powerCounter++;
							break;
						case 9:
							g.drawImage(pacPowerSprites[1], col * SIZE, row * SIZE, this);
							powerCounter = 0;
							break;
						}
						break;
					case -2:
						g.drawImage(pacdotSprite, col * SIZE + SIZE/3, row * SIZE + SIZE/3, this);
						break;
					case 1:						
						if(!Game.gameOver)
						{
							switch(Game.pacman.getDirection())
							{
							case 0:
								if(!Game.pacman.isMoving())
									g.drawImage(pacSprites[1], col * SIZE, row * SIZE, this);
								else
									switch(pacCounter)
									{
									case 0:
										g.drawImage(pacSprites[1], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 1:
										g.drawImage(pacSprites[3], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 2:
										g.drawImage(pacSprites[8], col * SIZE, row * SIZE, this);
										pacCounter = 0;
										break;
									}
								break;
							case 1:
								if(!Game.pacman.isMoving())
									g.drawImage(pacSprites[4], col * SIZE, row * SIZE, this);
								else
									switch(pacCounter)
									{
									case 0:
										g.drawImage(pacSprites[4], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 1:
										g.drawImage(pacSprites[6], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 2:
										g.drawImage(pacSprites[8], col * SIZE, row * SIZE, this);
										pacCounter = 0;
										break;
									}
								break;
							case 2:
								if(!Game.pacman.isMoving())
									g.drawImage(pacSprites[5], col * SIZE, row * SIZE, this);
								else
									switch(pacCounter)
									{
									case 0:
										g.drawImage(pacSprites[5], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 1:
										g.drawImage(pacSprites[7], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 2:
										g.drawImage(pacSprites[8], col * SIZE, row * SIZE, this);
										pacCounter = 0;
										break;
									}
								break;
							case 3:
								if(!Game.pacman.isMoving())
									g.drawImage(pacSprites[0], col * SIZE, row * SIZE, this);
								else
									switch(pacCounter)
									{
									case 0:
										g.drawImage(pacSprites[0], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 1:
										g.drawImage(pacSprites[2], col * SIZE, row * SIZE, this);
										pacCounter++;
										break;
									case 2:
										g.drawImage(pacSprites[8], col * SIZE, row * SIZE, this);
										pacCounter = 0;
										break;
									}
								break;
							}
						}
						
						else
							switch(deathCounter)
							{
							case 0:
								g.drawImage(pacSprites[9], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 1:
								g.drawImage(pacSprites[10], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 2:
								g.drawImage(pacSprites[11], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 3:
								g.drawImage(pacSprites[12], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 4:
								g.drawImage(pacSprites[13], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 5:
								g.drawImage(pacSprites[14], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 6:
								g.drawImage(pacSprites[15], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 7:
								g.drawImage(pacSprites[16], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 8:
								g.drawImage(pacSprites[17], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 9:
								g.drawImage(pacSprites[18], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 10:
								g.drawImage(pacSprites[19], col * SIZE, row * SIZE, this);
								deathCounter++;
								break;
							case 11:
								g.drawImage(pacSprites[20], col * SIZE, row * SIZE, this);
								break;
							}
						break;
					case 2:
						switch(Game.blinky.getDirection())
						{
						case 0:
							if(!blinkyAnimation)
							{
								g.drawImage(blinkySprites[6], col * SIZE, row * SIZE, this);
								blinkyAnimation = true;
							}
							else
							{
								g.drawImage(blinkySprites[7], col * SIZE, row * SIZE, this);
								blinkyAnimation = false;
							}
							break;
						case 1:
							if(!blinkyAnimation)
							{
								g.drawImage(blinkySprites[0], col * SIZE, row * SIZE, this);
								blinkyAnimation = true;
							}
							else
							{
								g.drawImage(blinkySprites[1], col * SIZE, row * SIZE, this);
								blinkyAnimation = false;
							}
							break;
						case 2:
							if(!blinkyAnimation)
							{
								g.drawImage(blinkySprites[2], col * SIZE, row * SIZE, this);
								blinkyAnimation = true;
							}
							else
							{
								g.drawImage(blinkySprites[3], col * SIZE, row * SIZE, this);
								blinkyAnimation = false;
							}
							break;
						case 3:
							if(!blinkyAnimation)
							{
								g.drawImage(blinkySprites[4], col * SIZE, row * SIZE, this);
								blinkyAnimation = true;
							}
							else
							{
								g.drawImage(blinkySprites[5], col * SIZE, row * SIZE, this);
								blinkyAnimation = false;
							}
							break;
						}
						break;
					case 3:
						switch(Game.pinky.getDirection())
						{
						case 0:
							if(!pinkyAnimation)
							{
								g.drawImage(pinkySprites[6], col * SIZE, row * SIZE, this);
								pinkyAnimation = true;
							}
							else
							{
								g.drawImage(pinkySprites[7], col * SIZE, row * SIZE, this);
								pinkyAnimation = false;
							}
							break;
						case 1:
							if(!pinkyAnimation)
							{
								g.drawImage(pinkySprites[0], col * SIZE, row * SIZE, this);
								pinkyAnimation = true;
							}
							else
							{
								g.drawImage(pinkySprites[1], col * SIZE, row * SIZE, this);
								pinkyAnimation = false;
							}
							break;
						case 2:
							if(!pinkyAnimation)
							{
								g.drawImage(pinkySprites[2], col * SIZE, row * SIZE, this);
								pinkyAnimation = true;
							}
							else
							{
								g.drawImage(pinkySprites[3], col * SIZE, row * SIZE, this);
								pinkyAnimation = false;
							}
							break;
						case 3:
							if(!blinkyAnimation)
							{
								g.drawImage(pinkySprites[4], col * SIZE, row * SIZE, this);
								pinkyAnimation = true;
							}
							else
							{
								g.drawImage(pinkySprites[5], col * SIZE, row * SIZE, this);
								pinkyAnimation = false;
							}
							break;
						}
						break;
					case 4:
						switch(Game.inky.getDirection())
						{
						case 0:
							if(!inkyAnimation)
							{
								g.drawImage(inkySprites[6], col * SIZE, row * SIZE, this);
								inkyAnimation = true;
							}
							else
							{
								g.drawImage(inkySprites[7], col * SIZE, row * SIZE, this);
								inkyAnimation = false;
							}
							break;
						case 1:
							if(!inkyAnimation)
							{
								g.drawImage(inkySprites[0], col * SIZE, row * SIZE, this);
								inkyAnimation = true;
							}
							else
							{
								g.drawImage(inkySprites[1], col * SIZE, row * SIZE, this);
								inkyAnimation = false;
							}
							break;
						case 2:
							if(!inkyAnimation)
							{
								g.drawImage(inkySprites[2], col * SIZE, row * SIZE, this);
								inkyAnimation = true;
							}
							else
							{
								g.drawImage(inkySprites[3], col * SIZE, row * SIZE, this);
								inkyAnimation = false;
							}
							break;
						case 3:
							if(!inkyAnimation)
							{
								g.drawImage(inkySprites[4], col * SIZE, row * SIZE, this);
								inkyAnimation = true;
							}
							else
							{
								g.drawImage(inkySprites[5], col * SIZE, row * SIZE, this);
								inkyAnimation = false;
							}
							break;
						}
						break;
					case 5:
						switch(Game.clyde.getDirection())
						{
						case 0:
							if(!clydeAnimation)
							{
								g.drawImage(clydeSprites[6], col * SIZE, row * SIZE, this);
								clydeAnimation = true;
							}
							else
							{
								g.drawImage(clydeSprites[7], col * SIZE, row * SIZE, this);
								clydeAnimation = false;
							}
							break;
						case 1:
							if(!clydeAnimation)
							{
								g.drawImage(clydeSprites[0], col * SIZE, row * SIZE, this);
								clydeAnimation = true;
							}
							else
							{
								g.drawImage(clydeSprites[1], col * SIZE, row * SIZE, this);
								clydeAnimation = false;
							}
							break;
						case 2:
							if(!clydeAnimation)
							{
								g.drawImage(clydeSprites[2], col * SIZE, row * SIZE, this);
								clydeAnimation = true;
							}
							else
							{
								g.drawImage(clydeSprites[3], col * SIZE, row * SIZE, this);
								clydeAnimation = false;
							}
							break;
						case 3:
							if(!clydeAnimation)
							{
								g.drawImage(clydeSprites[4], col * SIZE, row * SIZE, this);
								clydeAnimation = true;
							}
							else
							{
								g.drawImage(clydeSprites[5], col * SIZE, row * SIZE, this);
								clydeAnimation = false;
							}
							break;
						}
						break;
					case 6:
						if(!isGhostTime())
						{
							if(!ghostAnimation)
							{
								g.drawImage(ghostSprites[2], col * SIZE, row * SIZE, this);
								ghostAnimation = true;
							}
							else
							{
								g.drawImage(ghostSprites[3], col * SIZE, row * SIZE, this);
								ghostAnimation = false;
							}
						}
						else
						{
							switch(ghostCounter)
							{
							case 0:
								g.drawImage(ghostSprites[0], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 1:
								g.drawImage(ghostSprites[1], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 2:
								g.drawImage(ghostSprites[0], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 3:
								g.drawImage(ghostSprites[1], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 4:
								g.drawImage(ghostSprites[2], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 5:
								g.drawImage(ghostSprites[3], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 6:
								g.drawImage(ghostSprites[2], col * SIZE, row * SIZE, this);
								ghostCounter++;
								break;
							case 7:
								g.drawImage(ghostSprites[3], col * SIZE, row * SIZE, this);
								ghostCounter = 0;
								break;
							}
						}
						break;
					}
		
		g.setFont(game);
		
		if(!displayIntro)
			g.drawString("PAC-MAN BY ARCHIE", 150, 570);
		else
			g.drawString("BEWARE OF GLITCHES", 140, 570);
		
		String score = "" + Game.getScore();
		g.drawString("SCORE: " + score, 10, 20);
		
		if(displayIntro)
			g.drawString("GET READY!", 180, 20);
			
		if(Game.gameOver)
			g.drawString("GAME OVER!", 180, 20);
		
		if(Game.gameWon)
			g.drawString("YOU WON!!!", 180, 20);
	}
	
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public boolean isGhostTime() {
		return ghostTime;
	}

	public void setGhostTime(boolean ghostTime) {
		this.ghostTime = ghostTime;
	}
	
}
