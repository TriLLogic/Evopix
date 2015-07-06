import java.util.random;
import java.util.ArrayList;

public class Combo {
  
  private Coordinate[] locations;
  private List<Integer> cellTypes;
    // 1 = yellow
    // 2 = purple
    // 3 = blue
    // 4 = red
  
  public Combo(Coordinate[] l) {
    locations = l;
    cellTypes = new ArrayList<Integer>();
    
  }
  
  public generate() {
    Random rng = new Random();
    for(int i = 0; i < l.length; i++)
      cellTypes.set(i, rng.nextInt(4));
  }
  
  public Coordinate[] getLocations() {
    return locations;
  }
  
  public List<Integer> getCellTypes() {
    return cellTypes;
  }

}
