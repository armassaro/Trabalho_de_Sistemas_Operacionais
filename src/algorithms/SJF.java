package algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SJF {
    private List<Process> runtimeProcessesList = new LinkedList<>();
    private List<Process> allProcessesList = new ArrayList<>();
    private List<Process> finishedProcessesList = new ArrayList<>();
    private int totalTime = 0;
    private String timeline = "|0|";
    private boolean showDialogues, fastExecutionMode;
    private Process executingProcess = null;

    // Variáveis auxiliares para controlar mudanças na timeline
    private Process lastProcess = null;
    private int lastProcessStartTime = 0;

    public SJF(boolean showDialogues, boolean fastExecutionMode) {
        this.showDialogues = showDialogues;
        this.fastExecutionMode = fastExecutionMode;
    }

    public void run() {
        int allProcessesListSize = allProcessesList.size();

        while (finishedProcessesList.size() < allProcessesListSize) {
            // Simula passagem de tempo
            if (!fastExecutionMode) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Move os processos com tempo de chegada igual ao tempo atual para a fila de execução
            moveArrivingProcessToRuntimeList();

            // Seleciona o processo com menor tempo de execução restante
            Process shortest = checkForShortestJob();

            // Se houve mudança de processo (preempção), atualiza o processo atual
            if (executingProcess != shortest) {
                if (executingProcess != null) {
                    // Finaliza o processo anterior na timeline
                    timeline += "----" + executingProcess.getId() + "----|" + totalTime + "|";
                }
                if (shortest != null) {
                    lastProcessStartTime = totalTime;
                    if (shortest.getResponseTime() == -1) {
                        shortest.setResponseTime(totalTime - shortest.getArrivalTime());
                    }
                }
                executingProcess = shortest;
            }
            
            if (executingProcess != null) {
                // Decrementa o tempo de execução do processo atual
                executingProcess.decrementProcessTime();

                // Aumenta o tempo de espera dos demais processos
                incrementWaitingTimeForAllProcesses(executingProcess);

                // Verifica se o processo terminou e move para a lista de finalizados
                if (executingProcess.getProcessTime() == 0) {
                    Process finished = new Process(
                        executingProcess.getId(),
                        executingProcess.getArrivalTime().toString(),
                        "0"
                    );
                    finished.setWaitingTime(executingProcess.getWaitingTime());
                    finished.setResponseTime(executingProcess.getResponseTime());

                    runtimeProcessesList.remove(executingProcess);
                    finishedProcessesList.add(finished);

                    // Finaliza o processo na timeline
                    timeline += "----" + executingProcess.getId() + "----|" + (totalTime + 1) + "|";

                    executingProcess = null;
                }
            }

            if (showDialogues && executingProcess != null) {
                System.out.println("\n====================\n");
                System.out.println("Processo em execução: " + executingProcess.getId());
                System.out.println("Tempo restante do processo: " + executingProcess.getProcessTime());
                System.out.println("Tempo total: " + totalTime);
                System.out.println("Tempo de resposta do processo: " + executingProcess.getResponseTime());
            }

            totalTime++;
        }

        // Finalização pendente (caso o último processo termine sem preempção)
        if (executingProcess != null) {
            timeline += "----" + executingProcess.getId() + "----|" + totalTime + "|";
        }

        // Exibe a timeline ao final da execução e tempo médio de espera
        System.out.println("\nTimeline: " + timeline);
        System.out.printf("\nTempo médio de resposta: %.2fs\n", calculateAverageResponseTime(finishedProcessesList));
        System.out.printf("\nTempo médio de espera: %.2fs\n", calculateAverageWaitingTime(finishedProcessesList));
    }

    private Process checkForShortestJob() {
        if (runtimeProcessesList.isEmpty()) return null;

        Process shortestProcess = runtimeProcessesList.get(0);
        for (Process p : runtimeProcessesList) {
            if (p.getProcessTime() < shortestProcess.getProcessTime()) {
                shortestProcess = p;
            }
        }
        return shortestProcess;
    }

    private void incrementWaitingTimeForAllProcesses(Process currentProcess) {
        for (Process p : runtimeProcessesList) {
            if (p != currentProcess) {
                p.incrementWaitingTime();
            }
        }
    }

    private boolean moveArrivingProcessToRuntimeList() {
        boolean processAdded = false;
        for (Process p : allProcessesList) {
            if (p.getArrivalTime() == totalTime && !runtimeProcessesList.contains(p)) {
                runtimeProcessesList.add(p);
                processAdded = true;
            }
        }
        return processAdded;
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
            System.out.println("Tempo de espera: " + p.getWaitingTime());
            System.out.println("Tempo de resposta: " + p.getResponseTime());
        }
    }
    
    private double calculateAverageWaitingTime(List<Process> processesList) { 
        double total = 0;
        
        for(Process p : processesList) { 
            total = total + p.getWaitingTime();
        }
        
        // Retorna a média
        return total / processesList.size();
    }
    
    private double calculateAverageResponseTime(List<Process> processesList) {
        double total = 0;
        
        for(Process p : processesList) {
            total = total + p.getResponseTime();
        }
        
        // Retorna a média
        return total / processesList.size();
    }
}