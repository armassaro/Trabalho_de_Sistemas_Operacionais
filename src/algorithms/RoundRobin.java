package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author armassaro
 */
public class RoundRobin {
    private Queue<Process> runtimeProcessesQueue = new LinkedList<>();
    private List<Process> allProcessesList = new ArrayList<>();
    private List<Process> finishedProcessesList = new ArrayList<>();
    private Process executingProcess = null;
    private int quantum;
    private int totalTime = 0;

    private void bringFirstElementToLastIndex() {
        if (!runtimeProcessesQueue.isEmpty()) {
            runtimeProcessesQueue.add(runtimeProcessesQueue.poll());
        }
    }

    public void run(boolean showDialogues, boolean fastExecutionMode) {
        Scanner sc = ScannerSingleton.getInstance();
        System.out.printf("\nDigite o tempo de quantum: ");
        this.quantum = Integer.parseInt(sc.next());
        String timeline = "|0|";

        int allProcessesListInitialSize = allProcessesList.size();

        moveArrivingProcessToRuntimeQueue();

        executingProcess = runtimeProcessesQueue.peek();

        while (finishedProcessesList.size() < allProcessesListInitialSize) {
            int executionCount = 0;

            while (executionCount < quantum) {
                if (executingProcess == null) break;
                // tick(): Realiza testes de condição para verificar coisas como se existem próximos processos a serem implementados do allProcessesList para runtimeProcessesQueue
                tick(showDialogues, fastExecutionMode);
                
                executionCount++;
                
                // Se o processo termina o tempo de processamento, constrói na string com os tempos 
                if (executingProcess.getProcessTime() <= 0) {
                    finishedProcessesList.add(runtimeProcessesQueue.poll());
                    
                    timeline = timeline + "----" + executingProcess.getId() + "----" + "|" + totalTime + "|";
                    break;
                }
            }


            if(executingProcess != null && executingProcess.getProcessTime() > 0) { 
                // Constrói a String que constitui a linha de tempo de execução
                timeline = timeline + "----" + executingProcess.getId() + "----" + "|" + totalTime + "|";
                
                // Coloca o último elemento em execução para o último índice da fila e depois reatribui executingProcess
                bringFirstElementToLastIndex();
            }
            
            executingProcess = runtimeProcessesQueue.peek();
            
        }
        //.
        System.out.println("\nRelatório de execução do algoritmo Round Robin:");
        System.out.println("Linha do tempo de execução: " + timeline);
        System.out.println("Tempo médio de espera: " + calculateAverageWaitingTime(finishedProcessesList) + "s");
    }

    private void tick(boolean showDialogues, boolean fastExecutionMode) {
        if (!fastExecutionMode) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        incrementWaitingTimeForAllProcesses(executingProcess);
        moveArrivingProcessToRuntimeQueue();

        if (executingProcess != null) {
            executingProcess.decrementProcessTime();
        }

        if (showDialogues && executingProcess != null) {
            System.out.println("\n====================\n");
            System.out.println("Processo em execução: " + executingProcess.getId());
            System.out.println("Tempo restante do processo: " + executingProcess.getProcessTime());
            System.out.println("Tempo de espera do processo: " + executingProcess.getWaitingTime());
            System.out.println("Tempo total: " + totalTime);
        }
        
        totalTime++;
    }
    
    public static int calculateAverageWaitingTime(List<Process> processesList) { 
        int total = 0;
        
        for(Process p : processesList) { 
            total = total + p.getWaitingTime();
        }
        
        // Retorna a média
        return total / processesList.size();
    }
    
    private void incrementWaitingTimeForAllProcesses(Process currentProcess) {
        for (Process p : runtimeProcessesQueue) {
            if (p != currentProcess) {
                p.incrementWaitingTime();
            }
        }
    }

    private void moveArrivingProcessToRuntimeQueue() {
        List<Process> arriving = new ArrayList<>();
        for (Process p : allProcessesList) {
            if (p.getArrivalTime() == totalTime) {
                arriving.add(p);
            }
        }
        allProcessesList.removeAll(arriving);
        runtimeProcessesQueue.addAll(arriving);
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