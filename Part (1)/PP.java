/* Name: Max Thet Tin
*/
//This is the PP class that extends Algorithm class and implements the required PP algorithm

import java.util.*;

public class PP extends Algorithm {

    public int disp;
	
	//Constructor
    public PP(ArrayList<Process> processArray, int disp) {
        super(processArray, disp);
        this.disp = disp;
    }
	
	//This is the superclass's abstract method run being implemented to fulfill PP operation
    public void run() {

        System.out.println("\nPP:");
		//Sorts the processes based on each arrival time
        processes.sort(Comparator.comparingInt(Process::getArrTime));
		//Creates two linkedlist to resort the given processes into the required PP algorithm and to update the finished processes back into the original processes list
        LinkedList<Process> startProcesses = new LinkedList<>();
        LinkedList<Process> finalProcesses = new LinkedList<>();
		
        Process currentProcess = null;
        int time = 0;
		
		//This is the loop to sort each process with the PP algorithm
        while (!processes.isEmpty() || currentProcess != null || !startProcesses.isEmpty()) {
			
            //Adds process from the original list into the start process list based on the arrival time and implements them  to be worked on with PP algorithm
            while (!processes.isEmpty() && processes.get(0).getArrTime() <= time) {
                startProcesses.add(processes.remove(0));
            }
            //Sorts each process based on their priorities
            startProcesses.sort(Comparator.comparingInt(Process::getPriority));
			
            if (currentProcess == null || (!startProcesses.isEmpty() && startProcesses.getFirst().getPriority() < currentProcess.getPriority())) {
                time += disp; 
				//Adds the process into the start process list to be worked on if the currentProcess is null
                if (currentProcess != null) {
                    startProcesses.add(currentProcess);
                }
				//Extracts the process from the head of the list into the currentProcess and deletes it from the list
                currentProcess = startProcesses.poll();
				
				//Loops to print out each process
                if (currentProcess != null) {
                    currentProcess.setTime(time);
                    System.out.println("T" + time + ": " + currentProcess.getId() + "(" + currentProcess.getPriority() + ")");
                }
            }
			
            time++;
            if (currentProcess != null) {
                currentProcess.setCurrentSrvTime(currentProcess.getCurrentSrvTime() + 1);
				
                //Calculates the times of each process and updates the final process list with each finished process if a process is done
                if (currentProcess.getCurrentSrvTime() == currentProcess.getSrvTime()) {
                    currentProcess.setTurnArrTime(time - currentProcess.getArrTime());
                    currentProcess.setWaitTime(currentProcess.getTurnArrTime() - currentProcess.getSrvTime());
                    finalProcesses.add(currentProcess);
                    currentProcess = null; 
                }
            }
        }
        //Adds the processes from the final processes list to the original processes list
        processes.addAll(finalProcesses);
    }
}