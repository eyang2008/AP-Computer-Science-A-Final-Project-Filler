import java.awt.Color;
import java.util.ArrayList;

public class Game {
    private int currentPlayerTurn;
    private Square[][] grid;
    private Color[] colors;
    private PaletteSquare[] palette;
    private boolean startMenu, questionsPopUp;
    private boolean gameHappening, gameOverScreen;

    // ArrayLists of Square that keeps track of each player's captured squares.
    private ArrayList<CapturedSquare> player1CapturesSquares;
    private ArrayList<CapturedSquare> player2CapturesSquares;
    private ArrayList<CapturedSquare> player3CapturesSquares;
    private ArrayList<CapturedSquare> player4CapturesSquares;

    public Game() {
        gameHappening = false;
        gameOverScreen = false;
        resetGame();
    }

    public void resetGame() {
        gameHappening = true;
        gameOverScreen = false;
        grid = new Square[14][16]; // Instantiates a 12-14 2D array as the grid board that has a white boarder of squares.

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = new Square(Color.WHITE);
            }
        }
        Color blue = new Color(135, 206, 235);
        Color green = new Color(144, 238, 144);
        Color yellow = new Color(255, 244, 0);
        Color purple = new Color(106, 13, 173);
        Color black = new Color(0, 0, 0);
        Color red = new Color(186, 28, 28);
        Color pink = new Color(255, 179, 222);
        Color orange = new Color(250, 174, 97);

        colors = new Color[] { blue, green, yellow, purple, black, red, pink, orange }; // Creates an array of colors

        // Initialize palette variables
        palette = new PaletteSquare[8];
        int counter = 0;
        for (int i = 0; i < palette.length; i++) {
            palette[i] = new PaletteSquare(colors[counter]);
            counter++;
        }

        // Initialize each player's captured squares. (might need to move later)
        player1CapturesSquares = new ArrayList<>();
        player2CapturesSquares = new ArrayList<>();
        player3CapturesSquares = new ArrayList<>();
        player4CapturesSquares = new ArrayList<>();

        currentPlayerTurn = 1;
        // When ran, the game starts on the start menu with directions.
        startMenu = true;
        questionsPopUp = false;
        CapturedSquare.resetTotal();

        for (int r = 1; r < grid.length - 1; r++) {
            for (int c = 1; c < grid[r].length - 1; c++) {
                int randomIndex = (int) (Math.random() * colors.length);
                while (colors[randomIndex].equals(grid[r - 1][c].getColor()) ||
                        colors[randomIndex].equals(grid[r][c - 1].getColor()) ||
                        colors[randomIndex].equals(grid[r][c + 1].getColor()) ||
                        colors[randomIndex].equals(grid[r + 1][c].getColor())) {
                    randomIndex = (int) (Math.random() * colors.length);
                }
                grid[r][c].setColor(colors[randomIndex]);
            }
        }

        int indexOfStartColor;
        // Set all the corners of the grid to random colors, each a different color.
        // These represent the starting positions and colors of the players.
        indexOfStartColor = (int) (Math.random() * 8);
        while (colors[indexOfStartColor].equals(grid[1][2].getColor()) ||
                colors[indexOfStartColor].equals(grid[2][1].getColor())) {
            indexOfStartColor = (int) (Math.random() * 8);
        }
        grid[0][0].setColor(colors[indexOfStartColor]);
        indexOfStartColor = (int) (Math.random() * 8);
        while (colors[indexOfStartColor].equals(grid[0][0].getColor()) ||
                colors[indexOfStartColor].equals(grid[1][13].getColor()) ||
                colors[indexOfStartColor].equals(grid[2][14].getColor())) {
            indexOfStartColor = (int) (Math.random() * 8);
        }
        grid[0][15].setColor(colors[indexOfStartColor]);
        indexOfStartColor = (int) (Math.random() * 8);
        while (colors[indexOfStartColor].equals(grid[0][0].getColor()) ||
                colors[indexOfStartColor].equals(grid[0][15].getColor()) ||
                colors[indexOfStartColor].equals(grid[11][1].getColor()) ||
                colors[indexOfStartColor].equals(grid[12][2].getColor())) {
            indexOfStartColor = (int) (Math.random() * 8);
        }
        grid[13][0].setColor(colors[indexOfStartColor]);
        indexOfStartColor = (int) (Math.random() * 8);
        while ( // Ensures that no squares directly adjacent to one another are the same color.
        colors[indexOfStartColor].equals(grid[0][0].getColor()) ||
                colors[indexOfStartColor].equals(grid[0][15].getColor()) ||
                colors[indexOfStartColor].equals(grid[13][0].getColor()) ||
                colors[indexOfStartColor].equals(grid[11][14].getColor()) ||
                colors[indexOfStartColor].equals(grid[12][13].getColor())) {
            indexOfStartColor = (int) (Math.random() * 8);
        }
        grid[13][15].setColor(colors[indexOfStartColor]);

        // Set the corners of the board to the same color as each player at the start of
        // the game.
        grid[1][1].setColor(grid[0][0].getColor());
        grid[1][14].setColor(grid[0][15].getColor());
        grid[12][1].setColor(grid[13][0].getColor());
        grid[12][14].setColor(grid[13][15].getColor());

        // Initialize each player's captured squares.
        player1CapturesSquares.add(new CapturedSquare(1, 1, grid[1][1].getColor()));
        player2CapturesSquares.add(new CapturedSquare(1, 14, grid[1][14].getColor()));
        player3CapturesSquares.add(new CapturedSquare(12, 1, grid[12][1].getColor()));
        player4CapturesSquares.add(new CapturedSquare(12, 14, grid[12][14].getColor()));

        for (int i = 0; i < palette.length; i++)
        {
            if (palette[i].getColor().equals(player1CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player2CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player3CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player4CapturesSquares.get(0).getColor()))
                {
                    palette[i].setVisible(false);
            }
        }
    }




    public boolean isGameHappening() {
        return gameHappening;
    }

    public boolean onStartMenu() {
        return startMenu;
    }

    public void startGame() {
        // Moves the screen from start menu to the game panel.
        startMenu = false;
        questionsPopUp = false;
    }

    public boolean infoClicked() {
        return questionsPopUp;
    }

    public void directions() {
        questionsPopUp = true;
    }


    public Square[][] getGrid() {
        return grid;
    }

    public PaletteSquare[] getPalette() {
        return palette;
    }

    public boolean isGameOverScreen() {
        return gameOverScreen;
    }



    public void switchTurn() {
        currentPlayerTurn = (currentPlayerTurn % 4) + 1;
    }

    private ArrayList<CapturedSquare> getCurrentPlayerSquares() {
        switch (currentPlayerTurn) {
            case 1: return player1CapturesSquares;
            case 2: return player2CapturesSquares;
            case 3: return player3CapturesSquares;
            case 4: return player4CapturesSquares;
            default: return new ArrayList<>();
        }
    }       

    public int countSquares1(){
        int count = 0;
        for(int i = 0; i < player1CapturesSquares.size(); i++){
            count++;
        }
        return count;
    }

    public int countSquares2(){
        int count = 0;
        for(int i = 0; i < player2CapturesSquares.size(); i++){
            count++;
        }
        return count;
    }

    public int countSquares3(){
        int count = 0;
        for(int i = 0; i < player3CapturesSquares.size(); i++){
            count++;
        }
        return count;
    }

    public int countSquares4(){
        int count = 0;
        for(int i = 0; i < player4CapturesSquares.size(); i++){
            count++;
        }
        return count;
    }

    public void addCapturedSquare(int x, int y, Color selectedColor) {
        switch (currentPlayerTurn) {
            case 1:
                player1CapturesSquares.add(new CapturedSquare(x, y, selectedColor));
                break;
            case 2:
                player2CapturesSquares.add(new CapturedSquare(x, y, selectedColor));
                break;
            case 3:
                player3CapturesSquares.add(new CapturedSquare(x, y, selectedColor));
                break;
            case 4:
                player4CapturesSquares.add(new CapturedSquare(x, y, selectedColor));
                break;
            default:
                break;

        }
    }

    public void playerTurn(Color selectedColor) {
        // Update the captured squares with the selected color
        if (currentPlayerTurn == 1) {
            grid[0][0].setColor(selectedColor); // Changes the player's corner square
            for (CapturedSquare each : player1CapturesSquares) {
                each.setColor(selectedColor);
            }
        } else if (currentPlayerTurn == 2) {
            grid[0][15].setColor(selectedColor); // Changes the player's corner square
            for (CapturedSquare each : player2CapturesSquares) {
                each.setColor(selectedColor);
            }
        } else if (currentPlayerTurn == 3) {
            grid[13][0].setColor(selectedColor); // Changes the player's corner square
            for (CapturedSquare each : player3CapturesSquares) {
                each.setColor(selectedColor);
            }
        } else if (currentPlayerTurn == 4) {
            grid[13][15].setColor(selectedColor); // Changes the player's corner square
            for (CapturedSquare each : player4CapturesSquares) {
                each.setColor(selectedColor);
            }
        }

        // capturedSquares array is a copy of the current player's arraylist 
        // so that we don't need 4 long if-statements for each player.
        ArrayList<CapturedSquare> capturedSquares = getCurrentPlayerSquares();
        // Creating a new empty arraylist of CapturedSquare to add captured squares to
        // since we cannot append to the arraylist while iterating through it.
        // After iterating and finding all captured squares, we append all the CapturedSquare objects from
        // newlyCapturedSquares to the player's arraylist

        // Set all the colors to the currently captured squares to selected color before proceeding.
        for(CapturedSquare square : capturedSquares){
            grid[square.getRow()][square.getCol()].setColor(selectedColor);
            square.setColor(selectedColor);
        }
        ArrayList<CapturedSquare> newlyCapturedSquares = new ArrayList<>();    

        for (int i = 0; i < capturedSquares.size(); i++) {
            CapturedSquare square = capturedSquares.get(i);
            int x = square.getRow();
            int y = square.getCol();
            // Add squares to the player's captured squares if they are adjacent and same color as selected color.
            if (y + 1 < grid[0].length && grid[x][y + 1].getColor().equals(selectedColor) && !isCapturedByAnyPlayer(x, y + 1)) {
                addCapturedSquare(x, y + 1, selectedColor);
            }
            if (y - 1 >= 0 && grid[x][y - 1].getColor().equals(selectedColor) && !isCapturedByAnyPlayer(x, y - 1)) {
                addCapturedSquare(x, y - 1, selectedColor);
            }
            if (x + 1 < grid.length && grid[x + 1][y].getColor().equals(selectedColor) && !isCapturedByAnyPlayer(x + 1, y)) {
                addCapturedSquare(x + 1, y, selectedColor);
            }
            if (x - 1 >= 0 && grid[x - 1][y].getColor().equals(selectedColor) && !isCapturedByAnyPlayer(x - 1, y)) {
                addCapturedSquare(x - 1, y, selectedColor);
            } 
        }

        // Player turn depending on who the current player is.
        if (currentPlayerTurn == 1) 
        {
            player1CapturesSquares.addAll(newlyCapturedSquares);
            for (Square current : player1CapturesSquares) {
                current.setColor(selectedColor);
            }
            for (int i = 0; i < player1CapturesSquares.size(); i++) {
                if (player1CapturesSquares.get(i).getRow() >= 1 && player1CapturesSquares.get(i).getRow() < grid.length-1 &&
                player1CapturesSquares.get(i).getCol() >= 1 && player1CapturesSquares.get(i).getCol() < grid[0].length-1) {
                    grid[player1CapturesSquares.get(i).getRow()][player1CapturesSquares.get(i).getCol()].setColor(selectedColor);
                }
            } 
        } else if (currentPlayerTurn == 2)
        {
            player2CapturesSquares.addAll(newlyCapturedSquares);

            for (CapturedSquare current : player2CapturesSquares) {
                current.setColor(selectedColor);
            }
            for (int i = 0; i < player2CapturesSquares.size(); i++) {
                if (player2CapturesSquares.get(i).getRow() >= 1 && player2CapturesSquares.get(i).getRow() < grid.length-1 &&
                player2CapturesSquares.get(i).getCol() >= 1 && player2CapturesSquares.get(i).getCol() < grid[0].length-1) {
                    grid[player2CapturesSquares.get(i).getRow()][player2CapturesSquares.get(i).getCol()].setColor(selectedColor);
                }
            }             
        } else if (currentPlayerTurn == 3) 
        {
            player3CapturesSquares.addAll(newlyCapturedSquares);
            for (CapturedSquare current : player3CapturesSquares) {
                current.setColor(selectedColor);
            }
            for (int i = 0; i < player3CapturesSquares.size(); i++) {
                if (player3CapturesSquares.get(i).getRow() >= 1 && player3CapturesSquares.get(i).getRow() < grid.length-1 &&
                player3CapturesSquares.get(i).getCol() >= 1 && player3CapturesSquares.get(i).getCol() < grid[0].length-1) {
                    grid[player3CapturesSquares.get(i).getRow()][player3CapturesSquares.get(i).getCol()].setColor(selectedColor);
                }
            } 
        } else if (currentPlayerTurn == 4)
        {
            player4CapturesSquares.addAll(newlyCapturedSquares);
            for (CapturedSquare current : player4CapturesSquares) {
                current.setColor(selectedColor);
            }
            for (int i = 0; i < player4CapturesSquares.size(); i++) {
                if (player4CapturesSquares.get(i).getRow() >= 1 && player4CapturesSquares.get(i).getRow() < grid.length-1 &&
                player4CapturesSquares.get(i).getCol() >= 1 && player4CapturesSquares.get(i).getCol() < grid[0].length-1) {
                    grid[player4CapturesSquares.get(i).getRow()][player4CapturesSquares.get(i).getCol()].setColor(selectedColor);
                }
            } 
        }

        if (CapturedSquare.getTotal() == 168) {
            gameOverScreen = true;
            gameHappening = false;
        } else {
            // Increments player turn variable (unless player turn is 4, in which player turn returns back to 1).
            switchTurn();
            // Reset palette before the player selects a color.
            for (PaletteSquare color : palette) {
                color.setVisible(true);
            }
            // Check available colors and set so player can't choose the colors of the other players.
            for (int i = 0; i < palette.length; i++)
            {
            if (palette[i].getColor().equals(player1CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player2CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player3CapturesSquares.get(0).getColor()) ||
                palette[i].getColor().equals(player4CapturesSquares.get(0).getColor()))
                {
                    palette[i].setVisible(false);
                }
            }
        }
    }

        //------------------------------------------------------------------------------

    private boolean isCapturedByAnyPlayer(int x, int y) {
        return isCapturedByPlayer(player1CapturesSquares, x, y) || isCapturedByPlayer(player2CapturesSquares, x, y) || isCapturedByPlayer(player3CapturesSquares, x, y) || isCapturedByPlayer(player4CapturesSquares, x, y);
    }

    private boolean isCapturedByPlayer(ArrayList<CapturedSquare> playerSquares, int x, int y) {
        for (CapturedSquare square : playerSquares) {
            if (square.getRow() == x && square.getCol() == y) {
                return true;
            }
        }
        return false;
    }


    public String findWinner(){

        Color player1Final = player1CapturesSquares.get(0).getColor();
        Color player2Final = player2CapturesSquares.get(0).getColor();
        Color player3Final = player3CapturesSquares.get(0).getColor();
        Color player4Final = player4CapturesSquares.get(0).getColor();

        int finalNum1 = 0;
        int finalNum2 = 0;
        int finalNum3 = 0;
        int finalNum4 = 0;


        // iterate through the grid
        for(Square[] row : grid){
            for(Square square : row){
                if(square.getColor().equals(player1Final)){
                    finalNum1++;
                } else if(square.getColor().equals(player2Final)){
                    finalNum2++;
                } else if(square.getColor().equals(player3Final)){
                    finalNum3++;
                } else if(square.getColor().equals(player4Final)){
                    finalNum4++;
                }
            }
        }

        // determine max number of captured squares
        int maxCaptured = Math.max(Math.max(finalNum1, finalNum2), Math.max(finalNum3, finalNum4));

        ArrayList<String> winners = new ArrayList<>();


        // collect players w/ max number of captured squares
        if(finalNum1 == maxCaptured){
            winners.add("Player 1");
        } 
        if(finalNum2 == maxCaptured){
            winners.add("Player 2");
        }
        if(finalNum3 == maxCaptured){
            winners.add("Player 3");
        }
        if(finalNum4 == maxCaptured){
            winners.add("Player 4");
        }

        // check for ties (if tie, randomly select winner)
        if (winners.size() > 1) {
            int randomWinner = (int)(Math.random()*winners.size());
            String winner = winners.get(randomWinner) + " wins!";
            return winner;
        } else {
            return winners.get(0) + " wins!";
        }
    }    


    public int getCurrentPlayer() {
        return currentPlayerTurn;
    }

    public int getPlayer1Score()
    {
        return player1CapturesSquares.size();
    }
    public int getPlayer2Score()
    {
        return player2CapturesSquares.size();
    }
    public int getPlayer3Score()
    {
        return player3CapturesSquares.size();
    }
    public int getPlayer4Score()
    {
        return player4CapturesSquares.size();
    }

    // Skips to game over screen, for testing purposes.
    public void setGameToOver() {
        gameHappening = false;
        gameOverScreen = true;
        startMenu = false;
    }
}