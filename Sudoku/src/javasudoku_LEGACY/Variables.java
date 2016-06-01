/*
 * To change this template file, choose Tools | Templates
 */
package javasudoku_LEGACY;

/**
 *
 * @author Michal
 */
public class Variables {
    
    
    //obosolete
    static final int BOARD_SIZE = 4;  //4 pokud je 4x4 board nebo 9 pokud je 9x9 pripadne 16x16
    //dodelat Javadoc
    static final int BOXSIZE = (int) Math.sqrt(BOARD_SIZE);  //velikost maleho ramecku po 4 nebo 9

    static final int NUMRANGE = BOARD_SIZE;   //pocet kandidatu 1..9
    
    static class BoardType {
        public static final int SMALL = 4;
        public static final int BIG = 9;
    }
    

    /**
     *  BITOVA MASKA
     */
    public static final int PRINT_VALUE = 4;

    /**
     *  BITOVA MASKA
     */
    public static final int PRINT_CANDIDATES = 16;

    /**
     *  BITOVA MASKA
     */
    public static final int EDIT = 32;

    /**
     *  BITOVA MASKA
     */
    public static final int DELETE = 256;
    
    /**
     * Zkontroluje rozsah: 1 az max
     */
    public static void checkNonZeroRange(int max, int val) {
        if (val < 1 || val > max) {
            throw new IllegalArgumentException("VALUE IS OUT OF RANGE:\"" + val + "\"");
        }
    }

}
