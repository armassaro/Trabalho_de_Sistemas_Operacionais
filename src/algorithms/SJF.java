package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SJF {
    private Queue<Process> runtimeProcessesQueue = new LinkedList<>();
    private List<Process> allProcessesList = new ArrayList<>();
    private List<Process> finishedProcessesList = new ArrayList<>();
    private int totalTime = 0;
    private String timeline = "";
    private boolean showDialogues, fastExecutionMode;

    public SJF(boolean showDialogues, boolean fastExecutionMode) { 
        this.showDialogues = showDialogues;
        this.fastExecutionMode = fastExecutionMode;
    }

    public void run() { 
        // Simula passagem de tempo
        if(fastExecutionMode) { 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
    }

    private void checkForShortestJob() { 

    }

    // ================= Getters e Setters =================

     public Queue<Process> getRuntimeQueue() {
        return runtimeProcessesQueue;
    }

    public void setRuntimeQueue(Queue<Process> runtimeProcessesQueue) {
        this.runtimeProcessesQueue = runtimeProcessesQueue;
    }

    public List<Process> getAllProcessesList() {
        return allProcessesList;
    }

    public void setAllProcessesList(List<Process> allProcessesList) {
        this.allProcessesList = allProcessesList;
    }

    public void addProcessToRuntimeQueue(Process process) {
        this.runtimeProcessesQueue.add(process);
    }

    public void addProcessToAllProcessesList(Process process) {
        this.allProcessesList.add(process);
    }

    public List<Process> getFinishedProcessesList() {
        return finishedProcessesList;
    }

    // ================= Métodos auxiliares =================

    public static void showListContent(List<Process> processesList) {
        for (Process p : processesList) {
            System.out.println("\n====================\n");
            System.out.println("ID do processo: " + p.getId());
            System.out.println("Tempo de processamento restante: " + p.getProcessTime());
            System.out.println("Tempo de chegada do processo: " + p.getArrivalTime());
            System.out.println("Tempo de espera do processo: " + p.getWaitingTime());
        }
    }
}
