package com.jpmorgan.task;

import java.util.TreeSet;

public class Drop {
	
    private TreeSet<String> stack = new TreeSet<>();
    private boolean flag = false;
    
    public final static String FINALIZE_MESSAGE = "EXIT";

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
        if(!stack.contains(message)) stack.add(message);
        
        // Notify consumer that status
        // has changed.
        notifyAll();
        
    }

}
