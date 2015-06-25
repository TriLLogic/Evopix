public class Cell 
{
	//Attributes
	Boolean powered;
	Boolean controlled;
	Coordinate loc;
	Type type;
	int iType;

	//Constructor
	public Cell(Boolean p, Boolean c, Coordinate l, Type t)
	{
		//Set attributes
		powered = p;
		controlled = c;
		loc = l;
		type = t;
		switch(type)
		{
		case PHOTOSYNTHESIS: iType = 0; break;
		case BRAIN: iType = 1; break;
		case SHELL: iType = 2; break;
		case FLESH: iType = 3; break;
		case YELLOW: iType = 4; break;
		case PURPLE: iType = 5; break;
		case BLUE: iType = 6; break;
		case RED: iType = 7; break;
		}
	}
}
