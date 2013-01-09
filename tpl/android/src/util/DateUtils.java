package ${project_namespace}.harmony.util;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import ${namespace}.DemactApplication;

import android.text.format.DateFormat;

public class DateUtils extends android.text.format.DateUtils{
	private static java.text.DateFormat df = DateFormat.getDateFormat(DemactApplication.getApplication());
	private static java.text.DateFormat tf = DateFormat.getTimeFormat(DemactApplication.getApplication());

	public static String formatDateToString(DateTime date){
		return df.format(date.toDate());
	}
	
	public static String formatTimeToString(DateTime date){
		return tf.format(date.toDate());
	}
	
	public static DateTime formatStringToDate(String date){
		return formatStringToDateTime(df, date);
	}
	
	public static DateTime formatStringToTime(String time){
		return formatStringToDateTime(tf, time);
	}
	
	public static DateTime formatStringToDateTime(String date, String time){
		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToDate(time);
		
		dt = new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),t.getHourOfDay(),t.getMinuteOfHour());
		
		return dt;
	}
	
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
	
	public static DateTime formatISOStringToDateTime(String dateTime){
		return new DateTime(ISODateTimeFormat.dateTime().parseDateTime(dateTime));
	}
}
