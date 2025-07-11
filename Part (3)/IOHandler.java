/* Name: Max Thet Tin
 */
//IOHandler class that provides implementation of the temporary memory storage for processes 
//before being loaded into the main memory

import java.util.LinkedList;
import java.util.Objects;


public class IOHandler {
    private LinkedList<Page> tempMemory;
	
	//default constructor
    public IOHandler(LinkedList<Page> inputMemory){
        tempMemory = inputMemory;
    }
	
	//method to get page from the memory
    public Page getPage(String inputPage) {
        for(Page page : tempMemory){
            if(Objects.equals(page.getId(), inputPage)){
                return page;
            }
        }
        return null;
    }
}
