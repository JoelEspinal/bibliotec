package com.openSorce.proyect.bibliotec.utils;

import java.text.SimpleDateFormat;

public class DateUtil {

	public static String stringify(java.util.Date date){
		return new SimpleDateFormat("MM-dd-yyyy HH:mm").format(date);
	}
	public static String stringify(java.sql.Date date){
		return new SimpleDateFormat("MM-dd-yyyy HH:mm").format(date);
	}
}
