import java.util.ArrayList;
import java.util.List;


class Player {
    String name;
    List<Monster> monsters;
    int eliminatedMonsters;
    int side; // 0 for top, 1 for bottom

    Player(String name, int side) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.eliminatedMonsters = 0;
        this.side = side;
    }

    boolean isEliminated() {
        return eliminatedMonsters >= 11;
    }
}


