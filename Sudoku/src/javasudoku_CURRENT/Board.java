/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author evo
 */
public final class Board {

    //private static final int BOARD_SIZE = Variables.BOARD_SIZE; //4x4 board cislovani shora
    public int leftToSolve;
    public int iteraceKandidatu = 0;

    public Map boardSet = new HashMap<Pair, Position>(); //NESKODNY BUG V TYPU KOLEKCE //LEGACY jaky je rozdil mezi <NIC>  a <NECO>
    //public Map<Pair,Position> boardSet = new HashMap<>(); //NESKODNY BUG V TYPU KOLEKCE //LEGACY jaky je rozdil mezi <NIC>  a <NECO>
    //public Map<Pair,Position> boardSet = new HashMap(); //NESKODNY BUG V TYPU KOLEKCE //LEGACY jaky je rozdil mezi nebo uplne bez <>
    public Position[][] positions;
    public MyAtomicBooleanArray solvedRows;
    public MyAtomicBooleanArray solvedCols;
    public MyAtomicBooleanArray solvedBoxes;

    public Set<AtomicInteger>[] rowCandidates;
    public Set<AtomicInteger>[] colCandidates;
    public Set<AtomicInteger>[] boxCandidates;

    final int BOARD_SIZE;  //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16

    final int BOXSIZE;  //velikost maleho ramecku po 4 nebo 9

    final int NUMRANGE;

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

        final Set initCandidates = new HashSet();
//        for (int i = 1; i <= NUMRANGE; i++) {     //TOTO ZVYSI POCET ITERACI PROC????
//            initCandidates.add(new AtomicInteger(i));   // udelat pres stream generator?
//        }
        for (int i = 1; i <= NUMRANGE; i++) {
            initCandidates.add(i);   // udelat pres stream generator?
        }

        this.solvedRows = new MyAtomicBooleanArray(this);//+1 /nezajima me nulove pole
        this.solvedCols = new MyAtomicBooleanArray(this);//+1 /nezajima me nulove pole
        this.solvedBoxes = new MyAtomicBooleanArray(this);//+1 /nezajima me nulove pole

        this.positions = new Position[BOARD_SIZE + 1][BOARD_SIZE + 1];//+1 /nezajima me nulove pole
        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                //positions[x][y] = new Position(x, y, this, rowCandidates[x],colCandidates[y],boxCandidates[getBox(x, y)]);
                positions[x][y] = new Position(x, y, this, rowCandidates[x], colCandidates[y], boxCandidates[getBox(x, y)]);
                boardSet.put(new Pair(x, y), positions[x][y]);

            }
        }
        //printPolicka(PRINT_CANDIDATES);

    }

    /**
     * poloha(cislo) ctverce
     */
    int getBox(int x, int y) {
        int rowIndex = (int) Math.ceil((double) x / BOXSIZE) - 1;
        return (int) ((Math.ceil((double) y / BOXSIZE)) + BOXSIZE * (rowIndex));
    }

    //Board getInstance(){// <<<< jakoze board vrati rovnou board ALE jak VRATI TEN SET???
    //    return this;
    //}
    //Set getInstance(){// <<<< jakoze neco jako toto
    //  return board;
    //}
    
    /**
     * LEGACY CODE
     */
    Set<Map.Entry> getBoard() {
        //boardSet.forEach(x->x.);// NEJAKA SELEKCE ******************************
        return boardSet.entrySet();

    }

    void parseInput(int[][] input) {

        for (int x = 1; x <= BOARD_SIZE; x++) {
            for (int y = 1; y <= BOARD_SIZE; y++) {
                int number = input[x - 1][y - 1];
                if (number > 0) {
                    positions[x][y].assign(number);
                }
            }
        }
    }
    
    /**
     * LEGACY CODE
     */
    Stream stream(int x) {
        return Arrays.stream(positions[x]);
        //return boardSetstream();
    }

    /**
     * LEGACY CODE
     */
    Stream stream() {
        //return Arrays.stream(positions[x]);
        return getBoard().stream();
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
                //val = PRINT_INTERNAL;
                if ((option) == 0) { //COMPARE BITMASK
                    if (pole.valueRef().get() > 0) {
                        System.out.print(pole.valueRef() + " ");
                    } else {
                        System.out.print("*" + pole.internalCandidates + "i");
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
