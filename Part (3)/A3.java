/* Name: Max Thet Tin
 */
//Main class of the program used to read from file and executes the program

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A3 {
    public static void main(String[] args) {
        int framesCount;
        int quantum;
        LinkedList<Process> processList = new LinkedList<>();
        LinkedList<Page> tempMemory = new LinkedList<>();

        try {
            framesCount = Integer.parseInt(args[0]);
            quantum = Integer.parseInt(args[1]);

            for (int i = 2; i < args.length; i++) {
                Process newProcess = new Process(args[i]);

                File file = new File(args[i]);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.trim().isEmpty() || line.startsWith("//")) {
                        continue; 
                    }

                    String[] parts = line.split(";");
                    for (String part : parts) {
                        if (part.contains("page:")) {
                            String pgNumber = part.trim().split(":")[1].trim();
                            Page newPage = new Page(newProcess.getId() + "(" + pgNumber + ")");
                            newProcess.addPage(newPage.getId());
                            tempMemory.add(newPage);
                        }
                    }
                }

                processList.add(newProcess);
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
            return;
        }

        IOHandler ioController = new IOHandler(tempMemory);
        FixedLocal local = new FixedLocal(processList, ioController, framesCount, quantum);
        GlobalVariable global = new GlobalVariable(processList, ioController, framesCount, quantum);
        local.run();
        global.run();
        System.out.println("CLOCK - Fixed-Local Replacement:\n" + local.print());
        System.out.println("------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("CLOCK - Variable-Global Replacement:\n" + global.print());
    }
}
