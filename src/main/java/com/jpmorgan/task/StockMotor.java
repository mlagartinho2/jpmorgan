package com.jpmorgan.task;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpmorgan.controller.StockController;
import com.jpmorgan.model.Trade;

/**
 * @author marcolagartinho
 * 
 * Thread that calculates the Stock Value, the Dividend Yield and the P/E RATIO for each symbol
 * added on the queue controlled by the Drop Object
 * 
 * this thread terminates whenever a message containing  Drop.FINALIZE_MESSAGE is received
 *
 */
public class StockMotor implements Runnable {

	@Autowired
	StockController stockController;
	
	@Autowired 
	Drop drop;
	
	
	Set<Trade> stack = new TreeSet<>();
	
	boolean goFlag;

	public StockMotor() {
	}

	@Override
	public void run() {
		
		try{
			boolean keepGoing = true;
			while(keepGoing){
				
				//wait for next update
				String message = drop.take(); 
	            
	            
	        	if(message.equals(Drop.FINALIZE_MESSAGE))
	        		keepGoing = false;
	        	else{
	        		
	        		//perform the calculations for the symbol received 
	        		
	        		stockController.calculateStockPrice(message);
	        		stockController.calculateDividendYield(message);
	        		stockController.calculatePERatio(message);
	        		
	        	}
	        
	        }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

}
