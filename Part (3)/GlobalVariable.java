/* Name: Max Thet Tin
 */
//GlobalVariable class that provides implementation of Variable Allocation with Global Replacement Scope management scheme

import java.util.LinkedList;

public class GlobalVariable extends Algorithm {

    //default constructor
    public GlobalVariable(LinkedList<Process> inputQueue, IOHandler ioController, int frameCount, int quantum) {
        super(inputQueue, ioController, frameCount, quantum);
    }

    //method to check if main memory is full
    public boolean checkFramesFull(){
        for (Frame frame : mainMemory){
            if(!frame.isFull()){
                return false;
            }
        }
        return true;
    }

    //method to replace a page with the required policy
    public void replacePage(Page newPage, Process process) {
        if(checkFramesFull()){
            while(true){
                if(clockPointer == frameCount){
                    clockPointer = 0;
                }
                //first frame with reduced counter gets replaced
                if(mainMemory.get(clockPointer).getRefCount() == 0){
                    mainMemory.set(clockPointer, new Frame(newPage));
                    clockPointer++;
                    break;
                }
                //replaces frame
                mainMemory.get(clockPointer).decreaseRefCount();
                clockPointer++;
            }
        }
        //else add new frame
        else {
            mainMemory.set(clockPointer, new Frame(newPage));
            clockPointer ++;
        }
    }
}
