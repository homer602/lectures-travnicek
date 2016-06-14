/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_CURRENT;

/**
 * Ze pry Variables ma byt Constants
 *
 * @author Michal
 */
public class Variables {

    public static boolean DEBUG = false;

    public static class BoardType {

        public static final int SMALL = 4;
        public static final int BIG = 9;
    }

    /**
     * BITOVA MASKA
     */
    public enum Print {

        PRINT_VALUE,
        PRINT_CANDIDATES,
    }

}
