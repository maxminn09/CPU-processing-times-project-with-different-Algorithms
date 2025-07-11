/* Name: Max Thet Tin
 */
//Process class that provides implementation of the processes 
//to be ran through in algorithms

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Process {
    private ArrayList<Integer> frameRange = new ArrayList<>();
    private ArrayList<Integer> faultTimes = new ArrayList<>();
    private LinkedList<String> pageInstruction = new LinkedList<>();
    private String id;
    private int ioReturnTime;
    private int finishTime;
    private int clockPointer = 0;

    //default constructor
    public Process(){
        this.id = "";
    }

    //custom constructor
    public Process(String id){
        this.id = id;
    }

	//method to get process ID
    public String getId() {
        return id;
    }
	
	//method to get finish time
    public int getFinishTime() {
        return finishTime;
    }
	
	//method to set finish time
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
	
	//method to get return time
    public int getIOReturnTime() {
        return ioReturnTime;
    }
	
	//method to set return time
    public void setIOReturnTime(int totalTime){
        this.ioReturnTime = totalTime + 6;
    }
	
	//method to add fault times
    public void addFault(int faultTime){
        this.faultTimes.add(faultTime);
    }
	
	//method to get fault times
    public ArrayList<Integer> getFaultTimes() {
        return faultTimes;
    }
	
	//method to get clock pointer
    public int getClockPointer() {
        return clockPointer;
    }
	
	//method to set clock pointer
    public void setClockPointer(int clockPointer) {
        this.clockPointer = clockPointer;
    }
	
	//method to increase the clock pointer
    public void increaseClockPointer(){
        clockPointer++;
    }
	
	//method to add page
    public void addPage(String page){
        pageInstruction.add(page);
    }
	
	//method to get the frame range
    public ArrayList<Integer> getFrameRange() {
        return frameRange;
    }
	
	//method to get page queue
    public Queue<String> getPageQueue(){
        return pageInstruction;
    }
	
	//method to assist in loading process
    public void load(Process inputProcess) {
        this.id = inputProcess.getId();
        for(String pageId : inputProcess.getPageQueue()){
            addPage(pageId);
        }
    }
}
