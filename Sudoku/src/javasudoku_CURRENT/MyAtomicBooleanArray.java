/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudoku_CURRENT;

import java.util.concurrent.atomic.AtomicBoolean;
import static javasudoku_CURRENT.Variables.*;

/**
 *
 * @author evo
 */
public class MyAtomicBooleanArray {

    private final AtomicBoolean[] solvedEntity;

    public MyAtomicBooleanArray(Board board) {
        solvedEntity = new AtomicBoolean[board.BOARD_SIZE+1];
        for (int i = 1; i <= board.BOARD_SIZE; i++) {
            solvedEntity[i] = new AtomicBoolean();
        }

        // TOTO NEFUNGUJE PROC?
        /*for (AtomicBoolean row : rowSolved) {
            row = new AtomicBoolean(false);
            System.out.println(row);
        }*/
        //System.out.println(solved[1]);
    }

    /**
     * Nacte hodnotu
     */
    public AtomicBoolean get(int x) {
        checkNonZeroRange(solvedEntity.length, x);
        return solvedEntity[x];
    }

    /**
     * Nastavi jako vyresene
     */
    //@Nullable
    public void setSolved(int x) {
        checkNonZeroRange(solvedEntity.length, x);
        assert (x > 0);
        this.solvedEntity[x].set(true);
    }

}
