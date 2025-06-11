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
    // Processos que estão senod executados
    private Queue<Process> runtimeProcessesQueue = new LinkedList<>();
    // Todos os processos coletados a partir do arquivo de texto
    private List<Process> allProcessesList = new ArrayList<>();
    // Lista de processos terminados
    private List<Process> finishedProcessesList = new ArrayList<>();
    // Processo que está sendo executado
    private Process executingProcess = null;
    // Quantum de tempo para o round robin
    private int quantum;
    // Tempo de execução total do round robin
    private int totalTime = 0;
    
    // Pega o primeiro elemento na fila e coloca no último lugar da fila
    private void bringFirstElementToLastIndex() {
        if (!runtimeProcessesQueue.isEmpty()) {
            runtimeProcessesQueue.add(runtimeProcessesQueue.poll());
        }
    }
    
    // Principal comando para rodar o round robin
    public void run(boolean showDialogues, boolean fastExecutionMode) {
        Scanner sc = ScannerSingleton.getInstance();
        // Entra o tempo de quantum 
        System.out.printf("\nDigite o tempo de quantum: ");
        this.quantum = Integer.parseInt(sc.next());
        String timeline = "|0|";

        int allProcessesListInitialSize = allProcessesList.size();

        moveArrivingProcessToRuntimeQueue();

        executingProcess = runtimeProcessesQueue.peek();
        
        // Só termina a execução do round robin caso o tamanho inicial da fila de todos os processos seja igual ao tamanho da lista de
        // processos terminados
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
                    
                    executingProcess = null;
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
        
        System.out.println("\nRelatório de execução do algoritmo Round Robin:");
        System.out.println("Linha do tempo de execução: " + timeline);
        System.out.println("Tempo médio de espera: " + calculateAverageWaitingTime(finishedProcessesList) + "s");
    }
    
    // Método que contém tudo que será executado a cada segundo 
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
        
        // Mostra as informações do processo em execução caso showDialogues esteja como true
        if (showDialogues && executingProcess != null) {
            System.out.println("\n====================\n");
            System.out.println("Processo em execução: " + executingProcess.getId());
            System.out.println("Tempo restante do processo: " + executingProcess.getProcessTime());
            System.out.println("Tempo total: " + totalTime);
        }
        
        totalTime++;
    }
    
    // Calcula o tempo médio de espera dos processos ao fim da execução
    private static int calculateAverageWaitingTime(List<Process> processesList) { 
        int total = 0;
        
        for(Process p : processesList) { 
            total = total + p.getWaitingTime();
        }
        
        // Retorna a média
        return total / processesList.size();
    }
    
    // Incrementa o tempo de espera para todos os outros processos que não estão sendo executados 
    private void incrementWaitingTimeForAllProcesses(Process currentProcess) {
        for (Process p : runtimeProcessesQueue) {
            if (!p.getId().equals(currentProcess.getId())) {
                p.incrementWaitingTime();
            }
        }
    }
    
    // Move o processo que tá chegando para a fila de execução de processos
    private void moveArrivingProcessToRuntimeQueue() {
        for (Process p : allProcessesList) {
            if (p.getArrivalTime() == totalTime) {
                runtimeProcessesQueue.add(p);
                return;
            }
        }
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
        }
    }
}