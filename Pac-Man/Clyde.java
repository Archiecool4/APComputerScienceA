import java.util.Random;

public class Clyde extends Ghost
{

	private boolean opposite = false;
	private int counter = 0;
	private Random num = new Random();
	
	public Clyde(int r, int c, int d, Board b, Pacman p)
	{
		super(r, c, d, 5, b, p);
	}
	
	protected void AI()
	{
		if(!waiting)
		{
			update();
			
			if(chase)
			{
				scattered = false;
				if(pacmanDistance() > 10)
				{
					setDirection(pacman.getDirection());
					if(opposite)
					{
						if(counter++ == 50)
						{
							opposite = false;
							counter = 0;
						}
						followTarget(4, 1);
					}
					else
					{
						if(num.nextInt(40) == 1)
						{
							opposite = true;
						}
						if(getDirection() == 0)
						{
							followTarget(getPacmanLocation()[1], getPacmanLocation()[0] - 5);
						}
						else if(getDirection() == 2)
						{
							followTarget(getPacmanLocation()[1], getPacmanLocation()[0] + 5);
						}
						else if(getDirection() == 3)
						{
							followTarget(getPacmanLocation()[1] - 5, getPacmanLocation()[0]);
						}
						else
						{
							followTarget(getPacmanLocation()[1] + 5, getPacmanLocation()[0]);
						}
					}
				}
				else
					followPacman();
			}
			
			else if(scatter)
			{
				if(!scattered)
					followTarget(myBoard.NUM_COLS - 1, myBoard.NUM_ROWS - 1);
				
				if(getLocation()[0] == myBoard.NUM_ROWS - 4 && getLocation()[1] == myBoard.NUM_COLS - 2)
				{
					scattered = true;
					setDirection(3);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 4 && getLocation()[1] == myBoard.NUM_COLS - 13)
				{
					scattered = true;
					setDirection(0);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == myBoard.NUM_COLS - 13)
				{
					scattered = true;
					setDirection(1);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == myBoard.NUM_COLS - 10)
				{
					scattered = true;
					setDirection(0);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 10 && getLocation()[1] == myBoard.NUM_COLS - 10)
				{
					scattered = true;
					setDirection(1);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 10 && getLocation()[1] == myBoard.NUM_COLS - 7)
				{
					scattered = true;
					setDirection(2);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == myBoard.NUM_COLS - 7)
				{
					scattered = true;
					setDirection(1);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == myBoard.NUM_COLS - 2)
				{
					scattered = true;
					setDirection(2);
				}
			}
			
			else if(frightened)
			{
				scattered = false;
				setColor(6);
				runFromPac();
			}
			
			else if(eaten)
			{
				scattered = false;
				followTarget(13, 14);
				if(getLocation()[0] == 14 && getLocation()[1] == 13)
				{
					setColor(5);
					chase = true;
					eaten = false;
				}
			}
			
			move();
		}
		else
			wobble();
	}
}
