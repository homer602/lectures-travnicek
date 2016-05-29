package javasudoku1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static javasudoku.GroupingPredicates.*;
import static javasudoku.Variables.*;

/**
 *
 * @author Michal
 */
public class JavaSudokuBordel {

    public static void main(String[] args) {

        //List rowList = new LinkedList<Set>();
        Map mappings = new LinkedHashMap<String,Set>();
        //Map colList = new LinkedHashMap<String,Set>();
        //Map boxList = new LinkedHashMap<String,Set>();
        

        Board board = new Board(BoardType.SMALL);

        board.positions[1][1].assign(1);
        board.positions[2][3].assign(1);
        board.positions[3][1].assign(4);
        board.positions[3][3].assign(3);
        board.positions[4][2].assign(3);

        board.printPolicka(Variables.PRINT_VALUE);

        System.out.println("");

        Set s = new HashSet();
        s.addAll(board.getBoard());

        //List list = Arrays.asList(board.positions);
        SortedSet<Position> ss = new TreeSet(board.boardSet.values());

        for (int x = 1; x <= board.NUMRANGE; x++) {
            SortedSet<Position> tempSet = ss.subSet(board.positions[x][1], board.positions[x][board.NUMRANGE]);
            mappings.put("ROW"+x, tempSet);
        }
        
       for (int y = 1; y <= board.NUMRANGE; y++) {
        Set tempSet = (Set) board.boardSet.values().stream().filter(colMatch(y)).collect(Collectors.toSet());
        mappings.put("COL"+y, tempSet);
       }
       
       for (int z = 1; z <= board.NUMRANGE; z++) {
        Set tempSet = (Set) board.boardSet.values().stream().filter(boxMatch(z)).collect(Collectors.toSet());
        mappings.put("BOX"+z, tempSet);
       }
        
             
        
        //System.out.println(ss);
        SortedSet<Position> ss1 = ss.subSet(board.positions[1][1], board.positions[1][4]);
        SortedSet<Position> ss2 = ss.subSet(board.positions[2][1], board.positions[2][4]);
        
        //MUSI SE NEJDRIV PRIRADIT MNOZINA

        System.out.println();

        Set unsorted = new HashSet();
        unsorted.add(board.positions[3][3]);

        unsorted.addAll(ss1);
        unsorted.addAll(ss2);

        System.out.println(unsorted);

        Set ne = new Set() { //TODO nadefinovat si vlastni set toodoo
            @Override
            public int size() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isEmpty() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean contains(Object o) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Iterator iterator() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object[] toArray() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object[] toArray(Object[] a) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean add(Object e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean remove(Object o) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean containsAll(Collection c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean addAll(Collection c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean retainAll(Collection c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean removeAll(Collection c) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void clear() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        Set sorted = new TreeSet();
        //sorted.add((Set<Position>)ss.subSet(board.positions[1][1], board.positions[1][4])); CHYBA kvuli prirazeni!
        //sorted.add((Set<Position>) ss.subSet(board.positions[2][1], board.positions[2][4])); CHYBA --- NAVIC DVOJITA MNOZINA!!!!
        System.out.println(sorted);

        //**** unsorted.add(board.boardSet.values().stream().filter(rowMatch(1)).flatMap(x-> Stream.of(x)).collect(Collectors.toSet()));
        System.out.println(unsorted.contains(board.positions[2][1]));

        // System.out.println(ss.subSet(board.positions[2][1], false, board.positions[2][4], false));
        //System.out.println(sx.subSet(1,true, 3,true));
    }

}
/*

 Map t = new HashMap();
        t.put(1, "x1");
        t.put(2, "x2");
        t.put(3, "x3");
        t.put(4, "x4");
        t.put(5, "x5");
        t.put(6, "x6");
        t.put(7, "x7");
        t.put(8, "x8");
        
        Map sec = new HashMap();
        
        
        sec.put(10, t.values());
        
        System.out.println(sec.get(10));
        
        for (int i = 1; i <= 8; i++) {
            
            System.out.print(t.get(i) + " ");
            if (i%4 == 0) System.out.println();
        }
        
        
        System.out.println(t.size());
        
*/
