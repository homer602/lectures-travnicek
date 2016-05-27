package javasudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
public class JavaSudoku2 {

    //private static final int BOARD_SIZE = Variables.BOARD_SIZE; //4x4 board cislovani shora
    static int LeftToSolve = BOARD_SIZE * BOARD_SIZE;
    //static int IteraceKandidatu = 0;
    static int RowChecks = 0;
    static int ColChecks = 0;
    static int BoxChecks = 0;
    static Board global; // STATIC FTW   
 
    public static void main(String[] args) {
        
        

        Board board = new Board(4);
        
      //  IteraceKandidatu = board.IteraceKandidatu;
        global = board;
      /*
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
        
        
        
        board.parseInput(setting);
        */
        
        
        board.positions[1][1].assign(1);
        board.positions[2][3].assign(1);
        board.positions[3][1].assign(4);
        board.positions[3][3].assign(3);
        board.positions[4][2].assign(3);

        board.printPolicka(PRINT_VALUE);

        // jak z toho vytahnout ten class pro debug
        //co object.equals ? getClass apod...
        //
        ///Stream<Position> stream = board.getBoard().stream();
        ///.forEach(new Consumer()  - Vytvorit nake samostatne Consumery...
        //
        //STARA VERZE S POUZITIM POLE
        /*for (int i = 0; i < 10; i++) {
            eliminateByRow(board.policka);
            eliminateByColumn(board.policka);
            eliminateByBox(board.policka);
            if (LeftToSolve == 0) {
                break;
            }

        }*/
        //eliminateBy(rowNotSolved());
     /*   board.stream().findFirst().map(x -> x.toString());
        global.stream().filter(x -> x.x == 3 && x.y == 1).findAny().ifPresent(new Consumer() {
            public void accept(String t) {
                System.out.println(t);
            }

            @Override
            public void accept(Object t) {
                accept(t.toString());
            }
        });*/

        //System.exit(0);

        board.solvedRows.setSolved(1);
        board.solvedRows.setSolved(3);
        board.solvedCols.setSolved(2);
        //board.solvedBoxes.setSolved(1);
        board.solvedBoxes.setSolved(4);

        //System.out.println(board.positions[1][1].rowSolved);
        JavaSudoku2 main = new JavaSudoku2();
        MyLoopIterator looper = main.new MyLoopIterator(1, board.BOARD_SIZE);

        //final int CYCLES = 1000000;
        final int CYCLES = 10;

        // board.boardSet.spliterator().getComparator().compare(x, board.solvedCols);
        for (int i = 0; i < 10; i++) {

            if (LeftToSolve == 0) {
                break;
            }
            //HVEZDICOVA ELIMINACE - VSE NARAZ ze stredu prvku row+col+boxMATCH************

            //PRI kontrole hasValue muzu rovnou nacist hodnotu atd.. opakovane volani "niceho"
            //board.boardSet.stream().filter(hasValue().and(neighborsNotSolved())).forEach((any) -> {
            board.boardSet.stream().filter(hasValue()).forEach((any) -> {

                // PROZATIM SMAZU TY NEIGHBORS...
                //board.boardSet.stream().filter(hasValue()).forEach((any) -> {
                int row = ((Position) (any)).x;// row HIDES FIELD?
                int col = ((Position) (any)).y;
                int box = ((Position) (any)).BOX;
                boolean rowSol = ((Position) (any)).rowSolved.get();
                boolean colSol = ((Position) (any)).colSolved.get();
                boolean boxSol = ((Position) (any)).boxSolved.get();
                System.out.println("Iterating for (" + row + "," + col + ")");

                //if (((Position) (any)). ROWCOMPLETE...
                //** eliminateBy(rowMatch(row).or(colMatch(col)).or(boxMatch(box)));-vymaze kandidaty v cizich polich..CHYBA
                //eliminateBy(rowMatch(row));
                //eliminateBy(colMatch(col));
                //eliminateBy(boxMatch(box));
                //break;
                if (!rowSol) {
                    //ITERACE NAVIC !!!!!
                    // POTREBUJU PROSTE VEDETE TEN FILTER/PReDIKAT KTERY VOLAM

                    // UDELAT JEDEN PREDIKAT MATCH NECO - ROW,COL BOX
                    int matchRow = (int) board.boardSet.stream().filter(rowMatch(row)).filter(hasValue()).count();
                    Set matchRowSet = (Set) board.boardSet.stream().filter(rowMatch(row)).filter(hasValue()).collect(Collectors.toSet());
                    System.out.println("ROW MATCH:" + matchRow);
                    System.out.println("ROW MATCH:" + matchRowSet.toString());
                    if (matchRow == BOARD_SIZE) {
                        board.solvedRows.setSolved(row);
                        //board.boardSet.stream().filter(rowMatch(row)).forEach((Object r) -> {
                        //  ((Position) (r)).rowSolved.set(true); // NASTAVUJE SE PRO VSECHNY STACI PRO JEDEN----
                        //});
                    } else {
                        eliminateBy(rowMatch(row));
                        JavaSudoku2.RowChecks++;
                    }
                }
                if (!colSol) {
                    int matchCol = (int) board.boardSet.stream().filter(colMatch(col)).filter(hasValue()).count();
                    if (matchCol == BOARD_SIZE) {
                        board.solvedCols.setSolved(col);

                    } else {
                        eliminateBy(colMatch(col));
                        JavaSudoku2.ColChecks++;
                    }

                }
                if (!boxSol) {
                    int matchBox = (int) board.boardSet.stream().filter(boxMatch(box)).filter(hasValue()).count();
                    if (matchBox == BOARD_SIZE) {
                        board.solvedBoxes.setSolved(box);
                    } else {
                        eliminateBy(boxMatch(box));
                        JavaSudoku2.BoxChecks++;
                    }

                }

                //**System.out.println(predicate);
            });

        }

        //for (int i = 0; i < 1000000; i++)
        //iterateInThreads();
        for (int i = 0; i < CYCLES; i++) {
            // eliminateMixed();
            //iterateInThreads();
            if (LeftToSolve == 0) {
                break;
            }
        }

        /*  for (int i = 0; i < 1000000; i++) {
            //for (int i = 0; i < 10; i++) {

            looper.iterateMe((x) -> eliminateBy(matchRow(x)));
            looper.iterateMe((x) -> eliminateBy(matchCol(x)));
            looper.iterateMe((x) -> eliminateBy(matchBox(x)));

            if (LeftToSolve == 0) {
                //break;// UZ K NICEMU?
            }

        }
         */
        System.out.println("");
        System.out.println("Left:" + LeftToSolve + " Iteraci kandidatu:" + global.IteraceKandidatu);
        System.out.println("Rows:" + RowChecks + " Cols:" + ColChecks + " Boxes:" + BoxChecks);
        System.out.println("");

        board.printPolicka(PRINT_VALUE);

    }

