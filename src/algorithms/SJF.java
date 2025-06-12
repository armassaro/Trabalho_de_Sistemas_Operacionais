package algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SJF {
    private List<Process> runtimeProcessesList = new LinkedList<>();
    private List<Process> allProcessesList = new ArrayList<>();
    private List<Process> finishedProcessesList = new ArrayList<>();
    private int totalTime = 0;
    private String timeline = "";
    private boolean showDialogues, fastExecutionMode;
    private Process executingProcess = null;
    
    public SJF(boolean showDialogues, boolean fastExecutionMode) { 
        this.showDialogues = showDialogues;
        this.fastExecutionMode = fastExecutionMode;
    }

    public void run() { 
        int allProcessesListSize = allProcessesList.size();
        
        while(finishedProcessesList.size() < allProcessesListSize) { 
            // Simula passagem de tempo
            if(fastExecutionMode) { 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            executingProcess = checkForShortestJob();
            
            // Move o próximo processo para a fila de execução
            moveArrivingProcessToRuntimeList();
            
            if(showDialogues) {
                System.out.println("\n====================\n");
                System.out.println("Processo em execução: " + executingProcess.getId());
                System.out.println("Tempo restante do processo: " + executingProcess.getProcessTime());
                System.out.println("Tempo de espera do processo: " + executingProcess.getWaitingTime());
                System.out.println("Tempo total: " + totalTime);
            }
            
            totalTime++;
        }
    }

    private Process checkForShortestJob() { 
        // Pega o menor tempo dos processos disponíveis
        int shortestJobTime = (executingProcess == null ? 0 : executingProcess.getProcessTime());
        
        for(Process p : runtimeProcessesList) { 
            if(p.getProcessTime() < shortestJobTime) { 
                return p;
            }
        }
        
        // Caso passar pela runtimeQueue e não encontrar um processo com tempo menor que o processo com menor tempo, então retorna o próprio executingProcess
        return executingProcess;
    }
    
    // Move o próximo processo para a lista de processos em execução, ordenando os mesmos conforme tempo de processamento
    private void moveArrivingProcessToRuntimeList() {
        Iterator<Process> runtimeProcessesListIterator = runtimeProcessesList.iterator();
        
        for (Process p : allProcessesList) {
            if(p.getArrivalTime() == totalTime) {
                
            }
        }
    }

    // ================= Getters e Setters =================

     public List<Process> getRuntimeList() {
        return runtimeProcessesList;
    }

    public void setRuntimeList(List<Process> runtimeProcessesList) {
        this.runtimeProcessesList = runtimeProcessesList;
    }

    public List<Process> getAllProcessesList() {
        return allProcessesList;
    }

    public void setAllProcessesList(List<Process> allProcessesList) {
        this.allProcessesList = allProcessesList;
    }

    public void addProcessToRuntimeList(Process process) {
        this.runtimeProcessesList.add(process);
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
        }
    }
}
