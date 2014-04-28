<#assign sync=false />
<#list entities?values as entity>
	<#if entity.options.sync??>
		<#assign sync=true />
	</#if>
</#list>
<@header?interpret />
using System;
using System.Globalization;
using System.IO.IsolatedStorage;
using System.Windows;
using Microsoft.Phone.Net.NetworkInformation;

namespace ${project_namespace}
{
    public abstract class ${project_name?cap_first}ApplicationBase : Application
    {
        /// <summary>
        /// Preference Last sync date key.
        /// </summary>
        protected const string PREFERENCE_LAST_SYNC = "lastSyncDate";

        /// <summary>
        /// Date format.
        /// </summary>
        public static string DateFormat { get; protected set; }
        /// <summary>
        /// Time format.
        /// </summary>
        public static String TimeFormat { get; protected set; }
        /// <summary>
        /// 24HFormat
        /// </summary>
        public static bool Is24Hour { get; protected set; }
        /// <summary>
        /// Preferences
        /// </summary>
        protected static IsolatedStorageSettings Preferences { get; set; }

        protected ${project_name?cap_first}ApplicationBase()
        {
            Preferences = IsolatedStorageSettings.ApplicationSettings;
            DateFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortDatePattern;
            TimeFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern;
            Is24Hour = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern.Contains("H");

            if (!Preferences.Contains(PREFERENCE_LAST_SYNC))
            {
                // TODO: First Sync
                ${project_name?cap_first}ApplicationBase.SetLastSyncDate(
                    new DateTime(1970, 01, 01));
            }
        }

        /// <summary>
        /// Get the last sync date.
        /// </summary>
        /// <returns>A DateTime representing the last sync date</returns>
        public static DateTime GetLastSyncDate()
        {
            return GetPreference(PREFERENCE_LAST_SYNC, DateTime.MinValue);
        }

        /// <summary>
        /// Set the last sync date.
        /// </summary>
        /// <param name="dateTime">DateTime representing the last sync date to set</param>
        public static void SetLastSyncDate(DateTime dateTime)
        {
            UpdatePreference(
                PREFERENCE_LAST_SYNC,
                dateTime);
        }

        /// <summary>
        /// Get the device's UDID.
        /// </summary>
        /// <returns>A String containing the UDID</returns>
        public static String GetUdid()
        {
            String udid = null;

            byte[] id = (byte[]) Microsoft.Phone.Info
                .DeviceExtendedProperties.GetValue("DeviceUniqueId");

            udid = Convert.ToBase64String(id);

            return udid;
        }

        /// <summary>
        /// Check if Network is available.
        /// </summary>
        /// <returns>true if have a network</returns>
        public static bool IsNetworkAvailable()
        {
            return DeviceNetworkInformation.IsNetworkAvailable;
        }

        /// <summary>
        /// Get object from Preference store.
        /// </summary>
        /// <typeparam name="T">Type of the object to return</typeparam>
        /// <param name="key">Key of the object in the preference</param>
        /// <param name="defaultValue">Default value if object wasn't in preference</param>
        /// <returns>The needed object</returns>
        protected static T GetPreference<T>(String key, T defaultValue)
        {
            T result = defaultValue;

            try
            {
                result = (T)Preferences[key];
            }
            catch (Exception)
            {
                Preferences.Add(key, defaultValue);
            }

            return result;
        }

        /// <summary>
        /// Update object in preference. Add it if not present.
        /// </summary>
        /// <param name="key">Key of the object in preference</param>
        /// <param name="value">The object to add</param>
        protected static void UpdatePreference(String key, object value)
        {
            if (Preferences.Contains(key))
            {
                Preferences[key] = value;
            }
            else
            {
                Preferences.Add(key, value);
            }

            Preferences.Save();
        }
    }
}
