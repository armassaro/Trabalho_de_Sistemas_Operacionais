package algorithms;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author armassaro
 */
public class RoundRobin {
    // Instancia fila circular simples duplamente encadeada
    // runtimeProcessesQueue: fila para os processos sendo executados
    private Queue<Process> runtimeProcessesQueue = new LinkedList<>();
    // allProcessesList: fila com todos os processos a serem alocados
    private List<Process> allProcessesList = new ArrayList<>();
    // finishedProcessesList: lista com todos os processos terminados
    private List<Process> finishedProcessesList = new ArrayList<>();
    private Process executingProcess = null;
    private int totalTime = 0;
    
    // Pega o primeiro elemento da fila e coloca no último lugar da fila
    private void bringFirstElementToLastIndex() { 
        try { 
            runtimeProcessesQueue.add(runtimeProcessesQueue.remove());
        }
        catch(NoSuchElementException e) { 
            System.out.println("O arquivo não possui processos válidos!");
        }
    }
    
    // showDialogues: habilita ou desabilita prints de informações da execução dos processos
    // fastExecutionMode: habilita ou desabilita a execução rápida da simulação do Round Robin
    public String run(boolean showDialogues, boolean fastExecutionMode) throws InterruptedException { 
        String timeline = "0";
        System.out.printf("\nDigite o tempo de quantum: ");
        Integer quantum = Integer.parseInt(ScannerSingleton.getInstance().next());
        int allProcessesListInitialSize = allProcessesList.size();
        
        // Espera o primeiro processo a ser executado
        waitFirstExecutingProcessLoop();
        
        // Continua o Round Robin enquanto a fila de todos os processos continua com elementos
        while(finishedProcessesList.size() < allProcessesListInitialSize) { 
            for(int a = 0; a < quantum; a++) { 
                // Mostra informações do processo sendo executado, caso showDialogues esteja como true
                if(showDialogues) { 
                    System.out.println("\n====================\n");
                    System.out.println("Processo em execução: " + executingProcess.getId());
                    System.out.println("Tempo de execução do processo: " + executingProcess.getProcessTime());
                    System.out.println("Tempo de espera do processo: " + executingProcess.getWaitingTime());
                    System.out.println("Tempo total: " + totalTime);
                }
                
                // Simula passagem de tempo
                Thread.sleep(1000);
                
                // Aumenta o tempo de espera para todos os processos que estão esperando na allProcessesList, menos o que for inserido no argumento
                incrementWaitingTimeForAllProcesses();

                // Se o tempo de processo em execução for igual a zero, quebra o laço for e passa para o próximo processo
                try { 
                    if(executingProcess.decrementProcessTime() == 0) { 
                        // Retira o processo terminado da fila
                        finishedProcessesList.add(runtimeProcessesQueue.remove());
                        break;
                    }
                }
                catch(NullPointerException e) {}
                
                checkNextIncomingProcess();
                
                totalTime++;
            }
                
            // Decrementa o tempo de processamento do processo selecionado
            timeline =  timeline + "----" + executingProcess.getId() + "----" + totalTime;

            // Coloca o primeiro elemento na runtimeProcessesQueue no último lugar
            bringFirstElementToLastIndex();
        }
        
        return timeline; 
   }

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
    
    public void removeFirstProcessFromRuntimeQueue() { 
        this.runtimeProcessesQueue.remove();
    }
    
    public void incrementWaitingTimeForAllProcesses() { 
        for(Process p : runtimeProcessesQueue) { 
            if(!p.equals(executingProcess)) { 
                p.incrementProcessTime();
            }
        }
    }
    
    public static void showListContent(List<Process> processesList) { 
        for(Process p : processesList) { 
            System.out.println("\n====================\n");
            System.out.println("ID do processo: " + p.getId());
            System.out.println("Tempo de processamento: " + p.getProcessTime());
            System.out.println("Tempo de chegada do processo: " + p.getArrivalTime());   
        }
    }
    
    public static void showArrayContent(String[] str) { 
        for(String s : str) { 
            System.out.println(s);
        }
    }
    
    public void addProcessToAllProcessesList(Process process) { 
        this.allProcessesList.add(process);
    }
    
    public void removeProcessFromAllProcessesList(Process process) {
        this.allProcessesList.remove(process);
    }
    
    // Checa qual o próximo processo a ser incluído em runtimeProcessesQueue, caso não houver retorna null
    private Process checkNextIncomingProcess() { 
        for(int a = 0; a < allProcessesList.size(); a++) { 
            if(allProcessesList.get(a).getArrivalTime() == totalTime) { 
                return allProcessesList.remove(a);
            }
        }
        
        return null;
    }
    
    private void waitFirstExecutingProcessLoop() { 
        while(executingProcess == null) { 
            try {
                // Simula passagem de tempo
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(RoundRobin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            // Verifica qual o próximo processo a ser incluído no runtimeProcessesQueue
            executingProcess = checkNextIncomingProcess();
            if(executingProcess != null) { 
                runtimeProcessesQueue.add(executingProcess);
            }
            else {
                System.out.println("Nenhum processo em execução...");
            }
        }
    }
}
