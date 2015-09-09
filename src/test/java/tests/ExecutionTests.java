package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.controller.StockController;
import com.jpmorgan.model.Stock;
import com.jpmorgan.model.StockImpl;
import com.jpmorgan.model.Trade;
import com.jpmorgan.model.TradeImpl;
@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration("/system-configuration.xml")


public class ExecutionTests {
	
	@Autowired
	StockController sController;
	
	private static final double DELTA = 1e-15;
	
	List<Trade> listTrade;
	HashMap<String,Stock> stockMap = new HashMap<>();
	StockImpl tea;
	StockImpl pop;
	StockImpl ale;
	StockImpl gin;
	StockImpl joe;
	
    @Before 
    public void initialize() {
    		
    	tea= new StockImpl();
		tea.setSymbol("TEA");
		tea.setType(Stock.StockTypes.COMMON);
		tea.setLastDividend(0);
		tea.setFixedDividend(0);
		tea.setParValue(100);
		stockMap.put(tea.getSymbol(), tea);
		
		pop= new StockImpl();
		pop.setSymbol("POP");
		pop.setType(Stock.StockTypes.COMMON);
		pop.setLastDividend(8);
		pop.setFixedDividend(0);
		pop.setParValue(100);
		stockMap.put(pop.getSymbol(), pop);
		
		ale= new StockImpl();
		ale.setSymbol("ALE");
		ale.setType(Stock.StockTypes.COMMON);
		ale.setLastDividend(23);
		ale.setFixedDividend(0);
		ale.setParValue(60);
		stockMap.put(ale.getSymbol(), ale);
		
		gin= new StockImpl();
		gin.setSymbol("GIN");
		gin.setType(Stock.StockTypes.PREFERRED);
		gin.setLastDividend(8);
		gin.setFixedDividend(0.02);
		gin.setParValue(100);
		stockMap.put(gin.getSymbol(), gin);
		
		joe= new StockImpl();
		joe.setSymbol("JOE");
		joe.setType(Stock.StockTypes.COMMON);
		joe.setLastDividend(13);
		joe.setFixedDividend(0);
		joe.setParValue(250);
		stockMap.put(joe.getSymbol(), joe);
		
		
	 }
	
    
 	/** 
 	 *  test that the trade and is retrieved in an given time interval and 
 	 *  will not be retrieved after a given time interval (1 minute) 
 	 */
 	@Test
 	public void testRetrieveTrade() throws InterruptedException{
 		
 		Trade t= generateTrade("TEA",Trade.OperationType.BUY);
 		
 		sController.recordTrade(t);
 	
 		List<Trade> listForTest = new LinkedList<>();
 		listForTest.add(t);
 		System.out.println("Sleep for 1:01 minutes....");
 		Thread.sleep(61000); //sleeps for 61 seconds 
 		
 		assertEquals(sController.getTradesFromNMinutes(t.getStockSymbol(), 15),listForTest);
 		
 		// test that the trade will not be retrieved after 1 minute
 		// the application uses a time restriction of 15 minutes to calculate the stock price
 		
 		assertTrue(sController.getTradesFromNMinutes(t.getStockSymbol(),1).isEmpty());
 		System.out.println("DONE");
 	}
 	
 	/** 
 	 *  assures that 2 trades for the same time recorded in the same 
 	 *  second can be held in memory by the StockController
 	 */
	@Test
	public void testTwoTradesAtSameTimeSameStock(){
		Trade t1= generateTrade("JOE",Trade.OperationType.BUY);
		Trade t2= generateTrade("JOE",Trade.OperationType.BUY);
		sController.recordTrade(t1);
		sController.recordTrade(t2);
		
		assertEquals(sController.getTradesFromNMinutes("JOE",1).size(),2);
		
		
	}
	
	
	
	
	private Trade generateTrade(String symbol, Trade.OperationType type){
		
		Trade t = new TradeImpl();
		int quant = 0;
		int tradePrice = 0;
		
		t.setOperationType(type);
		quant = (int) (Math.random() * (1000 - 100) + 100); 
		t.setQuantity(quant);
		t.setStockSymbol(symbol);
		Stock s = stockMap.get(symbol);
		tradePrice = (int) (Math.random() * (s.getParValue()+10 - s.getParValue()-10) + s.getParValue()-10);
		t.setTradePrice(tradePrice);
		return t;
		
	}
	
	
	
}
