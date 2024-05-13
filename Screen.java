import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

import java.io.IOException;

public class Screen extends JPanel implements MouseListener{
    private Square[][] grid;
    private Color[] colors;

    public Screen() {
        grid = new Square[12][10];  // Instantiates a 12-10 2D array as the grid board
        colors = new Color[8];      // Creates an array of colors for the colors

        // Colors !!
        colors[0] = null;

        addMouseListener(this);
    }

    @Override
	public Dimension getPreferredSize() {
		//Sets the size of the panel
		return new Dimension(800,600);
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
        
    }

    // Detects where the mouse clicks.
    public void mousePressed(MouseEvent e) {
        // Print location of x and y.
        System.out.println("X: " + e.getX() + ", Y: " + e.getY());

        repaint();
    }


    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}



}
