<@header?interpret />


using Microsoft.Phone.Shell;
using System;
using System.Diagnostics;
using System.Windows;

namespace ${project_namespace}
{
    public partial class ${project_name?cap_first}Application : ${project_name?cap_first}ApplicationBase
    {
#if DEBUG
        public const Boolean DEBUG = true;
#else
        public const Boolean DEBUG = false;
#endif

        /// <summary>
        /// Constructor for the Application object.
        /// </summary>
        public ${project_name?cap_first}Application()
        {
            // Standard XAML initialization
            InitializeComponent();
            
            // Show graphics profiling information while debugging.
            if (Debugger.IsAttached)
            {
                // Display the current frame rate counters.
                Application.Current.Host.Settings.EnableFrameRateCounter = true;

                // Show the areas of the app that are being redrawn in each frame.
                //Application.Current.Host.Settings.EnableRedrawRegions = true;

                // Enable non-production analysis visualization mode,
                // which shows areas of a page that are handed off to GPU with a colored overlay.
                //Application.Current.Host.Settings.EnableCacheVisualization = true;

                // Prevent the screen from turning off while under the debugger by disabling
                // the application's idle detection.
                // Caution:- Use this under debug mode only. Application that disables user idle detection will continue to run
                // and consume battery power when the user is not using the phone.
                PhoneApplicationService.Current.UserIdleDetectionMode = IdleDetectionMode.Disabled;
            }
        }

        // Code to execute when the application is launching (eg, from Start)
        // This code will not execute when the application is reactivated
        protected override void Application_Launching(object sender, LaunchingEventArgs e)
        {
            base.Application_Launching(sender, e);
        }

        // Code to execute when the application is activated (brought to foreground)
        // This code will not execute when the application is first launched
        protected override void Application_Activated(object sender, ActivatedEventArgs e)
        {
            base.Application_Activated(sender, e);
        }

        // Code to execute when the application is deactivated (sent to background)
        // This code will not execute when the application is closing
        protected override void Application_Deactivated(object sender, DeactivatedEventArgs e)
        {
            base.Application_Deactivated(sender, e);
        }

        // Code to execute when the application is closing (eg, user hit Back)
        // This code will not execute when the application is deactivated
        protected override void Application_Closing(object sender, ClosingEventArgs e)
        {
            base.Application_Closing(sender, e);
        }
    }
}