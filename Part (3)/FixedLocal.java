/* Name: Max Thet Tin
 */
//FixedLocal class that provides implementation of Fixed Allocation with Local Replacement Scope management scheme

import java.util.LinkedList;

public class FixedLocal extends Algorithm {

    //default constructor
    public FixedLocal(LinkedList<Process> inputQueue, IOHandler ioController, int frameCount, int quantum) {
        super(inputQueue, ioController, frameCount, quantum);
        createFrames();
    }
	
	//method to check if a part of the main memory is full
    public boolean checkFramesFull(Process process){
        for (int index : process.getFrameRange()){
            if(!mainMemory.get(index).isFull()){
                return false;
            }
        }
        return true;
    }
	
    //method to create frames and assign them on processes
    public void createFrames(){
        int counter = 0;
        int frameSize = frameCount / readyQueue.size();
        for (Process process : readyQueue){
            int i = counter;
            process.setClockPointer(counter);
            while(i < counter + frameSize){
                process.getFrameRange().add(i);
                i++;
            }
            counter += frameSize;
        }
    }

    //method to replace a page if main memory is full with the required policy
    public void replacePage(Page newPage, Process inputProcess) {
        if(checkFramesFull(inputProcess)){
            int start = inputProcess.getFrameRange().get(0);
            int end = inputProcess.getFrameRange().get(inputProcess.getFrameRange().size() - 1) + 1;
            while(true){
                //if reached to the end of the list, loop again
                if(inputProcess.getClockPointer() == end){
                    inputProcess.setClockPointer(start);
                }
                //first frame with reduced counter gets replaced
                if(mainMemory.get(inputProcess.getClockPointer()).getRefCount() == 0){
                    mainMemory.set(inputProcess.getClockPointer(), new Frame(newPage));
                    inputProcess.increaseClockPointer();
                    break;
                }
                //replaces frame
                mainMemory.get(inputProcess.getClockPointer()).decreaseRefCount();
                inputProcess.increaseClockPointer();
            }
        }
        //else add new frame
        else {
            mainMemory.set(inputProcess.getClockPointer(), new Frame(newPage));
            inputProcess.increaseClockPointer();
        }
    }
}
