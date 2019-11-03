
public class Pacpower 
{

	public int posRow;
	public int posCol;
	public static Board myBoard;
	
	public Pacpower(int r, int c, Board b)
	{
		posRow = r;
		posCol = c;
		myBoard = b;
		myBoard.setValue(posRow, posCol, -3);
	}
	
	public void reset()
	{
		myBoard.setValue(posRow, posCol, -3);
	}
	
}
