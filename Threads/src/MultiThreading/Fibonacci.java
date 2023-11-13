package MultiThreading;

import java.util.Random;

public class Fibonacci extends AbstraktesProgramm {

    private int sequenzLaenge;
    private final GUI gui;

    public Fibonacci(int programmNummer, ErgebnisListener listener, GUI gui) {
        super(programmNummer, listener);

        this.gui = gui;
        // Erzeuge eine zufällige Sequenzlänge zwischen 1 und 20
        Random random = new Random();
        this.sequenzLaenge = random.nextInt(30) + 1; // 1 bis 20 inklusive
    }

    @Override
    protected String berechneAlgorithmus() {
        Random random = new Random();
        this.sequenzLaenge = random.nextInt(30) + 1; // 1 bis 20 inklusive
        return berechneFibonacciSequenz(sequenzLaenge);
    }


    private String berechneFibonacciSequenz(int laenge) {
        if (laenge <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        long a = 0, b = 1;
        builder.append(a);
        for (int i = 1; i < laenge; i++) {
            builder.append(", ").append(b);
            long next = a + b;
            a = b;
            b = next;
        }
        return builder.toString();
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }

    @Override
    protected String formatiereErgebnis(String ergebnis) {
        return String.format("Programm %d - Fibonacci-Sequenz bis zur %d. Zahl:\n%s", getProgrammNummer(), sequenzLaenge, ergebnis);
    }
}
