class Werewolf extends Monster {
    Werewolf(int x, int y, Player owner) {
        super(x, y, owner);
    }

    String getSymbol() {
        return this.owner.side == 0 ? "W1" : "W2";
    }
}
