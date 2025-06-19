package algorithms;

/**
 *
 * @author armassaro
 */
public class Process {
    private String id = "";
    private Integer processTime = 0;
    private Integer waitingTime = 0;
    private Integer arrivalTime = 0;
    // Define o tempo de resposta como -1 para que o código seja capaz de identificar se o tempo de resposta de processo já foi calculado
    private Integer responseTime = -1;

    public Process(String id, String processTime, String arrivalTime) {
        this.processTime = Integer.valueOf(processTime);
        this.arrivalTime = Integer.valueOf(arrivalTime);
        this.id = id;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }
    
    public Integer getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Integer processTime) {
        this.processTime = processTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Integer arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    
    
    public int decrementProcessTime() { 
        return --processTime;
    }
    
    public int decrementWaitingTime() { 
        return --waitingTime;
    }
    
    public int incrementProcessTime() { 
        return ++processTime;
    }
    
    public int incrementWaitingTime() { 
        return ++waitingTime;
    }

    @Override
    public String toString() {
        return "Process{" + "id=" + id + ", processTime=" + processTime + ", waitingTime=" + waitingTime + ", arrivalTime=" + arrivalTime + '}';
    }
}
