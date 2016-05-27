/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pelanek;

import java.util.HashSet;
import java.util.Set;

/**
 * Resicka sudoku vyuzivajici "lidske" strategie
 *
 * @author Pavel Micka
 */
public class HumanizedSudokuSolver {

    private SudokuField[][] sudoku;
    private SudokuStrategy[] strategies;

    static int rowCandidates = 0;
    static int colCandidates = 0;
    static int boxCandidates = 0;

    /**
     * Zkonstruuje resicku pro zadane sudoku
     *
     * @param setting zadani sudoku, 0 znaci prazdne pole
     * @param strategies pole strategii, ktere maji byt pouzity
     */
    HumanizedSudokuSolver(int[][] setting, SudokuStrategy... strategies) {
        this.strategies = strategies;
        sudoku = new SudokuField[setting.length][setting.length];
        for (int i = 0; i < setting.length; i++) {
            for (int j = 0; j < setting[i].length; j++) {
                if (setting[i][j] == 0) {
                    sudoku[i][j] = new SudokuField();
                } else {
                    sudoku[i][j] = new SudokuField(setting[i][j]);
                }
            }
        }
    }

    /**
     * Vrati reseni sudoku
     *
     * @return
     */
    public int[][] solve() {
        boolean succ;
        for (int i = 0; i < 1; i++) {
            do {
                succ = false;
                for (SudokuStrategy s : strategies) {
                    succ = s.solve(sudoku);
                    if (succ) {
                        break;
                    }
                }
            } while (succ);
        }

        int[][] solution = new int[sudoku.length][sudoku.length];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                solution[i][j] = sudoku[i][j].getSolution();
            }
        }

        return solution;

    }

    /**
     * Testovaci main metoda Program vyresi, co umi, coz jsou s temito strategiemi cca. az pokrocila Sudoku a vypise vysledek
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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

        int[][] setting2 = {
            {1, 0, 0, 0},
            {0, 0, 1, 0},
            {4, 0, 3, 0},
            {0, 3, 0, 0}
        };

        SudokuStrategy[] s = {new OneInARowStrategy(), new OneInAColumnStrategy(), new OneInAGroupStrategy()};
        HumanizedSudokuSolver solver = new HumanizedSudokuSolver(setting2, s);
        int[][] solution = solver.solve();
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution.length; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("Iterace rows:" + rowCandidates);
        System.out.println("Iterace cols:" + colCandidates);
        System.out.println("Iterace boxy:" + boxCandidates);
        System.out.println("Iterace kandidatu:" + (rowCandidates + colCandidates + boxCandidates));//soucet retezce!
    }
}

/**
 * Specifikuje metodu, kterou by mely mit vsechny strategie pro reseni Sudoku
 *
 * @author Pavel Micka
 */
interface SudokuStrategy {

    /**
     * Provede jeden krok reseni Sudoku
     *
     * @param sudoku pole obsahujici sudoku, 0 oznacuje prazdna mista
     * @return true pokud se povedlo krok provest, false v opacnem pripade
     */
    public boolean solve(SudokuField[][] sudoku);
}

/**
 * Strategie, ktera rika, ze v jednom radku smi byt dane cislo pouze jednou
 *
 * @author Pavel Micka
 */
class OneInARowStrategy implements SudokuStrategy {

    public boolean solve(SudokuField[][] sudoku) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                int solution = sudoku[i][j].getSolution();
                if (solution != 0) { //pokud pole neni vyreseno
                    for (int k = 0; k < sudoku[i].length; k++) { //overme radku na kandidaty
                        //<editor-fold defaultstate="collapsed" desc="loop">
                        if (sudoku[i][k].getSolution() == 0) { //pokud dane pole neni vyreseno
                            if (sudoku[i][k].hasCandidate(solution)) { //a pokud ma kandidata
                                sudoku[i][k].removeCandidate(solution); //tak kandidata odstranime
                                //System.out.println("OneInARow: Odstranuji kandidata " + solution
                                //       + " na souradnicich x: " + k + " y: " + i);
                                HumanizedSudokuSolver.rowCandidates++;
                                return true; //a vratime uspech
                            }
                        }
//</editor-fold>
                    }
                }
            }
        }
        return false;
    }
}

