
public class Pinky extends Ghost
{
	
	public Pinky(int r, int c, int d, Board b, Pacman p)
	{
		super(r, c, d, 3, b, p);
	}
	
	protected void AI()
	{
		if(!waiting)
		{
			update();
			
			if(chase)
			{
				scattered = false;
				int blinkyDir = Game.blinky.getDirection();
				if(pacmanDistance() < 5)
				{
					if(blinkyDir == 0 || blinkyDir == 2)
						setDirection(blinkyDir == 0 ? 2 : 0);
					if(blinkyDir == 1 || blinkyDir == 3)
						setDirection(blinkyDir == 1 ? 3 : 1);
					if(checkCollision())
						followPacman();
				}
				else
					followPacman();
			}
			
			else if(scatter)
			{
				if(!scattered)
					followTarget(1, 0);
				
				if(getLocation()[0] == 4 && getLocation()[1] == 6)
				{
					scattered = true;
					setDirection(3);
				}
				else if(getLocation()[0] == 4 && getLocation()[1] == 1)
				{
					scattered = true;
					setDirection(2);
				}
				else if(getLocation()[0] == 8 && getLocation()[1] == 1)
				{
					scattered = true;
					setDirection(1);
				}
				else if(getLocation()[0] == 8 && getLocation()[1] == 6)
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
					setColor(3);
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
