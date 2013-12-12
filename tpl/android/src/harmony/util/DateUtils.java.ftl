<@header?interpret />
package ${project_namespace}.harmony.util;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import android.util.Log;

import ${project_namespace}.${project_name?cap_first}Application;

/** 
 * Utility class for date manipulation.
 */
public class DateUtils extends android.text.format.DateUtils {
	/** TAG for debug purpose. */
	private static final String TAG = "DateUtils";

	/** Internal pattern for AM PM time. */
	private static final String TIME_AMPM_PATTERN = "hh:mm a";
	/** Internal pattern for H24 time. */
	private static final String TIME_24H_PATTERN = "HH:mm";

	/** Regexp Date for YAML. */
	private static final String REGEXP_YAML_DATE = 
			"([0-9][0-9][0-9][0-9])"
			+ "-([0-9][0-9]?)"
			+ "-([0-9][0-9]?)";
	
	/** Regexp Time for YAML. */
	private static final String REGEXP_YAML_TIME = 
			"([Tt]|[ \\t]+)([0-9][0-9]?)"
			+ ":([0-9][0-9])"
			+ ":([0-9][0-9])"
			+ "(\\.[0-9]*)?"
			+ "(([ \\t]*)Z|[-+][0-9][0-9]?(:[0-9][0-9])?)?";


	/** Regexp Date for XML. */
	private static final String REGEXP_XML_DATE = 
			"([0-9][0-9][0-9][0-9])"
			+ "-([0-9][0-9])"
			+ "-([0-9][0-9])";
	
	/** Regexp Time for XML. */
	private static final String REGEXP_XML_TIME = 
			"([Tt]|[ \\t]+)([0-9][0-9]?)"
			+ ":([0-9][0-9])"
			+ ":([0-9][0-9])"
			+ "(\\.[0-9]*)?";
	
	/** Regexp TimeZone for XML. */
	private static final String REGEXP_XML_TIMEZONE =
			 "(Z|[-+][0-9][0-9]:[0-9][0-9])";

	/** Time Format type. */
	public enum TimeFormatType {
		/** 24h format. */
		H24,
		/** AM/PM format. */
		AMPM,
		/** Format defined in user's Android configuration. */
		ANDROID_CONF;
	}


	/**
	 * Convert date to Android string date format.
	 *
	 * @param date to convert
	 *
	 * @return string date with Android date format
	 */
	public static String formatDateToString(DateTime date) {
		return DateUtils.formatDateToString(date, false);
	}

	/**
	 * Convert date to Android string date format.
	 *
	 * @param date to convert
	 * @param local should the date be displayed as a local datetime
	 *
	 * @return string date with Android date format
	 */
	public static String formatDateToString(DateTime date, boolean local) {
		java.text.DateFormat df = ${project_name?cap_first}Application.getDateFormat();
		
		if (local) {
			df.setTimeZone(date.getZone().toTimeZone());
		}

		return df.format(date.toDate());
	}

	/**
	 * Convert date to Android string time format.
	 * @param time Time to convert
	 * @return string time with Android time format
	 */
	public static String formatTimeToString(DateTime time) {
		String result = null;
		if (${project_name?cap_first}Application.is24Hour()) {
			result = time.toString(DateTimeFormat.forPattern(TIME_24H_PATTERN)
					.withZone(DateTimeZone.getDefault()));
		} else {
			result = time.toString(DateTimeFormat.forPattern(TIME_AMPM_PATTERN)
					.withZone(DateTimeZone.getDefault()));
		}

		return result;
	}

