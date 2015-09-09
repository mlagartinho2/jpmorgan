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


public class FormulasTests {
	
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
 	 *   
 	 *  Validates the Stock price, P/RATIO, Dividend YELD and GEOMETRIC MEAN
	 * @throws InterruptedException 
 	 */
	@Test
	public void testFormulas() throws InterruptedException{
		Trade gin1= generateTrade("GIN",Trade.OperationType.BUY);
		int qGin=gin1.getQuantity();
		double dGin=qGin*gin1.getTradePrice();
		sController.recordTrade(gin1);
		
		Trade gin2= generateTrade("GIN",Trade.OperationType.BUY);
		qGin+=gin2.getQuantity();
		dGin+=gin2.getQuantity()*gin2.getTradePrice();
		sController.recordTrade(gin2);
		Thread.sleep(1000); //so the StockMotor have time to work
		
		
		HashMap<String, StockImpl> theStocks = (HashMap<String, StockImpl>) sController.getAllStocks();
		
		StockImpl s = theStocks.get("GIN");
		double ginSP=dGin/qGin;
		//Asserts StockPrice
		assertEquals(s.getStockPrice(), ginSP, DELTA);
		double tickerPrice = dGin/qGin;
		//Asserts Dividend Yield Assuming that the ticker price is the stock price
		assertEquals(s.getDividendYield(), (gin.getFixedDividend()*gin.getParValue())/tickerPrice,DELTA);
		//Asserts P/E Ratio assuming that dividend is the last dividend from the initial stock information
		assertEquals(s.getPeRatio(), tickerPrice/gin.getLastDividend(),DELTA);
		
		
		Trade pop1= generateTrade("POP",Trade.OperationType.BUY);
		int qpop=pop1.getQuantity();
		double dpop=qpop*pop1.getTradePrice();
		sController.recordTrade(pop1);
		
		Trade pop2= generateTrade("POP",Trade.OperationType.BUY);
		qpop+=pop2.getQuantity();
		dpop+=pop2.getQuantity()*pop2.getTradePrice();
		sController.recordTrade(pop2);
		Thread.sleep(1000);
		double popSP=dpop/qpop;
		s = theStocks.get("POP");
		
		assertEquals(s.getStockPrice(), popSP, DELTA);
		
		Trade ale= generateTrade("ALE",Trade.OperationType.BUY);
		int qale=ale.getQuantity();
		double dale=qale*ale.getTradePrice();
		sController.recordTrade(ale);
		double aleSP=dale/qale;
		Thread.sleep(1000); //so the StockMotor have time to work
		s = theStocks.get("ALE");
		assertEquals(s.getStockPrice(), aleSP, DELTA);
		
		
		//Asserts GeometricMean
		double index = Math.pow(ginSP*popSP*aleSP*tea.getParValue()*joe.getParValue(),1/5.0);
		double indexFromController = sController.calculateGBCE();
		
		assertEquals(index, indexFromController, DELTA);
		
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
