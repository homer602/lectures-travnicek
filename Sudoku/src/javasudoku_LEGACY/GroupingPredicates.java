/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_LEGACY;

import java.util.function.Predicate;

/**
 *
 * @author evo
 */
public class GroupingPredicates {

    
    // UDELAT JEDEN PREDIKAT MATCH NECO - ROW,COL BOX     // kvuli lepsi citelnosti pri prejmenovani 
    public static Predicate<Position> rowMatch(int x) {  // prejmenovat MatchRow jen na Row atd nebo rowMatch//matchRow 
        return p -> p.ROW == x;
    }

    public static Predicate<Position> colMatch(int y) {
        return p -> p.COLUMN == y;
    }

    public static Predicate<Position> boxMatch(int z) { //matchBox = jakoze sirky?
        //void neco(){System.out.println("");}
        return new Predicate<Position>() {
            //public String neco(){return "ahoj";};
            @Override
            public boolean test(Position p) {
                //JavaSudoku.BoxChecks++;
                return p.BOX == z;

            }
        };
    }

    public static Predicate<Position> hasValue() {
        //int z = 1;
        return new Predicate<Position>() {
            //public void neco(){}; 
            @Override
            public boolean test(Position p) {
                return p.getValue() > 0;
            }
        };
    }

    public static Predicate<Position> neighborsNotSolved() {// OR bitova maska -- A | B | C ?

        //return rowNotSolved().and(colNotSolved()).and(boxNotSolved());// PROC TO PRI AND DAVA LEPSI VYSLEDEK?
        return rowNotSolved().or(colNotSolved()).or(boxNotSolved());// JE TO DOBRE?

    }

    public static Predicate<Position> rowNotSolved() {

        return (p -> p.rowSolved.get() == false);

    }

    public static Predicate<Position> colNotSolved() {

        return (p -> p.colSolved.get() == false);

    }

    public static Predicate<Position> boxNotSolved() {

        return (p -> p.boxSolved.get() == false);

    }
    
    //public static Comparator 

}
