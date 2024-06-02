//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MonsterMayhemGame {
    static List<Player> players = new ArrayList();
    static Board board = new Board();
    static Random random = new Random();

    public MonsterMayhemGame() {
    }

    public static void main(String[] args) {
        players.add(new Player("Player 1", 0));
        players.add(new Player("Player 2", 1));
        Scanner scanner = new Scanner(System.in);

        while(players.size() > 1) {
            Iterator var2 = players.iterator();

            while(var2.hasNext()) {
                Player player = (Player)var2.next();
                if (!player.isEliminated()) {
                    takeTurn(player, scanner);
                }
            }

            players.removeIf(Player::isEliminated);
        }

        System.out.println(((Player)players.get(0)).name + " wins!");
    }

    static void takeTurn(Player player, Scanner scanner) {
        System.out.println(player.name + "'s turn!");
        board.printBoard();
        System.out.println("1. Place new monster");
        System.out.println("2. Move existing monster");
        int choice = scanner.nextInt();
        if (choice == 1) {
            placeNewMonster(player, scanner);
        } else {
            moveExistingMonster(player, scanner);
        }

        resolveBattles();
    }

    static void placeNewMonster(Player player, Scanner scanner) {
        System.out.println("Choose monster type: 1. Vampire 2. Werewolf 3. Ghost");
        int type = scanner.nextInt();
        int x = player.side == 0 ? 0 : 9;
        boolean placed = false;

        for(int i = 0; i < 30; ++i) {
            int y = random.nextInt(10);
            if (!board.isOccupied(x, y)) {
                Object monster;
                switch (type) {
                    case 1 -> monster = new Vampire(x, y, player);
                    case 2 -> monster = new Werewolf(x, y, player);
                    case 3 -> monster = new Ghost(x, y, player);
                    default -> return;
                }

                player.monsters.add(monster);
                board.placeMonster((Monster)monster);
                placed = true;
                System.out.println("Placed " + monster.getClass().getSimpleName() + " at (" + x + ", " + y + ")");
                break;
            }
        }

        if (!placed) {
            System.out.println("No empty position found after attempts. Try again next turn.");
        }

    }

    static void moveExistingMonster(Player player, Scanner scanner) {
        System.out.println("Enter current y coordinate of the monster to move:");
        int x = scanner.nextInt();
        System.out.println("Enter current x coordinate of the monster to move:");
        int y = scanner.nextInt();
        Monster monster = board.grid[x][y];
        if (monster != null && monster.owner == player) {
            System.out.println("Enter new y coordinate:");
            int newX = scanner.nextInt();
            System.out.println("Enter new x coordinate:");
            int newY = scanner.nextInt();
            if (!board.isWithinBounds(newX, newY)) {
                System.out.println("Invalid move. Try again.");
            } else if (!monster.canMove(newX, newY)) {
                System.out.println("Invalid move. Try again.");
            } else {
                if (board.isOccupied(newX, newY)) {
                    Monster occupyingMonster = board.grid[newX][newY];
                    if (occupyingMonster != null) {
                        PrintStream var10000 = System.out;
                        String var10001 = monster.getClass().getSimpleName();
                        var10000.println("Resolving battle between " + var10001 + " and " + occupyingMonster.getClass().getSimpleName());
                        resolveBattle(monster, occupyingMonster);
                    }
                } else {
                    board.moveMonster(monster, newX, newY);
                }

            }
        } else {
            System.out.println("Invalid move. Try again.");
        }
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
                                System.out.println("Conflict detected at (" + i + ", " + j + ")");
                                resolveBattle(monster1, monster2);
                            }
                        }
                    }
                }
            }
        }

    }

    static void resolveBattle(Monster monster1, Monster monster2) {
        if (monster1.getClass() == Vampire.class && monster2.getClass() == Werewolf.class) {
            removeMonster(monster2);
        } else if (monster1.getClass() == Werewolf.class && monster2.getClass() == Ghost.class) {
            removeMonster(monster2);
        } else if (monster1.getClass() == Ghost.class && monster2.getClass() == Vampire.class) {
            removeMonster(monster2);
        } else if (monster1.getClass() == monster2.getClass()) {
            removeMonster(monster1);
            removeMonster(monster2);
        } else {
            PrintStream var10000 = System.out;
            String var10001 = monster1.getClass().getSimpleName();
            var10000.println("No specific battle rules for " + var10001 + " vs " + monster2.getClass().getSimpleName());
        }

    }

    static void removeMonster(Monster monster) {
        monster.owner.monsters.remove(monster);
        ++monster.owner.eliminatedMonsters;
        board.removeMonster(monster.x, monster.y);
        PrintStream var10000 = System.out;
        String var10001 = monster.getClass().getSimpleName();
        var10000.println("Removed " + var10001 + " from (" + monster.x + ", " + monster.y + ")");
    }
}
