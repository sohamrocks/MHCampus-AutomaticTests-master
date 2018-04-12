package com.mcgraw.test.automation.framework.core.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handling java.text.DateFormat in multi-threading environment
 * 
 * @author Andrei_Turavets
 * 
 */
public class ConcurrentDateFormatAccess {

	private static ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

		@Override
		public DateFormat get() {
			return super.get();
		}

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy MM dd");
		}

		@Override
		public void remove() {
			super.remove();
		}

		@Override
		public void set(DateFormat value) {
			super.set(value);
		}

	};

	public static Date convertStringToDate(String dateString) throws ParseException {
		return df.get().parse(dateString);
	}
	
	public static String convertDateToString(Date date){
		return convertDateToString(date, "yyyy MM dd");
	}
	
	public static String convertDateToString(Date date, String format){
		df.set(new SimpleDateFormat(format));
		return df.get().format(date);
	}

}
