//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.PrintStream;
import java.io.PrintWriter;

class Board {
    Monster[][] grid = new Monster[10][10];

    Board() {
    }

    boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    boolean isOccupied(int x, int y) {
        return this.grid[x][y] != null;
    }

    void placeMonster(Monster monster) {
        this.grid[monster.x][monster.y] = monster;
    }

    void removeMonster(int x, int y) {
        this.grid[x][y] = null;
    }

    void moveMonster(Monster monster, int newX, int newY) {
        this.removeMonster(monster.x, monster.y);
        monster.x = newX;
        monster.y = newY;
        this.placeMonster(monster);
    }

    void printBoard() {
        System.out.println("   0   1   2   3   4   5                 9");
        System.out.println("  ----------------------------------------");

        for(int i = 0; i < 10; ++i) {
            System.out.print("" + i + "|");

            for(int j = 0; j < 10; ++j) {
                if (this.grid[i][j] == null) {
                    System.out.print(" .  ");
                } else {
                    PrintStream var10000 = System.out;
                    String var10001 = this.grid[i][j].getSymbol();
                    var10000.print(" " + var10001 + "  ");
                }
            }

            System.out.println(" |");
        }

        System.out.println("  -------------------------------------");
    }

    void printBoard(PrintWriter printWriter) {
        printWriter.println("   0   1   2   3   4   5   6   7   8   9");
        printWriter.println("  ----------------------------------------");

        for(int i = 0; i < 10; ++i) {
            printWriter.print("" + i + "|");

            for(int j = 0; j < 10; ++j) {
                if (this.grid[i][j] == null) {
                    printWriter.print(" .  ");
                } else {
                    String var10001 = this.grid[i][j].getSymbol();
                    printWriter.print(" " + var10001 + " ");
                }
            }

            printWriter.println(" |");
        }

        printWriter.println("  ----------------------------------------");
    }
}
