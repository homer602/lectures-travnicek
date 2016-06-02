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
        Map<Integer, Set> mappings = new LinkedHashMap();//uz na nic....
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
        //alternativni zpusob k vlozeni dat
        BOARD_SMALL.positions[1][1].assign(1);
        BOARD_SMALL.positions[2][3].assign(1);
        BOARD_SMALL.positions[3][1].assign(4);
        BOARD_SMALL.positions[3][3].assign(3);
        BOARD_SMALL.positions[4][2].assign(3);

        // vyberem board na hrani
        Board board = BOARD_BIG;

       //po promazani tech map co nepotrebujeme je tu cisto a navic to dava lepsi iterace ze :)

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

        //aha no tady muzem vyuzit ten paralel stream ale vysledky to dava nahodne
        // to je neco jako pri pouziti threadu coz je jasne :)
//        z.parallelStream().forEach(x -> x.clearCandidates());
//        z.parallelStream().forEach(x -> x.clearCandidates());
//        z.parallelStream().forEach(x -> x.clearCandidates());
//        z.parallelStream().forEach(x -> x.clearCandidates());
//        z.parallelStream().forEach(x -> x.clearCandidates());
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
