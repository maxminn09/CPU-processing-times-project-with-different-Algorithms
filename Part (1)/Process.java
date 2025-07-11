/* Name: Max Thet Tin
 */
//This is the Process class which acts as a single process for each of the algorithms

import java.io.*;
import java.util.*;

public class Process {

    public String id;
    public int arrTime;
    public int srvTime;
    public int priority;
    public int time;
    public int turnArrTime;
	public int waitTime;
    public int initialSrvTime;
	public int currentSrvTime;
    public int executionTime;
    public int interruptTime = 0;

//Default constructor
    public Process() {
        id = "";
        arrTime = 0;
        srvTime = 0;
        priority = 0;
    }

//constructor
    public Process(String id, int arrTime, int srvTime, int priority) {
        this.id = id;
        this.arrTime = arrTime;
        this.srvTime = srvTime;
        this.priority = priority;
    }

//This is a method which resets the values of the process back to 0
	public void reset() {
        time = 0;
		turnArrTime = 0;
        waitTime = 0;
        currentSrvTime = 0;
        executionTime = 0;
        interruptTime = 0;
    }

//Gets the process ID
    public String getId() {
        return id;
    }

//Gets the process arrival time
    public int getArrTime() {
        return arrTime;
    }

//Gets the process service time
    public int getSrvTime() {
        return srvTime;
    }

//Gets the process priority
    public int getPriority() {
        return priority;
    }

//Gets the process time on the dispatcher    
    public int getTime() {
        return time;
    }

//Gets the process turnaround time	
	public int getTurnArrTime()
    {
        return turnArrTime;
    }

//Gets the process waiting time	
	public int getWaitTime()
    {
        return waitTime;
    } 

//Gets the process starting service time	
	public int getInitialSrvTime() {
        return initialSrvTime;
    }

//Gets the current process service time
	public int getCurrentSrvTime()
    {
        return currentSrvTime;
    }

//Gets the process execution time	
	public int getExecutionTime() {
        return executionTime;
    }

//Gets the process interrupt time	
    public int getInterruptTime() {
        return interruptTime;
    }

//Adds up time for each process
	public void addTime(int time) {
        this.time += time;
    }

//Sets the process ID
	public void setId(String id) {
        this.id = id;
    }

//Sets the process arrival time
    public void setArrTime(int arrTime) {
        this.arrTime = arrTime;
    }

//Sets the process service time
    public void setSrvTime(int srvTime) {
        this.srvTime = srvTime;
    }

//Sets the process priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

//Sets the process time in the dispatcher
    public void setTime(int time) {
        this.time = time;
    }
	
//Sets the process turnaround time	
	 public void setTurnArrTime(int turnArrTime)
    {
        this.turnArrTime = turnArrTime;
    }

//Sets the process waiting time
    public void setWaitTime(int waitTime)
    {
        this.waitTime = waitTime;
    }

//Sets the process starting service time	
	public void setInitialSrvTime(int initialSrvTime) {
        this.initialSrvTime = initialSrvTime;
    }

//Sets the process current service time
    public void setCurrentSrvTime(int currentSrvTime)
    {
        this.currentSrvTime = currentSrvTime;
    }

//Sets the process execution time
	public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

//Sets the process interrupt time
    public void setInterruptTime(int interruptTime) {
        this.interruptTime = interruptTime;
    }
}