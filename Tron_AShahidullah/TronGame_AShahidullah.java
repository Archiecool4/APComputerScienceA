import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class TronGame_AShahidullah extends JFrame
{
	
	public static Board_AShahidullah myBoard;
	public static Tron_AShahidullah Tron1;
	public static Tron_AShahidullah Tron2;
	public static boolean gameOver;
	public static boolean gameOver1;
	public static boolean gameOver2;
	public static boolean pause;
	public static boolean gameStarted;
	public static boolean epilepsy;
	public static boolean restart;
	
	public TronGame_AShahidullah() 
	{
		super("Tron");
		
		myBoard = new Board_AShahidullah(50, 50);
		Tron1 = new Tron_AShahidullah(4, 25, 4, 1, myBoard);
		Tron2 = new Tron_AShahidullah(45, 25, 2, 3, myBoard);
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		add(myBoard, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		keyListener();
		runGame();
	}
	
	public void keyListener()
	{
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_W:
					if(Tron1.getDir() != 2)
						Tron1.setDir(0);
					break;
				case KeyEvent.VK_D:
					if(Tron1.getDir() != 3)
						Tron1.setDir(1);
					break;
				case KeyEvent.VK_S:
					if(Tron1.getDir() != 0)
						Tron1.setDir(2);
					break;
				case KeyEvent.VK_A:
					if(Tron1.getDir() != 1)
						Tron1.setDir(3);
					break;
				case KeyEvent.VK_UP:
					if(Tron2.getDir() != 2)
						Tron2.setDir(0);
					break;
				case KeyEvent.VK_RIGHT:
					if(Tron2.getDir() != 3)
						Tron2.setDir(1);
					break;
				case KeyEvent.VK_DOWN:
					if(Tron2.getDir() != 0)
						Tron2.setDir(2);
					break;
				case KeyEvent.VK_LEFT:
					if(Tron2.getDir() != 1)
						Tron2.setDir(3);
					break;
				case KeyEvent.VK_E:
					epilepsy ^= true;
					break;
				case KeyEvent.VK_P:
					pause ^= true;
					break;
				case KeyEvent.VK_R:
					restart = true;
					dispose();
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
				}
			}
		});
	}
	
	public void runGame() 
	{	
		try {
			Thread.sleep(2000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		gameStarted = true;
		myBoard.repaint();
		
		while(true)
		{	
			if(Tron1.checkCollision() == true)
			{
				if(Tron2.checkCollision() == true)
				{
					gameOver = true;
					new Sound_AShahidullah("gameover.wav");
					myBoard.repaint();
					return;
				}
				gameOver1 = true; 
				new Sound_AShahidullah("gameover.wav");
				myBoard.repaint();
				return;
			}
			
			if(Tron2.checkCollision() == true)
			{
				gameOver2 = true;
				new Sound_AShahidullah("gameover.wav");
				myBoard.repaint();
				return;
			}
			
			Tron1.move();
			Tron2.move();
			myBoard.repaint();
			
			while(pause == true)
			{
				myBoard.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			} 
			
			myBoard.repaint();
			
			try {
				Thread.sleep(50);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Sound_AShahidullah("music.wav");
		do
		{
			restart = false;
			new TronGame_AShahidullah();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			gameOver = false; gameOver1 = false; gameOver2 = false; gameStarted = false; epilepsy = false; pause = false;
			while(restart == false)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			} 
		} while(restart == true);
		myBoard.repaint();
	}
	
}
