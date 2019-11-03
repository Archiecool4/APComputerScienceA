
public class Pacdot 
{

	public int posRow;
	public int posCol;
	public static Board myBoard;
	
	public Pacdot(int r, int c, Board b)
	{
		posRow = r;
		posCol = c;
		myBoard = b;
		myBoard.setValue(posRow, posCol, -2);
	}
	
	public void reset()
	{
		myBoard.setValue(posRow, posCol, -2);
	}
	
}
