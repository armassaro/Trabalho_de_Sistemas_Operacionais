package algorithms;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * @author armassaro
 */
public class RoundRobin {
    // Instancia fila circular simples duplamente encadeada
    // runtimeQueue: fila para os processos sendo executados
    private Queue<Process> runtimeQueue = new LinkedList<>();
    // allProcessesQueue: fila com todos os processos a serem alocados
    private Queue<Process> allProcessesQueue = new LinkedList<>();
    // finishedProcessesList: lista com todos os processos terminados
    private List<Process> finishedProcesses = new ArrayList<>();
    
    // Pega o primeiro elemento da fila e coloca no último lugar da fila
    private void bringFirstElementToLastIndex() { 
        try { 
            runtimeQueue.add(runtimeQueue.remove());
        }
        catch(NoSuchElementException e) { 
            System.out.println("O arquivo não possui processos válidos!");
        }
    }
    
    // showDialogues: habilita ou desabilita prints de informações da execução dos processos
    // fastExecutionMode: habilita ou desabilita a execução rápida da simulação do Round Robin
    public String run(boolean showDialogues, boolean fastExecutionMode) { 
        String timeline = "0";
        System.out.printf("\nDigite o tempo de quantum: ");
        Integer quantum = Integer.parseInt(ScannerSingleton.getInstance().next());
        int totalTime = 0;
        
        // Continua o Round Robin enquanto a fila de todos os processos continua com elementos
        while(!allProcessesQueue.isEmpty()) { 
            // Simula a passagem de tempo, caso fastExecutionMode esteja como false
            if(!fastExecutionMode) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.getLogger(RoundRobin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
            
            // Itera por todos os processos e implementa aquele que está com o tempo de chegada igual ao tempo total
            for(int a = 0; a < allProcessesQueue.size(); a++) {     
                if(allProcessesQueue.getArrivalTime() == totalTime) { 
                    runtimeQueue.add(p);
                    break;
                }
            }
            
            Iterator<Process> runtimeQueueIterator = runtimeQueue.iterator();
            while(runtimeQueueIterator.hasNext()) { 
                Process p = runtimeQueueIterator.next();
                
                // Decrementa o tempo de processamento do processo selecionado
                // Mostra informações do processo sendo executado, caso showDialogues esteja como true
                if(showDialogues) { 
                    System.out.println("\n====================\n");
                    System.out.println("Processo em execução: " + p.getId());
                    System.out.println("Tempo de execução do processo: " + p.getProcessTime());
                    System.out.println("Tempo de espera do processo: " + p.getWaitingTime());
                    System.out.println("Tempo total: " + totalTime);
                }

                // Aumenta o tempo de espera para todos os processos que estão esperando na allProcessesQueue, menos o que for inserido no argumento
                incrementWaitingTimeForAllProcesses(p);

                // Se o tempo de processo em execução for igual a zero, quebra o laço for e passa para o próximo processo
                if(p.decrementProcessTime() == 0) { 
                    // Retira o processo terminado da fila
                    runtimeQueue.remove();
                    break;
                }
                timeline =  timeline + "----" + p.getId() + "----" + ++totalTime;

                // Coloca o primeiro elemento na runtimeQueue no último lugar
                bringFirstElementToLastIndex();
            }
        }
        
        return timeline;
    }

    public Queue<Process> getRuntimeQueue() {
        return runtimeQueue;
    }

    public void setRuntimeQueue(Queue<Process> runtimeQueue) {
        this.runtimeQueue = runtimeQueue;
    }

    public Queue<Process> getAllProcessesQueue() {
        return allProcessesQueue;
    }

    public void setAllProcessesQueue(Queue<Process> allProcessesQueue) {
        this.allProcessesQueue = allProcessesQueue;
    }
    
    public void addProcessToRuntimeQueue(Process process) { 
        this.runtimeQueue.add(process);
    }
    
    public void removeFirstProcessFromRuntimeQueue() { 
        this.runtimeQueue.remove();
    }
    
    public void incrementWaitingTimeForAllProcesses(Process process) { 
        for(Process p : allProcessesQueue) { 
            if(!p.equals(process)) { 
                p.incrementProcessTime();
            }
        }
    }
    
    public static void showQueueContent(Queue<Process> queue) { 
        for(Process p : queue) { 
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
    
    public void addProcessToAllProcessesQueue(Process process) { 
        this.allProcessesQueue.add(process);
    }
    
    public void removeProcessFromAllProcessesQueue(Process process) {
        this.allProcessesQueue.remove(process);
    }
    
    private void transferElement() { 
        
    }
}
