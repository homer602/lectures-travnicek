/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pelanek;

/**
 *
 * @author evo
 */
/**
 * Resic Sudoku pomoci backtrackingu. Tato metoda nezjisti, jestli je dane
 * sudoku resitelne clovekem, ale zda-li ma dane zadani vubec reseni, pripadne
 * kolik takovychto reseni umoznuje (zjisti a vypise vsechna reseni).
 * @author Pavel Micka
 */
public class SudokuSolver {
    /**
     * Velikost ulohy == klasicke Sudoku 9*9, 3x vnitrni ctverec 3*3, kazde cislo
     * (1-9) pouze jednou v radku, sloupci i ctverci
     */
    public static final int SUDOKU_SIZE = 9;
    /**
     * Velikost vnitrnich ctvercu (3*3 dle beznych pravidel)
     */
    public static final int SQUARE_SIZE = 3;
    /**
     * Prazdne pole
     */
    public static final int EMPTY = 0;

    /**
     * Vyresi zadane Sudoku pomoci backtrackingu (najde vsechna reseni)
     * @param array pole zadani
     */
    public static void solveSudoku(int[][] array) {
        if(array.length != SUDOKU_SIZE) throw new IllegalArgumentException("Pole ma mit velikost 9x9");

        boolean[][] fixed = new boolean[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int i = 0; i < array.length; i++) {
            if (array[i].length != SUDOKU_SIZE) throw new IllegalArgumentException("Pole ma mit velikost 9x9");
            for (int j = 0; j < array.length; j++) {
                if (array[i][j] != 0) fixed[i][j] = true;

            }
        }

        //prejdeme na prvni nepredvyplnenou souradnici
        int x = -1;
        int y = 0;
        do {
            x = x + 1; //posun o souradnici dal
            boolean overflow = x >= SUDOKU_SIZE; //pretekl radek?
            if (overflow) {
                x = 0;
                y += 1;
            }
        } while(fixed[y][x]); //dokud je pole zablokovane (soucasti zadani)

        for (int i = 1; i <= SUDOKU_SIZE; i++) {
            solve(array, fixed, x, y, i);
        }
    }
    /**
     * Resi Sudoku
     * @param array pole reseni
     * @param fixed pole, ve kterem true znamena, ze jde o hodnotu ze zadani,
     * @false, ze jde o hodnotu resitele
     * @param x souradnice x, na kterou se bude pridavat dalsi hodnota
     * @param y souradnice y, na kterou se bude pridavat dalsi hodnota
     * @param value hodnota
     */
    private static void solve(int[][] array, boolean[][] fixed, int x, int y, int value) {
        if (!checkConsistency(array, x, y, value)) return; //reseni neni konzistentni
        array[y][x] = value; //set
        do {
            x = x + 1; //posun o souradnici dal
            boolean overflow = x >= SUDOKU_SIZE; //pretekl radek?
            if (overflow) {
                x = 0;
                y += 1;
                if (y == SUDOKU_SIZE) { //pretekl sloupec...konec reseni
                    printArray(array);
                    return;
                }
            }
        } while(fixed[y][x]);//dokud je pole zablokovane (soucasti zadani)
        for (int i = 1; i <= SUDOKU_SIZE; i++) { //backtrack
            solve(array, fixed, x, y, i);
        }
        array[y][x] = EMPTY; //reset policka (aby neprekazelo pri backtracku)
    }
    /**
     * Zkontroluje, jestli je reseni stale korektni
     * @param array pole reseni
     * @param x x souradnice, na kterou je pridavana hodnota
     * @param y y souradnice, na kterou je pridavana hodnota
     * @param value hodnota
     * @return true - pokud je reseni konzistentni, false - pokud reseni neni konzistentni
     */
    private static boolean checkConsistency(int[][] array, int x, int y, int value) {
        //Sloupec
        for (int i = 0; i < array.length; i++) {
            if (i != y && array[i][x] == value) return false;
        }
        //radek
        for (int i = 0; i < array[y].length; i++) {
            if (i != x && array[y][i] == value) return false;
        }
        //ctverec
        int vertical = y/SQUARE_SIZE; //o kolikaty vertikalni ctverec se jedna
        int horizontal = x/SQUARE_SIZE; //o kolikaty horizontalni ctverec se jedna

        for (int i = vertical*SQUARE_SIZE; i < vertical*SQUARE_SIZE + SQUARE_SIZE; i++) {
            for (int j = horizontal*SQUARE_SIZE; j < horizontal*SQUARE_SIZE + SQUARE_SIZE; j++) {
                if (array[i][j] == value) return false;
            }
        }
        return true;
    }
    /**
     * Vypise pole Sudoku
     * @param array pole sudoku
     */
    private static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "|");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
