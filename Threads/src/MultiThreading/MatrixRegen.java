package MultiThreading;

public class MatrixRegen extends AbstraktesProgramm {

    private static final char[] zeichen = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int BREITE = 70;
    private final boolean[][] bildschirm;
    private GUI gui;

    public MatrixRegen(int programmNummer, ErgebnisListener ergebnisListener, GUI gui) {
        super(programmNummer, ergebnisListener);
        this.gui = gui; // GUI-Objekt zuweisen
        bildschirm = new boolean[BREITE][];
        for (int i = 0; i < BREITE; i++) {
            bildschirm[i] = new boolean[30]; // 30 Zeilen hoch
        }
    }

    @Override
    protected String berechneAlgorithmus() {
        // Bewegen jedes Zeichens in jeder Spalte um eine Position nach unten
        for (int spalte = 0; spalte < BREITE; spalte++) {
            for (int zeile = bildschirm[spalte].length - 1; zeile > 0; zeile--) {
                bildschirm[spalte][zeile] = bildschirm[spalte][zeile - 1];
            }
            // Füge ein neues Zeichen oben in der Spalte ein
            bildschirm[spalte][0] = Math.random() > 0.5;
        }
        return erzeugeAusgabeString();
    }

    private String erzeugeAusgabeString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bildschirm[0].length; i++) {
            for (int j = 0; j < bildschirm.length; j++) {
                if (bildschirm[j][i]) {
                    sb.append(zeichen[(int) (Math.random() * zeichen.length)]);
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        if (gui != null) {
            gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
        } else {
            super.zeigeErgebnisAn(formatiertesErgebnis);
        }
    }


    @Override
    protected String formatiereErgebnis(String ergebnis) {

        return super.formatiereErgebnis(ergebnis);
    }

}
