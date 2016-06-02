/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_CURRENT;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static javasudoku_CURRENT.GroupingPredicates.*;
import java.util.stream.Collectors;

/**
 *
 * @author Michal
 */
public class Position implements Comparable <Position>  {

    private final Set<AtomicInteger> candidatesToRemove = new HashSet();
    final private Board board;
    private byte x;  //souradnice x
    private byte y;  //souradnice y       //AtomicIntegerFieldUpdater >><<<<<<<???
    private BitSet internalCandidates = new BitSet();//{0,1,2,3,4...,8,9};
    public byte ROW;
    public byte COLUMN;
    public byte BOX;
    private AtomicInteger value = new AtomicInteger(0);
    private boolean valueset = false;

    public Position(int x, int y, Board board, Set rowExt, Set colExt, Set boxExt) {//BOARD SIZE = board.size apod.. do konstruktoru
        this.x = (byte) x;
        this.y = (byte) y;
        this.board = board;

        internalCandidates.set(1, board.NUMRANGE+1);

        rowExt.add(valueRef());
        colExt.add(valueRef());
        boxExt.add(valueRef());


        candidatesToRemove.addAll(rowExt);
        candidatesToRemove.addAll(colExt);
        candidatesToRemove.addAll(boxExt);
        
        ROW = (byte) x;
        COLUMN = (byte) y;    // x = ROW -- REDUNDANCE
        
        BOX = (byte) board.getBox(x, y);

    }

    public void addSetIntersection () {
        candidatesToRemove.addAll(board.rowCandidates[ROW]);
        candidatesToRemove.addAll(board.colCandidates[COLUMN]);
        candidatesToRemove.addAll(board.boxCandidates[BOX]);

    }

    public boolean clearCandidates() {

        if (valueset) {
            return false;
        }
        System.out.println("i:"+internalCandidates);
        System.out.println("r:"+candidatesToRemove);

        board.iteraceKandidatu++;
        candidatesToRemove.stream().forEach((AtomicInteger m) -> {
            //NACTU A PRIRADIM (ODEBERU Z MNOZINY) SKUTECNE HODNOTY ODKAZOVANYCH CISEL
            if (internalCandidates.get(m.get()))
                internalCandidates.clear(m.get());
        });
        //pomoci distinct lze odebrat dupicity ale asi nema cenu

        System.out.println(internalCandidates.cardinality());
        if (internalCandidates.cardinality()== 1 && value.get() == 0) {//kdyz se nekontroluje value tak to "zahadne" propaguje
            int tmpvalue = (byte) (int) internalCandidates.nextSetBit(1);
            assign(tmpvalue);
            return true;

        }
        return false;
    }


    public AtomicInteger valueRef() {
        return value; 
    }

    public int getBox() {
        return (int) BOX; //Nutne pretypovat au
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


    public Set getCandidates() {
        return new HashSet (Arrays.asList(internalCandidates.toByteArray()));
    }

    @Override
    public int compareTo(Position o) {     //pry nedefinovano equals? ukazuje findbugs v NetBEANS
        
        if (this.x == o.x) {
            if (this.y == o.y) return 0;// tady to se vyhodnocuje nejak jen na zacatku seznamu...
            return (this.y < o.y ? -1 : 1);
        }
        else {
            return (this.x < o.x ? -1 : 1);
        }
        //....aneb byly tu urcite pokusy tu komparaci osidit a nekdy to projde
        //if (this.x <= o.x && this.y <= o.y) return -1; else return 1;
        
    }

}
