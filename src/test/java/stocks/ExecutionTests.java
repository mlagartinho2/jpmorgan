package stocks;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	
	
	
	List<Trade> listTrade;
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
		
		
		pop= new StockImpl();
		pop.setSymbol("POP");
		pop.setType(Stock.StockTypes.COMMON);
		pop.setLastDividend(8);
		pop.setFixedDividend(0);
		pop.setParValue(100);
		
		
		ale= new StockImpl();
		ale.setSymbol("ALE");
		ale.setType(Stock.StockTypes.COMMON);
		ale.setLastDividend(23);
		ale.setFixedDividend(0);
		ale.setParValue(60);
		
		
		gin= new StockImpl();
		gin.setSymbol("GIN");
		gin.setType(Stock.StockTypes.PREFERRED);
		gin.setLastDividend(8);
		gin.setFixedDividend(0.02);
		gin.setParValue(100);
		
		
		joe= new StockImpl();
		joe.setSymbol("JOE");
		joe.setType(Stock.StockTypes.COMMON);
		joe.setLastDividend(13);
		joe.setFixedDividend(0);
		joe.setParValue(250);
		
		// Set Up some trades
		
		Trade t = new TradeImpl();
		int quant = 0;
		int tradePrice = 0;
		
		t.setOperationType(Trade.OperationType.BUY);
		quant = (int) (Math.random() * (1000 - 100) + 100); 
		t.setQuantity(quant);
		t.setStockSymbol("JOE");
		tradePrice = (int) (Math.random() * (joe.getParValue()+10 - joe.getParValue()-10) + joe.getParValue()-10);
		t.setTradePrice(tradePrice);
		
		listTrade = new LinkedList<>();
		
		listTrade.add(t);
		
	 }
	
	
	// test that the trade and is retrieved in an given time interval and 
	//will not be retrieved after a given time interval (1 minute)
	@Test
	public void testRetrieveTrade() throws InterruptedException{
		
		Trade t =  listTrade.get(0);
		
		sController.recordTrade(t);
	
		List<Trade> listForTest = new LinkedList<>();
		listForTest.add(t);
		System.out.println("Sleep for 1:01 minutes....");
		Thread.sleep(61000); //sleeps for 61 seconds 
		
		assertEquals(sController.getTradesFromNMinutes(t.getStockSymbol(), 15),listForTest);
		
		// test that the trade will not be retrieved after 1 minute
		// the application uses a time restriction of 15 minutes to calculate the stock price
		
		assertTrue(sController.getTradesFromNMinutes(t.getStockSymbol(),1).isEmpty());
		
	}
	
	
	
	
	
	
}
