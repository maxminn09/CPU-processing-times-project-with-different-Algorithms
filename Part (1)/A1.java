/* Name: Max Thet Tin
 */
//This is the A1 class which works as a main class to read input file from the data text and run each algorithm and output them

import java.io.*;
import java.util.*;

public class A1 {

    public static void main(String[] args) throws Exception {
        String filename = args[0];
        int disp = 0;
        ArrayList<Process> processes = new ArrayList<>();
		File file = new File(filename);
		
		//Reads the file from the input text
        Scanner scanner = new Scanner(file);

            Process p = new Process();

            while (scanner.hasNextLine())
            {
                String line = scanner.next();

                if (line.contains("DISP"))
                {
                    disp = scanner.nextInt();
                } else if (line.contains("PID"))
                {
                    p.setId(scanner.next());
                } else if (line.contains("ArrTime"))
                {
                    p.setArrTime(scanner.nextInt());
                } else if (line.contains("SrvTime"))
                {
                    p.setSrvTime(scanner.nextInt());
                } else if (line.contains("Priority"))
                {
                    p.setPriority(scanner.nextInt());
                } else if (line.contains("END"))
                {
                    if (p.getId() != null && !p.getId().isEmpty())
                    {
                        processes.add(p);
                        p = new Process();
                    }
                } else if (line.contains("EOF"))
                {
                    break;
                }
            }
			
			//Creates a new FCFS object, runs the data input through the algorithm and prints the result and the summary
            FCFS fcfs = new FCFS(processes, disp);
            fcfs.run();
            fcfs.print();
			double[] fcfsAvgTime = fcfs.getAverageTimes();

			//Creates a new SPN object, runs the data input through the algorithm and prints the result and the summary
            SPN spn = new SPN(processes, disp);
            spn.run();
            spn.print();
			double[] spnAvgTime = spn.getAverageTimes();
			
			//Creates a new PP object, runs the data input through the algorithm and prints the result and the summary
            PP pp = new PP(processes, disp);
            pp.run();
            pp.print();
			double[] ppAvgTime = pp.getAverageTimes();

            //Creates a new PRR object, runs the data input through the algorithm and prints the result and the summary
			PRR prr = new PRR(processes, disp);
            prr.run();
            prr.print();
			double[] prrAvgTime = prr.getAverageTimes();

			//Prints output for summary
            System.out.printf("\nSummary:");
            System.out.printf("\nAlgorithm   Average Turnaround Time   Average Waiting Time\n");
            System.out.printf("FCFS        %.2f                     %.2f\n", fcfsAvgTime[0], fcfsAvgTime[1]);
            System.out.printf("SPN         %.2f                     %.2f\n", spnAvgTime[0], spnAvgTime[1]);
            System.out.printf("PP          %.2f                     %.2f\n", ppAvgTime[0], ppAvgTime[1]);
            System.out.printf("PRR         %.2f                     %.2f\n", prrAvgTime[0], prrAvgTime[1]);
        } 
}
