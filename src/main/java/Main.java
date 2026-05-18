import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import firebase.FirebaseClient;
import firebase.OnlineMove;

import org.fusesource.jansi.AnsiConsole;

public class Main {
    public static void main(String[] args) {

        System.out.println("MAIN STARTED");

        AnsiConsole.systemInstall();
        Scanner sc = new Scanner(System.in);
//        int size = 10;

        for (int i=0; i<20; i++){
            testFirebaseConnection(i+1);
        }

        int size = getGridSizeFromUser(sc);
        int playerCount = getPlayerCountFromUser(sc);

        List<Player> playerList =  createPlayersAndAssignColors(playerCount, sc);

        Cell[][] grid = getDefaultGrid(size);
        clearScreen();
        printGrid(grid, size);
        sleep(100);

        int currentPlayerIndex = 0;
        int totalPlayers = playerList.size();
        boolean isGameOver = false;
        int turnCount = 0;
        Set<Integer> eliminatedPlayers = new HashSet<>();

        while(!isGameOver) {

            Player currentPlayer = playerList.get(currentPlayerIndex);

            // After the first full round, check if current player has any cells
            if (turnCount >= totalPlayers) {
                if (getPlayerCellCount(grid, size, currentPlayer) == 0) {
                    if (eliminatedPlayers.add(currentPlayer.getId())) {
                        System.out.println("Player " + (currentPlayer.getId() + 1) + " is eliminated!");
                        sleep(100);
                    }
                    currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
                    continue;
                }
            }

            Move move = getMoveFromUser(sc, size, currentPlayer, grid);

            boolean[] gameOver = {false};
            incrementValue(grid, move.getRowPos(), move.getColPos(), size, currentPlayer, turnCount >= totalPlayers, playerList, gameOver);

            if (gameOver[0]) {
                isGameOver = true;
                clearScreen();
                printGrid(grid, size);
                printTextWithColor("\nPlayer " + (currentPlayer.getId() + 1) + " wins the game!\n\n", currentPlayer.getTextColor());
                break;
            }

            turnCount++;
            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;

        }
    }

    private static void testFirebaseConnection(int num) {

        System.out.println("Testing Firebase connection...");

        OnlineMove onlineMove = new OnlineMove("player1", 1+num, 2+num, System.currentTimeMillis());

        FirebaseClient.pushMove(
                "test-game",
                onlineMove
        );

        sleep(2000);
    }

    private static List<Player> createPlayersAndAssignColors(int playerCount, Scanner sc) {
        List<Player> playerList = new ArrayList<>();
        List<TextColor> availableColorsList = getAvailableColorsList();

        for(int i=0; i < playerCount; i++) {
            Player player = new Player();
            player.setId(i);
            TextColor textColor = getColorFromPlayer(player.getId(), sc, availableColorsList);
            player.setTextColor(textColor);
            playerList.add(player);
        }

        return playerList;

    }

    private static List<TextColor> getAvailableColorsList() {
        return new ArrayList<>(List.of(
                TextColor.RED,
                TextColor.BLUE,
                TextColor.GREEN,
                TextColor.YELLOW,
                TextColor.CYAN,
                TextColor.MAGENTA
        ));
    }

