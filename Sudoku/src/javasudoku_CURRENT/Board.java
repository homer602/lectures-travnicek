/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author evo
 */
public final class Board {

    final int BOARD_SIZE;  //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16
    final int BOXSIZE;  //velikost maleho ramecku po 4 nebo 9
    final int NUMRANGE;
    //private static final int BOARD_SIZE = Variables.BOARD_SIZE; //4x4 board cislovani shora
    public int leftToSolve;
    public int iteraceKandidatu = 0;
    public Map boardSet = new HashMap<Pair, Position>(); //NESKODNY BUG V TYPU KOLEKCE //LEGACY jaky je rozdil mezi <NIC>  a <NECO>
    //public Map<Pair,Position> boardSet = new HashMap<>(); //rozdil?
    //public Map<Pair,Position> boardSet = new HashMap(); //rozdil?
    public Position[][] positions;
    public Set<AtomicInteger>[] rowCandidates;
    public Set<AtomicInteger>[] colCandidates;
    public Set<AtomicInteger>[] boxCandidates;

    public Board(int size) {

        BOARD_SIZE = size; //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16

        BOXSIZE = (int) Math.sqrt(BOARD_SIZE);  //velikost maleho ramecku po 4 nebo 9

        NUMRANGE = BOARD_SIZE;

        leftToSolve = BOARD_SIZE * BOARD_SIZE;

        rowCandidates = new Set[NUMRANGE + 1];
        colCandidates = new Set[NUMRANGE + 1];
        boxCandidates = new Set[NUMRANGE + 1];
        for (int i = 1; i <= NUMRANGE; i++) {
            rowCandidates[i] = new HashSet();
            colCandidates[i] = new HashSet();
            boxCandidates[i] = new HashSet();
        }


        //NASLEDUJICI LEGACY KOD JE TROCHU HALUZ, V NETBEANS TO PRIDA, TADY UBERE POCET ITERACI
        //VIZ I PODOBNE AKCE S CYKLY DAL... nejak se to jinak promicha v pameti
        //PROTOZE TY MNOZINY HASHSET NEJSOU RAZENE A V PRIPADE PARALELSTREAM JE NAVIC ZPRACOVANI NELINEARNI/NAHODNE
//        final Set initCandidates = new HashSet();
//        for (int i = 1; i <= NUMRANGE; i++) {
//            initCandidates.add(new AtomicInteger(i));
//        }

        this.positions = new Position[BOARD_SIZE + 1][BOARD_SIZE + 1];//+1 /nezajima me nulove pole
//        for (int x = 1; x <= BOARD_SIZE; x++) {
//            for (int y = 1; y <= BOARD_SIZE; y++) {
//                //je potreba predavat celou tuto sracku? co predat, jen x, y,this?  nazor? :)
//                positions[x][y] = new Position(x, y, this, rowCandidates[x], colCandidates[y], boxCandidates[getBox(x, y)]);
//                boardSet.put(new Pair(x, y), positions[x][y]); // Pair je ke smazani dobry
//
//            }
//        }

        //POKUD TO UDELAM TAKTO, TAK SE TO NEJAK POZDNE SYNCHRONIZUJE - VYRESI SE VE VICE ITERACICH NA THREADY (paralelStream)
        executeInDoubleLoop((x, y) -> {
            //je potreba predavat celou tuto sracku? co predat, jen x, y,this?  nazor? :)
            positions[x][y] = new Position(x, y, Board.this, rowCandidates[x], colCandidates[y], boxCandidates[Board.this.getBox(x, y)]);
            boardSet.put(new Pair(x, y), positions[x][y]);
        });


        //executeInDoubleLoop((x, y) -> );
        Collection<Position> temp = boardSet.values();
        temp.forEach(Position::addSetIntersection);
    }

    interface LoopRunner {

        public abstract void iterate(int x, int y);
        //public abstract void postFirstLoop(int x, int y);


    }


    void executeInDoubleLoop(LoopRunner run) {
        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                run.iterate(x,y);

            }
        }

    }

    /**
     * poloha(cislo) ctverce
     */
    int getBox(int x, int y) {
        int rowIndex = (int) Math.ceil((double) x / BOXSIZE) - 1;
        return (int) ((Math.ceil((double) y / BOXSIZE)) + BOXSIZE * (rowIndex));
    }

    //Board getInstance(){// <<<< jakoze "board." vrati rovnou board ALE jak VRATI TEN SET???
    //    return this;
    //}
    //Set getInstance(){// <<<< jakoze neco jako toto resp chci pres board. vyvolat nejakou kolekci
    //  return board;
    //}


    void parseInput(int[][] input) {

//        for (int x = 1; x <= BOARD_SIZE; x++) {
//            for (int y = 1; y <= BOARD_SIZE; y++) {
//                int number = input[x - 1][y - 1];
//                if (number > 0) {
//                    positions[x][y].assign(number);
//                }
//            }
//        }

        //TADY COKOLI SE TAKHLE ZMENI TAK TO MA VLIV NA POCET ITERACI
        executeInDoubleLoop((x, y) -> {
            int number = input[x - 1][y - 1];
            //priradime nenulove hodnoty hraci desky
            if (number > 0) {
                positions[x][y].assign(number);
            }
        });
    }


    public void printPolicka(int option) {

        int val = 0;

        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {

                val = PRINT_VALUE;
                if ((option & val) == val) { //COMPARE BITMASK
                    System.out.print(positions[x][y].valueRef() + " ");
                }
                //System.out.print(policka[x][y].getBox() + " "); //VYPISE CISLA BOXU
                Position pole = positions[x][y];

                val = PRINT_CANDIDATES;
                if ((option & val) == val) { //COMPARE BITMASK
                    if (pole.valueRef().get() > 0) {
                        System.out.print(pole.valueRef() + " ");
                    } else {
                        System.out.print(pole.getCandidates() + " ");
                    }
                }
                //jakoby val = PRINT_INTERNAL;  //quick and dirty SMAZAT
                if ((option) == 0) {
                    if (pole.valueRef().get() > 0) {
                        System.out.print(pole.valueRef() + " ");
                    } else {
                        System.out.print("*" + pole.getCandidates() + "i");
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
