import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameSession {
}
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

class GameSession {
    int gameId;
    List<Player> players;
    static Board board= new Board();
    Random random;

    GameSession(int gameId) {
        this.gameId = gameId;
        this.players = new ArrayList<>();
//        this.board = new Board();
        this.random = new Random();
    }

    void addPlayer(Player player) {
        players.add(player);
    }

    String processCommand(Player player, String command) {
        Scanner scanner = new Scanner(command);
        String action = scanner.next();

        switch (action) {
            case "PLACE":
                int type = scanner.nextInt();
                String placeResult = placeNewMonster(player, type);
                return placeResult + "\n" + getBoardState(); // Include board state in the response
            case "MOVE":
                int currentX = scanner.nextInt();
                int currentY = scanner.nextInt();
                int newX = scanner.nextInt();
                int newY = scanner.nextInt();
                String moveResult = moveExistingMonster(player, currentX, currentY, newX, newY);
                resolveBattles(); // Check for conflicts after each move
                return moveResult + "\n" + getBoardState(); // Include board state in the response
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
        int y;

        for (int i = 0; i < 30; i++) {
            y = random.nextInt(10);

            if (!board.isOccupied(x, y)) {
                Monster monster;
                switch (type) {
                    case 1:
                        monster = new Vampire(x, y, player);
                        break;
                    case 2:
                        monster = new Werewolf(x, y, player);
                        break;
                    case 3:
                        monster = new Ghost(x, y, player);
                        break;
                    default:
                        return "Invalid monster type.";
                }

                player.monsters.add(monster);
                board.placeMonster(monster);
                return "Placed " + monster.getClass().getSimpleName() + " at (" + x + ", " + y + ")";
            }
        }

        return "No empty position found after attempts. Try again next turn.";
    }

    private String moveExistingMonster(Player player, int currentX, int currentY, int newX, int newY) {
        Monster monster = board.grid[currentX][currentY];
        if (monster == null || monster.owner != player) {
            return "Invalid move. Try again.";
        }

        if (!board.isWithinBounds(newX, newY) || !monster.canMove(newX, newY)) {
            return "Invalid move. Try again.";
        }

        System.out.println("occupying monster 1: "+currentX+" "+currentX+" "+newX+" "+ newY );

        if (board.isOccupied(newX, newY)) {
            Monster occupyingMonster = board.grid[newX][newY];
            System.out.println("occupying monster 2: "+currentX+" "+currentX+" "+newX+" "+ newY );
            System.out.println("Get monster: " +occupyingMonster.x+" " +occupyingMonster.y+" "+occupyingMonster.owner+" Name: " + occupyingMonster.getClass().getSimpleName());
            if (occupyingMonster.owner != null) {
                System.out.println("Monster occupying not null");
                String battleResult = resolveBattle(monster, occupyingMonster);
                System.out.println("Battle Result: "+battleResult);
                if (!battleResult.startsWith("Battle resolved")) {
                    return battleResult; // Return the battle result if it's not resolved
                }
            }
        }
        else
            // Move the monster if there is no battle or the battle is resolved
            board.moveMonster(monster, newX, newY);
        return "Moved " + monster.getClass().getSimpleName() + " to (" + newX + ", " + newY + ") "+ " from (" + currentX + ", " + currentY + ")";
    }

    private static String resolveBattle(Monster monster1, Monster monster2) {
        System.out.println("Starting battle between " + monster1.getClass().getSimpleName() + " and " + monster2.getClass().getSimpleName());

        if ((monster1.getClass() == Vampire.class && monster2.getClass() == Werewolf.class) ||
                (monster1.getClass() == Werewolf.class && monster2.getClass() == Ghost.class) ||
                (monster1.getClass() == Ghost.class && monster2.getClass() == Vampire.class)) {
            System.out.println(monster2.getClass().getSimpleName() + " is removed.");
            removeMonster(monster2); // Remove the first monster
        } else if ((monster1.getClass() == Werewolf.class && monster2.getClass() == Vampire.class) ||
                (monster1.getClass() == Ghost.class && monster2.getClass() == Werewolf.class) ||
                (monster1.getClass() == Vampire.class && monster2.getClass() == Ghost.class)) {
            System.out.println(monster1.getClass().getSimpleName() + " is removed.");
            removeMonster(monster1); // Remove the second monster
        } else if (monster1.getClass() == monster2.getClass()) {
            System.out.println("Both monsters are of the same type. Both are removed.");
            removeMonster(monster1);
            removeMonster(monster2);
        } else {
            System.out.println("No specific battle rules for " + monster1.getClass().getSimpleName() + " vs " + monster2.getClass().getSimpleName());
            return "No specific battle rules for " + monster1.getClass().getSimpleName() + " vs " + monster2.getClass().getSimpleName();
        }

//        System.out.println("Battle resolved.");
        return "Battle resolved.";
    }

    private static void removeMonster(Monster monster) {
        System.out.println("Removing monster-----: " + monster.getClass().getSimpleName() + " at (" + monster.x + ", " + monster.y + ")");
        monster.owner.monsters.remove(monster);
        monster.owner.eliminatedMonsters++;
        board.removeMonster(monster.x, monster.y);
    }

    static void resolveBattles() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Monster monster1 = board.grid[i][j];
                if (monster1 != null) {
                    for (int k = 0; k < 10; k++) {
                        for (int l = 0; l < 10; l++) {
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
