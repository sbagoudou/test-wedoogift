package com.sbagoudou.evaluation1.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";

	/**
	 * Formats a date using the given pattern
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(date);
	}

	/**
	 * Returns the previous day of date
	 * 
	 * @param date
	 * @return date - 1day
	 */
	public static Date getPreviousDay(Date date) {
		return new Date(date.getTime() - MILLIS_IN_A_DAY);
	}

	/**
	 * Adds one year to date
	 * 
	 * @param date
	 * @param nuberOfYear
	 * @return date + nuberOfYear year
	 */
	public static Date addYear(Date date, int nuberOfYear) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, nuberOfYear);
		return calendar.getTime();
	}

}
