package ${localnamespace}.harmony.util;

import java.text.ParseException;

import org.joda.time.DateTime;

import android.content.Context;
import android.text.format.DateFormat;

public class DateUtils extends android.text.format.DateUtils{
	private Context ctx;
	private static java.text.DateFormat df;
	private static java.text.DateFormat tf;

	public DateUtils(Context context) {
		this.ctx = context;
		df = DateFormat.getDateFormat(this.ctx);
		tf = DateFormat.getTimeFormat(this.ctx);
	}

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
}
