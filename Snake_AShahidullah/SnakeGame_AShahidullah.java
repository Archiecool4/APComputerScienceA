import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class SnakeGame_AShahidullah extends JFrame
{
	
	public static Board_AShahidullah myBoard;
	public static Snake_AShahidullah Snake;
	public static boolean gameOver;
	
	public SnakeGame_AShahidullah() 
	{
		super("Snake");
		
		myBoard = new Board_AShahidullah(50, 50);
		Snake = new Snake_AShahidullah(4, 25, 4, 1, myBoard);
		
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
					if(Snake.getDir() != 2)
						Snake.setDir(0);
					break;
				case KeyEvent.VK_D:
					if(Snake.getDir() != 3)
						Snake.setDir(1);
					break;
				case KeyEvent.VK_S:
					if(Snake.getDir() != 0)
						Snake.setDir(2);
					break;
				case KeyEvent.VK_A:
					if(Snake.getDir() != 1)
						Snake.setDir(3);
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
				}
			}
		});
	}
	
	public void runGame() 
	{		
		if(myBoard.findSpace() == true) // spawns initial fruit
			myBoard.addFruit();
		while(true)
		{	
			if(Snake.checkCollision() == true)
			{
				gameOver = true; 
				myBoard.repaint();
				return;
			}
			
			Snake.move();
			
			if(Snake.increaseLength == true) // checks whether the length has increased (aka a fruit was eaten)
				if(myBoard.findSpace() == true) // checks for an empty space
					myBoard.addFruit();
			
			myBoard.repaint();
			
			try {
				Thread.sleep(100);
			}
			catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		new SnakeGame_AShahidullah();
	}
}
