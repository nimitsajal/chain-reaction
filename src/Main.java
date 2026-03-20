public class Main {
    public static void main(String[] args) {
        int size = 10;
        int[][] defaultGrid = getDefaultGrid(size);
        printGrid(defaultGrid, size);
    }

    public static void addSeparation() {
        System.out.println("                                ");
        System.out.println("                                ");
        System.out.println("                                ");
        System.out.println("                                ");
        System.out.println("                                ");
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
        for (int i = 0; i < size + 1; i++) {
            if (i > 0) { // actual grid
                System.out.print(printNum(i) + " │ ");
                for (int j = 0; j < size; j++) {
                    System.out.printf(printNum(grid[i][j]) + " ");
                }
                System.out.println(" │ ");
            } else { // top row for the column numbers
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
        }
        System.out.print("   └─");
        for (int k = 1; k < size + 1; k++) {
            System.out.print("───");
        }
        System.out.print("─┘");
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