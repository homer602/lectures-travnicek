package examples.sudoku;

import java.util.*;

/**
 * This class generates and stores constants that will be used frequently
 * throughout the project.
 *
 * @author Subhomoy Haldar
 * @version 1.0
 */
public class Constants {
    /**
     * The side of the smaller square unit.
     */
    public static final int STRANA_CTVERCE = 3;
    /**
     * The number of squares in one unit.
     */
    public static final int CTVEREC_PRVKU = STRANA_CTVERCE * STRANA_CTVERCE;
    /**
     * The total number of squares in the game.
     */
    public static final int POCET_VSECH_POLI = CTVEREC_PRVKU * CTVEREC_PRVKU;
    /**
     * The String containing all the possible candidates.
     */
    public static final String CANDIDATES;
    /**
     * The list of all the squares in the game.
     */
    public static final List<String> SEZNAM_POLI;
    /**
     * The list of all units (rows, columns, squares) in the game.
     */
    public static final List<List<String>> UNITS;
    /**
     * The map between every square and its set of peers.
     */
    public static final Map<String, Set<String>> PEERS;

    /**
     * The maximum number of times the Generator will run the shuffle loop.
     */
    protected static final int MAX_SHUFFLE = 20;

    static {
        // All the possibilities linearly stored in this String
        StringBuilder builder = new StringBuilder(CTVEREC_PRVKU);
        for (int i = 1; i <= CTVEREC_PRVKU; i++) {
            builder.append(i);
        }
        CANDIDATES = builder.toString();//9

        // Generate the square labels
        List<String> squareList = new ArrayList<>(POCET_VSECH_POLI);
        char row = 'A';
        char col;
        for (int i = 0; i < CTVEREC_PRVKU; i++, row++) {
            col = '1';
            for (int j = 0; j < CTVEREC_PRVKU; j++, col++) {
                String square = "" + row + col;
                squareList.add(square);
            }
        }
        SEZNAM_POLI = Collections.unmodifiableList(squareList);

        // Generate the units
        List<List<String>> temporaryLists = new ArrayList<>(CTVEREC_PRVKU * 3);
        // First, the rows
        row = 'A';
        for (int i = 0; i < CTVEREC_PRVKU; i++, row++) {
            List<String> squares = new ArrayList<>(CTVEREC_PRVKU);
            col = '1';
            for (int j = 0; j < CTVEREC_PRVKU; j++, col++) {
                squares.add("" + row + col);
            }
            temporaryLists.add(squares);
        }
        // Second, the columns -- Tyto dva cykly delaji skoro to same
        col = '1';
        for (int i = 0; i < CTVEREC_PRVKU; i++, col++) {
            row = 'A';
            List<String> squares = new ArrayList<>(CTVEREC_PRVKU);
            for (int j = 0; j < CTVEREC_PRVKU; j++, row++) {
                squares.add("" + row + col);
            }
            temporaryLists.add(squares);
        }
        // Third, the squares
        int xOffset = 0;
        int yOffset = 0;
        while (xOffset < STRANA_CTVERCE && yOffset < STRANA_CTVERCE) {
            List<String> squares = new ArrayList<>(CTVEREC_PRVKU);
            for (int i = 0; i < STRANA_CTVERCE; i++) {
                for (int j = 0; j < STRANA_CTVERCE; j++) {
                    col = (char) ('1' + xOffset * STRANA_CTVERCE + j);
                    row = (char) ('A' + yOffset * STRANA_CTVERCE + i);
                    squares.add("" + row + col);
                }
            }
            temporaryLists.add(squares);
            xOffset++;
            if (xOffset == STRANA_CTVERCE) {
                xOffset = 0;
                yOffset++;
            }
        }
        UNITS = Collections.unmodifiableList(temporaryLists);

        // The peers for each square
        Map<String, Set<String>> peerMap = new HashMap<>(POCET_VSECH_POLI);
        for (String square : SEZNAM_POLI) {
            Set<String> peers = new HashSet<>(CTVEREC_PRVKU * 3 + 2 * (CTVEREC_PRVKU - STRANA_CTVERCE));
            UNITS.stream()
                    .filter(unit -> unit.contains(square))
                    .forEach(peers::addAll);
            peers.remove(square);
            peerMap.put(square, peers);
        }
        PEERS = Collections.unmodifiableMap(peerMap);
    }
}
