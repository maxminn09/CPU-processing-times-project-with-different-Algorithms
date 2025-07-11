/* Name: Max Thet Tin
 */
//Frame class that provides implementation of the pages in the main memory

public class Frame {
    private boolean full;
    private Page page = null;
    private int refCount;

	//default constructor
    public Frame(){
        this.full = false;
    }

	//custom constructor
    public Frame(Page newPage){
        this.full = true;
        this.page = newPage;
        this.refCount = 0;
    }

	//method to check if memory is full
    public boolean isFull() {
        return full;
    }
	
	//method to get page
    public Page getPage() {
        return page;
    }
	
	//method to get refCount
    public int getRefCount() {
        return refCount;
    }
	
	//method to increase reference count
    public void increaseRefCount() {
        refCount++;
    }
	
	//method to decrease reference count
    public void decreaseRefCount(){
        refCount--;
    }
}

