public class Organism() 
{
	private List <Cell> cells;
	private int glucose;
	private Coordinate location;
	
	public Organism(String template, Coordinate loc) 
	{
		location = loc;
		
		if(template.equals("bacterium") 
		{
			int x = location.x;
			int y = location.y;
			
			cells.add(new Cell(true, true, location, TYPE.BRAIN, true);
			
			cells.add(new Cell(true, true, new Coordinate(x+1,y), TYPE.FLESH, true);
			cells.add(new Cell(true, true, new Coordinate(x-1,y), TYPE.FLESH, true);
			
			cells.add(new Cell(true, true, new Coordinate(x-2,y), TYPE.PHOTOSYNTHESIS, true);
		}
	}
	
}
