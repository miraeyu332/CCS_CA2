class Ghost extends Monster {
    Ghost(int x, int y, Player owner) {
        super(x, y, owner);
    }

    @Override
    String getSymbol() {
        return owner.side == 0 ? "G1" : "G2";
    }

//    @Override
//    boolean canMove(int newX, int newY) {
//        return (x == newX || y == newY || Math.abs(x - newX) == Math.abs(y - newY)) && Math.abs(x - newX) <= 2 && Math.abs(y - newY) <= 2;
//    }
}

