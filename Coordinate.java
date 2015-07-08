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
		//Outputs coordinates in the form +00+00, or -73+09
		String ppString = "";
		if(x >= 0)
			ppString += "+";
		else
			ppString += "-";
		if(x >= 10 || x <= -10)
			ppString += Math.abs(x);
		else
			ppString += "0" + Math.abs(x);
		if(y >= 0)
			ppString += "+";
		else
			ppString += "-";
		if(y >= 10 || y <= -10)
			ppString += Math.abs(y);
		else
			ppString += "0" + Math.abs(y);
		return ppString;
	}
}
