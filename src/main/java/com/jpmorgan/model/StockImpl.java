package com.jpmorgan.model;

import com.jpmorgan.utils.FormatUtils;

/**
 * @author marcolagartinho
 * 
 * Class Representing A Stock
 *
 */
public class StockImpl implements Stock{

		private String symbol;
		private StockTypes type;
		private double lastDividend;
		private double fixedDividend;
		private double parValue;
		private double stockPrice;
		private double dividendYield;
		private double tickerPrice;
		private double peRatio;
		
		
		public double getPeRatio() {
			return peRatio;
		}
		public void setPeRatio(double peRatio) {
			this.peRatio = peRatio;
		}
		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public StockTypes getType() {
			return type;
		}
		public void setType(StockTypes type) {
			this.type = type;
		}
		public double getLastDividend() {
			return lastDividend;
		}
		public void setLastDividend(double lastDividend) {
			this.lastDividend = lastDividend;
		}
		public double getFixedDividend() {
			return fixedDividend;
		}
		public void setFixedDividend(double fixedDividend) {
			this.fixedDividend = fixedDividend;
		}
		public double getParValue() {
			return parValue;
		}
		public void setParValue(double parValue) {
			this.parValue = parValue;
			if(tickerPrice==0) tickerPrice=parValue;
		}
		public double getStockPrice() {
			return stockPrice;
		}
		public void setStockPrice(double stockPrice) {
			this.stockPrice = stockPrice;
			this.tickerPrice = stockPrice;
		}
		public double getTickerPrice() {
			return tickerPrice;
		}
		public void setTickerPrice(double tickerPrice) {
			this.tickerPrice = tickerPrice;
		}
		public double getDividendYield() {
			return dividendYield;
		}
		public void setDividendYield(double dividendYield) {
			this.dividendYield = dividendYield;
		}
		
		public String toString(){
			return "Symbol: " + FormatUtils.padRight(symbol,3) + 
					", Ticker Price: " + FormatUtils.padLeft(FormatUtils.formatAmount(tickerPrice),10) +
					", Type: " + FormatUtils.padRight(type.toString(),9) + 
					", Last Dividend: " + FormatUtils.padLeft(FormatUtils.formatAmount(lastDividend),10) + 
					", Fixed Dividend: " + FormatUtils.padLeft(FormatUtils.formatAmount(fixedDividend),10) + 
					", Par Value: " + FormatUtils.padLeft(FormatUtils.formatAmount(parValue).toString(),10);
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(dividendYield);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(fixedDividend);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(lastDividend);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(parValue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(peRatio);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(stockPrice);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
			temp = Double.doubleToLongBits(tickerPrice);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			StockImpl other = (StockImpl) obj;
			if (Double.doubleToLongBits(dividendYield) != Double.doubleToLongBits(other.dividendYield))
				return false;
			if (Double.doubleToLongBits(fixedDividend) != Double.doubleToLongBits(other.fixedDividend))
				return false;
			if (Double.doubleToLongBits(lastDividend) != Double.doubleToLongBits(other.lastDividend))
				return false;
			if (Double.doubleToLongBits(parValue) != Double.doubleToLongBits(other.parValue))
				return false;
			if (Double.doubleToLongBits(peRatio) != Double.doubleToLongBits(other.peRatio))
				return false;
			if (Double.doubleToLongBits(stockPrice) != Double.doubleToLongBits(other.stockPrice))
				return false;
			if (symbol == null) {
				if (other.symbol != null)
					return false;
			} else if (!symbol.equals(other.symbol))
				return false;
			if (Double.doubleToLongBits(tickerPrice) != Double.doubleToLongBits(other.tickerPrice))
				return false;
			if (type != other.type)
				return false;
			return true;
		}
		
		
}
