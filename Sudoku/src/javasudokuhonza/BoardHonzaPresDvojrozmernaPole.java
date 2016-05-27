/*
 */
package javasudokuhonza;

import static javasudokuhonza.Variables.STRANA;

/**
 * Casove uspornejsi reseni. Principielne stejne jako {@link BoardHonzaPresMapyMnozin}, ale misto mapMnozin jsou tu dvojrozmerna pole.
 * (Resp. pole tridy PC, kde PC je jen obalka nad polem.)
 */
public class BoardHonzaPresDvojrozmernaPole {
    
    private PC[] polickaPoRadcich = new PC[STRANA+1]; // nulty index nepouzivame
    private PC[] polickaPoSloupcich = new PC[STRANA+1]; // nulty index nepouzivame
    private PC[] polickaPoInboxech = new PC[STRANA+1]; // nulty index nepouzivame
  
    // toto bychom asi nepotrebovali. Stejna data ziskame napr. i z polickaPoRadcich
    public Position[][] policka = new Position[STRANA + 1][STRANA + 1];//+1 /nezajima me nulove pole

    public BoardHonzaPresDvojrozmernaPole() {
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

    public Position[] getRow(int rowNumber) {
      return polickaPoRadcich[rowNumber].getPositions();
    }

    public Position[] getColumn(int columnNumber) {
      return polickaPoSloupcich[columnNumber].getPositions();
    }

    public Position[] getInbox(int inboxNumber) {
      return polickaPoInboxech[inboxNumber].getPositions();
    }
    

    public void printPolicka() {
      // ... to by mohlo zustat
    }
    
    // PositionCollection ... bud jeden radek nebo jeden sloupec nebo jeden inbox
    private static class PC {
      private Position[/*poradi policka v kolekci*/] positions = new Position[STRANA]; // indexujem od nuly
      
      /**
       * Slouzi pouze k inicializaci metodou {@link #add()}.
       */
      // (Nula = nic neni inicializovano.)
      private int lastInitializedPositionIndex = 0;

      /**
       * Slouzi pouze k inicializaci ... kvuli naplneni.
       */
      public void add(Position position) {
        assert lastInitializedPositionIndex < STRANA;
        positions[++lastInitializedPositionIndex] = position;
      }

      public Position[] getPositions() {
        return positions;
      }
    }
}
