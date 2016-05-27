/*
 * To change this template file, choose Tools | Templates
 */
package javasudokuhonza;

/**
 *
 * @author Michal
 */
public class Variables {

    static final int STRANA = 4;  //4 pokud je 4x4 board nebo 9 pokud je 9x9
    //dodelat Javadoc
    static final int BOXSIZE = (int) Math.sqrt(STRANA);  //velikost maleho ramecku po 4 nebo 9

    static Integer LeftToSolve = 0;
    static Integer IteraceKandidatu = 0;

}
