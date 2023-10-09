import java.util.*;

public class Node {
    
    private int value;
    private int[] available = {1,2,3,4,5,6,7,8,9};
    private boolean known;

    public Node(){
        this.value = 0;
        this.known = false;
    }

    public Node(int val, boolean given){
        this.value = val;
        this.known = given;
    }

    //Getters and Setters
    /**
     * @return int return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return int[] return the available
     */
    public int[] getAvailable() {
        return available;
    }

    /**
     * @param available the available to set
     */
    public void setAvailable(int[] available) {
        this.available = available;
    }

    /**
     * @return boolean return the known
     */
    public boolean isKnown() {
        return known;
    }

    /**
     * @param known the known to set
     */
    public void setKnown(boolean known) {
        this.known = known;
    }
}