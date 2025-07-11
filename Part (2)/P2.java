/* Name: Max Thet Tin
 */
// This is the P2 class which manages clusters of multiple tasks using scheduled threads and records logs of tasks

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class P2 {
    // Semaphore for managing access to the log
    public static Semaphore logSemaphore = new Semaphore(1, true);
    // Counter for total threads
    public static AtomicInteger totalThreads = new AtomicInteger(0);
    // Counter for finished threads
    public static AtomicInteger finishedThreads = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("Task Manager initialized....");

        // Get the task list filename from the command line arguments
        String taskListFile = args[0];

        try (Scanner sc = new Scanner(new File(taskListFile))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Pattern pattern = Pattern.compile("^(\\S+)\\s+(\\d+)$");
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    // Extract task file name and the number of processors
                    String taskFileName = matcher.group(1);
                    int numProcessors = Integer.parseInt(matcher.group(2));

                    // Create a new task thread for each task
                    TaskThread taskThread = new TaskThread(taskFileName, numProcessors);
                    new Thread(taskThread).start();
                } else {
                    System.out.println("Input error, please try again.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // This is a task thread class which implements Runnable
    static class TaskThread implements Runnable {
        String taskFileName;
        int numProcessors;
        Semaphore processorSemaphore;

        // Constructor
        public TaskThread(String taskFileName, int numProcessors) {
            this.taskFileName = taskFileName;
            this.numProcessors = numProcessors;
            this.processorSemaphore = new Semaphore(numProcessors, true);
        }

        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(taskFileName))) {
                String line;
                int threadNo = 0;
                ArrayList<Thread> threads = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    int input = Integer.parseInt(line);
                    // Increment the total thread count
                    P2.totalThreads.incrementAndGet();
                    // Create a processing thread for each input
                    ProcessingThread pThread = new ProcessingThread(threadNo, taskFileName, input, processorSemaphore);
                    Thread thread = new Thread(pThread);
                    threads.add(thread);
                    thread.start();
                    threadNo++;
                }
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (Exception e) {
                System.out.println("An error has occurred, please try again.");
                e.printStackTrace();
            }
        }
    }

    // This is a processing thread class which implements Runnable
    static class ProcessingThread implements Runnable {
        int threadNo;
        String taskFileName;
        int input;
        Semaphore processorSemaphore;

        // Constructor
        public ProcessingThread(int threadNo, String taskFileName, int input, Semaphore processorSemaphore) {
            this.threadNo = threadNo;
            this.taskFileName = taskFileName;
            this.input = input;
            this.processorSemaphore = processorSemaphore;
        }

        public void run() {
            try {
                // Acquire a processor semaphore to indicate thread is running
                processorSemaphore.acquire();
                // Simulate processing time
                Thread.sleep(input * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Release the processor semaphore when done
                processorSemaphore.release();
            }

            try {
                // Acquire the log semaphore to log the task completion
                logSemaphore.acquire();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                String formattedDate = sdf.format(new Date());
                // Print the task details and result
                System.out.println("Time: " + formattedDate + ", Task: " + taskFileName + ", Thread No: " + threadNo + ", Input: " + input + ", Result: " + (input * input));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Release the log semaphore
                logSemaphore.release();

                // Check if all threads have finished
                if (finishedThreads.incrementAndGet() == totalThreads.get()) {
                    System.out.println("All files processed.");
                }
            }
        }
    }
}
