import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MonsterMayhemGame{
    static List<Player> players = new ArrayList<>();
    static Board board = new Board();
    static Random rand = new Random();

    public static void main(String[] args){
     players.add(new Player("Player 1", 0));
     players.add(new Player("Player 2", 1));

     Scanner sc = new Scanner(System.in);

        while (players.size() > 1){
            for (Player player : players){
                if (!player.isEliminated()){
                    takeTurn(player, sc);

                }
            }
            players.removeIf(Player::isEliminated);
        }

        System.out.println(players.get(0).name + "wins!");

    }
    static void takeTurn(Player player, Scanner sc){
        System.out.println(player.name + "'s turn!");
        board.printBoard();

        System.out.println("1. Place new monster");
        System.out.println("2. Move existing monster");
        int choice = sc.nextInt();

        if (choice == 1){
            placeNewMonster(player, sc);

        }else {
            move.ExistingMonster(player, sc);

        }
        resolveBattles();
    }

    static void placeNewMonster(Player playe, Scanner sc){
        System.out.println("Choose monster type: 1. Vampire 2. Werewolf 3. Ghost");
        int type =sc.nextInt();
        int x = player.sisde == 0 ? 0 : 9:;
        int y;

        boolean place = false;
        for (int i = 0; i < 30; i++){
            y = rand.nextInt(10) ;

            if(!board.isOccupied(x, y)){
                Monster monster;
                switch (type){
                    case 1:
                        monster = new Vampire(x, y, player);
                        break;
                    case 2:
                        monster = new Werewolf(x,y, player);
                        break;
                    case 3:
                        monster = new Ghost(x, y, player);
                        break;
                    default:
                        return;

                }

                player.monsters.add(monster);
                board.placeMonster(monster);
                place = true;
                System.out.println("placed" + monster.getClass().getSimpleName() + "at(" +x + ", "+ y +")");
                break;
            }
        }

        if(!placed){
            System.out.println("No empty position found after attempts. Try again next turn.");

        }
    }

    static void moveExistingMonster (Player player, Scanner sc){
        System.out.println("Enter current y coordinate of the monster to move: ");
        int x = sc.nextInt();
        System.out.println("Enter current x coordinate of the monster to move: ");
        int y = sc.nextInt();

     
    }

}