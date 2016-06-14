/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_CURRENT;

import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author Michal
 */
public class SudokuPrinter {

    public static void printPolicka(Print option, Board board) {

        for (int x = 1; x <= board.boardSize; x++) {
            for (int y = 1; y <= board.boardSize; y++) {

                if (option.equals(Print.PRINT_VALUE)) {
                    System.out.print(board.positions[x][y].getValue() + " ");
                }
                //System.out.print(policka[x][y].getBox() + " "); //VYPISE CISLA BOXU
                Position pole = board.positions[x][y];
                //TODO Pair x y - jako static

                if (option.equals(Print.PRINT_CANDIDATES)) {
                    if (pole.getValue() > 0) {
                        System.out.print(pole.getValue() + " ");
                    } else {
                        System.out.print(pole.getCandidates() + " ");
                    }
                }
                

                if (y % board.myBoxSize == 0 && y < board.boardSize) {
                    System.out.print("| ");
                }
            }
            // TADY BY TO CHTELO NEJAKY COLORING TECH PODCYKLU
            System.out.println("");
            if (x % board.myBoxSize == 0 && x < board.boardSize) {
                for (int i = 0; i <= board.boardSize + board.myBoxSize - 2; i++) {
                    System.out.print("- ");
                }
                System.out.println("");
            }
        }
    }

}