    private static void incrementValue(Cell[][] grid, int rowPos, int colPos, int size, Player currentPlayer, boolean checkGameOver, List<Player> playerList, boolean[] gameOver) {
        if (gameOver[0]) return;

        Cell currentCell = grid[rowPos][colPos];

        currentCell.setValue(currentCell.getValue() + 1);
        currentCell.setPlayer(currentPlayer);
        clearScreen();
        printGrid(grid, size);
        sleep(100);

        // Check if game is over mid-recursion
        if (checkGameOver) {
            int playersAlive = 0;
            for (Player p : playerList) {
                if (getPlayerCellCount(grid, size, p) > 0) {
                    playersAlive++;
                }
            }
            if (playersAlive == 1) {
                gameOver[0] = true;
                return;
            }
        }

        CellType cellType = getCellType(rowPos, colPos, size);
        if (currentCell.getValue() >= cellType.getCapacity()) {
            currentCell.setValue(0);
            currentCell.setPlayer(null);

            if (isBottomExists(rowPos, size)) {
                incrementValue(grid, rowPos+1, colPos, size, currentPlayer, checkGameOver, playerList, gameOver);
            }
            if (!gameOver[0] && isTopExists(rowPos)) {
                incrementValue(grid, rowPos-1, colPos, size, currentPlayer, checkGameOver, playerList, gameOver);
            }
            if (!gameOver[0] && isLeftExists(colPos)) {
                incrementValue(grid, rowPos, colPos-1, size, currentPlayer, checkGameOver, playerList, gameOver);
            }
            if (!gameOver[0] && isRightExists(colPos, size)) {
                incrementValue(grid, rowPos, colPos+1, size, currentPlayer, checkGameOver, playerList, gameOver);
            }
        }

    }

    private static int getPlayerCellCount(Cell[][] grid, int size, Player player) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (player.equals(grid[i][j].getPlayer())) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isRightExists(int colPos, int size) {
        return colPos < size - 1;
    }

    private static boolean isLeftExists(int colPos) {
        return colPos > 0;
    }

    private static boolean isBottomExists(int rowPos, int size) {
        return rowPos < size - 1;
    }

    private static boolean isTopExists(int rowPos) {
        return rowPos > 0;
    }

    private static CellType getCellType(int rowPos, int colPos, int size) {
        if (rowPos > 0 && rowPos < size-1 && colPos > 0 && colPos < size-1) {
            return CellType.MIDDLE;
        }
        if ((rowPos == 0 && colPos == 0) ||
                (rowPos == size-1 && colPos == size-1) ||
                (rowPos == 0 && colPos == size-1) ||
                (rowPos == size-1 && colPos == 0)) {
            return CellType.CORNER;
        }
        return CellType.EDGE;
    }

    private static TextColor getColorFromPlayer(int i, Scanner sc, List<TextColor> availableColorsList) {
        boolean isValidInput = false;
        int colorNumber;
        TextColor textColor = null;

        while (!isValidInput) {
            try {
                System.out.println("Select the Color for the Player: " + (i+1) + "[Enter the Number attached to the color]");

                for (int j = 0; j < availableColorsList.size(); j++) {
                    TextColor color = availableColorsList.get(j);
                    printTextWithColor("[" + (j+1) + "] " + color.name() + "\n", color);
                }
                System.out.println();

                colorNumber = sc.nextInt();
                if (colorNumber >= 1 && colorNumber <= availableColorsList.size()) {
                    colorNumber--;
                    textColor = availableColorsList.get(colorNumber);
                    String textToPrintWithColor = "Player [" + (i+1) + "] selected the color: " + textColor + "\n\n";
                    printTextWithColor(textToPrintWithColor, textColor);
                    availableColorsList.remove(colorNumber);
                    isValidInput = true;
                } else {
                    System.err.println("Wrong input! Please enter numbers only in the allowed range!");
                }
            } catch (InputMismatchException e) {
                System.err.println("Wrong input! Please enter only integer numbers!");
                sc.nextLine();
            }
        }

        return textColor;
    }

