package MultiThreading;
public abstract class AbstraktesProgramm implements Runnable {
    protected int programmNummer;
    private Thread workerThread;
    protected volatile boolean running = false;
    protected String lastResult = "";
    private final ErgebnisListener ergebnisListener;

    public AbstraktesProgramm(int programmNummer, ErgebnisListener ergebnisListener) {
        this.programmNummer = programmNummer;
        this.ergebnisListener = ergebnisListener;
    }
    public void starteAlgorithmus() {
        synchronized (this) {
            if (running) return;
            running = true;
        }
        workerThread = new Thread(this, "Algorithmus-Worker-" + programmNummer);
        workerThread.start();
    }
    public void stoppeAlgorithmus() {
        synchronized (this) {
            if (!running) return;
            running = false;
        }
        workerThread.interrupt(); // Unterbricht den Thread.
    }
    protected abstract String berechneAlgorithmus();

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String ergebnis = berechneAlgorithmus();
                synchronized (this) {
                    if (!running) break;
                    lastResult = formatiereErgebnis(ergebnis);
                }
                zeigeErgebnisAn(lastResult);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            System.out.println("Tschüss Programm " + programmNummer + "!");
        }
    }
    protected String formatiereErgebnis(String ergebnis) {
        return String.format("Programm %d - Ergebnis: %s", programmNummer, ergebnis);
    }
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        if (ergebnisListener != null) {
            ergebnisListener.updateAusgabe(programmNummer, formatiertesErgebnis);
        }
    }
    public int getProgrammNummer() {
        return programmNummer;
    }
    public boolean isRunning() {
        return running;
    }
}

