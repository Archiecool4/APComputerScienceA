
public class Blinky extends Ghost
{
	
	public Blinky(int r, int c, int d, Board b, Pacman p)
	{
		super(r, c, d, 2, b, p);
	}
	
	protected void AI()
	{
		if(!waiting)
		{
			update(); 
			
			if(chase)
			{
				scattered = false;
				followPacman();
			}
			
			else if(scatter)
			{
					if(!scattered)
						followTarget(myBoard.NUM_COLS - 2, 0);
					
					if(getLocation()[0] == 4 && getLocation()[1] == myBoard.NUM_COLS - 7)
					{
						scattered = true;
						setDirection(1);
					}
					else if(getLocation()[0] == 4 && getLocation()[1] == myBoard.NUM_COLS - 2)
					{
						scattered = true;
						setDirection(2);
					}
					else if(getLocation()[0] == 8 && getLocation()[1] == myBoard.NUM_COLS - 2)
					{
						scattered = true;
						setDirection(3);
					}
					else if(getLocation()[0] == 8 && getLocation()[1] == myBoard.NUM_COLS - 7)
					{
						scattered = true;
						setDirection(0);
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
					setColor(2);
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
