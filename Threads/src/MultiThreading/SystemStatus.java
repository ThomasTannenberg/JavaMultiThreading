package MultiThreading;

import javax.swing.SwingUtilities;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.management.ThreadInfo;
import java.text.DecimalFormat;

public class SystemStatus extends AbstraktesProgramm {

    private final GUI gui;

    public SystemStatus(int programmNummer, ErgebnisListener listener, GUI gui) {
        super(programmNummer, listener);
        this.gui = gui;
    }

    @Override
    protected String berechneAlgorithmus() {
        // Holt die Management Beans für das Betriebssystem und die Threads
        com.sun.management.OperatingSystemMXBean sunOsBean =
                ManagementFactory.getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);

        // Verwendet die com.sun.management.OperatingSystemMXBean, um die CPU-Last zu erhalten
        double cpuLoad = sunOsBean.getCpuLoad() * 100; // Umwandlung in Prozent

        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;


        int appThreadCount = getAppThreadCount();

        // Formatieren der Ausgabe
        DecimalFormat df = new DecimalFormat("#.##");
        String cpuLoadFormatted = cpuLoad < 0 ? "nicht verfügbar" : df.format(cpuLoad);
        String usedMemoryFormatted = df.format(usedMemory / 1024.0 / 1024.0);
        String totalMemoryFormatted = df.format(totalMemory / 1024.0 / 1024.0);

        // Verwendung von appThreadCount anstelle von threadCount ist besser!!!!!
        return String.format("CPU Last: %s %%, Verwendeter Speicher: %s MB, Gesamtspeicher: %s MB, Anwendungs Threads in der JVM gesamt: %d",
                cpuLoadFormatted, usedMemoryFormatted, totalMemoryFormatted, appThreadCount);
    }

    // Neue Methode, um die Anzahl der Anwendungsthreads zu berechnen... funktioniert nicht wirklich!
    private int getAppThreadCount() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadBean.getAllThreadIds();
        int appThreadCount = 0;
        for (long threadId : threadIds) {
            ThreadInfo info = threadBean.getThreadInfo(threadId);
            if (info != null) { // Sicherstellen, dass das ThreadInfo-Objekt nicht null ist
                String threadName = info.getThreadName();
                // Filtern von Threads, die wahrscheinlich zur JVM gehören und nicht zur Anwendung
                if (!threadName.startsWith("GC") && !threadName.startsWith("Compiler") && !threadName.startsWith("Signal Dispatcher") && !threadName.startsWith("Reference Handler") && !threadName.startsWith("Finalizer")) {
                    appThreadCount++;
                }
            }
        }
        return appThreadCount;
    }

    @Override
    protected void zeigeErgebnisAn(String formatiertesErgebnis) {
        // Sicheres Update der GUI im EDT (Event Dispatch Thread).
        SwingUtilities.invokeLater(() -> gui.updateAusgabe(getProgrammNummer(), formatiertesErgebnis));
    }

}



