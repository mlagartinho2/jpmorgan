package com.jpmorgan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.jpmorgan.model.Stock.StockTypes;
import com.jpmorgan.model.StockImpl;
import com.jpmorgan.model.Trade;
import com.jpmorgan.service.SimpleStockManager;
import com.jpmorgan.service.SimpleTradeManager;
import com.jpmorgan.task.Drop;

/**
 * @author Marco Lagartinho
 * This Class provides access to business logic methods 
 */
@Controller
public class StockController {
	Map<String,Trade> tradeRecord;
	
	@Autowired
	SimpleStockManager sService;
	
	@Autowired 
	SimpleTradeManager tService;
	
	@Autowired
	Drop drop;
	
	@Value("#{appProperties['NUMBER_OF_MINUTES_STOCK_PRICE']}")
	public int numberOfMinutesStockPrice=15;
	
	
	
	public void calculateDividendYield(String stockSymbol){
		
		StockImpl s = sService.getStock(stockSymbol);
		double dividendYield = 0;
		if(s.getType() == StockTypes.COMMON){
			dividendYield =  s.getLastDividend() / s.getTickerPrice();
		}else if(s.getType() == StockTypes.PREFERRED){
			dividendYield =  s.getFixedDividend() * s.getParValue() / s.getTickerPrice();
		}
		
		s.setDividendYield(dividendYield);
	}
	
	
	public void calculatePERatio(String stockSymbol){
		
		StockImpl s = sService.getStock(stockSymbol);
		
		double result = s.getStockPrice() / s.getLastDividend();
		
		s.setPeRatio(result == Double.POSITIVE_INFINITY ? 0: result); 
		
	}
	
	public void calculateStockPrice(String stockSymbol){
		
		StockImpl s = sService.getStock(stockSymbol);
		
		List<Trade> trades = tService.getTradesFromNMinutes(stockSymbol, numberOfMinutesStockPrice);
		int sumQuantity = 0;
		double sumR = 0;
		for(Trade t : trades){
			
			sumR += t.getTradePrice() * t.getQuantity();
			
			sumQuantity += t.getQuantity();
			
		}
		
		s.setStockPrice(sumR / sumQuantity);

	}
	
	public List<Trade> getStockTrades(String stockSymbol){
		return tService.getTradesFromNMinutes(stockSymbol, numberOfMinutesStockPrice);
		
	}
	
	/**
	 * 
	 * When a trade is recorded a message, a message is puts a message on the shared Object Drop.
	 * 
	 */
	public void recordTrade(Trade t){
		
		tService.recordTrade(t);
		
		synchronized(drop){
			drop.put(t.getStockSymbol());
		}
	}
	
	public List<Trade> getTradesFromNMinutes(String stockSympol, int minutes){
		
		return tService.getTradesFromNMinutes(stockSympol, minutes);
		
	}
	
	public double calculateGBCE(){
		
		double [] elements = sService.getGBCEElements();
		return Math.pow(elements[0], 1 / elements[1]);
	}
	
	
	public Map<String, StockImpl> getAllStocks(){
		return sService.getAllStocks();
		
	}
	
		
	/**
	 *  Shuts Down the Tread StockMotor
	 */
	public void cleanUp(){
		drop.put(Drop.FINALIZE_MESSAGE);
		
	}
	

}
