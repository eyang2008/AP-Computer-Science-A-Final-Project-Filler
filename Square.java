import java.awt.Graphics;
import java.awt.Color;

public class Square {
    private Color color;

    // Constructor
    public Square(Color color) {
        this.color = color;
    }

    public void drawSquare(Graphics g, int x, int y) {
        // Draw one square filled with the color indicated by the object's instance variable at location (x, y).
        
    }

    public void setColor(Color selectedColor) {
        // Sets the Color instance variable to a new color.
        this.color = selectedColor;
    }

    public void returnColor() {
        System.out.println(color);
    }


}