import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements KeyListener
{

	public static Board myBoard;
	public static Pacman pacman;
	public static Ghost blinky;
	public static Ghost pinky;
	public static Ghost inky;
	public static Ghost clyde;
	public static boolean gameOver;
	public static boolean gameWon;
	private boolean frightened;
	private boolean soundPlayed;
	private int frightenedCounter = 0;
	private int pacCounter = 0;
	private static int score = 0;
	private int seconds = 0;
	private int[] restrictedSpaces = new int[342]; 
	private static List<Pacdot> pacdots;
	private static List<Pacpower> pacpowers;
	
	public Game()
	{
		super("GHETTO PAC-MAN");
		
		myBoard = new Board(36, 28, 16);
		pacman = new Pacman(26, 13, 3, myBoard);
		blinky = new Blinky(14, 13, 3, myBoard, pacman);
		pinky = new Pinky(17, 13, 2, myBoard, pacman);
		inky = new Inky(17, 11, 0, myBoard, pacman);
		clyde = new Clyde(17, 15, 0, myBoard, pacman);
		setPacdots(new ArrayList<Pacdot>());
		setPacpowers(new ArrayList<Pacpower>());
		
		int k = 0;
		for(int i = 12; i < 23; i++)
			for(int j = 7; j < 21; j++)
			{
				restrictedSpaces[k] = i;
				restrictedSpaces[k + 1] = j;
				k += 2;
			}
		
		for(int l = 0; l < 6; l++)
		{
			restrictedSpaces[k] = 17;
			restrictedSpaces[k + 1] = l;
			k += 2;
		}
		
		for(int m = myBoard.NUM_COLS - 1; m >= myBoard.NUM_COLS - 6; m--)
		{
			restrictedSpaces[k] = 17;
			restrictedSpaces[k + 1] = m;
			k += 2;
		}

		restrictedSpaces[k] = 6;
		restrictedSpaces[k + 1] = 1;
		restrictedSpaces[k + 2] = 6;
		restrictedSpaces[k + 3] = myBoard.NUM_COLS - 2;
		restrictedSpaces[k + 4] = 26;
		restrictedSpaces[k + 5] = 1;
		restrictedSpaces[k + 6] = 26;
		restrictedSpaces[k + 7] = myBoard.NUM_COLS - 2;
		restrictedSpaces[k + 8] = pacman.getPosR().get(0);
		restrictedSpaces[k + 9] = pacman.getPosC().get(0);
		
		initPacdots();
		initPacpowers();
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		add(myBoard, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		addKeyListener(this);
		runGame();
	}
	
	private void initPacpowers()
	{
		getPacpowers().add(new Pacpower(6, 1, myBoard));
		getPacpowers().add(new Pacpower(6, myBoard.NUM_COLS - 2, myBoard));
		getPacpowers().add(new Pacpower(26, 1, myBoard));
		getPacpowers().add(new Pacpower(26, myBoard.NUM_COLS - 2, myBoard));
	}
	
	private void initPacdots()
	{
		for(int i = 3; i < myBoard.NUM_ROWS - 3; i++)
			for(int j = 0; j < myBoard.NUM_COLS; j++)
				if(myBoard.getValue(i, j) == 0 && !checkRestrictedSpaces(i, j))
					getPacdots().add(new Pacdot(i, j, myBoard));
	}
	
	private boolean checkRestrictedSpaces(int r, int c)
	{
		for(int i = 0; i < restrictedSpaces.length; i += 2)
			if(r == restrictedSpaces[i] && c == restrictedSpaces[i + 1])
				return true;
		return false;
	}
	
	public void keyPressed(KeyEvent e) { }
	
	public void keyTyped(KeyEvent e) { }
	
	public void keyReleased(KeyEvent e) { 
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			if(!pacman.checkWallCollision(0))
				pacman.setDirection(0);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			if(!pacman.checkWallCollision(1))
				pacman.setDirection(1);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			if(!pacman.checkWallCollision(2))
				pacman.setDirection(2);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			if(!pacman.checkWallCollision(3))
				pacman.setDirection(3);
			break;
		}
	}
	
			ActionListener pacListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			if(!pacman.checkCollision() && !gameWon)
			{
				switch(pacCounter)
				{
				case 2:
					if(pacman.checkPacdot())
					{
						setScore(getScore() + 10);
						new Sound("waka.wav");
						Iterator<Pacdot> dotitr = getPacdots().iterator();
						while(dotitr.hasNext())
						{
							Pacdot p = dotitr.next();
							if(p.posRow == pacman.getPosR().get(0) && p.posCol == pacman.getPosC().get(0))
								dotitr.remove();
						}
					}
					
					if(pacman.checkPacpower())
					{
						setScore(getScore() + 50);
						frightened = true;
						frightenedCounter = seconds;
						Iterator<Pacpower> poweritr = getPacpowers().iterator();
						while(poweritr.hasNext())
						{
							Pacpower p = poweritr.next();
							if(p.posRow == pacman.getPosR().get(0) && p.posCol == pacman.getPosC().get(0))
								poweritr.remove();
						}
					}
					
					if(pacdots.size() == 448 || (pacdots.size() < 500 && score > 2600))
						gameWon = true;
					
					pacman.move();
					pacCounter = 0;
					break;
				default:
					pacCounter++;
					break;
				}
			}
			
			else if(!gameWon)
			{
				if(!soundPlayed)
				{
					new Sound("gameover.wav");
					soundPlayed = true;
				}
				gameOver = true;
			}
			
			myBoard.repaint();
		}
			};
	
			ActionListener blinkyListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			if(!gameOver)
			{
				blinky.activate();
				
				if(blinky.frightened && blinky.checkPacCollision())
				{
					blinky.setColor(-4);
					blinky.eaten = true;
					blinky.frightened = false;
				}
				
				redrawDots(blinky);
				redrawPowers(blinky);
				blinky.AI();
			}
		}
			};
			
			ActionListener pinkyListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			if(!gameOver)
			{
				if(seconds == 7)
				{
					pinky.activate();
					pinky.release();
				}
				redrawDots(pinky);
				redrawPowers(pinky);
				pinky.AI();
			}
		}
			};
	
			ActionListener inkyListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			if(!gameOver)
			{
				if(seconds == 14)
				{
					inky.activate();
					inky.release();
				}
				redrawDots(inky);
				redrawPowers(inky);
				inky.AI();
			}
		}
			};
			
			ActionListener clydeListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			if(!gameOver)
			{
				if(seconds == 19)
				{
					clyde.activate();
					clyde.release();
				}
				redrawDots(clyde);
				redrawPowers(clyde);
				clyde.AI();
			}
		}
			};
			
			ActionListener timerListener = new ActionListener()
			{
		public void actionPerformed(ActionEvent event)
		{
			seconds++;
			
			if(!frightened)
			{
				if(seconds == 7)
				{
					blinky.scatter = false;
					pinky.scatter = false;
					inky.scatter = false;
					clyde.scatter = false;
					
					blinky.chase = true;
					pinky.chase = true;
					inky.chase = true;
					clyde.chase = true;
				}
				
				if(seconds == 27)
				{
					blinky.chase = false;
					pinky.chase = false;
					inky.chase = false;
					clyde.chase = false;
					
					blinky.scatter = true;
					pinky.scatter = true;
					inky.scatter = true;
					clyde.scatter = true;
				}
				
				if(seconds == 34)
				{
					blinky.scatter = false;
					pinky.scatter = false;
					inky.scatter = false;
					clyde.scatter = false;
					
					blinky.chase = true;
					pinky.chase = true;
					inky.chase = true;
					clyde.chase = true;
				}
				
				if(seconds == 54)
				{
					blinky.chase = false;
					pinky.chase = false;
					inky.chase = false;
					clyde.chase = false;
					
					blinky.scatter = true;
					pinky.scatter = true;
					inky.scatter = true;
					clyde.scatter = true;
				}
				
				if(seconds == 59)
				{
					blinky.scatter = false;
					pinky.scatter = false;
					inky.scatter = false;
					clyde.scatter = false;
					
					blinky.chase = true;
					pinky.chase = true;
					inky.chase = true;
					clyde.chase = true;
				}
				
				if(seconds == 79)
				{
					blinky.chase = false;
					pinky.chase = false;
					inky.chase = false;
					clyde.chase = false;
					
					blinky.scatter = true;
					pinky.scatter = true;
					inky.scatter = true;
					clyde.scatter = true;
				}
				
				if(seconds == 84)
				{
					blinky.scatter = false;
					pinky.scatter = false;
					inky.scatter = false;
					clyde.scatter = false;
					
					blinky.chase = true;
					pinky.chase = true;
					inky.chase = true;
					clyde.chase = true;
				}
			}
			
			else
			{
				blinky.scatter = false;
				pinky.scatter = false;
				inky.scatter = false;
				clyde.scatter = false;
				
				blinky.chase = false;
				pinky.chase = false;
				inky.chase = false;
				clyde.chase = false;
				
				if(!blinky.eaten)
					blinky.frightened = true;
				
				if(!pinky.eaten)
					pinky.frightened = true;
				
				if(!inky.eaten)
					inky.frightened = true;
				
				if(!clyde.eaten)
					clyde.frightened = true;
				
				if(seconds - frightenedCounter == 10)
					myBoard.setGhostTime(true);
				
				if(seconds - frightenedCounter == 13)
				{
					myBoard.setGhostTime(false);
					
					blinky.frightened = false;
					pinky.frightened = false;
					inky.frightened = false;
					clyde.frightened = false;
					
					blinky.chase = true;
					pinky.chase = true;
					inky.chase = true;
					clyde.chase = true;
					
					blinky.setColor(2);
					pinky.setColor(3);
					inky.setColor(4);
					clyde.setColor(5);
					
					frightened = false;
				}
			}
		}
			};
			
	private void redrawDots(Ghost g)
	{
		for(Pacdot p : pacdots)
			if(p.posRow == g.getLocation()[0] && p.posCol == g.getLocation()[1])
				p.reset();
	}
			
	private void redrawPowers(Ghost g)
	{
		for(Pacpower p : pacpowers)
			if(p.posRow == g.getLocation()[0] && p.posCol == g.getLocation()[1])
				p.reset();
	}
	
	private void runGame()
	{
		new Sound("intro.wav");
		try {
			Thread.sleep(5000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		myBoard.displayIntro = false;
		
		Timer pacTimer = new Timer(75, pacListener);
		pacTimer.start();
		
		Timer blinkyTimer = new Timer(300, blinkyListener);
		blinkyTimer.start();
		
		Timer pinkyTimer = new Timer(300, pinkyListener);
		pinkyTimer.start();
		
		Timer inkyTimer = new Timer(300, inkyListener);
		inkyTimer.start();
		
		Timer clydeTimer = new Timer(300, clydeListener);
		clydeTimer.start();
		
		Timer secondTimer = new Timer(1000, timerListener);
		secondTimer.start();
	}
	
	public static void main(String[] args)
	{
		new Game();
	}

	public static int getScore() {
		return score;
	}

	@SuppressWarnings("static-access")
	public void setScore(int score) {
		this.score = score;
	}

	public static List<Pacdot> getPacdots() {
		return pacdots;
	}

	public static void setPacdots(List<Pacdot> pacdots) {
		Game.pacdots = pacdots;
	}

	public static List<Pacpower> getPacpowers() {
		return pacpowers;
	}

	public static void setPacpowers(List<Pacpower> pacpowers) {
		Game.pacpowers = pacpowers;
	}
	
}
