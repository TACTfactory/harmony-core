<@header?interpret />

using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.UsersControls
{
    /// <summary>
    /// Used for harmony navigation back ability.
    /// </summary>
    public sealed partial class BackBrowser : UserControl
    {
        /// <summary>
        /// UserControl back button.
        /// </summary>
        public Button Btn_Back { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public BackBrowser()
        {
            this.InitializeComponent();
            this.Btn_Back = this.btn_back;
        }
    }
}
