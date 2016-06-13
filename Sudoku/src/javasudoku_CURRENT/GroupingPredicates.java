/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.function.Predicate;

/**
 * @author evo
 */
public class GroupingPredicates {


    // no kdyz budu delat p.x = x tak to bude zas matouci ne? (ohledne redundance ROW apod)
    public static Predicate<Position> rowMatch(int x) {  // prejmenovat MatchRow jen na Row atd nebo rowMatch//matchRow 
        return p -> p.ROW == x;
    }

    public static Predicate<Position> colMatch(int y) {
        return p -> p.COLUMN == y;
    }

    public static Predicate<Position> boxMatch(int z) {
        return p -> p.BOX == z;
    }

    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA
     */
    public static Predicate<Position> hasValue() {
        return new Predicate<Position>() {
            //public void neco()?{}...nacpat sem nakou funkci;
            @Override
            public boolean test(Position p) {
                return p.valueRef().get() > 0;// STEJNE SE UZ NEPOUZIVA
            }
        };
    }

    /**
     * LEGACY CODE -- UZ SE NEPOUZIVA ale mozna se jeste hodi...?
     */
//    public static Predicate<Position> neighborsNotSolved() {// OR bitova maska -- A | B | C ?
//
//        //return rowNotSolved().and(colNotSolved()).and(boxNotSolved());// PROC TO PRI AND DAVA LEPSI VYSLEDEK?
//        return rowNotSolved().or(colNotSolved()).or(boxNotSolved());// JE TO DOBRE? :)
//
//    }

}
