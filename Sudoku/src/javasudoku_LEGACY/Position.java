/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_LEGACY;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import static javasudoku_LEGACY.GroupingPredicates.*;

/**
 *
 * @author Michal
 */
public class Position implements Comparable <Position>  {

    private byte value = 0;
    public byte x;  //souradnice x - K NICEMU POKUD MAM ROW apod?
    public byte y;  //souradnice y
    private Set candidates = new HashSet<Integer>();//{0,1,2,3,4...,8,9}; pouzit BitSet
    public byte ROW;
    public byte COLUMN;
    public byte BOX;
    public AtomicBoolean rowSolved;
    public AtomicBoolean colSolved;
    public AtomicBoolean boxSolved;
    final private Board board;

    public Position(int x, int y, Board board, Set initCand) {//BOARD SIZE = board.size apod.. do konstruktoru
        this.x = (byte) x;
        this.y = (byte) y;
        this.board = board;
         
        candidates.addAll(initCand);
        
       // for (int i = 1; i <= board.NUMRANGE; i++) {  // PRENEST VEN?
       //     candidates.add(i);  // BLBOST DELA SE XKRAT STACI JEDNOU **************************8
       // }
        
        ROW = (byte) x;
        COLUMN = (byte) y;
        
        int rowindex = (int) Math.ceil((double) x/board.BOXSIZE)-1;
        BOX = (byte) ((Math.ceil((double) y/board.BOXSIZE))+board.BOXSIZE*(rowindex));
        
        rowSolved = board.solvedRows.get(x);
        colSolved = board.solvedCols.get(y);
        boxSolved = board.solvedBoxes.get(BOX);
        //rowSolved=JavaSudoku.row;
        
        //BOX = (byte) ((byte) ((BOXSIZE % x) /BOXSIZE) + ((BOXSIZE % y) / BOXSIZE));//MOJE posledni spatne
         //if (x > BOXSIZE) {BOX += (BOXSIZE % x) / BOXSIZE;} //TADY NAK 9x9 BLBNE TO DELENI ATD NEFUNGUJEE TO PRESNE..        
        //BOX++;
        
       
        //BOX = (byte) ((byte) (Math.ceil((double) x/BOXSIZE))+(Math.ceil((double) y/BOXSIZE)));//MOJE posledni spatne
               
         ///BOX = (byte) ((byte) ((x-1) % STRANA) * STRANA + (y-1) + 1);//HONZAAA?

    }   

    public Integer getValue() {
        return (int) value; //Nutne pretypovat au
    }

    public int getBox() {
        return (int) BOX; //Nutne pretypovat au
    }

    void assign(int num) {
        value = (byte) num;
        getCandidates().clear();
        board.LeftToSolve--;
    }

    void removeCandidates(Set set) {
        
        if (getCandidates().removeAll(set)) { //SNIZENI POCTU "FALESNYCH ITERACI"
            
        if (getCandidates().size() == 1) {
            int tmpvalue = (byte) (int) getCandidates().toArray()[0];
            assign(tmpvalue);
            
            //value = (byte) (int) getCandidates().toArray()[0];
            //board.LeftToSolve--;
        }
        board.IteraceKandidatu++;
        }
        
    }
    
  
    @Override
    public String toString() {
        return (String.valueOf(x) + "," + String.valueOf(y) + getCandidates());
    }

    /**
     * @return the candidates
     */
    public Set getCandidates() {
        return candidates;
    }
    
    public int valuesInBox() {
        return (int) board.stream().filter(boxMatch(BOX).and(hasValue())).count();
    }

    @Override
    public int compareTo(Position o) {
        
        if (this.x == o.x) { 
            return (this.y < o.y ? -1: 1);
        }
        else {
            return (this.x < o.x ? -1 : 1 );
        }
        
        //if (this.x <= o.x && this.y <= o.y) return -1; else return 1;
        
    }

}
