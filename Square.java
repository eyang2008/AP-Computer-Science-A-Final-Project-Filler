import java.awt.Graphics;
import java.awt.Color;

public class Square {
    private Color color;
    private int x, y;

    // Constructor
    public Square(Color color) {
        this.color = color;
    }

    public Square(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void drawSquare(Graphics g, int x, int y) {
        // Draw one square filled with the color indicated by the object's instance variable at location (x, y).
        g.setColor(color);
        g.fillRect(x, y, 40, 40);
        this.x = x;
        this.y = y;
    }

    public void setColor(Color selectedColor) {
        // Sets the Color instance variable to a new color.
        this.color = selectedColor;
    }

    public void returnColor() {
        System.out.println(color);
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}