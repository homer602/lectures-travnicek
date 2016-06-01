package javasudoku_CURRENT;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import static javasudoku_CURRENT.GroupingPredicates.*;
import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author Michal
 */
public class JavaSudoku {

    public static void main(String[] args) {

        final boolean DEBUG = false;

        //List rowList = new LinkedList<Set>();
        Map<Integer, Set> mappings = new LinkedHashMap();
        Map<Integer, Set> values = new LinkedHashMap();
        //Map colList = new LinkedHashMap<String,Set>();
        //Map boxList = new LinkedHashMap<String,Set>();

        final int ROW = 64;
        final int COL = 128;
        final int BOX = 256;

        Board BOARD_BIG = new Board(BoardType.BIG);

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

        BOARD_BIG.parseInput(setting);

        Board BOARD_SMALL = new Board(BoardType.SMALL);

        BOARD_SMALL.positions[1][1].assign(1);
        BOARD_SMALL.positions[2][3].assign(1);
        BOARD_SMALL.positions[3][1].assign(4);
        BOARD_SMALL.positions[3][3].assign(3);
        BOARD_SMALL.positions[4][2].assign(3);

        Board board = BOARD_BIG;

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

        if (DEBUG) {
            board.printPolicka(PRINT_CANDIDATES);
        }
        System.out.println("");

        //System.out.println(board.rowCandidates[1]);
        Set<Position> z = new HashSet();
        z.addAll(board.boardSet.values());
        z.stream().forEach(x -> x.clearCandidates()); //ZATIM JEN RUCNE
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        board.printPolicka(PRINT_CANDIDATES);

        //
        if (DEBUG) {
            System.out.println("");
            board.printPolicka(0);

        }
        System.out.println("");
        System.out.println("Iteraci:"+board.iteraceKandidatu);

    }

}
