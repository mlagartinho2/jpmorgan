package com.jpmorgan.service;

import java.util.Map;

import com.jpmorgan.model.StockImpl;

/**
 * @author marcolagartinho
 * Inteface defining the behavior of the Stock Manager
 */
public interface StockManager {
	public void setStocks(Map<String,StockImpl> sList);
		
	public StockImpl getStock(String name);
	
	public void calculateStockPrice(String stockSymbol);
	
	public double[] getGBCEElements();
	
	public Map<String, StockImpl> getAllStocks();
	
}
