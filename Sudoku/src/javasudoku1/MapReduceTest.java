/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku1;

import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javasudoku.GroupingPredicates.*;
import static javasudoku.JavaSudokuSmall.global;

/**
 *
 * @author evo
 */
public class MapReduceTest {

    public static void main(String[] args) {
        Board board1 = new Board(Variables.BoardType.BIG);
        global = board1;

        int[][] setting = { //MF DNES 23.8.2010, priloha leto
            {0, 0, 4, 0, 3, 6, 9, 2, 7},
            {1, 0, 0, 0, 0, 5, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0, 0, 4},
            {0, 0, 5, 0, 0, 0, 0, 6, 0},
            {6, 4, 0, 0, 0, 0, 0, 8, 5},
            {0, 7, 0, 0, 0, 0, 2, 0, 0},
            {5, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0, 2},
            {4, 3, 7, 9, 2, 0, 5, 0, 0}
        };

        board1.parseInput(setting);
        //   Set reset = (Set) board1.boardSet.entrySet().stream().collect(Collectors.toSet());
        //   TreeSet tree = new TreeSet(reset);

        //board1.getBoard().stream().filter(f -> !f.getValue().equals(null)).map(e -> e.getValue()).
        //board1.getBoard().stream().filter(p -> p.getKey().hashCode()>10).forEach(System.out::print);
        board1.getBoard().stream().filter(p -> p.getKey().equals(new Pair(1, 1))).forEach(System.out::print);
        //board1.getBoard().stream().filter(p -> p.getKey().hashCode()>>2 >1).forEach(System.out::print);

        System.out.println(new Pair(1, 1));
        System.out.println(new Pair(1, 1).equals(new Pair(1, 1)));
        println(new Pair(1, 1) == new Pair(1, 1));

        for (int x = 0; x < 10; x++) {

            for (int y = 0; y < 10; y++) {
                System.out.println((x << 2 | y << 4));

            }

        }
           
        int temp = 136 >> 4;
        System.out.println(temp);
        
        //board1.getBoard().stream().forEach(System.out::print);
        Consumer build = new Stream.Builder() {
            @Override
            public void accept(Object t) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Stream build() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        Consumer printConsumer = new Consumer() {
            @Override
            public void accept(Object t) {
                System.out.print(t);
            }
        };

        //** println(tree);
        //println(null);
        //   List list1 = new ArrayList(tree); // GENERIKA ZAJISTI JEN COMPILE CHECK
        //  list1.add("ahoj");
        //list1.forEach(printConsumer);
        for (int z = 0; z < 2; z++) {

            //System.out.println(z%5);
            //Arrays.stream(global.positions).flatMap(Arrays::stream).filter(x -> x!=null).filter(hasValue()).forEach(MapReduceTest::println);
            // Arrays.stream(global.positions).flatMap(Arrays::stream).filter(hasValue()).forEach(System.out::print);
            //TOTO NEJEDE PROC? KVULI TOMU NULLU!!!!
            println("");
            //Arrays.stream(global.positions).flatMap(Arrays::stream).forEach(printConsumer);
            // global.stream().flatMap((x) -> Stream.of(((Position) (x)).getValue())).forEach(printConsumer);

            //Arrays.stream(global.positions).flatMap(Arrays::stream).forEach(printConsumer);
            //Arrays.stream(global.positions).flatMap(Arrays::stream).forEach(System.out::print);
            //global.stream().flatMap((x) -> Stream.of(((Position) (x)).getValue())).forEach(printConsumer);
            //global.stream().filter(hasValue().and(rowMatch(1))).flatMap((x)->Stream.of(Position::getValue));
            //  Set fx = global.stream().filter(hasValue().and(rowMatch(z % 5 + 1))).collect(Collectors.mapping(Position::getValue, Collectors.toSet()));
            //*** fx.forEach(MapReduceTest::println);
            //println();
            //tree.stream().filter(hasValue()).
            //tree.stream().filter(hasValue()).flatMap((x) -> Stream.of(((Position) (x)).getValue())).forEach(System.out::print);
            //println();
        }

        //println();
    }

    //  public static <T> Stream<T> stream(T[] array) {
    // return stream(array, 0, array.length);
    // }
    private static void printcln() {
        System.out.println("");
    }

    public static <T> void println(T str) {
        System.out.println(String.valueOf(str));
    }
    /* public static void println(Object str) {
        System.out.println(str);
    }
     */

}
