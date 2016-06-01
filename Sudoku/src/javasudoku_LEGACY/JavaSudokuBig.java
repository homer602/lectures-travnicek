package javasudoku_LEGACY;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static javasudoku_LEGACY.GroupingPredicates.*;
import static javasudoku_LEGACY.Variables.*;

/**
 *
 * @author Michal
 */
public class JavaSudokuBig {

    //static int IteraceKandidatu = 0;
    static int RowChecks = 0;
    static int ColChecks = 0;
    static int BoxChecks = 0;
    static Board global; // STATIC FTW

    public static void main(String[] args) {

        Board board1 = new Board(BoardType.BIG);
        global = board1;

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

        board1.parseInput(setting);

            
        Board board2 = new Board(BoardType.SMALL);
        //global = board2;
        
        int[][] setting2 = {
            {1, 0, 0, 0},
            {0, 0, 1, 0},
            {4, 0, 3, 0},
            {0, 3, 0, 0}
        };
        
        board2.parseInput(setting2);
        
        
        Board board = board1;
        global = board1;
         
        //<editor-fold defaultstate="collapsed" desc="zadani">
        /*
        board.positions[1][1].assign(1);
        board.positions[2][3].assign(1);
        board.positions[3][1].assign(4);
        board.positions[3][3].assign(3);
        board.positions[4][2].assign(3);
        
         */
//</editor-fold>
        board.printPolicka(PRINT_VALUE);

        //<editor-fold defaultstate="collapsed" desc="comment">
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
//</editor-fold>
        //eliminateBy(rowNotSolved());
        //<editor-fold defaultstate="collapsed" desc="comment">
//        board.stream().findFirst().map(x -> x.toString());
//        global.stream().filter(x -> x.x == 3 && x.y == 1).findAny().ifPresent(new Consumer() {
//            public void accept(String t) {
//                //  System.out.println(t);
//            }
//
//            @Override
//            public void accept(Object t) {
//                accept(t.toString());
//            }
//        });
//</editor-fold>

        /*         
        board.positions[3][2].assign(1);
        board.positions[4][1].assign(2);
        board.positions[4][3].assign(4);
        
        board.positions[4][4].assign(1);
        board.positions[3][4].assign(2);
        board.positions[2][1].assign(3);
        
        board.positions[1][2].assign(4);
        board.positions[2][2].assign(2);
        
        board.solvedRows.setSolved(3);
        board.solvedRows.setSolved(4);
        
        board.solvedBoxes.setSolved(3);
        
        board.solvedCols.setSolved(1);
        board.solvedCols.setSolved(2);
        
         */
        System.out.println("");
        board.printPolicka(PRINT_VALUE);
        Set reset = board.stream().filter(rowNotSolved()).collect(Collectors.toSet());

        //reset.iterator().
        TreeSet tree = new TreeSet(reset);

        Set xset = board.stream().collect(Collectors.toSet());

        System.out.println(xset);

        board.stream().sorted(new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                //return (o1.x < o2.x ? -1 : 1);
                if (o1.getCandidates().size() == o2.getCandidates().size()) {
                    return 0;
                }
                return (o1.getCandidates().size() < o2.getCandidates().size() ? -1 : 1);
            }
        }).forEach(x -> System.out.print(x));
        System.out.println("");
        board.stream().forEach(x -> System.out.print(x));
        System.out.println("");

        //}).collect(Collectors.toCollection(() -> new TreeSet<Position>()));
        //System.out.println(xset2);
        // System.exit(0);
        //PRIORITY QUEUE?
                //  MALY CHEAT PRO LEPSI VYLSEDKY na 4x4
      /*  board.solvedRows.setSolved(1);
        board.solvedRows.setSolved(3);
        board.solvedCols.setSolved(2);
        //board.solvedBoxes.setSolved(1);
        board.solvedBoxes.setSolved(4);
        */ 
        //System.out.println(board.positions[1][1].rowSolved);
        JavaSudokuBig main = new JavaSudokuBig();
        MyLoopIterator looper = main.new MyLoopIterator(1, board.BOARD_SIZE);

        //final int CYCLES = 1000000;
        final int CYCLES = 10;

        // board.boardSet.spliterator().getComparator().compare(x, board.solvedCols);
        for (int i = 0; i < 10; i++) {

            if (board.LeftToSolve == 0) {
                break;
            }
            //HVEZDICOVA ELIMINACE - VSE NARAZ ze stredu prvku row+col+boxMATCH************

            //PRI kontrole hasValue muzu rovnou nacist hodnotu atd.. opakovane volani "niceho"
            Comparator sortByBoxes = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    if (((Position) o1).valuesInBox() == ((Position) o2).valuesInBox()) {
                        return 0;
                    }
                    return (((Position) o1).valuesInBox() > ((Position) o2).valuesInBox() ? -1 : 1);
                }
            };

            Comparator sortByCandidates = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    if (((Position) o1).getCandidates().size() == ((Position) o2).getCandidates().size()) {
                        return 0;
                    }
                    return (((Position) o1).getCandidates().size() > ((Position) o2).getCandidates().size() ? -1 : 1);

                }
            };
            
            Comparator myRevers = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    //return 1; // -1 obrati stream; + s filtrem hasValue to dava 499 // candidates sorting na HasValue dava 535-haluz?
                    return -1;
                }
            };
            
            
             // ZATIM NEJLEPSI        
            //board.boardSet.stream().filter(hasValue()).sorted(myRevers).sorted(sortByCandidates).forEach((any) -> {
                
                
            // TAhkle se obejdem bez komparatoru
            //board.boardSet.stream().filter(hasValue()).sorted(myRevers).sorted(Comparator.comparingInt(Position::valuesInBox).reversed()).forEach((any) -> {
            
            board.boardSet.stream().filter(hasValue()).sorted(myRevers).forEach((any) -> {
            //board.boardSet.stream().filter(hasValue()).forEach((any) -> {

                //board.boardSet.stream().filter(hasValue()).forEach((any) -> {
                // PROZATIM SMAZU TY NEIGHBORS...
                //board.boardSet.stream().filter(hasValue()).forEach((any) -> {
                int row = ((Position) (any)).x;// row HIDES FIELD?
                int col = ((Position) (any)).y;
                int box = ((Position) (any)).BOX;
                boolean rowSolution = ((Position) (any)).rowSolved.get();
                boolean colSolution = ((Position) (any)).colSolved.get();
                boolean boxSolution = ((Position) (any)).boxSolved.get();
                System.out.println("Iterating element (" + row + "," + col + ")");

                //if (((Position) (any)). ROWCOMPLETE...
                //** eliminateBy(rowMatch(row).or(colMatch(col)).or(boxMatch(box)));-vymaze kandidaty v cizich polich..CHYBA
                //eliminateBy(rowMatch(row));
                //eliminateBy(colMatch(col));
                //eliminateBy(boxMatch(box));
                //break;
                if (!rowSolution) {
                    //ITERACE/OPAKUJICI SE KOD NAVIC !!!!!
                    // POTREBUJU PROSTE VEDETE TEN FILTER/PReDIKAT KTERY VOLAM

                    // UDELAT JEDEN PREDIKAT MATCH NECO - ROW,COL BOX
                    int matchRow = (int) board.boardSet.stream().filter(rowMatch(row)).filter(hasValue()).count();
                    Set matchRowSet = (Set) board.boardSet.stream().filter(rowMatch(row)).filter(hasValue()).collect(Collectors.toSet());
                    System.out.println("ROW MATCH:" + matchRow);
                    System.out.println("ROW MATCH:" + matchRowSet.toString());
                    if (matchRow == board.BOARD_SIZE) {
                        board.solvedRows.setSolved(row);
                        //board.boardSet.stream().filter(rowMatch(row)).forEach((Object r) -> {
                        //  ((Position) (r)).rowSolved.set(true); // NASTAVUJE SE PRO VSECHNY STACI PRO JEDEN----
                        //});
                    } else {
                        eliminateBy(rowMatch(row));
                        JavaSudokuBig.RowChecks++;
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="colsol">
                if (!colSolution) {
                    int matchCol = (int) board.boardSet.stream().filter(colMatch(col)).filter(hasValue()).count();
                    if (matchCol == board.BOARD_SIZE) {
                        board.solvedCols.setSolved(col);

                    } else {
                        eliminateBy(colMatch(col));
                        JavaSudokuBig.ColChecks++;
                    }

                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="boxsol">
                if (!boxSolution) {
                    int matchBox = (int) board.boardSet.stream().filter(boxMatch(box)).filter(hasValue()).count();
                    if (matchBox == board.BOARD_SIZE) {
                        board.solvedBoxes.setSolved(box);
                    } else {
                        eliminateBy(boxMatch(box));
                        JavaSudokuBig.BoxChecks++;
                    }

                }
                //</editor-fold>

                //**System.out.println(predicate);
            });

        }

        //for (int i = 0; i < 1000000; i++)
        //iterateInThreads();
        for (int i = 0; i < CYCLES; i++) {
            // eliminateMixed();
            //iterateInThreads();
            if (board.LeftToSolve == 0) {
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
        System.out.println("Left:" + board.LeftToSolve + " Iteraci kandidatu:" + board.IteraceKandidatu);
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
        System.out.println("Left:" + global.LeftToSolve + "> iterK:" + global.IteraceKandidatu + " ******************");

        // System.out.println(predicate.getClass().getName() + candidates);
        // NAZVY FUNKCI JSOU JEN ANONYMNI LAMBDY, JAK DOSTAT KONKRETNI NAZEV FUNKCE
    }

    //remove candidates on row
}