    private static Move getMoveFromUser(Scanner sc, int size, Player currentPlayer, Cell[][] grid) {
        Move move = new Move();
        int r = 0;
        int c = 0;

        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Enter the move for ");
                printTextWithColor("Player: " + (currentPlayer.getId() + 1), currentPlayer.getTextColor());
                System.out.print(" - row and column positions: ");
                r = sc.nextInt();
                c = sc.nextInt();
                if (r > 0 && c > 0 && r <= size && c <= size) {
                    if (currentPlayer.equals(grid[r-1][c-1].getPlayer()) || grid[r-1][c-1].getPlayer() == null) {
                        isValidInput = true;
                    } else {
                        System.err.println("Wrong input! Please enter position occupied by you or which is empty!");
                    }
                } else {
                    System.err.println("Wrong input! Please enter numbers within the grid!");
                }
            } catch (InputMismatchException e) {
                System.err.println("Wrong input! Please enter only integer numbers!");
                sc.nextLine();
            }
        }

        move.setRowPos(r-1);
        move.setColPos(c-1);
        return move;
    }

    private static int getPlayerCountFromUser(Scanner sc) {
        boolean isValidInput = false;
        int count = 0;

        while (!isValidInput) {
            try {
                System.out.println("Enter the Number of Players [Allowed Range: 2 - 6]: ");
                count = sc.nextInt();
                if (count >= 2 && count <= 6) {
                    System.out.println("Number of Players selected: " + count);
                    isValidInput = true;
                } else {
                    System.err.println("Wrong input! Please enter numbers only in the allowed range!");
                }
            } catch (InputMismatchException e) {
                System.err.println("Wrong input! Please enter only integer numbers!");
                sc.nextLine();
            }
        }

        return count;
    }

    private static int getGridSizeFromUser(Scanner sc) {
        boolean isValidInput = false;
        int size = 0;

        while (!isValidInput) {
            try {
                System.out.println("Enter the size of the Grid [Allowed Range: 4 - 15]: ");
                size = sc.nextInt();
                if (size >= 4 && size <= 15) {
                    System.out.println("Size of the grid selected: " + size);
                    isValidInput = true;
                } else {
                    System.err.println("Wrong input! Please enter numbers only in the allowed range!");
                }
            } catch (InputMismatchException e) {
                System.err.println("Wrong input! Please enter only integer numbers!");
                sc.nextLine();
            }
        }

        return size;
    }

    private static Cell[][] getDefaultGrid(int size) {

        Cell[][] grid = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell();
                grid[i][j].setValue(0);
            }
        }

        return grid;

    }

    private static void printGrid(Cell[][] grid, int size) {
        printTopRowWithBorder(size);
        printMainGrid(grid, size);
        printBottomBorder(size);
    }

    private static void printTopRowWithBorder(int size) {
        System.out.print("     ");
        for (int k = 1; k <= size; k++) {
            System.out.print(printNum(k) + " ");
        }
        System.out.println();
        System.out.print("   ┌─");
        for (int k = 1; k <= size; k++) {
            System.out.print("───");
        }
        System.out.print("─┐");
        System.out.println();
    }

    private static void printMainGrid(Cell[][] grid, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(printNum(i+1) + " │ ");
            for (int j = 0; j < size; j++) {
                int cellValue = grid[i][j].getValue();
                Player currentPlayer = grid[i][j].getPlayer();
                TextColor textColor;
                if (currentPlayer == null) {
                    textColor = TextColor.WHITE;
                } else {
                    textColor = currentPlayer.getTextColor();
                }
                String textToPrint = printNum(cellValue) + " ";
                printTextWithColor(textToPrint, textColor);

                // NOTE: to check the position of each element
//                System.out.print(i + String.valueOf(j) + " ");
            }
            System.out.println(" │ ");
        }
    }

    private static void printTextWithColor(String text, TextColor textColor) {
        String textColorCode = textColor.getColorCode();
        String textResetColorCode = TextColor.RESET.getColorCode();
        System.out.print(textColorCode + text + textResetColorCode);
    }

    private static void printBottomBorder(int size) {
        System.out.print("   └─");
        for (int k = 1; k <= size; k++) {
            System.out.print("───");
        }
        System.out.print("─┘");
        System.out.println();
    }

    private static String printNum(int num) {
        if (num < 10) {
            if (num == 0) {
                return " .";
            }
            return " " + num;
        }
        return String.valueOf(num);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

 }