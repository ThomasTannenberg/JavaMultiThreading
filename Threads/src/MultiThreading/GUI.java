package MultiThreading;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class GUI extends AbstraktGUI implements ErgebnisListener {

    private final ExecutorService executorService;
    ErgebnisListener listener = this;
    private final List<AbstraktesProgramm> alleLaufendenProgramme = new ArrayList<>();

    public GUI() {
        super();
        executorService = Executors.newFixedThreadPool(10);

    }

    protected void starteProgramme(int programCount) {
        for (int i = 0; i < programCount; i++) {
            WorkerThread task = erstelleTaskProgrammNummer(i, this);
            if (task != null) {
                alleLaufendenProgramme.add(task.getProgramm());
                executorService.execute(task);
            }
        }
        WorkerThread task = erstelleTaskProgrammNummer(7, this);
        if (task != null) {
            alleLaufendenProgramme.add(task.getProgramm());
            executorService.execute(task);
        }
    }

    private WorkerThread erstelleTaskProgrammNummer(int programNumber, GUI gui) {
        AbstraktesProgramm task;
        switch (programNumber) {
            case 0:
                task = new PrimZahlFaktoren(programNumber + 1, listener, gui);
                break;
            case 1:
                int dimension = 20;
                task = new MatrixMultiplikation(programNumber + 1, listener, gui, dimension);
                break;
            case 2:
                int arrayGroesse = 40;
                task = new ArraySortieren(programNumber + 1, listener, gui, arrayGroesse);
                break;
            case 3:
                task = new Fibonacci(programNumber + 1, listener, gui);
                break;
            case 4:
                int breite = 35;
                int hoehe = 35;
                task = new GameOfLife(programNumber + 1, listener, gui, breite, hoehe);
                break;
            case 5:
                task = new MatrixRegen(programNumber + 1, listener, gui);
                break;
            case 6:
                task = new SchaltJahr(programNumber + 1, listener, this);
                break;
            case 7:
                task = new SystemStatus(programNumber + 1, listener, gui);
                break;
            default:
                throw new IllegalArgumentException("Ungültige Programmnummer: " + (programNumber + 1));
        }
        return new WorkerThread(task);
    }

    @Override
    protected void erfrageProgrammNummerUndStarte() {

        String input = JOptionPane.showInputDialog(
                frame,
                "Wie viele Programme (1 bis 7) möchten Sie starten? NACH DEM START BITTE MAXIMIEREN!",
                "Anzahl der Programme",
                JOptionPane.QUESTION_MESSAGE
        );
        int programCount;
        try {

            programCount = Integer.parseInt(input);
        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(frame, "Bitte geben Sie eine gültige Zahl ein!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (programCount < 1 || programCount > 7) {

            JOptionPane.showMessageDialog(frame, "Die Zahl muss zwischen 1 und 7 liegen!", "Fehler", JOptionPane.ERROR_MESSAGE);
        } else {

            starteProgramme(programCount);
        }
    }

    @Override
    protected void beendeProgramme() {

        stopAllRunningPrograms();

        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS))
                    System.err.println("Es wurde keine Aufgabe gelöscht.");
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        frame.dispose();
    }

    private void stopAllRunningPrograms() {
        for (AbstraktesProgramm programm : alleLaufendenProgramme) {
            programm.stoppeAlgorithmus();
        }
        alleLaufendenProgramme.clear();
    }

    @Override
    public void updateAusgabe(int programNumber, String result) {
        if (programNumber < 1 || programNumber > outputLabels.length) {
            return;
        }

        // Konvertieren des Ergebnistexts in HTML, um Zeilenumbrüche zu ermöglichen
        String formattedResult = "<html>" + result.replace("\n", "<br>") + "</html>";

        // Verwendung von SwingUtilities.invokeLater, um Thread-Sicherheit zu gewährleisten,
        // da updateOutput von einem anderen Thread als dem Event-Dispatch-Thread aufgerufen werden könnte
        SwingUtilities.invokeLater(() -> {
            // Setzen des Texts für das entsprechende Label
            outputLabels[programNumber - 1].setText(formattedResult);
        });
    }

}






