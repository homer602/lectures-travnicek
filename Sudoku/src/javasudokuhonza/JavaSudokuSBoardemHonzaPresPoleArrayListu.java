package javasudokuhonza;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javasudokuhonza.GroupingPredicates.hasValue;

/**
 *
 * @author Michal
 */
public class JavaSudokuSBoardemHonzaPresPoleArrayListu {

    private static final int STRANA = Variables.STRANA; //4x4 board cislovani shora
    static int LeftToSolve = STRANA * STRANA;
    static int IteraceKandidatu = 0;
    static BoardHonzaPresPoleArrayListu global; // STATIC FTW

    public static void main(String[] args) {

        BoardHonzaPresPoleArrayListu board = new BoardHonzaPresPoleArrayListu();
        global = board;
        

        board.policka[1][1].assign(1);
        board.policka[2][3].assign(1);
        board.policka[3][1].assign(4);
        board.policka[3][3].assign(3);
        board.policka[4][2].assign(3);

        board.printPolicka();
        JavaSudokuSBoardemHonzaPresPoleArrayListu main = new JavaSudokuSBoardemHonzaPresPoleArrayListu();
        MyLoopIterator looper = main.new MyLoopIterator(1, STRANA);

        for (int i = 0; i < 1000000; i++) {
            //for (int i = 0; i < 10; i++) {

            looper.iterateMe((x) -> eliminateBy(global.getRow(x)));
            looper.iterateMe((x) -> eliminateBy(global.getColumn(x)));
            looper.iterateMe((x) -> eliminateBy(global.getInbox(x)));

            if (LeftToSolve == 0) {
                //break;// UZ K NICEMU?
            }

        }

        System.out.println("");
        System.out.println("Left:" + LeftToSolve + " Iteraci kandidatu:" + IteraceKandidatu);
        System.out.println("");

        board.printPolicka();

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

    /**
     * @param positions Bud vsechna policka v jednom radku nebo v jednom sloupci nebo v jednom inboxu.
     */
    static void eliminateBy(List<Position> positions) {
        Set<Integer> usedValues = positions.stream().filter(hasValue()).map(p -> p.getValue()).collect(Collectors.toSet());
        positions.stream().filter(hasValue().negate()).forEach(p -> p.removeCandidates(usedValues));
    }
}
