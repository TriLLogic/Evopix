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
		String tempString = "" + x, xString = "", yString = "";
		switch(tempString.length) {
		case 1: xString += "0";
		case 2: xString += "0";
		case 3: xString += "" + x;
		}
		tempString = "" + y;
		switch(tempString.length) {
		case 1: yString += "0";
		case 2: yString += "0";
		case 3: yString += "" + y;
		}
		return "(" + xString + "," + yString + ")";
	}
}
