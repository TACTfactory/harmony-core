<@header?interpret />

using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.UsersControls
{
    /// <summary>
    /// Used for harmony list navigation abilities.
    /// </summary>
    public sealed partial class NavigationBrowser : UserControl
    {
        /// <summary>
        /// UserControl back button.
        /// </summary>
        public Button Btn_Back { get; set; }
        
        /// <summary>
        /// UserControl new button.
        /// </summary>
        public Button Btn_New { get; set; }
        
        /// <summary>
        /// UserControl existing button.
        /// </summary>
        public Button Btn_Existing { get; set; }
        
        /// <summary>
        /// UserControl erase all button.
        /// </summary>
        public Button Btn_Erase_All { get; set; }
        
        /// <summary>
        /// UserControl button stackpanel.
        /// </summary>
        public StackPanel Stackpanel_Btn { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public NavigationBrowser()
        {
            this.InitializeComponent();
            this.Btn_Back = this.btn_back;
            this.Btn_New = this.btn_new;
            this.Btn_Existing = this.btn_existing;
            this.Btn_Erase_All = this.btn_erase_all;
            this.Stackpanel_Btn = this.stackpanel_btn;
        }
    }
}
