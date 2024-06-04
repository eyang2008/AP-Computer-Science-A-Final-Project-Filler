import java.awt.Color;

public class CapturedSquare extends Square {
  private int row;
  private int col;
  private Color color;
  private static int total = 0;
  
  public CapturedSquare(int r, int c, Color colour) {
    super(colour);
    total++;
    row = r;
    col = c;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public static int getTotal() {
    return total;
  }

  public static void resetTotal() {
    total = 0;
  }

}