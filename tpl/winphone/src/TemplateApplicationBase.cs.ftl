<#assign sync=false />
<#list entities?values as entity>
	<#if entity.options.sync??>
		<#assign sync=true />
	</#if>
</#list>
<@header?interpret />


using System;
using System.Diagnostics;
using System.Globalization;
using System.IO.IsolatedStorage;
using System.Windows;
using System.Windows.Markup;
using System.Windows.Navigation;
using Microsoft.Phone.Net.NetworkInformation;
using Microsoft.Phone.Shell;
using Microsoft.Phone.Controls;
using ${project_namespace}.Resources.Values;
using ${project_namespace}.Data.Base;

namespace ${project_namespace}
{
    public abstract class ${project_name?cap_first}ApplicationBase : Application
    {
        <#if (sync)>
        /// <summary>
        /// Preference Last sync date key.
        /// </summary>
        protected const string PREFERENCE_LAST_SYNC = "lastSyncDate";
        
        </#if>
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
        /// <summary>
        /// Provides easy access to the root frame of the Phone Application.
        /// </summary>
        /// <returns>The root frame of the Phone Application.</returns>
        public static PhoneApplicationFrame RootFrame { get; private set; }
        
        public bool IsResumed { get; private set; }

        protected ${project_name?cap_first}ApplicationBase()
        {
            Preferences = IsolatedStorageSettings.ApplicationSettings;
            DateFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortDatePattern;
            TimeFormat = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern;
            Is24Hour = CultureInfo.CurrentCulture.DateTimeFormat.ShortTimePattern.Contains("H");

            <#if (sync)>
            if (!Preferences.Contains(PREFERENCE_LAST_SYNC))
            {
                // TODO: First Sync
                ${project_name?cap_first}ApplicationBase.SetLastSyncDate(
                    new DateTime(1970, 01, 01));
            }
            </#if>
            // Global handler for uncaught exceptions.
            UnhandledException += Application_UnhandledException;
            
            // Phone-specific initialization
            InitializePhoneApplication();
            
            // Language display initialization
            InitializeLanguage();
            
            // Create the database if it does not exist.
            using (${project_name?cap_first}SqlOpenHelperBase db =
                new ${project_name?cap_first}SqlOpenHelperBase())
            {
                db.CreateDatabaseIfNotExists();
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
        
        // Code to execute when the application is launching (eg, from Start)
        // This code will not execute when the application is reactivated
        protected virtual void Application_Launching(object sender, LaunchingEventArgs e)
        {
        }

        // Code to execute when the application is activated (brought to foreground)
        // This code will not execute when the application is first launched
        protected virtual void Application_Activated(object sender, ActivatedEventArgs e)
        {
            this.IsResumed = true;
        }

        // Code to execute when the application is deactivated (sent to background)
        // This code will not execute when the application is closing
        protected virtual void Application_Deactivated(object sender, DeactivatedEventArgs e)
        {
        }

        // Code to execute when the application is closing (eg, user hit Back)
        // This code will not execute when the application is deactivated
        protected virtual void Application_Closing(object sender, ClosingEventArgs e)
        {
        }

        // Code to execute if a navigation fails
        protected virtual void RootFrame_NavigationFailed(object sender, NavigationFailedEventArgs e)
        {
            if (Debugger.IsAttached)
            {
                // A navigation has failed; break into the debugger
                Debugger.Break();
            }
        }

        // Code to execute on Unhandled Exceptions
        protected virtual void Application_UnhandledException(object sender, ApplicationUnhandledExceptionEventArgs e)
        {
            if (Debugger.IsAttached)
            {
                // An unhandled exception has occurred; break into the debugger
                Debugger.Break();
            }
        }

        #region Phone application initialization

        // Avoid double-initialization
        private bool phoneApplicationInitialized = false;

        // Do not add any additional code to this method
        protected virtual void InitializePhoneApplication()
        {
            if (phoneApplicationInitialized)
                return;

            // Create the frame but don't set it as RootVisual yet; this allows the splash
            // screen to remain active until the application is ready to render.
            RootFrame = new PhoneApplicationFrame();
            RootFrame.Navigated += CompleteInitializePhoneApplication;

            // Handle navigation failures
            RootFrame.NavigationFailed += RootFrame_NavigationFailed;

            // Handle reset requests for clearing the backstack
            RootFrame.Navigated += CheckForResetNavigation;

            // Ensure we don't initialize again
            phoneApplicationInitialized = true;
        }

        // Do not add any additional code to this method
        private void CompleteInitializePhoneApplication(object sender, NavigationEventArgs e)
        {
            // Set the root visual to allow the application to render
            if (RootVisual != RootFrame)
                RootVisual = RootFrame;

            // Remove this handler since it is no longer needed
            RootFrame.Navigated -= CompleteInitializePhoneApplication;
        }

        protected virtual void CheckForResetNavigation(object sender, NavigationEventArgs e)
        {
            // If the app has received a 'reset' navigation, then we need to check
            // on the next navigation to see if the page stack should be reset
            if (e.NavigationMode == NavigationMode.Reset)
                RootFrame.Navigated += ClearBackStackAfterReset;
        }

        protected virtual void ClearBackStackAfterReset(object sender, NavigationEventArgs e)
        {
            // Unregister the event so it doesn't get called again
            RootFrame.Navigated -= ClearBackStackAfterReset;

            // Only clear the stack for 'new' (forward) and 'refresh' navigations
            if (e.NavigationMode != NavigationMode.New && e.NavigationMode != NavigationMode.Refresh)
                return;

            // For UI consistency, clear the entire page stack
            while (RootFrame.RemoveBackEntry() != null)
            {
                ; // do nothing
            }
        }

        #endregion

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
        protected virtual void InitializeLanguage()
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
                RootFrame.Language = XmlLanguage.GetLanguage(StringsResources.ResourceLanguage);

                // Set the FlowDirection of all elements under the root frame based
                // on the ResourceFlowDirection resource string for each
                // supported language.
                //
                // If a compiler error is hit then ResourceFlowDirection is missing from
                // the resource file.
                FlowDirection flow = (FlowDirection)Enum.Parse(typeof(FlowDirection), StringsResources.ResourceFlowDirection);
                RootFrame.FlowDirection = flow;
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
    }
}
