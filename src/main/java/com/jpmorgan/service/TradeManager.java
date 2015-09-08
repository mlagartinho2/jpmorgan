package com.jpmorgan.service;

import java.util.List;

import com.jpmorgan.model.Trade;

public interface TradeManager {
	public void recordTrade(Trade t);
	public List<Trade> getTradesFromNMinutes(String stockSympol, int minutes);
}
