import java.awt.Color;
import java.awt.Graphics;

// This class creates and moves one confetti
public class Confetti{

  private int x, y;
  private Color[] color;
  private Color set;

  public Confetti(){
    //randomly generate x and y
    x = (int)(Math.random()*799);
    y = (int)(Math.random()*596);

    Color blue = new Color(135, 206, 235);
    Color green = new Color(144, 238, 144);
    Color yellow = new Color(255, 244, 0);
    Color purple = new Color(106, 13, 173);
    Color black = new Color(0, 0, 0);
    Color red = new Color(186, 28, 28);
    Color pink = new Color(255, 179, 222);
    Color orange = new Color(250, 174, 97);

     color = new Color[] { blue, green, yellow, purple, black, red, pink, orange }; // Creates an array of colors


     set = color[(int) (Math.random() * color.length)];
  }

  // draw one confetti
  public void drawConfetti(Graphics g) {
    g.setColor(set);
    g.fillOval(x, y, 4, 4);
  }

  // animate a confetti
  public void moveDown() {
    y++;
    if(y>600){
      y = -5;
      x = (int)(Math.random()*798);
    }
  }
}