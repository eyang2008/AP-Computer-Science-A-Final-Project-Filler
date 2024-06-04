import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

// For the images
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// For the mouse clicks
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// For the key clicks
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

// For the sounds
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Screen extends JPanel implements MouseListener, KeyListener {
    private Square[][] grid;
    private PaletteSquare[] palette;
    private int paletteX, paletteY, colorHeight, colorWidth;
    private BufferedImage startMenuBackground, startButton, fillerLogo, questionIcon, gameScreenBackground, marker, newgame;
    private Font instructionsFont;
    private Color selectedColor;
    private Game game;       
    private Confetti[] confetti;
    private Font screenFont;
    private String winnerText;

    public Screen() {
        // Create game backend object. This object acts as the game manager.  
        // It tracks and handles the game calculations, positioning, points, and other game mechanics.
        game = new Game();

        confetti = new Confetti[100];   // For animation on the end screen.

        for(int i = 0; i< confetti.length; i++){
            confetti[i] = new Confetti();
        }

        grid = game.getGrid();  // To get the grid from the Game class.
        // Instantiates the instance variables for the dimensions of the color palette.
        paletteX = 300;
        paletteY = 550;
        colorHeight = 30;
        colorWidth = 30;

        palette = game.getPalette();

        // Images !!
        try {
            startMenuBackground = ImageIO.read(new File("backdrop.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            startButton = ImageIO.read(new File("start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fillerLogo = ImageIO.read(new File("logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            questionIcon = ImageIO.read(new File("question.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            gameScreenBackground = ImageIO.read(new File("gamebackdrop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            marker = ImageIO.read(new File("playermarker.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            newgame = ImageIO.read(new File("new game.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        instructionsFont = new Font("Franklin Gothic Medium", Font.PLAIN, 10);

        selectedColor = null;
        winnerText = "";

        addMouseListener(this);
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        // Sets the size of the panel
        return new Dimension(800, 600);
    }

    // Plays the sound for when the player starts the game.
    public void playStartSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("startgame.wav").getAbsoluteFile()));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    // Plays the sound for when the player selects a color from the palette.
    public void playPaintSound(){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("paint.wav").getAbsoluteFile()));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    // Plays the sound for when the game is finished and the end game screen is shown.
    public void playFinishedSound() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("endgame.wav").getAbsoluteFile()));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game.onStartMenu()) {
            g.drawImage(startMenuBackground, -175, -70, null);
            g.drawImage(startButton, 300, 425, 200, 150, null);
            g.drawImage(fillerLogo, 140, 10, 500, 500, null);
            g.setColor(new Color(244, 123, 174));
            g.fillRoundRect(740, 540, 50, 50, 20, 20);
            g.drawImage(questionIcon, 750, 550, 30, 30, null);
            if (game.infoClicked()) {
                g.setColor(new Color(242, 227, 230));
                g.fillRect(550, 300, 243, 200);
                g.setColor(Color.BLACK);
                g.setFont(instructionsFont);
                g.drawString("Rules:", 553, 315);
                g.drawString("1. Each player is assigned a corner tile", 553, 330);
                g.drawString("    at the start of the game.", 553, 345);
                g.drawString("2. Players take turns filling their tiles", 553, 360);
                g.drawString("    one of the 8 colors in an attempt to", 553, 375);
                g.drawString("    capture adjacent tiles of the same color", 553, 390);
                g.drawString("3. You are not allowed to change the color of", 553, 405);
                g.drawString("    your tiles into the color of your opponents tiles", 553, 420);
                g.drawString("4. The game ends when there are no more tiles", 553, 435);
                g.drawString("     to occupy", 553, 450);
                g.drawString("Player who managed to capture the most", 570, 470);
                g.drawString("     tiles wins!!!", 580, 485);
            }

            // if(game.replay()){
            //     resetGame() = true;
            // }
        } else if (game.isGameHappening() && !game.isGameOverScreen()) {
            g.drawImage(gameScreenBackground, -200, -450, null);
            // Draws the grid
            int startRow = 100;
            int startColumn = 20;
            for (int row = 0; row < grid.length; row++) {
                for (int column = 0; column < grid[row].length; column++) {
                    grid[row][column].drawSquare(g, startRow, startColumn);
                    startRow += 36;
                }
                startRow = 100;
                startColumn += 36;
            }
            // Draws the color palette.
            int x = paletteX;
            for (int i = 0; i < palette.length; i++) {
                palette[i].drawSquare(g, x, paletteY);
                x += colorWidth;
            }

            // Draw player numbers on corner tiles
            g.setColor(Color.WHITE);
            g.drawString("1", 115, 40);
            g.drawString("2", 650, 40);
            g.drawString("3", 115, 515);
            g.drawString("4", 650, 515);

            if (game.getCurrentPlayer() == 1) {
                g.drawImage(marker, 60, 20, 30, 30, null);
            } else if (game.getCurrentPlayer() == 2) {
                g.drawImage(marker, 700, 20, 30, 30,null);
            } else if (game.getCurrentPlayer() == 3) {
                g.drawImage(marker, 60, 500, 30, 30,null);
            } else if (game.getCurrentPlayer() == 4) {
                g.drawImage(marker, 700, 500, 30, 30,null);
            }

            // Draw the player scoreboard

            g.setColor(new Color(238, 217, 255));
            g.fillRect(0, 80, 100, 100);
            g.setColor(Color.BLACK);
            g.drawString("Player 1: " + game.getPlayer1Score(), 15, 100);
            g.drawString("Player 2: " + game.getPlayer2Score(), 15, 120);
            g.drawString("Player 3: " + game.getPlayer3Score(), 15, 140);
            g.drawString("Player 4: " + game.getPlayer4Score(), 15, 160);
        } else if (game.isGameOverScreen() && !game.isGameHappening()) {
            screenFont = new Font("Arial", Font.BOLD, 40);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 800, 600);
            for (Confetti each : confetti) {
                each.drawConfetti(g);
            }
            g.setColor(Color.BLACK);
            g.setFont(screenFont);
            g.drawString(winnerText, 275, 200);
            g.setColor(new Color(244, 123, 174));
            g.fillRect(200, 400, 400, 100);
            g.setColor(new Color(243, 227, 230));
            g.fillRect(201, 401, 398, 98);
            g.drawImage(newgame, 204, 294, 395, 300, null);
        }

    }

    // Detects where the mouse clicks.
    public void mousePressed(MouseEvent e) {
        // Print location of x and y.
        if (game.onStartMenu()) {
            if (e.getX() >= 300 && e.getX() <= 500 && e.getY() >= 425 && e.getY() <= 575) {
                playStartSound();
                game.startGame();
            }

            // Checks if User pressed the question button (bottom right corner on the start screen).
            if (!game.infoClicked()) {
                if (e.getX() >= 740 && e.getX() <= 790 && e.getY() >= 540 && e.getY() < 590) {
                    game.directions();
                }
            }

        } else if (game.isGameHappening() && !game.isGameOverScreen()) {
            // Checks if user clicks a color from the color palette.
            if (e.getY() >= paletteY && e.getY() <= paletteY + colorHeight) {
                int colorIndex = (e.getX() - paletteX) / colorWidth;
                if (colorIndex >= 0 && colorIndex < palette.length && palette[colorIndex].isVisible()) {
                    playPaintSound();
                    Color selectedColor = palette[colorIndex].getColor();
                    game.playerTurn(selectedColor);
                }       
            }
            if (CapturedSquare.getTotal() == 168) {
                // Play the end screens sound once the game is finished.
                // The game is finished once CapturedSquare's static count variable has reached 168,
                // which is the total amount of squares on the board.
                winnerText = game.findWinner();
                game.setGameToOver();
                playFinishedSound();
            }
        } else if (!game.isGameHappening() && game.isGameOverScreen()) {
            // Check if player has pressed restart button
            if (e.getX() >= 200 && e.getX() <= 600 && e.getY() >= 400 && e.getY() <= 500) {
                game.resetGame();
                grid = game.getGrid();
                palette = game.getPalette();
            }
        }
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e){
        // For a cheat key so we can get to the end of the game.
        if (e.getKeyCode() == 79) {		
            playFinishedSound();
            game.setGameToOver();

        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    // For animation
    public void animate(){
        while(true){
            try {
                Thread.sleep(10);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if(!game.isGameHappening() && game.isGameOverScreen()){
                for(Confetti each : confetti){
                    each.moveDown();
                  }
            }
            repaint();
        }
    }

}
