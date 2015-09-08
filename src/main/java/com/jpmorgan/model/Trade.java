package com.jpmorgan.model;

import java.time.Instant;

public interface Trade {
	
	public enum OperationType {SELL, BUY}
	
	
	public Instant getTradeDateTime() ;
	public void setTradeDateTime(Instant tradeDateTime) ;
	public OperationType getOperationType() ;
	public void setOperationType(OperationType operationType) ;
	public String getStockSymbol() ;
	public void setStockSymbol(String stockSymbol) ;
	public int getQuantity() ;
	public void setQuantity(int quantity) ;
	public double getTradePrice();
	public void setTradePrice(double tradePrice) ;
	
	
}
