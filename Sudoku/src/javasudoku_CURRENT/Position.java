/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_CURRENT;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import static javasudoku_CURRENT.Variables.DEBUG;

/**
 *
 * @author Michal
 */
public final class Position implements Comparable<Position> {

    private final Set<AtomicInteger> candidatesToRemove = new HashSet();
    final private Board board;
    private final byte x;  //souradnice x
    private final byte y;  //souradnice y       //AtomicIntegerFieldUpdater >><<<<<<<???
    private final BitSet internalCandidates = new BitSet();//{1,2,3,4...,8,9};
    public final byte myRow;
    public final byte myColumn;
    public final byte myBox;
    private final AtomicInteger value = new AtomicInteger(0);
    private boolean valueset = false;

    public Position(int x, int y, Board board, Set rowExt, Set colExt, Set boxExt) {//BOARD SIZE = board.size apod.. do konstruktoru
        this.x = (byte) x;
        this.y = (byte) y;
        this.board = board;

        internalCandidates.set(1, board.NUMRANGE + 1);

        rowExt.add(valueRef());
        colExt.add(valueRef());
        boxExt.add(valueRef());

        candidatesToRemove.addAll(rowExt);
        candidatesToRemove.addAll(colExt);
        candidatesToRemove.addAll(boxExt);

        myRow = (byte) x;
        myColumn = (byte) y;    // x = ROW -- REDUNDANCE

        myBox = (byte) board.getBox(x, y);

    }

    public void addSetIntersection() {
        candidatesToRemove.addAll(board.rowCandidates.get(myRow));
        candidatesToRemove.addAll(board.colCandidates.get(myColumn));
        candidatesToRemove.addAll(board.boxCandidates.get(myBox));
        //tady sem mel vsude ROWCANDIDATES !!!!!!!!!!
        //System.out.println("cre:"+candidatesToRemove);

    }

    public boolean clearCandidates() {//vracime bool

        if (valueset) {
            return false;
        }
        //
        if (DEBUG) {
            System.out.println("row" + board.rowCandidates.get(myRow));
            System.out.println("col" + board.colCandidates.get(myColumn));
            System.out.println("box" + board.boxCandidates.get(myBox));
            
            System.out.println("int:" + internalCandidates);
            System.out.println("rem:" + candidatesToRemove);
        }

        board.iteraceKandidatu++;//TODO pouzit nejaky listener/callback?
        candidatesToRemove.stream().forEach((AtomicInteger m) -> {
            //NACTU A PRIRADIM (ODEBERU Z MNOZINY) SKUTECNE HODNOTY ODKAZOVANYCH CISEL
            int numero = m.get();
            if (internalCandidates.get(numero)) {
                internalCandidates.clear(numero);
            }
        });
        //pomoci distinct lze odebrat dupicity ale asi nema cenu

        if (DEBUG) {
            System.out.println(this + "Cardinality:" + internalCandidates.cardinality());
        }

        if (internalCandidates.cardinality() == 1 && value.get() == 0) {//kdyz se nekontroluje value tak to "zahadne" propaguje
            int tmpvalue = (byte) (int) internalCandidates.nextSetBit(1);
            if (DEBUG) {
                System.out.println("set:" + tmpvalue);
                System.out.println("");
            }
            assign(tmpvalue);
            return true;

        }
        if (DEBUG) {
            System.out.println("");
        }
        return false;
    }

    public AtomicInteger valueRef() {
        return value;
    }

    public int getValue() {
        return value.get();
    }

    public int getBox() {
        return (int) myBox; //Nutne pretypovat au
    }

    void assign(int num) {
        if (valueset) {
            return;   // bez tohoto "zahadna" propagace na 4x4
        }
        valueset = true;
        value.set(num);
        board.leftToSolve--;
    }

    @Override
    public String toString() {
        return (String.valueOf(x) + "," + String.valueOf(y) + getCandidates());
    }

    public String getCandidates() {
        //to stare fungovalo?
        //return new HashSet (Arrays.asList(internalCandidates.toByteArray()));
        return internalCandidates.toString();
    }

    @Override
    public int compareTo(Position o) {     //pry nedefinovano equals? ukazuje findbugs v NetBEANS

        if (this.x == o.x) {
            if (this.y == o.y) {
                return 0;// tady to se vyhodnocuje nejak jen na zacatku seznamu...
            }
            return (this.y < o.y ? -1 : 1);
        } else {
            return (this.x < o.x ? -1 : 1);
        }
        //....aneb byly tu urcite pokusy tu komparaci osidit a nekdy to projde
        //if (this.x <= o.x && this.y <= o.y) return -1; else return 1;

    }

}
