public class Coordinate
{
	//Attributes
	int x;
	int y;
	
	//Constructor
	public Coordinate(int xloc, int yloc)
	{
		//Set attributes
		x = xloc;
		y = yloc;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
