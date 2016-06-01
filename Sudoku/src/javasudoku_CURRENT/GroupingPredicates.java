/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.function.Predicate;

/**
 *
 * @author evo
 */
public class GroupingPredicates {

    
    // UDELAT JEDEN PREDIKAT MATCH ALL, pripadne prejmenovat na ROW,COL BOX kvuli lepsi citelnosti
    public static Predicate<Position> rowMatch(int x) {  // prejmenovat MatchRow jen na Row atd nebo rowMatch//matchRow 
        return p -> p.ROW == x;
    }

    public static Predicate<Position> colMatch(int y) {
        return p -> p.COLUMN == y;
    }

    public static Predicate<Position> boxMatch(int z) { //matchBox = jakoze sirky?
        return new Predicate<Position>() {
            @Override
            public boolean test(Position p) {
                //JavaSudoku.BoxChecks++;//hm?
                return p.BOX == z;

            }
        };
    }
    
    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> hasValue() {
        //int z = 1;
        return new Predicate<Position>() {
            //public void neco(){}; 
            @Override
            public boolean test(Position p) {
                return p.valueRef().get() > 0;// STEJNE SE UZ NEPOUZIVA
            }
        };
    }
    
     /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> neighborsNotSolved() {// OR bitova maska -- A | B | C ?

        //return rowNotSolved().and(colNotSolved()).and(boxNotSolved());// PROC TO PRI AND DAVA LEPSI VYSLEDEK?
        return rowNotSolved().or(colNotSolved()).or(boxNotSolved());// JE TO DOBRE?

    }
    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> rowNotSolved() {

        return (p -> p.rowSolved.get() == false);

    }
    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> colNotSolved() {

        return (p -> p.colSolved.get() == false);

    }

    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> boxNotSolved() {

        return (p -> p.boxSolved.get() == false);

    }
    
    //public static Comparator ..?

}
