package com.jpmorgan.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.jpmorgan.model.Trade;

@Service
public class SimpleTradeManager {
	

	Map<String, Map<Long, List<Trade>>> trades = new HashMap<>(); 
	
	public SimpleTradeManager() {
			
	}
	
	
	public void recordTrade(Trade t){
		
		String symbol = t.getStockSymbol();
		TreeMap<Long, List<Trade>> sortedTimeMap;
		
		List<Trade> tradeList = null;
		Instant now = Instant.now();
		Long timeStamp = now.getEpochSecond();
		t.setTradeDateTime(now);
		if(trades.containsKey(symbol)){
			
			sortedTimeMap = (TreeMap<Long,List<Trade>>) trades.get(symbol);
			if(sortedTimeMap.containsKey(timeStamp))
				tradeList = sortedTimeMap.get(timeStamp);
			else{
				tradeList = new LinkedList<>();
				sortedTimeMap.put(timeStamp, tradeList);
			}
			tradeList.add(t);
		}else{
			sortedTimeMap = new TreeMap<>();
			tradeList = new LinkedList<>();
			tradeList.add(t);
			sortedTimeMap.put(timeStamp, tradeList);
			trades.put(symbol, sortedTimeMap);
		}
	}
	
	public List<Trade> getTradesFromNMinutes(String stockSympol, int minutes){
		Instant now = Instant.now();
		Long pastLimit = now.minusSeconds(minutes*60).getEpochSecond();
		
		
		List<Trade> l = new LinkedList<>();
		
		TreeMap<Long,List<Trade>> sMap = (TreeMap<Long,List<Trade>>) trades.get(stockSympol);
		if(sMap != null){
			SortedMap<Long,List<Trade>> subSet= sMap.tailMap(pastLimit);
			
			for(List<Trade> list: subSet.values())
				l.addAll(list);
		}
		return l;
		
	}
	
}
