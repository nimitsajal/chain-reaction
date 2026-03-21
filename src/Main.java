import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
//        int size = 10;
        int size = getGridSizeFromUser(sc);

        int[][] defaultGrid = getDefaultGrid(size);
        printGrid(defaultGrid, size);

        while(true) {

            Move move = getMoveFromuser(sc, size);
            int r = move.getRowPos() - 1;
            int c  = move.getCollPos() - 1;

            System.out.println("Selected Row and Column: " + move.getRowPos() + ", " + move.getCollPos());

            int[][] grid = defaultGrid;

            grid[r][c] += 1;
            if (grid[r][c] == 3) {
                grid[r][c] = 0;
                grid[r-1][c] += 1;
                grid[r][c-1] += 1;
                grid[r+1][c] += 1;
                grid[r][c+1] += 1;
            }
            printGrid(grid, size);
        }
    }

    public static Move getMoveFromuser(Scanner sc, int size) {
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

        move.setRowPos(r);
        move.setCollPos(c);
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