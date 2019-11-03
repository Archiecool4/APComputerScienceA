
public class Inky extends Ghost
{

	public Inky(int r, int c, int d, Board b, Pacman p)
	{
		super(r, c, d, 4, b, p);
	}
	
	protected void AI()
	{
		if(!waiting)
		{
			update();
			
			if(chase)
			{
				scattered = false;
				if(pacmanDistance() > 7.5)
				{
					followTarget(Game.pinky.getPosC().get(0), Game.pinky.getPosR().get(0));
				}
				else
					followPacman();
			}
			
			else if(scatter)
			{
				if(!scattered)
					followTarget(0, myBoard.NUM_ROWS - 1);
				
				if(getLocation()[0] == myBoard.NUM_ROWS - 4 && getLocation()[1] == 1)
				{
					scattered = true;
					setDirection(1);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 4 && getLocation()[1] == 12)
				{
					scattered = true;
					setDirection(0);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == 12)
				{
					scattered = true;
					setDirection(3);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == 9)
				{
					scattered = true;
					setDirection(0);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 10 && getLocation()[1] == 9)
				{
					scattered = true;
					setDirection(3);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 10 && getLocation()[1] == 6)
				{
					scattered = true;
					setDirection(2);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == 6)
				{
					scattered = true;
					setDirection(3);
				}
				else if(getLocation()[0] == myBoard.NUM_ROWS - 7 && getLocation()[1] == 1)
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
					setColor(4);
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