	/**
	 * Convert date to Android string time format.
	 * @param time to convert
	 * @param formatType The time format
	 * @return string time with Android time format
	 */
	public static String formatTimeToString(
			DateTime time,
			TimeFormatType formatType) {
		String result;
		if (formatType.equals(TimeFormatType.H24)) {
			result =
				time.toString(DateTimeFormat.forPattern(TIME_24H_PATTERN)
						.withZone(DateTimeZone.getDefault()));
		} else if (formatType.equals(TimeFormatType.AMPM)) {
			result =
				time.toString(DateTimeFormat.forPattern(TIME_AMPM_PATTERN)
						.withZone(DateTimeZone.getDefault()));
		} else if (formatType.equals(TimeFormatType.ANDROID_CONF)) {
			result = formatTimeToString(time);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Convert datetime to Android string date + time format.
	 * @param date to convert
	 * @return string datetime with Android date + time format
	 */
	public static String formatDateTimeToString(DateTime date) {
		return String.format("%s %s",
				formatDateToString(date),
				formatTimeToString(date));
	}

	/**
	 * Convert Android String date format to datetime.
	 * @param date Android string date format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDate(String date) {
		return formatStringToDateTime(${project_name?cap_first}Application
							.getDateFormat(), date);
	}

	/**
	 * Convert Android String time format to datetime.
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToTime(String time) {
		return formatStringToDateTime(${project_name?cap_first}Application
							.getTimeFormat(), time);
	}

	/**
	 * Convert Android String time format to datetime.
	 * @param time Android string time format to convert
	 * @param formatType The time format type
	 * @return datetime The parsed datetime
	 */
	public static DateTime formatStringToTime(
			String time,
			TimeFormatType formatType) {
		DateTime result;
		if (formatType.equals(TimeFormatType.H24)) {
			result = DateTimeFormat.forPattern(TIME_24H_PATTERN).parseDateTime(
					time);
		} else if (formatType.equals(TimeFormatType.AMPM)) {
			result = DateTimeFormat.forPattern(TIME_AMPM_PATTERN).parseDateTime(
					time);
		} else if (formatType.equals(TimeFormatType.ANDROID_CONF)) {
			result = DateUtils.formatStringToTime(time);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Convert Android String date and time format to datetime.
	 * @param date Android string date format to convert
	 * @param time Android string time format to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(String date, String time) {
		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToTime(time);

		dt = new DateTime(dt.getYear(),
				dt.getMonthOfYear(),
				dt.getDayOfMonth(),
				t.getHourOfDay(),
				t.getMinuteOfHour());

		return dt;
	}

	/**
	 * Convert Android String date and time format to datetime.
	 * @param date Android string date format to convert
	 * @param time Android string time format to convert
	 * @param timeFormat The time format
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(String date,
			String time,
			TimeFormatType timeFormat) {

		DateTime dt = formatStringToDate(date);
		DateTime t = formatStringToTime(time, timeFormat);

		dt = new DateTime(dt.getYear(),
				dt.getMonthOfYear(),
				dt.getDayOfMonth(),
				t.getHourOfDay(),
				t.getMinuteOfHour());

		return dt;
	}

	/**
	 * Convert String date with dateformat to datetime.
	 * @param dateFormat date format of datetime
	 * @param dateTime string datetime to convert
	 * @return datetime
	 */
	public static DateTime formatStringToDateTime(
							java.text.DateFormat dateFormat, String dateTime) {
		DateTime dt = null;

		try {
			dt = new DateTime(dateFormat.parse(dateTime).getTime());
		} catch (ParseException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert ISO8601 string date to datetime.
	 * @param dateTime ISO8601 string date
	 * @return datetime
	 */
	public static DateTime formatISOStringToDateTime(String dateTime) {
		DateTime dt = null;

		try {
			dt = ISODateTimeFormat.dateTime()
					.withOffsetParsed().parseDateTime(dateTime);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	
	/**
	 * Convert ISO8601 string date to datetime.
	 * @param time ISO8601 string date
	 * @return datetime
	 */
	public static DateTime formatISOStringToTime(String time) {
		DateTime dt = null;

		try {
			dt = ISODateTimeFormat.timeParser().withOffsetParsed()
					.parseDateTime(time);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert ISO8601 string localdate to datetime.
	 * @param dateTime ISO8601 string localdate
	 * @return datetime
	 */
	public static DateTime formatLocalISOStringToDateTime(String dateTime) {
		DateTime dt = null;

		try {
			dt = ISODateTimeFormat.localDateOptionalTimeParser()
					.parseDateTime(dateTime);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert Yaml string date/time to datetime.
	 * @param dateTime yaml string date/time
	 * @return datetime the datetime
	 */
	public static DateTime formatYAMLStringToDateTime(String dateTime) {
		DateTime dt = null;
		DateTime date = null;
		DateTime time = null;
		DateTime defaultDt = new DateTime(DateTimeZone.UTC);
		
		Matcher match = Pattern.compile(REGEXP_YAML_DATE).matcher(dateTime);
		
		if (match.find()) {
			int year = Integer.parseInt(match.group(1));
			int month = Integer.parseInt(match.group(2));
			int day = Integer.parseInt(match.group(3));
			
			date = new DateTime(
					year,
					month,
					day,
					defaultDt.getHourOfDay(),
					defaultDt.getMinuteOfHour(),
					defaultDt.getSecondOfMinute());
		} else {
			date = new DateTime(0);
		}
		
		Matcher matchTime = Pattern.compile(REGEXP_YAML_TIME).matcher(dateTime);
		
		if (matchTime.find()) {
			int hour = Integer.parseInt(matchTime.group(2));
			int minute = Integer.parseInt(matchTime.group(3));
			int second = Integer.parseInt(matchTime.group(4));
			String millisString = matchTime.group(5);
			int millis;
			if (millisString != null) {
				millis = Integer.parseInt(millisString.substring(1));
			} else {
				millis = 0;
			}
			String timeZoneString = matchTime.group(6);
			DateTimeZone timeZone;
			if (timeZoneString == null) {
				timeZone = DateTimeZone.UTC;
			} else {
				timeZone = DateTimeZone.forID(timeZoneString);
			}
			time = new DateTime(
					defaultDt.getYear(),
					defaultDt.getMonthOfYear(),
					defaultDt.getDayOfMonth(),
					hour,
					minute,
					second,
					millis,
					timeZone);
		} else {
			time = new DateTime(defaultDt);
		}
		
		dt = DateUtils.merge(date, time);
		return dt;
	}

	/**
	 * Convert XML string date/time to datetime.
	 * @param dateTime XML string date/time
	 * @return datetime the datetime
	 */
	public static DateTime formatXMLStringToDateTime(String dateTime) {
		DateTime dt = null;
		DateTime date = null;
		DateTime time = null;
		DateTime defaultDt;
		
		Matcher matchTimeZone = 
				Pattern.compile(REGEXP_XML_TIMEZONE).matcher(dateTime);
		
		if (matchTimeZone.find()) {
			String timeZoneString = matchTimeZone.group(1);
			defaultDt = new DateTime(0, DateTimeZone.forID(timeZoneString));
		} else {
			defaultDt = new DateTime(0, DateTimeZone.UTC);
		}
		
		Matcher matchTime = Pattern.compile(REGEXP_XML_TIME).matcher(dateTime);
		
		Matcher match = Pattern.compile(REGEXP_XML_DATE).matcher(dateTime);
		
		if (match.find()) {
			int year = Integer.parseInt(match.group(1));
			int month = Integer.parseInt(match.group(2));
			int day = Integer.parseInt(match.group(3));
			
			date = new DateTime(
					year,
					month,
					day,
					defaultDt.getHourOfDay(),
					defaultDt.getMinuteOfHour(),
					defaultDt.getSecondOfMinute(),
					defaultDt.getZone());
		} else {
			date = new DateTime(defaultDt);
		}
		
		
		if (matchTime.find()) {
			int hour = Integer.parseInt(matchTime.group(2));
			int minute = Integer.parseInt(matchTime.group(3));
			int second = Integer.parseInt(matchTime.group(4));
			String millisString = matchTime.group(5);
			int millis;
			if (millisString != null) {
				millis = Integer.parseInt(millisString.substring(1));
			} else {
				millis = 0;
			}
			
			time = new DateTime(
					defaultDt.getYear(),
					defaultDt.getMonthOfYear(),
					defaultDt.getDayOfMonth(),
					hour,
					minute,
					second,
					millis,
					defaultDt.getZone());
		} else {
			time = new DateTime(defaultDt);
		}
		
		dt = DateUtils.merge(date, time);
		return dt;
	}
	
	/**
	 * Merge two DateTime reprensenting a date and a time.
	 * 
	 * @param date The date
	 * @param time The time
	 *
	 * @return The merged date time
	 */
	public static DateTime merge(DateTime date, DateTime time) {
		return new DateTime(date.getYear(),
				date.getMonthOfYear(),
				date.getDayOfMonth(),
				time.getHourOfDay(),
				time.getMinuteOfHour(),
				time.getSecondOfMinute(),
				time.getMillisOfSecond(),
				time.getZone());
	}

	/**
	 * Convert a string to a datetime thanks to the given pattern.
	 * @param pattern The datetime pattern (ex. "dd-mm-yyyy" or "yyyy-MM-dd
	 * hh:mm")
	 * @param dateTime date string
	 * @param locale Locale
	 * @return datetime
	 */
	public static DateTime formatPattern(
			String pattern, String dateTime, Locale locale) {
		DateTime dt = null;

		try {
			dt = DateTimeFormat.forPattern(pattern)
					.withLocale(locale).parseDateTime(dateTime);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}

	/**
	 * Convert a string to a datetime thanks to the given pattern.
	 * @param pattern The datetime pattern (ex. "dd-mm-yyyy" or "yyyy-MM-dd
	 * hh:mm")
	 * @param dateTime date string
	 * @return datetime
	 */
	public static DateTime formatPattern(String pattern, String dateTime) {
		return formatPattern(pattern, dateTime, Locale.getDefault());
	}

	/**
	 * Convert a string to a locale datetime thanks to the given pattern.
	 * @param pattern The datetime pattern (ex. "dd-mm-yyyy" or "yyyy-MM-dd
	 * hh:mm")
	 * @param dateTime date string
	 * @return datetime
	 */
	public static DateTime formatLocalPattern(String pattern, String dateTime) {
		DateTime dt = null;

		try {
			LocalDateTime tmp = DateTimeFormat.forPattern(pattern)
					.parseLocalDateTime(dateTime);
			dt = DateUtils.formatLocalISOStringToDateTime(tmp.toString());
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getMessage());
		}

		return dt;
	}
}
