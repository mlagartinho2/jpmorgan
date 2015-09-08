package com.jpmorgan.task;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpmorgan.controller.StockController;
import com.jpmorgan.model.Trade;

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
	        		
	        		//for the symbol received perform the calculations
	        		
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
