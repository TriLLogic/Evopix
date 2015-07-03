import java.util.random;

public class Combo {
  
  private Coordinate[] locations;
  private int[] cellTypes;
    // 1 = yellow
    // 2 = purple
    // 3 = blue
    // 4 = red
  
  public Combo(Coordinate[] l) {
    locations = l;
  }
  
  public generate() {
    Random rng = new Random();
    for(int i = 0; i < l.length; i++)
      cellTypes[i] = rng.nextInt(4);
  }

}
