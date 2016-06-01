/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_LEGACY;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javasudoku_LEGACY.Variables.*;

/**
 *
 * @author evo
 */
public class Board {

    //private static final int BOARD_SIZE = Variables.BOARD_SIZE; //4x4 board cislovani shora
    public int LeftToSolve;
    public int IteraceKandidatu = 0;

    //public static int BOARD_SIZE;   // POZOR STATIC NA VIC INSTANCI - kvuli kompatibilite s funkcemi na pole
    //public int BOARD_S;  // PRO INSTANCE
    public Set boardSet = new HashSet<Position>(); // zmenit na TreeSet - dava horsi vysledky... razeni je nahodne pri HashSEt
    public Position[][] positions;
    public AtomicBooleanArray solvedRows;
    public AtomicBooleanArray solvedCols;
    public AtomicBooleanArray solvedBoxes;

    int BOARD_SIZE;  //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16

    int BOXSIZE = (int) Math.sqrt(BOARD_SIZE);  //velikost maleho ramecku po 4 nebo 9

    int NUMRANGE = BOARD_SIZE;

    //public  RowSolved solvedRows;
    //public  RowSolved solvedRows;
    public Board(int size) {

        BOARD_SIZE = size; //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16

        BOXSIZE = (int) Math.sqrt(BOARD_SIZE);  //velikost maleho ramecku po 4 nebo 9

        NUMRANGE = BOARD_SIZE;
        
        LeftToSolve = BOARD_SIZE * BOARD_SIZE;


        final Set initCandidates = new HashSet();
        for (int i = 1; i <= NUMRANGE; i++) {
            initCandidates.add(i);   // udelat pres stream generator?
        }

        this.solvedRows = new AtomicBooleanArray(this);//+1 /nezajima me nulove pole
        this.solvedCols = new AtomicBooleanArray(this);//+1 /nezajima me nulove pole
        this.solvedBoxes = new AtomicBooleanArray(this);//+1 /nezajima me nulove pole

        this.positions = new Position[BOARD_SIZE + 1][BOARD_SIZE + 1];//+1 /nezajima me nulove pole
        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                positions[x][y] = new Position(x, y, this, initCandidates);
                boardSet.add(positions[x][y]);
            }
        }
    }

    //Board getInstance(){// <<<< jakoze board vrati rovnou board ALE jak VARATI TEN SET???
    //    return this;
    //}
    //Set getInstance(){// <<<< jakoze neco jako toto
    //  return board;
    //}
    
    Set getBoard() {
        //boardSet.forEach(x->x.);// NEJAKA SELEKCE ******************************
        return boardSet;

    }

    void parseInput(int[][] input) {

        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                int number = input[x - 1][y - 1];
                if (number > 0) {
                    positions[x][y].assign(number);
                }
            }
            //System.out.println("" + is.);
        }
    }

    Stream<Position> stream() {
        return boardSet.stream();
    }

    Set getSubset(Predicate pred) {
        return (Set) boardSet.stream().filter(pred).collect(Collectors.toSet());

    }

    //remove candidates on row
    void eliminateByRow(Position[][] policka) {
        for (int x = 1; x <= BOARD_SIZE; x++) {
            Set rowcandidates = collectValuesFromRow(policka, x);
            for (int y = 1; y <= BOARD_SIZE; y++) {
                //dat sem remove columns na naky if
                policka[x][y].removeCandidates(rowcandidates);
            }
        }
    }

    void eliminateByColumn(Position[][] policka) {
        for (int y = 1; y <= BOARD_SIZE; y++) {
            Set rowcandidates = collectValuesFromColumn(policka, y);
            for (int x = 1; x <= BOARD_SIZE; x++) {
                //dat sem remove columns na naky if
                policka[x][y].removeCandidates(rowcandidates);
            }
        }
    }

    void iterateRow(Runnable rum) {
        int i;
        for (i = 1; i <= BOARD_SIZE; i++) // TODOOOOOOOOOOOOOO
        {
            rum.run();
        }

    }
///zmenit na collect podle Predicate

    Set collectValuesFromRow(Position[][] policka, int row) {

        //return board.stream().filter((Policko p) -> p.value!=0)
        Set temp = new HashSet();
        for (int y = 1; y <= BOARD_SIZE; y++) {
            Position pole = policka[row][y];
            if (pole.getValue() > 0) {
                temp.add(pole.getValue());
            }
        }
        return temp;
    }

    Set collectValuesFromColumn(Position[][] policka, int column) {

        //return board.stream().filter((Policko p) -> p.value!=0)
        Set temp = new HashSet();
        for (int x = 1; x <= BOARD_SIZE; x++) {
            Position pole = policka[x][column];
            if (pole.getValue() > 0) {
                temp.add(pole.getValue());
            }
        }
        return temp;
    }

    //ulozit hodnoty collect do promennych v main?
    //boxu je stejne jako policek na jedne strane = 4 a obsahuji tez 4
    // asi to chce priradit naky TAG pro ROW,COLUMN,a BOX
    Set collectValuesFromBox(Position[][] policka, int box) {

        //QUICK AND DIRTY vylepsit!!!!
        Set temp = new HashSet();
        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                if (policka[x][y].getBox() == box) {
                    temp.add(policka[x][y].getValue());
                }
            }

        }
        return temp;
    }

    private void eliminateByBox(Position[][] policka) {

        for (int i = 1; i <= BOARD_SIZE; i++) {
            Set boxcandidates = collectValuesFromBox(policka, i);
            for (int x = 1; x <= BOARD_SIZE; x++) {
                for (int y = 1; y <= BOARD_SIZE; y++) {
                    if (policka[x][y].getBox() == i) {//BOX odpovida
                        policka[x][y].removeCandidates(boxcandidates);
                    }
                }
            }

        }

    }

    static void printBoard(Set board) { //smazat?
        for (Object col : board) {

            System.out.println(col);
        }

    }

    public void printPolicka(int option) {

        int val = 0;

        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {

                val = PRINT_VALUE;
                if ((option & val) == val) { //COMPARE BITMASK
                    System.out.print(positions[x][y].getValue() + " ");
                }
                //System.out.print(policka[x][y].getBox() + " ");
                Position pole = positions[x][y];

                val = PRINT_CANDIDATES;
                if ((option & val) == val) { //COMPARE BITMASK
                    if (pole.getValue() > 0) {
                        System.out.print(pole.getValue() + " ");
                    } else {
                        System.out.print(pole.getCandidates() + " ");
                    }
                }

                if (y % BOXSIZE == 0 && y < BOARD_SIZE) {
                    System.out.print("| ");
                }
            }

            System.out.println("");
            if (x % BOXSIZE == 0 && x < BOARD_SIZE) {
                for (int i = 0; i <= BOARD_SIZE + BOXSIZE - 2; i++) {
                    System.out.print("- ");
                }
                System.out.println("");
            }
        }
    }
}
