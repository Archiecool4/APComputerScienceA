
public class Tron_AShahidullah
{
	public static Board_AShahidullah myBoard;
	private int posRow;
	private int posCol;
	private int color;
	private int dir; // 0 is up; 1 is right; 2 is down; 3 is left
	
	public Tron_AShahidullah(int r, int c, int cl, int d, Board_AShahidullah b)
	{
		posRow = r;
		posCol = c;
		color = cl;
		dir = d;
		myBoard = b;
		myBoard.setValue(posRow, posCol, color);
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
		
		if(r < 0 || c < 0 || r >= myBoard.NUM_ROW || c >= myBoard.NUM_COL || myBoard.getValue(r, c) != 0)
			return true;
		
		return false;
	}
	
	public void move()
	{
		myBoard.setValue(posRow, posCol, color - 1);
		
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
		
		myBoard.setValue(posRow, posCol, color);
	}
	
}
