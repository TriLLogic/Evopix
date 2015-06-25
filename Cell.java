public class Cell 
{
	//Attributes
	Boolean powered;
	Boolean controlled;
	Coordinate loc;
	Type type;

	//Constructor
	public Cell(Boolean p, Boolean c, Coordinate l, Type t)
	{
		//Set attributes
		powered = p;
		controlled = c;
		loc = l;
		type = t;
	}
}
