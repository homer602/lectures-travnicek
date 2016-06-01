/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_CURRENT;

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

    private AtomicInteger value = new AtomicInteger(0);
    private boolean valueset = false;
    public byte x;  //souradnice x - K NICEMU POKUD MAM ROW apod? JE TO ZLY? :)
    public byte y;  //souradnice y       //AtomicIntegerFieldUpdater >><<<<<<<???
    //private Set candidates = new HashSet<AtomicInteger>();//{0,1,2,3,4...,8,9}; pouzit BitSet
    public Set internalCandidates = new HashSet();//{0,1,2,3,4...,8,9}; pouzit BitSet
    private final Set candidates = new HashSet();
    private final Set<AtomicInteger> candidatesToRemove = new HashSet();
    public byte ROW;
    public byte COLUMN;
    public byte BOX;
    public AtomicBoolean rowSolved;//pujde pryc
    public AtomicBoolean colSolved;
    public AtomicBoolean boxSolved;
    final private Board board;

    public Position(int x, int y, Board board, Set rowExt, Set colExt, Set boxExt) {//BOARD SIZE = board.size apod.. do konstruktoru
        this.x = (byte) x;
        this.y = (byte) y;
        this.board = board;
        
        //internalCandidates.add(initCand);
        
            //candidates.add(initCand);
        for (int i=1;i<=board.NUMRANGE;i++) {  // BLBOST DELA SE XKRAT STACI JEDNOU **************************
            internalCandidates.add(i);
        }
            //internalCandidates.add(board.initCandidates);  //TODO
        
        //initCand=candidates;
        rowExt.add(valueRef());
        colExt.add(valueRef());
        boxExt.add(valueRef());
        //candidates.add(rowExt);  //schvalne co dela addAll
        internalCandidates.removeAll(rowExt);
        internalCandidates.removeAll(colExt);  // ASI nedeela nic
        internalCandidates.removeAll(boxExt);
        candidatesToRemove.addAll(rowExt);
        candidatesToRemove.addAll(colExt);
        candidatesToRemove.addAll(boxExt);
        
        ROW = (byte) x;
        COLUMN = (byte) y;    // x = ROW -- REDUNDANCE
        
        BOX = (byte) board.getBox(x, y);
        
        rowSolved = board.solvedRows.get(x);
        colSolved = board.solvedCols.get(y);
        boxSolved = board.solvedBoxes.get(BOX);

    }
    
    public boolean clearCandidates() {
        
          if (valueset) {
              return false;
        }
          // Tato funkce je docasne jeste zprasena
          board.iteraceKandidatu++;
          candidatesToRemove.addAll(board.rowCandidates[x]);
          candidatesToRemove.addAll(board.colCandidates[y]); // SHROMAZDENE KANDIDATY ze vsech poli
          candidatesToRemove.addAll(board.boxCandidates[BOX]);// NUTNE AKTUALIZOVAT PROC? nestaci prirazeni z konstruktoru?
          
          internalCandidates.removeAll( // z odkazovane hodnoty ziskam vlastni hodnotu
                  candidatesToRemove.stream().collect(Collectors.mapping(m -> m.get(), Collectors.toSet()))                  
          );
          
          if (internalCandidates.size() == 1 && value.get() == 0) {//kdyz se nekontroluje value tak to "zahadne" propaguje
              int tmpvalue = (byte) (int) internalCandidates.toArray()[0];
                assign(tmpvalue);  // TOTO JE DOBRE ZAKOMENTOVAT pro debug :)
                //clearCandidates();
                return true;   //kontrola zpetne hodnoty pres Optional?
            
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
        return candidates;
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
