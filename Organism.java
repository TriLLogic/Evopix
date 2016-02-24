import java.util.ArrayList;

public class Organism
{
	ArrayList <Cell> cells = new ArrayList<Cell>();
	int glucose;
	Coordinate location;
	boolean moves;

	public Organism(String template, Coordinate loc) 
	{
		
		location = loc;

		int x = location.x;
		int y = location.y;
		
		
		switch(template)
		{
		case "bacterium":
			cells.add(new Cell(true, true, location, Type.BRAIN, 0, true));

			cells.add(new Cell(true, true, new Coordinate(x+1,y), Type.FLESH, 0, true));
			cells.add(new Cell(true, true, new Coordinate(x-1,y), Type.FLESH, 0, true));
			cells.add(new Cell(true, true, new Coordinate(x-2,y), Type.PHOTOSYNTHESIS, 0, true));
			break;
			
		case "phytoplankton":
			cells.add(new Cell(true, true, location, Type.BRAIN, 0, true));

			cells.add(new Cell(true, true, new Coordinate(x-1,y+1), Type.FLESH, 0, true));
			cells.add(new Cell(true, true, new Coordinate(x-1,y), Type.PHOTOSYNTHESIS, 0, true));
			cells.add(new Cell(true, true, new Coordinate(x,y+1), Type.PHOTOSYNTHESIS, 0, true));
			break;
			
		default: 		
			cells.add(new Cell(true, true, location, Type.BRAIN, 0, true));
			break;

		}
	}
}
