/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasudokuhonza;

import static javasudokuhonza.Variables.STRANA;

import java.util.ArrayList;
import java.util.List;

/**
 * Velmi podobne k {@link BoardHonzaPresDvojrozmernaPole}. Pouze misto primitivniho pole v {@link PC} pouziva {@link ArrayList}.
 * Casove by zrejme melo byt srovnatelne s {@link BoardHonzaPresDvojrozmernaPole}. Ale nezkousel jsem.
 * Vyhoda je, ze diky Listu (ArrayListu) muzem v {@link JavaSudokuSBoardemHonzaPresPoleArrayListu} pouzit tve oblibene lamby :-)
 */
public class BoardHonzaPresPoleArrayListu {

    // Pozn. Java jaksi nesere private List<Position>[] polickaPoRadcich = new ArrayList<Position>[STRANA+1].
    // Pise "Cannot create a generic array of ArrayList<Position>".
    // Bez generik to sice sezere, ale bez generik to zas neni tak citelny.
    // Proto jsem ArrayList obalil do PC. A private PC[] uz sezere.

    private PC[] polickaPoRadcich = new PC[STRANA+1]; // nulty index nepouzivame
    private PC[] polickaPoSloupcich = new PC[STRANA+1]; // nulty index nepouzivame
    private PC[] polickaPoInboxech = new PC[STRANA+1]; // nulty index nepouzivame
  
    // toto bychom asi nepotrebovali. Stejna data ziskame napr. i z polickaPoRadcich
    public Position[][] policka = new Position[STRANA + 1][STRANA + 1];//+1 /nezajima me nulove pole

    public BoardHonzaPresPoleArrayListu() {
        for (int x = 1; x <= STRANA; x++) {
            for (int y = 1; y <= STRANA; y++) {
                Position position = new Position(x, y);
                policka[x][y] = position;

                // koncova jednicka, abychom indexovali od 1, jak to mas v oblibe
                int inboxNumber = ((x-1) % STRANA) * STRANA + (y-1) + 1;

                polickaPoRadcich[x].add(position);
                polickaPoSloupcich[y].add(position);
                polickaPoInboxech[inboxNumber].add(position);
            }
        }
    }

    public List<Position> getRow(int rowNumber) {
      return polickaPoRadcich[rowNumber].getPositions();
    }

    public List<Position> getColumn(int columnNumber) {
      return polickaPoSloupcich[columnNumber].getPositions();
    }

    public List<Position> getInbox(int inboxNumber) {
      return polickaPoInboxech[inboxNumber].getPositions();
    }
    

    public void printPolicka() {
      // ... to by mohlo zustat
    }
    
    // PositionCollection ... bud jeden radek nebo jeden sloupec nebo jeden inbox
    private static class PC {
      private List<Position> positions = new ArrayList<>(STRANA);

      /**
       * Slouzi pouze k inicializaci ... kvuli naplneni.
       */
      public void add(Position position) {
        assert positions.size() < STRANA;
        positions.add(position);
      }

      public List<Position> getPositions() {
        return positions;
      }
    }
}
