
package MultiThreading;

// Import der notwendigen Klassen aus den Swing- und AWT-Bibliotheken für GUI-Komponenten und Layout-Management.
import javax.swing.*;
import java.awt.*;

// Deklaration einer abstrakten Klasse für das GUI, die nicht direkt instanziiert werden kann.
public abstract class AbstraktGUI {
    // Geschützte GUI-Komponenten, damit sie von Unterklassen verwendet werden können.
    protected JFrame frame;
    protected JButton startButton;
    protected JButton endButton;
    protected JLabel[] outputLabels;
    protected JPanel labelPanel;
    protected JScrollPane scrollPane;

    // Konstruktor der abstrakten GUI-Klasse.
    public AbstraktGUI() {
        // Initialisieren der GUI-Komponenten.
        initialisiereKomponenten();
        // Konfigurieren und Einrichten des GUIs.
        setupGUI();
    }

    // Initialisierung der GUI-Komponenten mit Standardwerten und Konfiguration.
    private void initialisiereKomponenten() {
        // Erstellen des Hauptfensters mit einem Titel.
        frame = new JFrame("LF11 MultiThreading by Thomas Tannenberg");
        // Festlegen der Standardaktion beim Schließen des Fensters auf Beenden der Anwendung.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Erstellen eines Panels zur Aufnahme von Beschriftungen mit einem Rasterlayout.
        labelPanel = new JPanel(new GridLayout(4, 2, 0, 0));

        // Setzen Sie den Hintergrund des Hauptfensters und der Panels auf Schwarz
        frame.getContentPane().setBackground(Color.BLACK);

        labelPanel.setBackground(Color.BLACK);
        labelPanel.setForeground(Color.WHITE);

        // Initialisierung eines Arrays für die Ausgabeetiketten.
        outputLabels = new JLabel[8];

        // Erstellen eines Bildlaufbereichs, der das Etikettenpanel enthält.
        scrollPane = new JScrollPane(labelPanel);

        // Sicherstellen, dass die vertikale Bildlaufleiste immer angezeigt wird.
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Erstellen von Schaltflächen zum Starten und Beenden von Programmen.
        startButton = new JButton("Start");
        endButton = new JButton("Beenden");

        // Setzen Sie die Vordergrundfarbe der Schaltflächen auf Weiß
        startButton.setForeground(Color.WHITE);
        endButton.setForeground(Color.WHITE);

        // Setzen Sie die Hintergrundfarbe der Schaltflächen auf Schwarz
        startButton.setBackground(Color.BLACK);
        endButton.setBackground(Color.BLACK);

        // Hinzufügen eines ActionListeners zur Beenden-Schaltfläche, um zu definieren, was beim Klicken passieren soll.
        endButton.addActionListener(e -> beendeProgramme());

        // Hinzufügen der Beenden-Schaltfläche zum Frame (wird später im SetupGUI-Methode verschoben).
        frame.add(endButton);
    }

    // Einrichten des GUI-Layouts und Hinzufügen von Komponenten zum Frame.
    private void setupGUI() {
        // Initialisieren jedes Ausgabeetiketts und zum Panel hinzufügen.
        for (int i = 0; i < outputLabels.length; i++) {
            outputLabels[i] = new JLabel("Programm " + (i + 1) + ": Warte auf Start...", SwingConstants.CENTER);
            outputLabels[i].setOpaque(true); // Sollte auf true gesetzt werden, um die Hintergrundfarbe sichtbar zu machen
            outputLabels[i].setBackground(Color.BLACK); // Setzt den Hintergrund auf Schwarz
            outputLabels[i].setForeground(Color.WHITE); // Setzt die Textfarbe auf Weiß
            labelPanel.add(outputLabels[i]);
        }
        // Hinzufügen von ActionListeners zu den Schaltflächen, um das Verhalten beim Klicken zu definieren.
        startButton.addActionListener(e -> erfrageProgrammNummerUndStarte());
        endButton.addActionListener(e -> beendeProgramme());

        // Erstellen eines Steuerungspanels, um die Start- und Beenden-Schaltflächen aufzunehmen.
        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(endButton);

        // Hinzufügen des Bildlaufbereichs und des Steuerungspanels zum Frame mit festgelegten Layoutpositionen.
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Zusammenpacken der Komponenten innerhalb des Frames.
        frame.pack();
        // Festlegen einer Mindestgröße für den Frame.
        frame.setMinimumSize(new Dimension(900, 700));
        // Festlegen einer bevorzugten Größe für den Frame.
        frame.setPreferredSize(new Dimension(900, 700));
        // Zentrieren des Frames auf dem Bildschirm.
        frame.setLocationRelativeTo(null);
        // Sichtbarmachen des Frames.
        frame.setVisible(true);
    }

    // Abstrakte Methoden, die von der GUI.java Unterklasse implementiert werden muss.
    protected abstract void starteProgramme(int programCount);
    protected abstract void erfrageProgrammNummerUndStarte();
    protected abstract void beendeProgramme();
    public abstract void updateAusgabe(int programNumber, String result);
}
