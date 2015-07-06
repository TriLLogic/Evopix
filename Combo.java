import java.util.ArrayList;
import java.util.Random;

public class Combo {
  
  private Coordinate[] locations;
  private ArrayList<Integer> cellTypes;
    // 1 = yellow
    // 2 = purple
    // 3 = blue
    // 4 = red
  
  public Combo(Coordinate[] l) {
    locations = l;
    cellTypes = new ArrayList<Integer>();
    Random rng = new Random();
    for(int i = 0; i < l.length; i++)
      cellTypes.set(i, rng.nextInt(4));
  }
  
  public Coordinate[] getLocations() {
    return locations;
  }
  
  public ArrayList<Integer> getCellTypes() {
    return cellTypes;
  }

}
