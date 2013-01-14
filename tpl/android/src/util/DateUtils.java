package ${project_namespace}.harmony.util;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import ${project_namespace}.${project_name?cap_first}Application;

import android.text.format.DateFormat;

/** Utils for date manipulation */
public class DateUtils extends android.text.format.DateUtils{
	private static java.text.DateFormat df = DateFormat.getDateFormat(
			${project_name?cap_first}Application.getApplication());
	private static java.text.DateFormat tf = DateFormat.getTimeFormat(
			${project_name?cap_first}Application.getApplication());

	/**
	 * Convert date to Android string date format
	 * @param date to convert
	 * @return string date with Android date format
	 */
	public static String formatDateToString(DateTime date){
		return df.format(date.toDate());
	}
	
	/**
	 * Convert date to Android string time format
	 * @param date to convert
	 * @return string time with Android time format
	 */
	public static String formatTimeToString(DateTime date){
		return tf.format(date.toDate());
	}
	
	/**
	 * Convert Android String date format to datetime
	 * @param date Android string date format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDate(String date){
		return formatStringToDateTime(df, date);
	}
	
	/**
	 * Convert Android String time format to datetime
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToTime(String time){
		return formatStringToDateTime(tf, time);
	}
	
	/**
	 * Convert Android String date and time format to datetime
	 * @param date Android string date format to convert
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(String date, String time){
		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToTime(time);
		
		dt = new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),t.getHourOfDay(),t.getMinuteOfHour());
		
		return dt;
	}
	
	/**
	 * Convert String date with dateformat to datetime
	 * @param dateFormat date format of datetime
	 * @param dateTime string datetime to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(java.text.DateFormat dateFormat, String dateTime){
		DateTime dt = null;
		
		try {
			dt = new DateTime(dateFormat.parse(dateTime).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dt;
	}
	
	/**
	 * Convert ISO8601 string date to datetime
	 * @param dateTime ISO8601 string date
	 * @return datetime
	 */
	public static DateTime formatISOStringToDateTime(String dateTime){
		return new DateTime(ISODateTimeFormat.dateTime().parseDateTime(dateTime));
	}
}