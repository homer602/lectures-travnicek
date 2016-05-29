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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.*;

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
public class JavaSudoku_copy {

    static Map globalRefs;
    static Map globVals;
    static Board globBoard;

    public static void main(String[] args) {

        //List rowList = new LinkedList<Set>();
        Map mappings = new LinkedHashMap<Integer, Set>();
        Map values = new LinkedHashMap<Integer, Set>();
        //Map colList = new LinkedHashMap<String,Set>();
        //Map boxList = new LinkedHashMap<String,Set>();

        final int ROW = 64;
        final int COL = 128;
        final int BOX = 256;

        Board board = new Board(BoardType.SMALL);
        globBoard = board;

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

        for (int y = 1; y <= board.NUMRANGE; y++) {
            Collection boardMap = board.boardSet.values();
            Set tempMap = (Set) boardMap.stream().filter(rowMatch(y)).collect(Collectors.toSet());
            Set tempVal = (Set) boardMap.stream().filter(rowMatch(y)).collect(Collectors.mapping(Position::valueRef, Collectors.toSet()));
            mappings.put(ROW + y, tempMap);
            values.put(ROW + y, tempVal);
            tempMap = (Set) boardMap.stream().filter(colMatch(y)).collect(Collectors.toSet());
            tempVal = (Set) boardMap.stream().filter(colMatch(y)).collect(Collectors.mapping(Position::valueRef, Collectors.toSet()));
            mappings.put(COL + y, tempMap);
            values.put(COL + y, tempVal);
            tempMap = (Set) boardMap.stream().filter(boxMatch(y)).collect(Collectors.toSet());
            tempVal = (Set) boardMap.stream().filter(boxMatch(y)).collect(Collectors.mapping(Position::valueRef, Collectors.toSet()));
            mappings.put(BOX + y, tempMap);
            values.put(BOX + y, tempVal);
        }

        Set<Position> reset = (Set<Position>) board.boardSet
                .values().stream()
                .filter(rowMatch(1)).collect(Collectors.mapping(Position::valueRef, Collectors.toSet()));

        System.out.println(reset);

       //** board.positions[1][2].assign(2);
        System.out.println(reset);

        //System.out.println(mappings);
        globalRefs = mappings;
        globVals = values;
        System.out.println("");
        //board.printPolicka(PRINT_CANDIDATES);
        //**board.positions[2][2].assign(3);
        System.out.println("");
        board.printPolicka(PRINT_CANDIDATES);

        //System.out.println(board.rowCandidates[1]);
        solve(ROW, 1);

        Set<Position> z = new HashSet();
        z.addAll(board.boardSet.values());
        z.stream().forEach(x -> x.clearCandidates());
        board.printPolicka(PRINT_CANDIDATES);
        System.out.println("");
        //
        board.printPolicka(0);

        // ODEBRAT KANDIDATY TAKY ODKAZEM? resp neodebirat?
    }

    static final void solve(int key, int offset) {
        key = key + offset;
        Set<Position> s = (Set<Position>) globalRefs.get(key);
        Set vals = (Set) globVals.get(key);
        Set valx = new HashSet();

        System.out.println("vals" + vals);

        //s.stream().map(x -> x.valueRef()).forEach(System.out::print);
        System.out.println("");
        //s.stream().map(x -> x.getCandidates()).forEach(System.out::print);
        System.out.println("");
        System.out.println("");

        //s.stream().forEach(x -> x.removeCandidates(vals));
        //s.stream().forEach(x -> x.);
        //globBoard.rowCandidates[offset].removeAll(vals);
        s.stream().map(x -> x.valueRef()).forEach(System.out::print);
        System.out.println("");
        s.stream().map(x -> x.getCandidates()).forEach(System.out::print);
        System.out.println("");
        System.out.println(globalRefs.values());

    }

}
