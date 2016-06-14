package javasudoku_CURRENT;

import java.util.HashSet;
import java.util.Set;
import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author Michal
 */
public class JavaSudoku {

    public static void main(String[] args) {

        final boolean DEBUG = Variables.DEBUG;

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
        //alternativni zpusob vlozeni dat
        BOARD_SMALL.positions[1][1].assign(1);
        BOARD_SMALL.positions[2][3].assign(1);
        BOARD_SMALL.positions[3][1].assign(4);
        BOARD_SMALL.positions[3][3].assign(3);
        BOARD_SMALL.positions[4][2].assign(3);

        // vyberem board na hrani
        Board board = BOARD_SMALL;

        if (DEBUG) {
            board.printPolicka(Print.PRINT_CANDIDATES);
        }
        System.out.println("");

        Set<Position> z = new HashSet();
        z.addAll(board.boardSet.values()); //YAGNI - Hashmap je zbytecny
        z.stream().forEach(x -> x.clearCandidates()); //ZATIM JEN RUCNE
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());
        z.stream().forEach(x -> x.clearCandidates());

        board.printPolicka(Print.PRINT_CANDIDATES);

        System.out.println("");
        System.out.println("Iteraci:" + board.iteraceKandidatu);

    }

}
