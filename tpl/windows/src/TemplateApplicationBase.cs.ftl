<@header?interpret />

using ${project_namespace}.Data;
using System;
using System.Diagnostics;
using System.Globalization;
using Windows.Networking.Connectivity;
using Windows.Security.ExchangeActiveSyncProvisioning;
using Windows.Storage;

namespace ${project_namespace}
{
    public partial class ${project_name?cap_first}ApplicationBase
    {
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
        /// Application container name
        /// </summary>
        private const string DEMACT_CONTAINER = "demact_container";
        
        /// <summary>
        /// Application local setting base container
        /// </summary>
        private ApplicationDataContainer localSettings = Windows.Storage.ApplicationData.Current.LocalSettings;
        
        /// <summary>
        /// Preferences
        /// </summary>
        protected static ApplicationDataContainer Preferences { get; set; }

        /// <summary>
        /// Get the device's UDID.
        /// </summary>
        /// <returns>A String containing the UDID</returns>
        public static String GetUdid()
        {
            String udid = null;

            var deviceInformation = new EasClientDeviceInformation();
            udid = deviceInformation.Id.ToString();
            byte[] bytes = new byte[udid.Length * sizeof(char)];
            System.Buffer.BlockCopy(udid.ToCharArray(), 0, bytes, 0, bytes.Length);

            udid = Convert.ToBase64String(bytes);

            return udid;
        }

        /// <summary>
        /// Check if Network is available.
        /// </summary>
        /// <returns>true if have a network</returns>
        public static bool IsNetworkAvailable()
        {
            var isInternetConnected = false;
            var connectionProfile = NetworkInformation.GetInternetConnectionProfile();

            if (connectionProfile != null)
            {
                var connectivityLevel = connectionProfile.GetNetworkConnectivityLevel();
                isInternetConnected = connectivityLevel == NetworkConnectivityLevel.InternetAccess;
            }

            return isInternetConnected;
        }

        /// <summary>
        /// Get object from Preference store.
        /// </summary>
        /// <typeparam name="T">Type of the object to return</typeparam>
        /// <param name="key">Key of the object in the preference</param>
        /// <param name="defaultValue">Default value if object wasn't in preference</param>
        /// <returns>The needed object</returns>
        static T GetPreference<T>(String key, T defaultValue)
        {
            T result = defaultValue;

            try
            {
                result = (T)Preferences.Containers[DEMACT_CONTAINER].Values[key];
            }
            catch (Exception)
            {
                Preferences.Containers[DEMACT_CONTAINER].Values.Add(key, defaultValue);
            }

            return result;
        }

        /// <summary>
        /// Update object in preference. Add it if not present.
        /// </summary>
        /// <param name="key">Key of the object in preference</param>
        /// <param name="value">The object to add</param>
        static void UpdatePreference(String key, object value)
        {
            if (Preferences.Containers[DEMACT_CONTAINER].Values.ContainsKey(key))
            {
                Preferences.Containers[DEMACT_CONTAINER].Values[key] = value;
            }
            else
            {
                Preferences.Containers[DEMACT_CONTAINER].Values.Add(key, value);
            }
        }

        // Initialize the app's font and flow direction as defined in its localized resource strings.
        //
        // To ensure that the font of your application is aligned with its supported languages and that the
        // FlowDirection for each of those languages follows its traditional direction, ResourceLanguage
        // and ResourceFlowDirection should be initialized in each resx file to match these values with that
        // file's culture. For example:
        //
        // AppResources.es-ES.resx
        //    ResourceLanguage's value should be "es-ES"
        //    ResourceFlowDirection's value should be "LeftToRight"
        //
        // AppResources.ar-SA.resx
        //     ResourceLanguage's value should be "ar-SA"
        //     ResourceFlowDirection's value should be "RightToLeft"
        //
        // For more info on localizing Windows Phone apps see http://go.microsoft.com/fwlink/?LinkId=262072.
        //
        void InitializeLanguage()
        {
            try
            {
                // Set the font to match the display language defined by the
                // ResourceLanguage resource string for each supported language.
                //
                // Fall back to the font of the neutral language if the Display
                // language of the phone is not supported.
                //
                // If a compiler error is hit then ResourceLanguage is missing from
                // the resource file.

                //RootFrame.Language = XmlLanguage.GetLanguage(StringsResources.ResourceLanguage);

                // Set the FlowDirection of all elements under the root frame based
                // on the ResourceFlowDirection resource string for each
                // supported language.
                //
                // If a compiler error is hit then ResourceFlowDirection is missing from
                // the resource file.

                //TODO check FlowDirection flow = (FlowDirection)Enum.Parse(typeof(FlowDirection), StringsResources.ResourceFlowDirection);
                //RootFrame.FlowDirection = flow;
            }
            catch
            {
                // If an exception is caught here it is most likely due to either
                // ResourceLangauge not being correctly set to a supported language
                // code or ResourceFlowDirection is set to a value other than LeftToRight
                // or RightToLeft.

                if (Debugger.IsAttached)
                {
                    Debugger.Break();
                }

                throw;
            }
        }

        public ${project_name?cap_first}ApplicationBase()
        {
            Microsoft.ApplicationInsights.WindowsAppInitializer.InitializeAsync(
               Microsoft.ApplicationInsights.WindowsCollectors.Metadata |
               Microsoft.ApplicationInsights.WindowsCollectors.Session);

            Preferences = localSettings.CreateContainer(DEMACT_CONTAINER, Windows.Storage.ApplicationDataCreateDisposition.Always);
            DateFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortDatePattern;
            TimeFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern;
            Is24Hour = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern.Contains("H");

            // Language display initialization
            InitializeLanguage();

            // Create the database if it does not exist.
            using (${project_name?cap_first}SQLiteOpenHelper db =
                new ${project_name?cap_first}SQLiteOpenHelper())
            {
                //db.DeleteDatabase();
                db.CreateDatabase();
            }
        }
    }
}