/* Name: Max Thet Tin
 */
//This is the PRR class that extends Algorithm class and implements the required PRR algorithm

import java.util.*;

public class PRR extends Algorithm {
	//Creates two variables based on the PRR algorithm based on their high or low priority processes
    public int highPrioQuantum = 4;
    public int lowPrioQuantum = 2;

    public int disp;

//Constructor
    public PRR(ArrayList<Process> processArray, int disp) {
        super(processArray, disp);
        this.disp = disp;
    }

//This is a method to get the quantum value
    public int getQuantum(Process p) {
        if (p.getPriority() <= 2){
			return highPrioQuantum;
		}
		else{
			return lowPrioQuantum;
		}
    }

//This is the superclass's abstract method run being implemented to fulfill PRR operation
    public void run() {
        System.out.println("\nPRR:");
		
		//Sorts the processes based on each arrival time
        processes.sort(Comparator.comparingInt(Process::getArrTime));
		
		//Creates two linkedlist to resort the given processes into the required PRR algorithm and to update the finished processes back into the original processes list
        LinkedList<Process> startProcesses = new LinkedList<>();
        LinkedList<Process> finalProcesses = new LinkedList<>();
        Process currentProcess = null;

        int time = 0;
        int currentQuantum = 0;
		
		//Adds process from the original list into the start process list based on the arrival time and implements them  to be worked on with PRR algorithm
        while (!processes.isEmpty() || currentProcess != null || !startProcesses.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getArrTime() <= time) {
                startProcesses.add(processes.remove(0));
            }
			
			//Checks to see if each process is required to be handled different based on the quantum values
            if (currentProcess == null || currentQuantum == 0) {
				//If it is the first process, adds the process into the start processes list to start working on it
                if (currentProcess != null && currentQuantum == 0) {
                    startProcesses.add(currentProcess);
                }

                time += disp;
				//Extracts the first process from the start process list into the current process and deletes it
                currentProcess = startProcesses.poll();
				
				//Outputs the values of the current process
                if (currentProcess != null) {
                    currentQuantum = getQuantum(currentProcess);
                    currentProcess.setTime(time);
                    System.out.println("T" + time + ": " + currentProcess.getId() + "(" + currentProcess.getPriority() + ")");
                }
            }
            time++;
            currentQuantum--;
			
            if (currentProcess != null) {
                currentProcess.setCurrentSrvTime(currentProcess.getCurrentSrvTime() + 1);
				
				//Calculates the times of each process and updates the final process list with each finished process if a process is done
                if (currentProcess.getCurrentSrvTime() == currentProcess.getSrvTime()) {
                    currentProcess.setTurnArrTime(time - currentProcess.getArrTime());
                    currentProcess.setWaitTime(currentProcess.getTurnArrTime() - currentProcess.getSrvTime());
                    finalProcesses.add(currentProcess);
                    currentProcess = null;
                    currentQuantum = 0;
                }
            }
        }
		//Adds the processes from the final processes list to the original processes list
        processes.addAll(finalProcesses);
    }
}