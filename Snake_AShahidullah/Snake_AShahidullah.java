import java.util.ArrayList;

public class Snake_AShahidullah
{
	public static Board_AShahidullah myBoard;
	private int posRow;
	private int posCol;
	private ArrayList<Integer> posR = new ArrayList<Integer>(); // keeps track of the row position of snake
	private ArrayList<Integer> posC = new ArrayList<Integer>(); // keeps track of the column position of snake
	private int color;
	private int dir; // 0 is up; 1 is right; 2 is down; 3 is left
	private int length; // length of snake
	public boolean increaseLength; //boolean use to indicate whether to spawn fruit in SnakeGame_AShahidullah class
	
	public Snake_AShahidullah(int r, int c, int cl, int d, Board_AShahidullah b)
	{
		posRow = r;
		posCol = c;
		// following add initial positions to ArrayList
		posR.add(r);
		posC.add(c);
		posR.add(r-1);
		posR.add(r-2);
		posC.add(c-1);
		posC.add(c-2);
		// end added positions
		color = cl;
		dir = d;
		length = 3;
		myBoard = b;
		
		for(int i = 0; i < length; i++)
			myBoard.setValue(posR.get(i), posC.get(i), color);
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public void setDir(int d)
	{
		dir = d;
	}
	
	public boolean checkCollision()
	{
		int r = posRow;
		int c = posCol;
		
		switch(dir)
		{
		case 0:
			c--;
			break;
		case 1:
			r++;
			break;
		case 2:
			c++;
			break;
		case 3:
			r--;
			break;
		}
		
		if(r < 0 || c < 0 || r >= myBoard.NUM_ROW || c >= myBoard.NUM_COL || (myBoard.getValue(r, c) > 0))
			return true;
		
		return false;
	}
	
	public void move()
	{
		for(int i = 0; i < length; i++) // sets snake to 0
			myBoard.setValue(posR.get(i), posC.get(i), 0);
		
		switch(dir)
		{
		case 0:
			posCol--;
			break;
		case 1:
			posRow++;
			break;
		case 2:
			posCol++;
			break;
		case 3:
			posRow--;
			break;
		}
		
		if(myBoard.getValue(posRow, posCol) != -1) // removes tail if the next space is not a fruit
		{
			posR.remove(0);
			posC.remove(0);
		}
		
		grow();
		posR.add(posRow);
		posC.add(posCol);
		
		for(int j = 0; j < length; j++) // resets snake
			myBoard.setValue(posR.get(j), posC.get(j), color);
	}
	
	public void grow()
	{
		increaseLength = false;
		if(myBoard.getValue(posRow, posCol) == -1) // increases length only if a fruit has been eaten
		{
			increaseLength = true;
			length++;
		}
	}
	
}
