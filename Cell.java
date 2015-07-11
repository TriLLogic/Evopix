public class Cell 
{
	//Attributes
	boolean powered;
	boolean controlled;
	Coordinate loc;
	Type type;
	int energyUsed;
	int iType;
	boolean usedInCombo = false;
	boolean beingDeleted = false;
	boolean nonPlayer;

	//Constructor
	public Cell(Boolean p, boolean c, Coordinate l, Type t, int eu, Boolean npc)
	{
		//Set attributes
		powered = p;
		controlled = c;
		loc = l;
		type = t;
		energyUsed = eu;
		nonPlayer = npc;
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
	
	public void setUsedInCombo(Boolean used)
	{
		usedInCombo = used;
		if(usedInCombo)
			energyUsed = 1;
		else
			energyUsed = 0;
	}
	
	@Override
	public String toString()
	{
		//Returns powered + controlled + iType + coordinates
		String preString = "";
		if(powered)
			preString += "p";
		else
			preString += "n";
		if(controlled)
			preString += "c";
		else
			preString += "n";
		return preString + iType + loc.toString();
	}
}
