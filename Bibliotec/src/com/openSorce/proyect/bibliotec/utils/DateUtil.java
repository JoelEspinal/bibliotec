package com.openSorce.proyect.bibliotec.utils;

import java.text.SimpleDateFormat;

public class DateUtil {

	public static String stringify(java.util.Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	public static String stringify(java.sql.Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(date.getTime()));
	}
	public static java.util.Date toUtilize(java.sql.Date date) throws Exception{
		
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringify(date));
	}
	public static java.util.Date toFormat(java.util.Date date) throws Exception{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringify(date));
	}
}
