package com.openSorce.proyect.bibliotec.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
	
	public static String stringify(java.util.Date date){
		return dateFormat.format(date);
	}
	public static String stringify(java.sql.Date date){
		return dateFormat.format(new java.util.Date(date.getTime()));
	}
	public static java.util.Date toUtilize(java.sql.Date date) throws Exception{
		return dateFormat.parse(stringify(date));
	}
	public static java.util.Date toFormat(java.util.Date date) throws Exception{
		return dateFormat.parse(stringify(date));
	}
	public static String Calendarize(java.util.Date date){
		GregorianCalendar calendar = new GregorianCalendar(Locale.US){
			private static final long serialVersionUID = 1L;
			@Override
			public String toString(){
				return  get(Calendar.YEAR) + "-" + (get(Calendar.MONTH) + 1) + "-" + get(Calendar.DAY_OF_MONTH);
			}
		};
		calendar.setTime(date);
		return calendar.toString();
	}
}
