//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class Vampire extends Monster {
    Vampire(int x, int y, Player owner) {
        super(x, y, owner);
    }

    String getSymbol() {
        return this.owner.side == 0 ? "V1" : "V2";
    }
}
