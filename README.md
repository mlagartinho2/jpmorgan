# jpmorgan

Notes:

- The solution uses the spring container with xml configuration provided in file system-configuration.xml and is built with Java 1.8
- The initial list of Stocks is defined inside the system-configuration.xml, though a list of beans which is injected on the StockController bean.

Assumptions
- The Ticker price of a Stock will be initially equals to the Par Value. After the first Trade has been recorded, it will assume the Stock Price.
- Since no information is provided about the format number that the prices in pennies and ratios should have, all of the output will be presented with 2 decimal places and stored internally as double.
- The dividend portion on the P/E Ratio formula will be last dividend value provided for each Stock on the test description


Assuming that the ticker price is the stock price every time a trade recorded all the Stock Price, Dividend Yield and P/E Ratio will have to be updated.
In order to avoid calculating repetitiously those formulas each time a client requests the values, a thread was implemented to provide asynchronous processing. This thread – implemented by the class StockMotor will keep the values updated each time a trade is recorded.

The application keeps the recorded trades in memory in the follow structure:

  HashMap &lt;String (stock symbol), TreeMap &lt;Long (time stamp), LinkedList &lt;Trade Object&gt; &gt; &gt;

The TreeMap is sorted by time stamp, allowing the use of method tailMap to retrieved the trades with a time stamp bigger than the given value (15 minutes)
The values in the TreeMap are Lists of Objects Trade to allow more than Trade of the same Stock Symbol to be save at the same time.

The application have one properties file - config.properties - where the amount of minutes for the Stock Price formula can be defined.



Class Struture:

<b>Package com.jpmorgan</b>

<b>Class App</b> – Runnable class that implements a simple text interface. It allows to perform the requested calculations, lists the stocks and the registered trades for each stock

<b>package com.jpmorgan.controller</b>

<b>class StockController</b> - provides access to clients to business logic methods.

<b>Package com.jpmorgan.service</b>

<b>interface TradeManager</b> – defines the behaviorof the TradeManager 

<b>interface StockManager</b> - defines the behavior of the Stock Manager

<b>class SimpleStockManager</b> - manages operations on Stocks

<b>class SimpleTradeManager</b> - manage operations on Trades



<b>package com.jpmorgan.task</b>

<b>class StockMotor </b>- Thread that calculates the Stock Value, the Dividend Yield and the P/E RATIO for each symbol added on the queue controlled by the Drop Object. This thread terminates whenever a message containing Drop.FINALIZE_MESSAGE is received. This message is send by the cleanup method of the StockManager which is defined as destroy-method on the spring xml configuration file (bean stockController)

<b>class Drop</b> provides 2 synchronized methods to add and remove Strings from a TreeSet, Every time a Trade is recorded, the StockContoller calls the put method to add a String containing the stock symbol of the Trade to the TreeSet.
The TreeSet was choosen here because:
-	Since it doesn't allow repeated values, if stock symbol exists on the set, the string is not added and so duplicated and unnecessary calculations are prevent. 
-	It also provides the pollFirst() method, wich retrieves and removes the first element.
-	The order of in which the elements are retrieved doesn’t matter

<b>package com.jpmorgan.model</b>

<b>interface Trade</b> - defines the behavior of a Trade

<b>interface Stock</b> - defines the behavior of a Stock

<b>class StockImpl</b> – Implements the Stock Entity, since the all data is held in memory instances of this class will keep the information about the Stocks

<b>class TradeImpl</b> – Implements the Trede Entity, instances of this class will keep the information about the Trades.


<b>package com.jpmorgan.utils</b>
<b>final class FormatUtils</b>  - Final class that provides methods to format dates, strings and numbers


<b>package tests</b>

<b>class SetupTests</b> - JUnit Test that asserts that the initial stock list is correctly initialized 

<b>class ExecutionTests</b> – contains to tests, one asserting that a trade will not be retrieved after a given period of time. Although the application has a time out of 15 minutes, this test is set to wait only for 1 minute. The other test asserts that 2 trades for the  same stock will be keep in memory.

<b>class FormulasTests</b> – JUnit Test that asserts that the all the formulas are being calculated correctly 
