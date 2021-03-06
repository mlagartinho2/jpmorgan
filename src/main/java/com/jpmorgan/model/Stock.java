package com.jpmorgan.model;


/**
 * @author marcolagartinho
 * Interface that defines the behavior of a Stock
 */
public interface Stock {
	public enum StockTypes {COMMON, PREFERRED}
	
	public double getPeRatio() ;
	public void setPeRatio(double peRatio);
	public String getSymbol() ;
	public void setSymbol(String symbol) ;
	public StockTypes getType() ;
	public void setType(StockTypes type) ;
	public double getLastDividend() ;
	public void setLastDividend(double lastDividend) ;
	public double getFixedDividend() ;
	public void setFixedDividend(double fixedDividend) ;
	public double getParValue() ;
	public void setParValue(double parValue) ;
	public double getStockPrice() ;
	public void setStockPrice(double stockPrice) ;
	public double getTickerPrice() ;
	public void setTickerPrice(double tickerPrice);
	public double getDividendYield();
	public void setDividendYield(double dividendYield);
	
}