/**
 * Strategie, ktera rika, ze v jednom sloupci smi byt dane cislo pouze jednou
 *
 * @author Pavel Micka
 */
class OneInAColumnStrategy implements SudokuStrategy {

    public boolean solve(SudokuField[][] sudoku) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                int solution = sudoku[j][i].getSolution();
                if (solution != 0) { //pokud pole neni vyreseno
                    for (int k = 0; k < sudoku.length; k++) { //overme sloupec na kandidaty
                        if (sudoku[k][i].getSolution() == 0) { //pokud dane pole neni vyreseno
                            if (sudoku[k][i].hasCandidate(solution)) { //a pokud ma kandidata
                                sudoku[k][i].removeCandidate(solution); //tak kandidata odstranime
                                //System.out.println("OneInAColumn: Odstranuji kandidata " + solution
                                //        + " na souradnicich x: " + i + " y: " + k);
                                HumanizedSudokuSolver.colCandidates++;
                                return true; //a vratime uspech
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

/**
 * Strategie, ktera rika, ze v jedne skupine muze byt dane cislo pouze jednou
 *
 * @author Pavel Micka
 */
class OneInAGroupStrategy implements SudokuStrategy {

    public boolean solve(SudokuField[][] sudoku) {
        int groupCount = (int) Math.sqrt(sudoku.length); //pocitame se ctvercovym sudoku, pocet skupin na radku a sloupec
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                int solution = sudoku[i][j].getSolution();
                if (solution != 0) { //pokud pole neni vyreseno
                    for (int k = i - (i % groupCount); k < i - (i % groupCount) + groupCount; k++) {//radky
                        for (int l = j - (j % groupCount); l < j - (j % groupCount) + groupCount; l++) {//sloupce
                            if (sudoku[k][l].getSolution() == 0) { //pokud pole jeste neni vyreseno
                                if (sudoku[k][l].hasCandidate(solution)) { //a ma odpovidajiciho kandidata
                                    sudoku[k][l].removeCandidate(solution);
                                    //System.out.println("OneInAGroup: Odstranuji kandidata " + solution
                                    //        + " na souradnicich x: " + l + " y: " + k);
                                    HumanizedSudokuSolver.boxCandidates++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

/**
 * Trida policka sudoku
 *
 * @author Pavel Micka
 */
class SudokuField {

    /**
     * Mnozina kandidatu
     */
    private Set<Integer> candidates;

    /**
     * Vytvori policko, kandidaty jsou cisla 1-9
     */
    public SudokuField() {
        this.candidates = new HashSet<Integer>();
        for (int i = 1; i <= 4; i++) {
            candidates.add(i);
        }
    }

    /**
     * Vytvori policko s jedinym kandidatem (resenim)
     *
     * @param nr reseni
     */
    public SudokuField(int nr) {
        this.candidates = new HashSet<Integer>();
        candidates.add(nr);
    }

    /**
     * Zjisti, jestli je dane cislo kandidatem tohoto pole
     *
     * @param nr cislo
     * @return true pokud je cislo kandidatem, false jinak
     */
    public boolean hasCandidate(int nr) {
        return candidates.contains(nr);
    }

    /**
     * Odstrani kandidata<br> {@code Set candidates.remove(nr);}
     *
     * @param nr kandidat
     */
    public void removeCandidate(int nr) {
        boolean succ = candidates.remove(nr);
        assert succ;
    }

    /**
     * Vrati cislo, ktere je resenim tohoto policka
     *
     * @return cislo, ktere je resenim, 0 pokud jeste pole nebylo vyreseno
     */
    public int getSolution() {
        if (candidates.size() != 1) {
            return 0;
        } else {
            return (Integer) (candidates.toArray()[0]);
        }
    }
}
