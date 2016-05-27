/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudokuhonza;

import static javasudokuhonza.Variables.STRANA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Uspornejsi reseni na pocet (neskrytych) cyklu. Casove mozna neni uspornejsi. (Ne-li horsi. Nevim.)
 * Nebot HashMapy maji nejakou svou vnitrni implementaci, ktera taky nejaky ten cas stoji ...
 */
public class BoardHonzaPresMapyMnozin {
    
    // Pozn. Dala by se taky vyuzit SetMultimap z google-guava. Pak by nam odpadla metoda addPosition().
    private Map<Integer /*cislo radku*/, Set<Position> /*policka v danem radku*/> polickaPoRadcich = new HashMap<>();
    private Map<Integer /*cislo sloupce*/, Set<Position> /*policka v danem sloupci*/> polickaPoSloupcich = new HashMap<>();
    private Map<Integer /*cislo inboxu*/, Set<Position> /*policka v danem inboxu*/> polickaPoInboxech = new HashMap<>();
  
    public Position[][] policka = new Position[STRANA + 1][STRANA + 1];//+1 /nezajima me nulove pole

    public BoardHonzaPresMapyMnozin() {
        for (int x = 1; x <= STRANA; x++) {
            for (int y = 1; y <= STRANA; y++) {
                Position position = new Position(x, y);
                policka[x][y] = position;

                // koncova jednicka, abychom indexovali od 1, jak to mas v oblibe
                int inboxNumber = ((x-1) % STRANA) * STRANA + (y-1) + 1;

                addPosition(polickaPoRadcich, x, position);
                addPosition(polickaPoSloupcich, y, position);
                addPosition(polickaPoInboxech, inboxNumber, position);
            }
        }
    }
    
    /**
     * V {@code output} vyhleda mnozinu policek s klicem {@code key} a do teto mnoziny prida zadane policko {@code position}.
     * 
     * @param output Predstavuje bud vsechny radky nebo vsechny sloupce nebo vsechny inboxy.
     * @param key Predstavuje cislo konkretniho radku/sloupce/inboxu.
     * @param position Policko, ktere ma byt pridano do daneho radku/sloupce/inboxu
     */
    private void addPosition(Map<Integer, Set<Position>> output, Integer key, Position position) {
      Set<Position> positions = output.get(key);
      if (positions == null) {
        positions = new HashSet<>();
        output.put(key, positions);
      }
      assert positions.size() < STRANA; // ve vysledku chceme mit v positions prave STRANA policek
      positions.add(position);
    }
    
    public Set<Position> getRow(int rowNumber) {
      return polickaPoRadcich.get(rowNumber);
    }

    public Set<Position> getColumn(int columnNumber) {
      return polickaPoSloupcich.get(columnNumber);
    }

    public Set<Position> getInbox(int inboxNumber) {
      return polickaPoInboxech.get(inboxNumber);
    }
    

    public void printPolicka() {
      // ... to by mohlo zustat
    }
}
