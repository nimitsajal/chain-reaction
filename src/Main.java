import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int size = getGridSizeFromUser(sc);
//        int size = 10;

        int[][] defaultGrid = getDefaultGrid(size);
        printGrid(defaultGrid, size);

        while(true) {
            System.out.println("Enter Row and Column: ");
            int r = sc.nextInt() - 1;
            int c = sc.nextInt() - 1;

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