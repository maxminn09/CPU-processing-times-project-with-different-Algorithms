/* Name: Max Thet Tin
 */
// This is the P3 class which manages the scheduling of tasks and threads using a monitor

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class P3 {
    // Monitor for logging access
    private static Object logMonitor = new Object();
    // Counter for total threads
    public static AtomicInteger totalThreads = new AtomicInteger(0);
    // Counter for finished threads
    public static AtomicInteger finishedThreads = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("Task Scheduler initialized....");

        // Check if the command line argument is provided
        if (args.length < 1) {
            System.out.println("Please provide the task list filename as a command line argument.");
            return;
        }
        // Get the task list filename from the command line arguments
        String taskListFile = args[0];

        try (Scanner scanner = new Scanner(new File(taskListFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Split the line into parts
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    String taskFileName = parts[0];
                    int numProcessors = Integer.parseInt(parts[1]);

                    // Create a new task thread with extracted values and start it
                    TaskThread taskThread = new TaskThread(taskFileName, numProcessors);
                    new Thread(taskThread).start();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Task list file not found.");
            e.printStackTrace();
        }
    }
	
// Static class for managing individual task threads
    static class TaskThread implements Runnable {
        String taskFileName;
        int numProcessors;
        Object processorMonitor;
        int availableProcessors;

        public TaskThread(String taskFileName, int numProcessors) {
            this.taskFileName = taskFileName;
            this.numProcessors = numProcessors;
            this.processorMonitor = new Object();
            this.availableProcessors = numProcessors;
        }

        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(taskFileName))) {
                String line;
                int threadNo = 0;
                ArrayList<Thread> threads = new ArrayList<>();
                // Read lines from the file
                while ((line = br.readLine()) != null) {
                    int input = Integer.parseInt(line);
                    // Increment the total number of threads
                    totalThreads.incrementAndGet();
                    // Create a new processing thread with relevant information
                    ProcessingThread pThread = new ProcessingThread(threadNo, taskFileName, input, processorMonitor, this);
                    Thread t = new Thread(pThread);
                    threads.add(t);
                    t.start();
                    threadNo++;
                }
                // Wait for all processing threads to complete
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

// Static class for managing individual processing threads
    static class ProcessingThread implements Runnable {
        int threadNo;
        String taskFileName;
        int input;
        Object processorMonitor;
        TaskThread task;

        public ProcessingThread(int threadNo, String taskFileName, int input, Object processorMonitor, TaskThread task) {
            this.threadNo = threadNo;
            this.taskFileName = taskFileName;
            this.input = input;
            this.processorMonitor = processorMonitor;
            this.task = task;
        }

        public void run() {
            synchronized (processorMonitor) {
                // Wait until there are available processors
                while (task.availableProcessors <= 0) {
                    try {
                        processorMonitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Acquire a processor
                task.availableProcessors--;

                try {
                    // Simulate the processing time
                    Thread.sleep(input * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Release the processor
                task.availableProcessors++;
                processorMonitor.notifyAll();
            }

            synchronized (logMonitor) {
                // Format the current date and time
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                String formattedDate = sdf.format(new Date());

                // Log task details and results
                System.out.println("Time: " + formattedDate + ", Task: " + taskFileName + ", Thread No: " + threadNo + ", Input: " + input + ", Result: " + (input * input));

                // Check if all threads have finished
                if (finishedThreads.incrementAndGet() == totalThreads.get()) {
                    System.out.println("All files processed.");
                }
            }
        }
    }
}
