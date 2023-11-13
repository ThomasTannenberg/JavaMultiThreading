package MultiThreading;

import java.util.Arrays;
import java.util.Random;

public class ArraySortieren extends AbstraktesProgramm {

    private final Random random;
    private final GUI gui;
    private final int arrayGroesse;

    public ArraySortieren(int programmNummer, ErgebnisListener listener, GUI gui, int arrayGroesse) {
        super(programmNummer, listener);

        this.random = new Random();
        this.gui = gui;
        this.arrayGroesse = arrayGroesse;

    }

    @Override
    protected String berechneAlgorithmus() {
        int[] zahlenArray = generiereZufaelligesArray(arrayGroesse);
        int[] sortiertesArray = zahlenArray.clone();
        Arrays.sort(sortiertesArray); // Verwendet die Dual-Pivot-Quicksort-Implementierung von Arrays.sort

        return "Ursprüngliches Array: " + Arrays.toString(zahlenArray) +
                "\nSortiertes Array: " + Arrays.toString(sortiertesArray);
    }

    private int[] generiereZufaelligesArray(int groesse) {
        int[] zahlenArray = new int[groesse];
        for (int i = 0; i < groesse; i++) {
            zahlenArray[i] = random.nextInt(100); // Generiert eine Zufallszahl zwischen 0 und 99
        }
        return zahlenArray;
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }

    @Override
    protected String formatiereErgebnis(String ergebnis) {
        return String.format("Programm %d - Sortierung eines Arrays:\n%s", getProgrammNummer(), ergebnis);
    }
}
