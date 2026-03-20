public class Main {
    public static void main(String[] args) {
        int size = 10;
        int[][] defaultGrid = getDefaultGrid(size);
        printGrid(defaultGrid, size);
    }

    public static int[][] getDefaultGrid(int size) {

        int[][] grid = new int[size + 1][size];

        for (int i = 0; i < size + 1; i++) {
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

    private static void printMainGrid(int[][] grid, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(printNum(i+1) + " │ ");
            for (int j = 0; j < size; j++) {
                System.out.printf(printNum(grid[i][j]) + " ");

                // NOTE: to check the position of each element
//                System.out.printf(i + String.valueOf(j) + " ");
            }
            System.out.println(" │ ");
        }
    }

    private static void printBottomBorder(int size) {
        System.out.print("   └─");
        for (int k = 1; k < size + 1; k++) {
            System.out.print("───");
        }
        System.out.print("─┘");
    }

    private static void printTopRowWithBorder(int size) {
        System.out.print("     ");
        for (int k = 1; k < size + 1; k++) {
            System.out.printf(printNum(k) + " ");
        }
        System.out.println();
        System.out.print("   ┌─");
        for (int k = 1; k < size + 1; k++) {
            System.out.print("───");
        }
        System.out.print("─┐");
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