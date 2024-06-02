abstract class Monster {
    int x, y;
    Player owner;

    Monster(int x, int y, Player owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    abstract String getSymbol();

    boolean canMove(int newX, int newY) {
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        return (dx == 0 || dy == 0 || (dx <= 2 && dy <= 2 && dx == dy));
    }
}
