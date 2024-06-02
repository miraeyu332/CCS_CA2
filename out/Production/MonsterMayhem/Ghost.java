//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Ghost extends Monster {
    Ghost(int x, int y, Player owner) {
        super(x, y, owner);
    }

    String getSymbol() {
        return this.owner.side == 0 ? "G1" : "G2";
    }
}
