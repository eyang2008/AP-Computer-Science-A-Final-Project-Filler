import java.awt.Graphics;
import java.awt.Color;


public class PaletteSquare extends Square {
    private boolean visible;
    private Color color;

    public PaletteSquare(Color colour) {
        super(colour);
        color = colour;
        visible = true;
    }


    @Override
    public void drawSquare(Graphics g, int x, int y) {
        // Draw one square filled with the color indicated by the object's instance variable at location (x, y).
        if (visible) {
            g.setColor(color);
            g.fillRect(x, y, 30, 30);
        }
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean setTo) {
        visible = setTo;
    }

}