import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
//        int size = 10;
        int size = getGridSizeFromUser(sc);
        int playerCount = getPlayerCountFromUser(sc);

        List<Player> playerList =  createPlayersAndAssignColors(playerCount);

        Cell[][] grid = getDefaultGrid(size);
        printGrid(grid, size);

        int currentPlayerIndex = 0;
        int totalPlayers = playerList.size();
        boolean isGameOver = false;

        while(!isGameOver) {

            Player currentPlayer = playerList.get(currentPlayerIndex);

            Move move = getMoveFromUser(sc, size, currentPlayer, grid);

            incrementValue(grid, move.getRowPos(), move.getColPos(), size, currentPlayer);
//            printGrid(grid, size);

            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;

        }
    }

    private static List<Player> createPlayersAndAssignColors(int playerCount) {
        List<Player> playerList = new ArrayList<>();

        for(int i=0; i < playerCount; i++) {
            Player player = new Player();
            player.setId(i);
            TextColor textColor = getColorFromPlayer(player.getId());
            player.setTextColor(textColor);
            playerList.add(player);
        }

        return playerList;

    }

    private static void incrementValue(Cell[][] grid, int rowPos, int colPos, int size, Player currentPlayer) {
            Cell currentCell = grid[rowPos][colPos];

            currentCell.setValue(grid[rowPos][colPos].getValue() + 1);
            currentCell.setPlayer(currentPlayer);
            printGrid(grid, size);
            CellType cellType = getCellType(rowPos, colPos, size);
            if (currentCell.getValue() >= cellType.getCapacity()) {
                currentCell.setValue(0);
                currentCell.setPlayer(null);

                if (isBottomExists(rowPos, size)) {
                    incrementValue(grid, rowPos+1, colPos, size, currentPlayer);
                }
                if (isTopExists(rowPos)) {
                    incrementValue(grid, rowPos-1, colPos, size, currentPlayer);
                }
                if (isLeftExists(colPos)) {
                    incrementValue(grid, rowPos, colPos-1, size, currentPlayer);
                }
                if (isRightExists(colPos, size)) {
                    incrementValue(grid, rowPos, colPos+1, size, currentPlayer);
                }
            }

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

    private static TextColor getColorFromPlayer(int i) {
        Scanner sc = new Scanner(System.in);
        boolean isValidInput = false;
        int colorNumber;
        TextColor textColor = null;

        while (!isValidInput) {
            try {
                System.out.println("Select the Color for the Player: " + i + "[Enter the Number attached to the color]");
                System.out.println("Allowed Colors: [1] RED, [2] BLUE, [3] GREEN, [4] YELLOW, [5] CYAN, [6] MAGENTA");
                colorNumber = sc.nextInt();
                if (colorNumber >= 1 && colorNumber <= 6) {
                    textColor = getTextColorEnumFromSelectedNumber(colorNumber);
                    System.out.println("Selected Color: [" + colorNumber + "] " + textColor);
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

    private static TextColor getTextColorEnumFromSelectedNumber(int colorNumber) {
        return switch (colorNumber) {
            case 1 -> TextColor.RED;
            case 2 -> TextColor.BLUE;
            case 3 -> TextColor.GREEN;
            case 4 -> TextColor.YELLOW;
            case 5 -> TextColor.CYAN;
            case 6 -> TextColor.MAGENTA;
            default -> TextColor.WHITE;
        };
    }

    private static Move getMoveFromUser(Scanner sc, int size, Player currentPlayer, Cell[][] grid) {
        Move move = new Move();
        int r = 0;
        int c = 0;

        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.println("Enter the move for Player: " + currentPlayer.getId() + 1 + " - row and column positions: ");
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

 }