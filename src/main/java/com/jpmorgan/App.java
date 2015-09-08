package com.jpmorgan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jpmorgan.controller.StockController;
import com.jpmorgan.model.StockImpl;
import com.jpmorgan.model.Trade;
import com.jpmorgan.model.TradeImpl;


public class App {
	
	 static StockController controller = null;
	 static HashMap<String,StockImpl> theStocks;
	 static BufferedReader bufferRead;
	 
	 public App(){
		 
		
	 }
	
	public static void main(String[] args) throws IOException {
		 
         
		 AbstractApplicationContext context = new ClassPathXmlApplicationContext("system-configuration.xml");
		 controller = (StockController) context.getBean("stockController");
		 theStocks = (HashMap<String, StockImpl>) controller.getAllStocks();

         
         boolean flagContinue = true;
         
        
        
         bufferRead = new BufferedReader(new InputStreamReader(System.in));
         
         try {
	         printInfoStocks();
	         printMainOptions();
	         
	         do{
	        	 
	        	 
	        	 System.out.print("option > ");
	        	 String option;
				
				option = bufferRead.readLine();
				
	        	 
	        	 switch (option){
	        	 	case "1":	stockOperations();
	        	 	 			printInfoStocks();
	        	 	 			printMainOptions();
	        	 				break;
	        	 	case "2":	printGBCEIndex();
	        	 				printMainOptions();
	        	 				break;
	        	 	case "q":	flagContinue=false;
	        	 				break;
	        	 	default: 	System.out.print("Invalid option. Insert a valid option >");
	                break;
	        	 }
	        	 
	        	 
	         }while(flagContinue);
		 
         } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
         /*
         Trade t = new Trade();
         t.setOperationType(OperationType.BUY);
         t.setQuantity(100);
         t.setStockSymbol("TEA");
         t.setTradePrice(101);
         
        bean.recordTrade(t);
         
         try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         Trade t2 = new Trade();
         t2.setOperationType(OperationType.SELL);
         t2.setQuantity(102);
         t2.setStockSymbol("POP");
         t2.setTradePrice(141);
         
         Trade t3 = new Trade();
         t3.setOperationType(OperationType.SELL);
         t3.setQuantity(9);
         t3.setStockSymbol("TEA");
         t3.setTradePrice(111);
         
         bean.recordTrade(t3);
         bean.recordTrade(t2);
         
//         System.out.println(bean.calculateStockPrice("TEA"));
         
         
         System.out.println("GBCE = " + bean.calculateGBCE());
         */
        System.out.println("Existing...");
        context.close();
        System.out.println("Terminated");
         
	}
	
	
	private static void printInfoStocks(){
		System.out.println("================================================ Stocks Available ==============================================");
		for(StockImpl s : theStocks.values()){
			System.out.println(s.toString());
			
		}
		System.out.println("================================================================================================================");
		
	}
	private static void recordTrade(String symbol){
		try {
			String option;
			Trade t = new TradeImpl();
					t.setStockSymbol(symbol);
				
			boolean flagT = false;
			
			System.out.print("Enter Operation type BUY/SELL (c to cancel) > ");
			do{
				option = bufferRead.readLine();	
				if(option.equals("c"))
					return;
				else{
						 
							 if(option.equals(Trade.OperationType.BUY.toString())){
								 t.setOperationType(Trade.OperationType.BUY);
								 flagT =  true;
							 }else if(option.equals(Trade.OperationType.SELL.toString())){
								 t.setOperationType(Trade.OperationType.SELL);
								 flagT =  true;
							 }else
								 	System.out.print("Invalid Operation. Enter Operation type BUY/SELL (c to cancel) > ");
					}
				
			}while(!flagT);
			boolean flagQ = false;
			
			System.out.print("Enter Quantity (c to cancel) > ");
			do{
				option = bufferRead.readLine();	
				if(option.equals("c"))
					return;
				else{
						 try{
							 int quant = (Integer.parseInt(option));
							 if(quant <= 0 ) System.out.print("Quantity must be bigger than 0. Enter Quantity (c to cancel) > ");
							 else{
								 t.setQuantity(quant); 
								 flagQ = true;
							 }
						 }catch(NumberFormatException e){
							 System.out.print("Invalid Quantity. Enter Quantity (c to cancel) > ");
						 }
						 
				}
			}while(!flagQ);
			
			
			boolean flagP = false;
			
			System.out.print("Enter Price (c to cancel) > ");
			do{
				option = bufferRead.readLine();	
				if(option.equals("c"))
					return;
				else{
						 try{
							 int price = Integer.parseInt(option);
							 if(price <= 0 ) System.out.print("Price must be bigger than 0. Enter Price (c to cancel) > ");
							 else{
								 t.setTradePrice(price);  
								 flagP =  true;
							 }
						 }catch(NumberFormatException e){
							 System.out.print("Invalid Price. Enter Price (c to cancel) > ");
						 }
				}
			}while(!flagP);
			
			controller.recordTrade(t);
			System.out.println("Trade Recorded");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	static private void stockOperations(){
		boolean continua = false;
		String stockName = null;
		String option = null;
		try{
			System.out.print("Enter a stock symbol(c to cancel) > ");
			do{
				stockName = bufferRead.readLine();	
				if(stockName.equals("c"))
					return;
				else if(theStocks.containsKey(stockName))
					continua = true;
				else
					System.out.print("Invalid Stock. Enter a stock symbol(c to cancel) >  ");
				
			}while(!continua);
			
			StockImpl s = theStocks.get(stockName);
			
			boolean flagP = false;
			
			
			do{
				System.out.println("================================================ Selected Stock ================================================");
				
				System.out.println(s.toString());
				
			
			
			
				printStockTrades(stockName);
				System.out.println("================================================================================================================");
				
			
				System.out.println("\nAvailable Options:");
				System.out.println("1 - Record a Trade");
				System.out.println("2 - Show Calculated Information");
				System.out.println("c - cancel");
				System.out.print("Enter Operation (c to cancel) > ");
				option = bufferRead.readLine();	
				if(option.equals("c"))
					return;
				else{
						 try{
							 if(option.equals("1")){
								 recordTrade(stockName);
							 }else if(option.equals("2"))
								 showCalculatedInfo(s);
							 else
								 System.out.println("Invalid Option");
							 
						 }catch(NumberFormatException e){
							 System.out.print("Invalid Option");
						 }
				}
			}while(!flagP);
		

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	static private void showCalculatedInfo(StockImpl s){
		System.out.println("\nCalculations for Stock " + s.getSymbol() + ":");
		System.out.println("\nStock Price:   " + s.getStockPrice());
		System.out.println("Dividend Yeld: " + s.getDividendYield());
		System.out.println("P/E Ratio:   : " + s.getPeRatio());
		
		System.out.println("\n\npress ENTER to continue... \n\n" );
		try {
			bufferRead.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	static private void printMainOptions(){
		 System.out.println("Select Option:");
         System.out.println("1 - Stock Operations");
         System.out.println("2 - Calculate the GBCE All Share Index");

    	 System.out.println("q - Exit");
	}
	static private void printStockTrades(String stockSymbol){
		
		List<Trade> trades = controller.getTradesFromNMinutes(stockSymbol, 15);
		System.out.println("\n============================= Recorded Trades (last 15min.) ==================================");
		if(trades.isEmpty() )
			System.out.println("(no trades recorded)");
		else{
			for(Trade t : trades){
				System.out.println(t.toString());
			}
		}
		
	}
	
	static private void printGBCEIndex(){
		Double index = controller.calculateGBCE();
		
		System.out.println("\nGBCE All Share Index: " + index);
		
		System.out.println("\n\npress ENTER to continue... \n\n" );
		try {
			bufferRead.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
