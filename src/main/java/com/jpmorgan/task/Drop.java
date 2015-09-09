package com.jpmorgan.task;

import java.util.TreeSet;

/**
 * @author marcolagartinho
 * 
 * This class provides 2 synchronized methods to add and remove Strings from a TreeSet, which provides a poolFirst method
 *  
 *
 */
public class Drop {
	
    private TreeSet<String> stack = new TreeSet<>();
    private boolean flag = false;
    
    public final static String FINALIZE_MESSAGE = "EXIT";
    
    
    
    /**
     * The following method provides synchronized reading access to the TreeSet
     * It is used by the Thread StockMotor
     */
    public synchronized String take() {
        // Wait until message is
        // available.
        while (!flag || stack.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        flag = false;
        // Notify producer that
        // status has changed.
        notifyAll();
        return stack.pollFirst();
    }

    /**
     * Every time a Trade is recorded, the StockContoller calls this method to add a String containing the stock symbol of the Trade to the TreeSet
     * Since the TreeSet doesn't allow repeated values , if it exists the the string is not added and so  duplicated and 
     * unnecessary calculations are prevent
     */
    public synchronized void put(String message) {
        // Wait until message has
        // been retrieved.
        while (flag) {
            try { 
                wait();
            } catch (InterruptedException e) {}
        }
        // Toggle status.
        flag = true;
        // Store message.
        stack.add(message);
        
        // Notify consumer that status
        // has changed.
        notifyAll();
        
    }

}
