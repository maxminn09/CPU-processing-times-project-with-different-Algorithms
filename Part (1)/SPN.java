/* Name: Max Thet Tin
 */
//This is the SPN class that extends Algorithm class and implements the required SPN algorithm

import java.util.*;

public class SPN extends Algorithm  {

	//Constructor
    public SPN(ArrayList<Process> processArray, int disp) {
        super(processArray, disp);
    }
	
	//This is the superclass's abstract method run being implemented to fulfill SPN operation
    public void run() {
        System.out.println("\nSPN:");
        int time = 0;
		//Creates two linkedlist to resort the given processes into the required shortest process next algorithm and to update the finished processes back into the original processes list
        LinkedList<Process> startProcesses = new LinkedList<>();
        LinkedList<Process> finalProcesses = new LinkedList<>();
		
		//This is the loop to sort each process with the SPN algorithm
        while (!processes.isEmpty() || !startProcesses.isEmpty()) {
			//Creates an arraylist which acts as a temporary array list to delete the original processes
            ArrayList<Process> removeProcesses = new ArrayList<>();
			//Sorts each process based on their arrival time
            for (Process p : processes) {
                if (p.getArrTime() <= time) {
					//Adds the processes into the start processes list to start working on it using SPN algorithm
                    startProcesses.add(p);
                    removeProcesses.add(p);
                }
            }
			//Deletes the temp list
            processes.removeAll(removeProcesses);
			
            //Sorts the starting processes list based on each process's service time
            startProcesses.sort(Comparator.comparingInt(Process::getSrvTime));
			
			//Calculates the times of each process and updates the final process list with each finished process
            if (!startProcesses.isEmpty()) {
                Process p = startProcesses.poll();
                p.setTime(disp + time);
                p.setWaitTime(p.getTime() - p.getArrTime());
                p.setTurnArrTime(p.getWaitTime() + p.getSrvTime());
                time += p.getSrvTime() + disp;
                finalProcesses.add(p);
            } 
        }
		//Loops to print out each process
        for (Process p : finalProcesses) {
            System.out.println("T" + p.getTime() + ": " + p.getId() + "(" + p.getPriority() + ")");
        }
		//Adds the processes from the final processes list to the original processes list
        processes.addAll(finalProcesses);
    }
}