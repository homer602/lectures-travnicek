/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

/**
 *
 * Nakonec k nicemu
 */
public class Pair {

    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        // return "*[" + x + "]" + "[" + y + "]";
        return super.toString();
    }

    @Override
    public int hashCode() {
        return x << 2 | y << 4;  // HMMMMMMMMMMM?????
    }
    // no tady se spis hodi neco jako x.hashcode() ^ x.hashcode >>> 16 (ze souboru HashMap.java)

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair papa = (Pair) obj;
        return (this.x == papa.x) && (this.y == papa.y);
    }

}
