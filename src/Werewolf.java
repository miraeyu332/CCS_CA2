class Werewolf extends Monster {
    Werewolf(int x, int y, Player owner) {
        super(x, y, owner);
    }

    @Override
    String getSymbol() {
        return owner.side == 0 ? "W1" : "W2";
    }

//    @Override
//    boolean canMove(int newX, int newY) {
//        return (x == newX || y == newY || Math.abs(x - newX) == Math.abs(y - newY)) && Math.abs(x - newX) <= 2 && Math.abs(y - newY) <= 2;
//    }
}



