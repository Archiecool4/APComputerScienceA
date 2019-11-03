import java.util.List;
import java.util.ArrayList;

public class Pacman 
{

	public static Board myBoard;
	private List<Integer> posR;
	private List<Integer> posC;
	private int direction; // 0 is up; 1 is right; 2 is down; 3 is left
	private boolean isMoving = true;
	
	public Pacman(int r, int c, int d, Board b)
	{
		setPosR(new ArrayList<Integer>());
		setPosC(new ArrayList<Integer>());
		
		getPosR().add(r);
		getPosC().add(c);
		setDirection(d);
		myBoard = b;
		
		myBoard.setValue(getPosR().get(0), getPosC().get(0), 1);
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
		
		if(myBoard.getValue(r, c) > 1 && myBoard.getValue(r, c) < 6)
			return true;
		
		return false;
	}
	
	public boolean checkWallCollision(int d)
	{
		int r = getPosR().get(0);
		int c = getPosC().get(0);
		
		switch(d)
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
		
		if(myBoard.getValue(r, c) == -1)
		{
			setMoving(false);
			return true;
		}
		
		setMoving(true);
		return false;
	}
	
	public boolean checkPacdot()
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
		
		if(myBoard.getValue(r, c) == -2)
			return true;
		
		return false;
	}
	
	public boolean checkPacpower()
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
		
		if(myBoard.getValue(r, c) == -3)
			return true;
		
		return false;
	}
	
	public void move()
	{
		myBoard.setValue(getPosR().get(0), getPosC().get(0), 0);
		
		int r = getPosR().get(0);
		int c = getPosC().get(0);
		
		if(!checkWallCollision(getDirection()))
		{
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
		}
		
		myBoard.setValue(getPosR().get(0), getPosC().get(0), 1);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
			this.direction = direction;			
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

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
}
