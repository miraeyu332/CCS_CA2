import java.io.PrintWriter;

class Board {
    Monster[][] grid;

    Board() {
        this.grid = new Monster[10][10];
    }

    boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    boolean isOccupied(int x, int y) {
        return grid[x][y] != null;
    }

    void placeMonster(Monster monster) {
        grid[monster.x][monster.y] = monster;
    }

    void removeMonster(int x, int y) {
        grid[x][y] = null;
    }

    void moveMonster(Monster monster, int newX, int newY) {
        removeMonster(monster.x, monster.y);
        monster.x = newX;
        monster.y = newY;
        placeMonster(monster);
    }

    void printBoard() {
        System.out.println("   0   1   2   3   4   5                 9");
        System.out.println("  ----------------------------------------");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] == null) {
                    System.out.print(" .  ");
                } else {
                    System.out.print(" " + grid[i][j].getSymbol() + "  ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("  -------------------------------------");
    }

    void printBoard(PrintWriter printWriter) {
        printWriter.println("   0   1   2   3   4   5   6   7   8   9");
        printWriter.println("  ----------------------------------------");
        for (int i = 0; i < 10; i++) {
            printWriter.print(i + "|");
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] == null) {
                    printWriter.print(" .  ");
                } else {
                    printWriter.print(" " + grid[i][j].getSymbol() + " ");
                }
            }
            printWriter.println(" |");
        }
        printWriter.println("  ----------------------------------------");
    }
}