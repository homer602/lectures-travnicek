/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.*;
import static javasudoku.GroupingPredicates.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static javasudoku.Variables.*;

/**
 *
 * @author Michal
 */
public class Position_copy implements Comparable <Position_copy>  {

    private AtomicInteger value = new AtomicInteger(0);
    public byte x;  //souradnice x - K NICEMU POKUD MAM ROW apod?
    public byte y;  //souradnice y       //AtomicIntegerFieldUpdater >><<<<<<<???
    //private Set candidates = new HashSet<AtomicInteger>();//{0,1,2,3,4...,8,9}; pouzit BitSet
    public Set internalCandidates = new HashSet();//{0,1,2,3,4...,8,9}; pouzit BitSet
    private Set candidates = new HashSet();
    private Set<AtomicInteger> ts = new HashSet();
    public byte ROW;
    public byte COLUMN;
    public byte BOX;
    public AtomicBoolean rowSolved;
    public AtomicBoolean colSolved;
    public AtomicBoolean boxSolved;
    final private Board board;

    public Position_copy(int x, int y, Board board, Set rowExt, Set colExt, Set boxExt) {//BOARD SIZE = board.size apod.. do konstruktoru
        this.x = (byte) x;
        this.y = (byte) y;
        this.board = board;
        
        //internalCandidates.add(initCand);
        
        for (int i=1;i<=board.NUMRANGE;i++) {
            internalCandidates.add(i);
        }
        
        //initCand=candidates;
        rowExt.add(valueRef());
        colExt.add(valueRef());
        boxExt.add(valueRef());
        candidates.add(rowExt);  //schvalne co dela addAll
        internalCandidates.removeAll(rowExt);
        internalCandidates.removeAll(colExt);  // ASI nedeela nic
        internalCandidates.removeAll(boxExt);
        ts.addAll(rowExt);
        ts.addAll(colExt);
        ts.addAll(boxExt);
        //board.rowCandidates[x].add(1);
        
         
         
        //candidates.add(initCand);
        
       // for (int i = 1; i <= board.NUMRANGE; i++) {  // PRENEST VEN?
       //     candidates.add(i);  // BLBOST DELA SE XKRAT STACI JEDNOU **************************8
       // }
        
        ROW = (byte) x;
        COLUMN = (byte) y;    // x = ROW -- REDUNDANCE
        
//        int rowIndex = (int) Math.ceil((double) x/board.BOXSIZE)-1;
//        BOX = (byte) ((Math.ceil((double) y/board.BOXSIZE))+board.BOXSIZE*(rowIndex));
                
        BOX = (byte) board.getBox(x, y);
        
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
    
    public boolean clearCandidates() {
          //this.rowcandiates = 
          //Set<AtomicInteger> ts = new HashSet();
          ts.addAll(board.rowCandidates[x]);
          ts.addAll(board.colCandidates[y]); // SHROMAZDENE KANDIDATY ze vsech poli
          ts.addAll(board.boxCandidates[BOX]);// NUTNE AKTUALIZOVAT PROC? nestaci prirazeni z konstruktoru?
          
          Set reset = candidates;
          internalCandidates.removeAll( // z odkazovane hodnoty ziskam vlastni hodnotu
                  ts.stream().collect(Collectors.mapping(m -> m.get(), Collectors.toSet()))                  
          );
          
          if (internalCandidates.size() == 1) {
              int tmpvalue = (byte) (int) internalCandidates.toArray()[0];
                //assign(tmpvalue);  // TOTO JE DOBRE ZAKOMENTOVAT
                return true;
            
        }
          else return false;
    }

    public AtomicInteger valueRef() {
        return value; 
    }

    public int getBox() {
        return (int) BOX; //Nutne pretypovat au
    }

    void assign(int num) {
//      if (value.get()>0) return; //bez tohoto "zahadna" propagace na 4x4
        value.set(num);
        getCandidates().clear();
        board.leftToSolve--;
    }

    void removeCandidates(Set set) {
        
        //candidates.remove(new AtomicInteger(1));
        if (getCandidates().removeAll(set)) { //SNIZENI POCTU "FALESNYCH ITERACI"
            
            if (getCandidates().size() == 1) {
                int tmpvalue = (byte) (int) getCandidates().toArray()[0];
                assign(tmpvalue);

                //value = (byte) (int) getCandidates().toArray()[0];
                //board.LeftToSolve--;
            }
        board.iteraceKandidatu++;
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
        //return (int) board.stream().filter(boxMatch(BOX).and(hasValue())).count();
        return 1;//****
    }

    @Override
    public int compareTo(Position_copy o) {
        
        if (this.x == o.x) {
            if (this.y == o.y) return 0;// NOVE PRIDANY RADEK
            return (this.y < o.y ? -1 : 1);
        }
        else {
            return (this.x < o.x ? -1 : 1);
        }
        
        //if (this.x <= o.x && this.y <= o.y) return -1; else return 1;
        
    }

}
