package MultiThreading;

public class WorkerThread implements Runnable {
    private final AbstraktesProgramm programm;

    public WorkerThread(AbstraktesProgramm programm) {
        this.programm = programm;
    }

    @Override
    public void run() {
        programm.starteAlgorithmus();
    }

    public AbstraktesProgramm getProgramm() {
        return programm;
    }
}
