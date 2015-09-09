package com.jpmorgan.utils;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class FormatUtils {
	
	final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	final static String AMOUNT_PATTERN = "0.00";
	
	static DateTimeFormatter formatter =
			  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}
	
	public static String formatDate(Instant i){
		return formatter.format(i);
	}
	public static String formatAmount(double val){
		DecimalFormat myFormatter = new DecimalFormat(AMOUNT_PATTERN);
		return myFormatter.format(val);
	}
}