    interface LoopRunner {

        public abstract void iter(int i);

    }

    public class MyLoopIterator {

        final int LOOP_START;
        final int LOOP_END;

        public MyLoopIterator(int start, int end) {
            LOOP_START = start;
            LOOP_END = end;
        }

        void iterateMe(LoopRunner runner) { // TADY SE DA JESTE VLOZIT NEJAKA FUNKCE Z KONSTRUKTORU APOD

            for (int i = LOOP_START; i <= LOOP_END; i++) {
                runner.iter(i);
            }

        }
    }

    static Set getSubset(Predicate pred) { // K NICEMU?
        return (Set) global.boardSet.stream().filter(pred).collect(Collectors.toSet());

    }

    // parametr bude group mapping (subset)
    static int checkMinCandidates(Predicate predicate) {

        return 0;

    }

    static void iterateInThreads() {
        new Thread(() -> eliminateBy(rowMatch(1))).start();
        new Thread(() -> eliminateBy(rowMatch(2))).start();
        new Thread(() -> eliminateBy(rowMatch(3))).start();
        new Thread(() -> eliminateBy(rowMatch(4))).start();

        new Thread(() -> eliminateBy(colMatch(1))).start();
        new Thread(() -> eliminateBy(colMatch(2))).start();
        new Thread(() -> eliminateBy(colMatch(3))).start();
        new Thread(() -> eliminateBy(colMatch(4))).start();

        new Thread(() -> eliminateBy(boxMatch(1))).start();
        new Thread(() -> eliminateBy(boxMatch(2))).start();
        new Thread(() -> eliminateBy(boxMatch(3))).start();
        new Thread(() -> eliminateBy(boxMatch(4))).start();
    }

    static void eliminateMixed() {
        eliminateBy(rowMatch(1));
        eliminateBy(colMatch(1));
        eliminateBy(boxMatch(1));

        eliminateBy(rowMatch(2));
        eliminateBy(colMatch(2));
        eliminateBy(boxMatch(2));

        eliminateBy(rowMatch(3));
        eliminateBy(colMatch(3));
        eliminateBy(boxMatch(3));

        eliminateBy(rowMatch(4));
        eliminateBy(colMatch(4));
        eliminateBy(boxMatch(4));

    }

    static void eliminateBy(Predicate predicate) {

        //"removeIf" odstrani prvky z Collection
        // TADY BY TO CHTELO JESTE NEJAK ZHUSTIT
        //Set candidates = (Set) global.board.parallelStream() //POMALEJSI PROC???*********************
        //  boolean row = false;
        //  boolean col = false;
        //  boolean box = false;
        Set<Position> removeFromCandidates = (HashSet<Position>) global.boardSet.stream()
                .filter(predicate.and(hasValue())) // HAS VALUE SE MUZE SMAZAT KONTroluje se predtim?!!!
                .map((i) -> {
                    return ((Position) (i)).getValue();
                })
                .collect(Collectors.toSet());

        System.out.println(removeFromCandidates);

        if (removeFromCandidates.size() == NUMRANGE) //JAK ZJISTIT KTERY PREDIKAT VOLAM? a co ty CONSUMABLES?** DEBUG v tomto bode = SOLVED????? 
        {
            System.out.println("SET IS FULL");
            /*  global.boardSet.stream().filter(predicate)
                    .forEach((i) -> {
                        //    ((Position) (i)).x;
                    });
             */

            //IF ALL HAVE VALUE = universal set of SOLVED (ROW,COL, BOX)?
        }
        if (removeFromCandidates.isEmpty()) {
            System.out.println("SET IS EMPTY");
        }
        //global.board.parallelStream() //?
        global.boardSet.stream()
                .filter(predicate.and(hasValue().negate()))
                .forEach((x) -> {
                    ((Position) (x)).removeCandidates(removeFromCandidates);
                    //**System.out.println(predicate);
                });
        /* for (int x = 1; x <=4 ; x++) {
          for (int y = 1; y <=4; y++) {
            if (predicate.test(global.policka[x][y])){
            BoxChecks++;
                }    
            }
        }*/

        global.printPolicka(PRINT_CANDIDATES);
        System.out.println(LeftToSolve + ">" + global.IteraceKandidatu + " ******************");

        // System.out.println(predicate.getClass().getName() + candidates);
        // NAZVY FUNKCI JSOU JEN ANONYMNI LAMBDY, JAK DOSTAT KONKRETNI NAZEV FUNKCE
    }

    //remove candidates on row
}
