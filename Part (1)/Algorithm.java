/* Name: Max Thet Tin
 */
//This is the Algorithm abstract class which acts as a super class for all the different algorithms to be implemented


import java.util.*;

public abstract class Algorithm
{
    int disp;
    LinkedList<Process> processes = new LinkedList<>();

//Default constructor
public Algorithm() {
        processes = null;
		int disp = 0;
    }

//Constructor
    public Algorithm(ArrayList<Process> processes, int disp) {
        this.disp = disp;
        this.processes.addAll(processes);

        for (Process p : processes)
        {
            p.reset();
        }
    }

//This is the print method to output the required processes and the times
    public void print() {
		
		//Sort the processes based on process IDs using Comparator class
        processes.sort(Comparator.comparing(Process::getId));
        System.out.println("");
        System.out.printf("%-12s %-18s %-8s%n", "Process", "Turnaround Time", "Waiting Time");
        
		//Loops for each process in the processes linked list
		for (Process p : processes) {
            System.out.printf("%-12s %-18d %-7d%n", p.getId(), p.getTurnArrTime(), p.getWaitTime());
        }
    }
	
//This is the method to get average times of both turnaround time and waiting time for each of the algorithms
    public double[] getAverageTimes() {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int numberOfProcesses = processes.size();
		
		//Gets turnaround time and waiting time for each process and adds up to get total
        for (Process p : processes) {
            totalTurnaroundTime += p.getTurnArrTime();
            totalWaitingTime += p.getWaitTime();
        }
		//Calculation of the average times
        double avgTurnaroundTime = (double) totalTurnaroundTime / numberOfProcesses;
        double avgWaitingTime = (double) totalWaitingTime / numberOfProcesses;
		
		//Returns the values as the first two element of a double array
        return new double[]{avgTurnaroundTime, avgWaitingTime};
    }

//This is a abstract method for each algorithm implementation
	public abstract void run();
}