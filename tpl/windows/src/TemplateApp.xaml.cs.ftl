<@header?interpret />


using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View;
using System;
using Windows.ApplicationModel;
using Windows.ApplicationModel.Activation;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Navigation;

namespace ${project_namespace}
{
    public sealed partial class App : Application
    {
        // Base object used on application initialization
        public ${project_name?cap_first}ApplicationBase ${project_name?lower_case}ApplicationBase { get; set; }
        
        /// <summary>
        /// Constructor for the Application object.
        /// Used a singleton to manage is access.
        /// For accessing ${project_name?cap_first}Application
        /// Base use it with "(App.Current as App).${project_name?cap_first}ApplicationBase".
        /// </summary>
        public App()
        {
            // Standard XAML initialization
            this.InitializeComponent();
            
            // Initialization of current ${project_name?cap_first}ApplicationBase used to manage application info
            this.${project_name?lower_case}ApplicationBase = new ${project_name?cap_first}ApplicationBase();
            
            // Bind suspending event to manage application state
            base.Suspending += OnSuspending;
        }

        /// <summary>
        /// Code to execute when the application is launching (eg, from Start)
        /// This code will not execute when the application is reactivated
        /// </summary>
        /// <param name="args">Activation event</param>
        protected override void OnLaunched(LaunchActivatedEventArgs args)
        {

#if DEBUG
            if (System.Diagnostics.Debugger.IsAttached)
            {
                base.DebugSettings.EnableFrameRateCounter = true;
            }
#endif

            Frame rootFrame = Window.Current.Content as Frame;

            // Do not reiterate initialization if window already have content
            // just check for window be active
            if (rootFrame == null)
            {
                // Create a Frame used like navigation context and go to first Page
                rootFrame = new Frame();

                rootFrame.NavigationFailed += OnNavigationFailed;

                if (args.PreviousExecutionState == ApplicationExecutionState.Terminated)
                {
                    //TODO: load application state previously suspended
                }

                // Throw the Frame in active window
                Window.Current.Content = rootFrame;
            }

            if (rootFrame.Content == null)
            {
                // Use harmony state machine to launch navigation
                ViewStateMachine.Instance.SetTransition(Transition.HomePage, new HomePage());
            }
            // Check that current window is active
            Window.Current.Activate();
        }

        /// <summary>
        /// Code to execute when the application is activated (brought to foreground)
        /// This code will not execute when the application is first launched
        /// </summary>
        /// <param name="args">Activation event</param>
        protected override void OnActivated(IActivatedEventArgs args)
        {
            base.OnActivated(args);
        }
        
        /// <summary>
        /// Called on Page navigation failed
        /// </summary>
        /// <param name="sender">Original Frame which cause navigation failure</param>
        /// <param name="e">Relative detail of navigation failure</param>
        void OnNavigationFailed(object sender, NavigationFailedEventArgs e)
        {
            throw new Exception("Failed to load Page " + e.SourcePageType.FullName);
        }

        /// <summary>
        /// Called on application execution is suspended. Application state is saved
        /// without knowing if application can closed without spoil memory content
        /// </summary>
        /// <param name="sender">Source of suspension request</param>
        /// <param name="e">Suspension request details</param>
        private void OnSuspending(object sender, SuspendingEventArgs e)
        {
            var deferral = e.SuspendingOperation.GetDeferral();
            //TODO: Save application state and stop any background activities
            deferral.Complete();
        }
    }
}