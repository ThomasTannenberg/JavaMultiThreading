package MultiThreading;

public class GameOfLife extends AbstraktesProgramm {

    private final int breite;
    private final int hoehe;
    private boolean[][] zellen;
    private final GUI gui;

    public GameOfLife(int programmNummer, ErgebnisListener ergebnisListener, GUI gui, int breite, int hoehe) {
        super(programmNummer, ergebnisListener);
        this.gui = gui;
        this.breite = breite;
        this.hoehe = hoehe;
        zellen = new boolean[breite][hoehe];
        initialisiereZufaellig();
    }

    private void initialisiereZufaellig() {
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                zellen[x][y] = Math.random() > 0.5;
            }
        }
    }

    @Override
    protected String berechneAlgorithmus() {
        boolean[][] naechsterZustand = new boolean[breite][hoehe];
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                int lebendeNachbarn = zaehleLebendeNachbarn(x, y);
                if (zellen[x][y]) {
                    naechsterZustand[x][y] = lebendeNachbarn == 2 || lebendeNachbarn == 3;
                } else {
                    naechsterZustand[x][y] = lebendeNachbarn == 3;
                }
            }
        }
        zellen = naechsterZustand;
        return erzeugeAusgabeString();
    }

    private int zaehleLebendeNachbarn(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = (x + i + breite) % breite;
                int ny = (y + j + hoehe) % hoehe;
                if (zellen[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    private String erzeugeAusgabeString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < hoehe; y++) {
            for (int x = 0; x < breite; x++) {
                sb.append(zellen[x][y] ? 'O' : '.');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis);
    }

    @Override
    protected String formatiereErgebnis(String ergebnis) {
        return String.format("Programm %d - Aktueller Zustand des Game of Life:\n%s", getProgrammNummer(), ergebnis);
    }

    @Override
    public void stoppeAlgorithmus() {
        super.stoppeAlgorithmus();
        if (gui != null) {
            gui.updateAusgabe(getProgrammNummer(), "Algorithmus gestoppt.");
        }
    }
}
