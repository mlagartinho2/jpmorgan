package com.jpmorgan.model;

import java.time.Instant;

import com.jpmorgan.utils.FormatUtils;

/**
 * @author marcolagartinho
 * Class Representing a Trade
 *
 */
public class TradeImpl implements Trade{
	
	
	private OperationType operationType;
	private String stockSymbol;
	private int quantity;
	private double tradePrice;
	Instant tradeDateTime;
	
	
	public Instant getTradeDateTime() {
		return tradeDateTime;
	}
	public void setTradeDateTime(Instant tradeDateTime) {
		this.tradeDateTime = tradeDateTime;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String toString(){
		
	 
		return "Symbol: " + FormatUtils.padRight(stockSymbol,3) + 
				", Type: " + FormatUtils.padRight(operationType.toString(),5) + 
				", Price: " + FormatUtils.padLeft(new Double(tradePrice).toString(),10) + 
				", Quantity: " + FormatUtils.padLeft(new Integer(quantity).toString(),10) + 
				", Date: " + FormatUtils.padLeft(FormatUtils.formatDate(tradeDateTime),20);
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((tradeDateTime == null) ? 0 : tradeDateTime.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tradePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeImpl other = (TradeImpl) obj;
		if (operationType != other.operationType)
			return false;
		if (quantity != other.quantity)
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (!stockSymbol.equals(other.stockSymbol))
			return false;
		if (tradeDateTime == null) {
			if (other.tradeDateTime != null)
				return false;
		} else if (!tradeDateTime.equals(other.tradeDateTime))
			return false;
		if (Double.doubleToLongBits(tradePrice) != Double.doubleToLongBits(other.tradePrice))
			return false;
		return true;
	}
	
}
