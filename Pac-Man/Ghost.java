import java.util.List;
import java.util.ArrayList;

public abstract class Ghost 
{

	public static Board myBoard;
	public static Pacman pacman;
	private List<Integer> posR = new ArrayList<Integer>();
	private List<Integer> posC = new ArrayList<Integer>();
	private int direction; // 0 is up; 1 is right; 2 is down; 3 is left
	private int color;
	private int dir;
	private int[] location = new int[2];
	private int[] pacmanLocation = new int[2];
	protected boolean waiting = true;
	public boolean scattered;
	public boolean chase ;
	public boolean scatter = true;
	public boolean frightened;
	public boolean eaten;
	
	public Ghost(int r, int c, int d, int cl, Board b, Pacman p)
	{
		getPosR().add(r);
		getPosC().add(c);
		setDirection(d);
		myBoard = b;
		pacman = p;
		setColor(cl);
		
		myBoard.setValue(getPosR().get(0), getPosC().get(0), getColor());
	}
	
	public void activate()
	{
		waiting = false;
	}
	
	public void deactivate()
	{
		waiting = true;
	}
	
	public void wobble()
	{
		if(getDirection() == 0)
		{
			setDirection(2);
			move();
		}
		else
		{
			setDirection(0);
			move();
		}
	}
	
	public boolean checkCollision()
	{
		int r = getPosR().get(0);
		int c = getPosC().get(0);
		
		switch(getDirection())
		{
		case 0:
			r--;
			break;
		case 1:
			c++;
			break;
		case 2: 
			r++;
			break;
		case 3:
			c--;
			break;
		}
		
		if(myBoard.getValue(r, c) == -1 || myBoard.getValue(r, c) > 1)
			return true;
		
		return false;
	}
	
	public boolean checkPacCollision()
	{
		int r = getPosR().get(0);
		int c = getPosC().get(0);
		
		switch(getDirection())
		{
		case 0:
			r--;
			break;
		case 1:
			c++;
			break;
		case 2: 
			r++;
			break;
		case 3:
			c--;
			break;
		}
		
		if(myBoard.getValue(r, c) == 1 || myBoard.getValue(r - 1, c) == 1 || myBoard.getValue(r + 1, c) == 1 || myBoard.getValue(r, c - 1) == 1 || myBoard.getValue(r, c + 1) == 1)
			return true;
		
		return false;
	}
	
	public void release()
	{
		myBoard.setValue(getPosR().get(0), getPosC().get(0), 0);
		getPosR().set(0, 14);
		getPosC().set(0, 13);
		myBoard.setValue(getPosR().get(0), getPosC().get(0), getColor());
	}
	
	public void move()
	{
		myBoard.setValue(getPosR().get(0), getPosC().get(0), 0);
		
		int r = getPosR().get(0);
		int c = getPosC().get(0);
		
		switch(getDirection())
		{
		case 0:
			r--;
			break;
		case 1:
			c++;
			break;
		case 2: 
			r++;
			break;
		case 3:
			c--;
			break;
		}

		if(r >= 0 && c >= -1 && r < myBoard.NUM_ROWS - 1 && c <= myBoard.NUM_COLS && myBoard.getValue(r, c) != -1)
		{
			if(c < 0)
				c = myBoard.NUM_COLS - 1;
			if(c >= myBoard.NUM_COLS)
				c = 0;
			
			getPosR().set(0, r);
			getPosC().set(0, c);
		}
		
		myBoard.setValue(getPosR().get(0), getPosC().get(0), getColor());
	}
	
	protected void update()
	{
		setDir(getDirection());
		getLocation()[0] = getPosR().get(0);
		getLocation()[1] = getPosC().get(0);
		getPacmanLocation()[0] = pacman.getPosR().get(0);
		getPacmanLocation()[1] = pacman.getPosC().get(0);
	}
	
