package MultiThreading;

import java.util.Random;

public class SchaltJahr extends AbstraktesProgramm {
    private final GUI gui;
    private final Random random;

    public SchaltJahr(int programmNummer, ErgebnisListener listener, GUI gui) {
        super(programmNummer, listener);
        this.gui = gui;
        this.random = new Random();
    }

    @Override
    protected String berechneAlgorithmus() {
        int jahr = random.nextInt(9998) + 1;
        if (jahr % 4 == 0 && (jahr % 100 != 0 || (jahr % 100 == 0 && jahr % 400 == 0 ))){
            return String.format("Das Jahr ist %d ist ein Schaltjahr.", jahr);
        } else {
            return String.format("Das Jahr ist %d ist kein Schaltjahr.", jahr);
        }
    }
    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }
}
