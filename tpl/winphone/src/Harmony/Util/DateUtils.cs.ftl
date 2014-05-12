<@header?interpret />

using System;
using System.Text.RegularExpressions;

namespace ${project_namespace}.Harmony.Util
{
    /** 
     * Utility class for date manipulation.
     */
    public class DateUtils
    {
        /** TAG for debug purpose. */
        private const String TAG = "DateUtils";

        /** Internal pattern for AM PM time. */
        private const String TIME_AMPM_PATTERN = "hh:mm a";
        /** Internal pattern for H24 time. */
        private const String TIME_24H_PATTERN = "HH:mm";

        /** Regexp Date for YAML. */
        private const String REGEXP_YAML_DATE = 
                "([0-9][0-9][0-9][0-9])"
                + "-([0-9][0-9]?)"
                + "-([0-9][0-9]?)";
    
        /** Regexp Time for YAML. */
        private const String REGEXP_YAML_TIME = 
                "([Tt]|[ \\t]+)([0-9][0-9]?)"
                + ":([0-9][0-9])"
                + ":([0-9][0-9])"
                + "(\\.[0-9]*)?"
                + "(([ \\t]*)Z|[-+][0-9][0-9]?(:[0-9][0-9])?)?";


        /** Regexp Date for XML. */
        private const String REGEXP_XML_DATE = 
                "([0-9][0-9][0-9][0-9])"
                + "-([0-9][0-9])"
                + "-([0-9][0-9])";
    
        /** Regexp Time for XML. */
        private const String REGEXP_XML_TIME = 
                "([Tt]|[ \\t]+)([0-9][0-9]?)"
                + ":([0-9][0-9])"
                + ":([0-9][0-9])"
                + "(\\.[0-9]*)?";
    
        /** Regexp TimeZone for XML. */
        private const String REGEXP_XML_TIMEZONE =
                 "(Z|[-+][0-9][0-9]:[0-9][0-9])";

        /** Time Format type. */
        public enum TimeFormatType {
            /** 24h format. */
            H24,
            /** AM/PM format. */
            AMPM,
            /** Format defined in user's Android configuration. */
            ANDROID_CONF
        }

        /**
         * Convert XML string date/time to datetime.
         * @param dateTime XML string date/time
         * @return datetime the datetime
         */
        public static DateTime formatXMLStringToDateTime(String dateTime) {
            DateTime dt;
            DateTime date;
            DateTime time;
            DateTime defaultDt;
        
            Match matchTimeZone = Regex.Match(dateTime, REGEXP_XML_TIMEZONE);
        
            if (matchTimeZone.Success) {
                String timeZoneString = matchTimeZone.Groups[1].Value;
                //TODO TimeZone
                defaultDt = new DateTime();
            } else {
                //TODO UTC
                defaultDt = new DateTime();
            }
        
            Match matchTime = Regex.Match(dateTime, REGEXP_XML_TIME);
        
            Match match = Regex.Match(dateTime, REGEXP_XML_DATE);
        
            if (match.Success) {
                int year = Convert.ToInt32(match.Groups[1].Value);
                int month = Convert.ToInt32(match.Groups[2].Value);
                int day = Convert.ToInt32(match.Groups[3].Value);
            
                date = new DateTime(
                        year,
                        month,
                        day,
                        defaultDt.Hour,
                        defaultDt.Minute,
                        defaultDt.Second,
                        defaultDt.Kind);
            } else {
                date = new DateTime(defaultDt.Ticks);
            }
        
        
            if (matchTime.Success) {
                int hour = Convert.ToInt32(matchTime.Groups[2].Value);
                int minute = Convert.ToInt32(matchTime.Groups[3].Value);
                int second = Convert.ToInt32(matchTime.Groups[4].Value);
                String millisString = matchTime.Groups[5].Value;

                int millis;
                if (millisString != null) {
                    millis = Convert.ToInt32(millisString.Substring(1));
                } else {
                    millis = 0;
                }
            
                time = new DateTime(
                        defaultDt.Year,
                        defaultDt.Month,
                        defaultDt.Day,
                        hour,
                        minute,
                        second,
                        millis,
                        defaultDt.Kind);
            } else {
                time = new DateTime(defaultDt.Ticks);
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
            return new DateTime(date.Year,
                    date.Month,
                    date.Day,
                    time.Hour,
                    time.Minute,
                    time.Second,
                    time.Millisecond,
                    time.Kind);
        }
    }
}