	protected void followTarget(int x, int y)
	{
		int horizontalDistance = getLocation()[1] - x;
		int verticalDistance = getLocation()[0] - y;
		
		int horizontalDir = horizontalDistance > 0 ? 3 : 1;
		int verticalDir = verticalDistance > 0 ? 0 : 2;
		
		boolean vertical = Math.abs(verticalDistance) > Math.abs(horizontalDistance);
		
		if(vertical)
			setDirection(verticalDir);
		else
			setDirection(horizontalDir);
		
		if(checkCollision())
		{
			if(vertical)
			{
				if(getDir() == 1 || getDir() == 3)
				{
					setDirection(getDir());
					if(checkCollision())
						setDirection(getDirection() == 3 ? 1 : 3);
				}
				else
				{
					setDirection(horizontalDir);
					if(checkCollision())
					{
						setDirection(horizontalDir == 3 ? 1 : 3);
						if(checkCollision())
							setDirection(verticalDir == 0 ? 2 : 0);
					}
				}
			}
			else
			{
				if(getDir() == 0 || getDir() == 2)
				{
					setDirection(getDir());
					if(checkCollision())
						setDirection(getDirection() == 0 ? 2 : 0);
				}
				else
				{
					setDirection(verticalDir);
					if(checkCollision())
					{
						setDirection(verticalDir == 0 ? 2 : 0);
						if(checkCollision())
							setDirection(horizontalDir == 3 ? 1 : 3);
					}
				}
				
			}
		}
	}
	
	protected void followPacman()
	{
		followTarget(getPacmanLocation()[1], getPacmanLocation()[0]);
	}
	
	protected double targetDistance(int x, int y)
	{
		double distance = 0;
		distance = Math.sqrt(Math.pow(x - getLocation()[1], 2) + Math.pow(y - getLocation()[0], 2));
		return distance;
	}
	
	protected double pacmanDistance()
	{
		return targetDistance(getPacmanLocation()[1], getPacmanLocation()[0]);
	}
	
	protected void runAway(int x, int y)
	{
		int horizontalDistance = getLocation()[1] - x;
		int verticalDistance = getLocation()[0] - y;
		
		int horizontalDir = horizontalDistance > 0 ? 1 : 3;
		int verticalDir = verticalDistance > 0 ? 2 : 0;
		
		boolean vertical = Math.abs(verticalDistance) > Math.abs(horizontalDistance);
		
		if(vertical)
			setDirection(verticalDir);
		else
			setDirection(horizontalDir);
		
		if(checkCollision())
		{
			if(vertical)
			{
				if(getDir() == 1 || getDir() == 3)
				{
					setDirection(getDir());
					if(checkCollision())
						setDirection(getDirection() == 3 ? 1 : 3);
				}
				else
				{
					setDirection(horizontalDir);
					if(checkCollision())
					{
						setDirection(horizontalDir == 3 ? 1 : 3);
						if(checkCollision())
							setDirection(verticalDir == 0 ? 2 : 0);
					}
				}
			}
			else
			{
				if(getDir() == 0 || getDir() == 2)
				{
					setDirection(getDir());
					if(checkCollision())
						setDirection(getDirection() == 0 ? 2 : 0);
				}
				else
				{
					setDirection(verticalDir);
					if(checkCollision())
					{
						setDirection(verticalDir == 0 ? 2 : 0);
						if(checkCollision())
							setDirection(horizontalDir == 3 ? 1 : 3);
					}
				}
				
			}
		}
	}
	
	protected void runFromPac()
	{
		runAway(getPacmanLocation()[1], getPacmanLocation()[0]);
	}
	
	protected abstract void AI();
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public List<Integer> getPosR() {
		return posR;
	}

	public void setPosR(List<Integer> posR) {
		this.posR = posR;
	}

	public List<Integer> getPosC() {
		return posC;
	}

	public void setPosC(List<Integer> posC) {
		this.posC = posC;
	}

	public int[] getPacmanLocation() {
		return pacmanLocation;
	}

	public void setPacmanLocation(int[] pacmanLocation) {
		this.pacmanLocation = pacmanLocation;
	}

	public int[] getLocation() {
		return location;
	}

	public void setLocation(int[] location) {
		this.location = location;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

}
