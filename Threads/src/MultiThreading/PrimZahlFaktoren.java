package MultiThreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimZahlFaktoren extends AbstraktesProgramm {

    private final Random random;
    private final GUI gui;

    public PrimZahlFaktoren(int programmNummer, ErgebnisListener listener, GUI gui) {
        super(programmNummer, listener);
        this.random = new Random();
        this.gui = gui;
    }

    @Override
    protected String berechneAlgorithmus() {
        int zahl = random.nextInt(10000) + 1; // Generiert eine Zahl zwischen 1 und 10000.
        List<Integer> faktoren = findePrimfaktoren(zahl);
        return String.format("Die zufällig generierte Zahl %d hat folgende Primzahlfaktoren: %s", zahl, faktoren);
    }

    private List<Integer> findePrimfaktoren(int zahl) {
        List<Integer> faktoren = new ArrayList<>();
        while (zahl % 2 == 0) {
            faktoren.add(2);
            zahl /= 2;
        }
        for (int i = 3; i <= Math.sqrt(zahl); i += 2) {
            while (zahl % i == 0) {
                faktoren.add(i);
                zahl /= i;
            }
        }
        if (zahl > 2) {
            faktoren.add(zahl);
        }
        return faktoren;
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        // Update die GUI mit dem formatierten Ergebnis
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }

}
