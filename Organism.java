import java.util.ArrayList;

public class Organism
{
	private ArrayList <Cell> cells;
	private int glucose;
	private Coordinate location;
	
	public Organism(String template, Coordinate loc) 
	{
		location = loc;
		
		if(template.equals("bacterium"))
		{
			int x = location.x;
			int y = location.y;
			
			cells.add(new Cell(true, true, location, Type.BRAIN, 0, true));
			
			cells.add(new Cell(true, true, new Coordinate(x+1,y), Type.FLESH, 0, true));
			cells.add(new Cell(true, true, new Coordinate(x-1,y), Type.FLESH, 0, true));
			
			cells.add(new Cell(true, true, new Coordinate(x-2,y), Type.PHOTOSYNTHESIS, 0, true));
		}
	}
}
