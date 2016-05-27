/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudokuhonza;

import java.util.function.Predicate;

/**
 *
 * @author evo
 */
public class GroupingPredicates {

    public static Predicate<Position> matchRow(int x) {
        return p -> p.ROW == x;
    }

    public static Predicate<Position> matchCol(int y) {
        return p -> p.COLUMN == y;
    }

    public static Predicate<Position> matchBox(int z) {
        //void neco(){System.out.println("");}
        return new Predicate<Position>() {
            //public String neco(){return "ahoj";};
            @Override
            public boolean test(Position p) {
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
        

}
