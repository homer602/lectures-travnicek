/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pelanek;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author evo
 *  1 2 3 4
 *  3 4 2 1
 *  2 1 4 3
 *  4 3 1 2
 */
public class Pelanek {

    
    public static void main(String[] args) {
        //forcycle();
        List lip = new ArrayList(4);
        //lip.add(lip);???
        Integer[] fajn = {1,2,3,4};
        Integer[] jajn = {3,2,4,1};
        Integer[][] zajn = new Integer[4][4];
        zajn[0][0]=1;
        zajn[1][0]=2;
        zajn[2][0]=3;
        zajn[3][0]=4;
        
        lip.add(fajn);
        lip.add(jajn);
        //lip.add(2);
        System.out.println(lip.get(0));;
        
        
        
    }

    public static void forcycle() {
        for (int i = -10; i < 11; i++) {
            for (int j = -10; j < 11; j++) {
                
                if (i%2==0 && Math.abs(j)>=Math.abs(i)|| j%2==0 && Math.abs(i)>=Math.abs(j)) System.out.print("*");
                else System.out.print(" ");
                
            }
            System.out.println("");
        }
    }
    
}
