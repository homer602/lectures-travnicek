/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javasudoku_CURRENT.Variables.Print;

/**
 *
 * @author evo
 */
public final class Board {

    final int boardSize;  //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16
    final int myBoxSize;  //velikost maleho ramecku po 4 nebo 9
    final int NUMRANGE;
    public int leftToSolve;
    public int iteraceKandidatu = 0;
    public Map boardSet = new HashMap<Pair, Position>(); //NESKODNY BUG V TYPU KOLEKCE //LEGACY jaky je rozdil mezi <NIC>  a <NECO>
    //public Map<Pair,Position> boardSet = new HashMap<>(); //rozdil?
    //public Map<Pair,Position> boardSet = new HashMap(); //rozdil?
    public Position[][] positions;
    //public List<Set<AtomicInteger>> rowCandidates = new ArrayList<>(new HashSet<Set<AtomicInteger>>());//redundantni definice?
    public List<Set<AtomicInteger>> rowCandidates = new ArrayList<>();
    public List<Set<AtomicInteger>> colCandidates = new ArrayList<>();
    public List<Set<AtomicInteger>> boxCandidates = new ArrayList<>();

    public Board(int size) {

        boardSize = size; //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16

        myBoxSize = (int) Math.sqrt(boardSize);  //velikost maleho ramecku - 4 nebo 9

        NUMRANGE = boardSize;

        leftToSolve = boardSize * boardSize;

        rowCandidates.add(0, null);//nulovy prvek
        colCandidates.add(0, null);
        boxCandidates.add(0, null);

        for (int i = 1; i <= NUMRANGE; i++) {
            rowCandidates.add(new HashSet<>());
            colCandidates.add(new HashSet<>());
            boxCandidates.add(new HashSet<>());
        }

        this.positions = new Position[boardSize + 1][boardSize + 1];//+1 /nezajima me nulove pole
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

            positions[x][y] = new Position(x, y, Board.this,
                    rowCandidates.get(x),
                    colCandidates.get(y),
                    boxCandidates.get(Board.this.getBox(x, y))
            );
            boardSet.put(new Pair(x, y), positions[x][y]);
        });

        //executeInDoubleLoop((x, y) -> );
        Collection<Position> temp = boardSet.values();
        temp.forEach(Position::addSetIntersection);
    }

    interface LoopRunner {

        public abstract void iterate(int x, int y);
        //public abstract void afterFirstLoop(int x, int y);//....

    }

    void executeInDoubleLoop(LoopRunner run) {
        for (int x = 1; x <= boardSize; x++) {
            for (int y = 1; y <= boardSize; y++) {
                run.iterate(x, y);

            }
        }

    }

    /**
     * poloha(cislo) ctverce
     */
    int getBox(int x, int y) {
        int rowIndex = (int) Math.ceil((double) x / myBoxSize) - 1;
        return (int) ((Math.ceil((double) y / myBoxSize)) + myBoxSize * (rowIndex));
    }

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

    public void printPolicka(Print option) {
        SudokuPrinter.printPolicka(option, this);

    }
}
