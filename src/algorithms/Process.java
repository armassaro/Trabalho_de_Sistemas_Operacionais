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
    private String flag = "";

    
    public Process(String id, String processTime, String arrivalTime) {
        this.processTime = Integer.valueOf(processTime);
        this.arrivalTime = Integer.valueOf(arrivalTime);
        this.id = id;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Process{" + "id=" + id + ", processTime=" + processTime + ", waitingTime=" + waitingTime + ", arrivalTime=" + arrivalTime + '}';
    }
}
