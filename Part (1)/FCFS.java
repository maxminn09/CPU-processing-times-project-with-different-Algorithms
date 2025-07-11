/* Name: Max Thet Tin
 */
//This is the FCFS class that extends Algorithm class and implements the required FCFS algorithm

import java.util.*;

public class FCFS extends Algorithm {

	//Constructor
    public FCFS(ArrayList<Process> processArray, int disp) {
		//Uses the super class's method for implementation
        super(processArray, disp);
		
		//Sorts the processes based on the arrival time for each process
        processes.sort(Comparator.comparingInt(Process::getArrTime));
    }

//This is the superclass's abstract method run being implemented to fulfill FCFS operation
    public void run() {
        System.out.println("FCFS:");
        int time = 0;
		//For each process in the processes linked list, calculates time, waiting time, arrival time, service time and proceeds to add up the time after each process is done
        for (Process p: processes)
        {	
			//Calculates starting time by adding dispatcher time and current time for each process
            p.setTime(disp + time);
			//Calculates waiting time by calculating the difference between the time and current process arrival time
            p.setWaitTime(p.getTime() - p.getArrTime());
			//Calculates turnaround time by adding the waiting time and the service time of each process
            p.setTurnArrTime(p.getWaitTime() + p.getSrvTime());
			//Updates the time by adding the finished process service time and the dispatcher time
            time += p.getSrvTime() + disp;
        }
		
		//This is to print out each process and outputs the time of each process and priorities
        for (Process p : processes)
        {
            System.out.println("T" + p.getTime() + ": " + p.getId() + "(" + p.getPriority() + ")");
        }
    }
}