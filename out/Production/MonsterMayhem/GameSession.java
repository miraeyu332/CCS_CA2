//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class GameSession {
    int gameId;
    List<Player> players;
    static Board board = new Board();
    Random random;

    GameSession(int gameId) {
        this.gameId = gameId;
        this.players = new ArrayList();
        this.random = new Random();
    }

    void addPlayer(Player player) {
        this.players.add(player);
    }

    String processCommand(Player player, String command) {
        Scanner scanner = new Scanner(command);
        switch (scanner.next()) {
            case "PLACE":
                int type = scanner.nextInt();
                String placeResult = this.placeNewMonster(player, type);
                return placeResult + "\n" + this.getBoardState();
            case "MOVE":
                int currentX = scanner.nextInt();
                int currentY = scanner.nextInt();
                int newX = scanner.nextInt();
                int newY = scanner.nextInt();
                String moveResult = this.moveExistingMonster(player, currentX, currentY, newX, newY);
                resolveBattles();
                return moveResult + "\n" + this.getBoardState();
            default:
                return "Unknown command.";
        }
    }

    public String getBoardState() {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        board.printBoard(printWriter);
        return writer.toString();
    }

    private String placeNewMonster(Player player, int type) {
        int x = player.side == 0 ? 0 : 9;

        for(int i = 0; i < 30; ++i) {
            int y = this.random.nextInt(10);
            if (!board.isOccupied(x, y)) {
                Object monster;
                switch (type) {
                    case 1 -> monster = new Vampire(x, y, player);
                    case 2 -> monster = new Werewolf(x, y, player);
                    case 3 -> monster = new Ghost(x, y, player);
                    default -> return "Invalid monster type.";
                }

                player.monsters.add(monster);
                board.placeMonster((Monster)monster);
                return "Placed " + monster.getClass().getSimpleName() + " at (" + x + ", " + y + ")";
            }
        }

        return "No empty position found after attempts. Try again next turn.";
    }

    private String moveExistingMonster(Player player, int currentX, int currentY, int newX, int newY) {
        Monster monster = board.grid[currentX][currentY];
        if (monster != null && monster.owner == player) {
            if (board.isWithinBounds(newX, newY) && monster.canMove(newX, newY)) {
                System.out.println("occupying monster 1: " + currentX + " " + currentX + " " + newX + " " + newY);
                if (board.isOccupied(newX, newY)) {
                    Monster occupyingMonster = board.grid[newX][newY];
                    System.out.println("occupying monster 2: " + currentX + " " + currentX + " " + newX + " " + newY);
                    int var10001 = occupyingMonster.x;
                    System.out.println("Get monster: " + var10001 + " " + occupyingMonster.y + " " + String.valueOf(occupyingMonster.owner) + " Name: " + occupyingMonster.getClass().getSimpleName());
                    if (occupyingMonster.owner != null) {
                        System.out.println("Monster occupying not null");
                        String battleResult = resolveBattle(monster, occupyingMonster);
                        System.out.println("Battle Result: " + battleResult);
                        if (!battleResult.startsWith("Battle resolved")) {
                            return battleResult;
                        }
                    }
                } else {
                    board.moveMonster(monster, newX, newY);
                }

                return "Moved " + monster.getClass().getSimpleName() + " to (" + newX + ", " + newY + ")  from (" + currentX + ", " + currentY + ")";
            } else {
                return "Invalid move. Try again.";
            }
        } else {
            return "Invalid move. Try again.";
        }
    }

    private static String resolveBattle(Monster monster1, Monster monster2) {
        PrintStream var10000 = System.out;
        String var10001 = monster1.getClass().getSimpleName();
        var10000.println("Starting battle between " + var10001 + " and " + monster2.getClass().getSimpleName());
        if (monster1.getClass() == Vampire.class && monster2.getClass() == Werewolf.class || monster1.getClass() == Werewolf.class && monster2.getClass() == Ghost.class || monster1.getClass() == Ghost.class && monster2.getClass() == Vampire.class) {
            System.out.println(monster2.getClass().getSimpleName() + " is removed.");
            removeMonster(monster2);
        } else if (monster1.getClass() == Werewolf.class && monster2.getClass() == Vampire.class || monster1.getClass() == Ghost.class && monster2.getClass() == Werewolf.class || monster1.getClass() == Vampire.class && monster2.getClass() == Ghost.class) {
            System.out.println(monster1.getClass().getSimpleName() + " is removed.");
            removeMonster(monster1);
        } else {
            if (monster1.getClass() != monster2.getClass()) {
                var10000 = System.out;
                var10001 = monster1.getClass().getSimpleName();
                var10000.println("No specific battle rules for " + var10001 + " vs " + monster2.getClass().getSimpleName());
                String var2 = monster1.getClass().getSimpleName();
                return "No specific battle rules for " + var2 + " vs " + monster2.getClass().getSimpleName();
            }

            System.out.println("Both monsters are of the same type. Both are removed.");
            removeMonster(monster1);
            removeMonster(monster2);
        }

        return "Battle resolved.";
    }

    private static void removeMonster(Monster monster) {
        PrintStream var10000 = System.out;
        String var10001 = monster.getClass().getSimpleName();
        var10000.println("Removing monster-----: " + var10001 + " at (" + monster.x + ", " + monster.y + ")");
        monster.owner.monsters.remove(monster);
        ++monster.owner.eliminatedMonsters;
        board.removeMonster(monster.x, monster.y);
    }

    static void resolveBattles() {
        for(int i = 0; i < 10; ++i) {
            for(int j = 0; j < 10; ++j) {
                Monster monster1 = board.grid[i][j];
                if (monster1 != null) {
                    for(int k = 0; k < 10; ++k) {
                        for(int l = 0; l < 10; ++l) {
                            Monster monster2 = board.grid[k][l];
                            if (monster2 != null && monster1 != monster2 && monster1.x == monster2.x && monster1.y == monster2.y) {
                                resolveBattle(monster1, monster2);
                            }
                        }
                    }
                }
            }
        }

    }
}
