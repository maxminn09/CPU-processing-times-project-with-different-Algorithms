/* Name: Max Thet Tin
 */
//Abstract Algorithm class that provides implementation of the RR scheduler and
//acts as a parent class for local and global set classes

import java.util.*;

public abstract class Algorithm {

    public int totalTime = 0;
    public int clockPointer = 0;
    public int globalQuantum;
    public int frameCount;
    public IOHandler ioController;
    public ArrayList<Frame> mainMemory;
    public Process runningProcess;
    public LinkedList<Process> blockQueue = new LinkedList<>();
    public LinkedList<Process> readyQueue = new LinkedList<>();
    public LinkedList<Process> finishQueue = new LinkedList<>();
    public LinkedList<Process> processes = new LinkedList<>();

    //default constructor
    public Algorithm(LinkedList<Process> inputQueue, IOHandler ioController, int frameCount, int quantum){
        this.ioController = ioController;
        this.mainMemory = new ArrayList<>(frameCount);
        this.globalQuantum = quantum;
        this.frameCount = frameCount;
        makeFrames();
        createReadyList(inputQueue);
    }

    //method to run the scheduler
    public void run(){
        //While unfinished processes exists
        while(!blockQueue.isEmpty() || !readyQueue.isEmpty() || runningProcess != null) {
            checkBlockQueue();
            //if ready process available, run it
            if(!readyQueue.isEmpty()){
                runningProcess = readyQueue.poll();
                executeProcess();
            }
            else {
                totalTime++;
            }
        }
    }

    //method to execute a process
    public void executeProcess(){
        int quantum = globalQuantum;
        //while quantum time is not completed or there is only one running ready process
        while((quantum != 0 && !runningProcess.getPageQueue().isEmpty()) ||
                (readyQueue.isEmpty() && !runningProcess.getPageQueue().isEmpty())) {

            //if required page is in main memory
            if(checkPageFrame()){
                runningProcess.getPageQueue().poll();
                totalTime++;
                checkBlockQueue();
                quantum--;
            }
            //else send the process to block queue
            else {
                blockQueue.add(runningProcess);
                runningProcess.addFault(totalTime);
                runningProcess.setIOReturnTime(totalTime);
                runningProcess = null;
                return;
            }
        }
        //after the process is interrupted, add process to finished list if finished
        if(runningProcess.getPageQueue().isEmpty()){
            runningProcess.setFinishTime(totalTime);
            finishQueue.add(runningProcess);
            runningProcess = null;
        }
		//else if return process to ready queue
		else if (quantum == 0) {
            readyQueue.add(runningProcess);
            runningProcess = null;
        }
    }

    //method to create list with empty frames
    private void makeFrames() {
        for (int i = 0; i < frameCount; i++){
            mainMemory.add(new Frame());
        }
    }

    //method to create a ready queue of processes
    private void createReadyList(LinkedList<Process> inputQueue) {
        for(Process inputProcess : inputQueue){
            Process newProcess = new Process();
            newProcess.load(inputProcess);
            this.readyQueue.add(newProcess);
            this.processes.add(newProcess);
        }
    }

    //method to check if a requried page exists in the main memory
    public boolean checkPageFrame(){
        for(Frame frame : mainMemory){
            if(frame.isFull()){
                if(Objects.equals(runningProcess.getPageQueue().peek(), frame.getPage().getId())){
                    frame.increaseRefCount();
                    return true;
                }
            }
        }
        return false;
    }

    //method to check the block queue to add ready processes
    public void checkBlockQueue(){
        Iterator<Process> iterator = blockQueue.iterator();
        while(iterator.hasNext()){
            Process process = iterator.next();
            if (process.getIOReturnTime() == totalTime){
                Page pickedPage = ioController.getPage(process.getPageQueue().peek());
                replacePage(pickedPage, process);
                readyQueue.add(process);
                iterator.remove();
            }
        }
    };

    //method to print results
    public String print(){
        String output = "PID\tProcess-Name\t\tTurnaround\t\t\t# Faults\t\tFault-Times\n";
        for(int i = 0; i < processes.size(); i++){
			//to remove file extension
			String processName = processes.get(i).getId().replace(".txt", "");
            output += (i + 1) +"\t" +
                    processName + "\t\t" +
                    processes.get(i).getFinishTime() + "\t\t\t\t" +
                    processes.get(i).getFaultTimes().size() + "\t\t\t" +
                    processes.get(i).getFaultTimes() + "\n";
        }
        return output;
    }
	
    //abstract method for the replacing page with different policies in local and global set classes
    public abstract void replacePage(Page newPage, Process process);
}
