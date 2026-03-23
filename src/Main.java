import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
//        int size = 10;
        int size = getGridSizeFromUser(sc);

        int[][] grid = getDefaultGrid(size);
        printGrid(grid, size);

        while(true) {

            Move move = getMoveFromUser(sc, size);

            incrementValue(grid, move.getRowPos(), move.getColPos(), size);
//            printGrid(grid, size);
        }
    }

    private static void incrementValue(int[][] grid, int rowPos, int colPos, int size) {
        grid[rowPos][colPos] += 1;
        printGrid(grid, size);
        CellType cellType = getCellType(rowPos, colPos, size);
        if (grid[rowPos][colPos] >= cellType.getCapacity()) {
            grid[rowPos][colPos] = 0;

            if (isBottomExists(rowPos, size)) {
                incrementValue(grid, rowPos+1, colPos, size);
            }
            if (isTopExists(rowPos)) {
                incrementValue(grid, rowPos-1, colPos, size);
            }
            if (isLeftExists(colPos)) {
                incrementValue(grid, rowPos, colPos-1, size);
            }
            if (isRightExists(colPos, size)) {
                incrementValue(grid, rowPos, colPos+1, size);
            }
        }
    }

    public static boolean isRightExists(int colPos, int size) {
        return colPos < size - 1;
    }

    public static boolean isLeftExists(int colPos) {
        return colPos > 0;
    }

    public static boolean isBottomExists(int rowPos, int size) {
        return rowPos < size - 1;
    }

    public static boolean isTopExists(int rowPos) {
        return rowPos > 0;
    }

    public static CellType getCellType(int rowPos, int colPos, int size) {
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

    public static Move getMoveFromUser(Scanner sc, int size) {
        Move move = new Move();
        int r = 0;
        int c = 0;

        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.println("Enter the move - row and column positions: ");
                r = sc.nextInt();
                c = sc.nextInt();
                if (r > 0 && c > 0 && r <= size && c <= size) {
                    isValidInput = true;
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

    public static int[][] getDefaultGrid(int size) {

        int[][] grid = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }

        return grid;

    }

    public static void printGrid(int[][] grid, int size) {
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

    private static void printMainGrid(int[][] grid, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(printNum(i+1) + " │ ");
            for (int j = 0; j < size; j++) {
                System.out.print(printNum(grid[i][j]) + " ");

                // NOTE: to check the position of each element
//                System.out.print(i + String.valueOf(j) + " ");
            }
            System.out.println(" │ ");
        }
    }

    private static void printBottomBorder(int size) {
        System.out.print("   └─");
        for (int k = 1; k <= size; k++) {
            System.out.print("───");
        }
        System.out.print("─┘");
        System.out.println();
    }

    public static String printNum(int num) {
        if (num < 10) {
            if (num == 0) {
                return " .";
            }
            return " " + num;
        }
        return String.valueOf(num);
    }

 }