/*
 * To change this template file, choose Tools | Templates
 */
package javasudokuhonza;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import static javasudokuhonza.Variables.*;

/**
 *
 * @author Michal
 */
public class Position {

    private byte value = 0;
    public byte x;  //souradnice x - K NICEMU POKUD MAM ROW apod?
    public byte y;  //souradnice y
    private Set candidates = new HashSet<Integer>();//{0,1,2,3,4...,8,9}; pouzit BitSet
    public byte ROW;
    public byte COLUMN;
    public byte BOX;

    public Position(int x, int y) {
        this.x = (byte) x;
        this.y = (byte) y;
        for (int i = 1; i <= Variables.STRANA; i++) {
            candidates.add(i);
        }
        ROW = (byte) x;
        COLUMN = (byte) y;
        //BOX = (byte) ((byte) (x + y) % 2 + 1);  //?????????????
        //BOX = (byte) ((byte) (boxsize % x) + (y>boxsize ? boxsize %y +1:0));
        BOX = (byte) ((byte) ((BOXSIZE % x) / 2) + ((BOXSIZE % y) / 2));//MOJE posledni spatne
       
        
        
        if (x > BOXSIZE) {
            BOX += (BOXSIZE % x) / BOXSIZE; //TADY NAK 9x9 BLBNE TO DELENI ATD NEFUNGUJEE TO PRESNE..
        }
        BOX++;
        
         ///BOX = (byte) ((byte) ((x-1) % STRANA) * STRANA + (y-1) + 1);//HONZAAA

    }   

    public Integer getValue() {
        return (int) value; //Nutne pretypovat au
    }

    public int getBox() {
        return (int) BOX; //Nutne pretypovat au
    }

    void assign(int num) {
        value = (byte) num;
        candidates.clear();
        Variables.LeftToSolve--;//kolik zbyva
    }

    void removeCandidates(Set set) {
        candidates.removeAll(set);
        if (candidates.size() == 1) {
            value = (byte) (int) candidates.toArray()[0];
            Variables.LeftToSolve--; //quick and dirty
        }
        Variables.IteraceKandidatu++; //quick and dirty
    }
    
     
    

    @Override
    public String toString() {
        return (String.valueOf(x) + "," + String.valueOf(y) + candidates);
    }

}
