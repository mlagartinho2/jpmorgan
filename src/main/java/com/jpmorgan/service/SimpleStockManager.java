package com.jpmorgan.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jpmorgan.model.StockImpl;
import com.jpmorgan.model.Trade;


/**
 * @author marcolagartinho
 * 
 *
 */
public class SimpleStockManager implements StockManager {
	
	@Autowired
	SimpleTradeManager tradeService;
	
	Map<String,StockImpl> stocks;
	
	
	public void setStocks(Map<String,StockImpl> sList){
		this.stocks = sList;
 	}
	
	
	public StockImpl getStock(String name){
		return stocks.get(name);
	}

	public void calculateStockPrice(String stockSymbol){
		
		StockImpl s = getStock(stockSymbol);
		
		List<Trade> trades = tradeService.getTradesFromNMinutes(stockSymbol, 15);
		int sumQuantity = 0;
		double sumR = 0;
		
		for(Trade t : trades){
			
			sumR += t.getTradePrice() * t.getQuantity();
			
			sumQuantity += t.getQuantity();
			
		}
		
		s.setStockPrice(sumR / sumQuantity);

	}
	
	
	public double[] getGBCEElements(){
		
		double[] elements = new double[2];
		
		
		for(StockImpl s : stocks.values()){
			double v = s.getStockPrice() == 0 ? s.getParValue() : s.getStockPrice();
			if( elements[0] == 0 )
				elements[0] =  v;
			else
				elements[0] *= v ;
		}
		elements[1] = stocks.size();
		return elements;
		
	}
	
	public Map<String, StockImpl> getAllStocks(){
		return stocks;
	}
		
}
