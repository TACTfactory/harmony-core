<@header?interpret />

using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.UsersControls
{
    /// <summary>
    /// Used for harmony navigation show abilities.
    /// </summary>
    public sealed partial class ShowBrowser : UserControl
    {
        /// <summary>
        /// UserControl back button.
        /// </summary>
        public Button Btn_Back { get; set; }
        
        /// <summary>
        /// UserControl edit button.
        /// </summary>
        public Button Btn_Edit { get; set; }
        
        /// <summary>
        /// UserControl delete button.
        /// </summary>
        public Button Btn_Delete { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public DetailBrowser()
        {
            this.InitializeComponent();
            this.Btn_Back = this.btn_back;
            this.Btn_Edit = this.btn_edit;
            this.Btn_Delete = this.btn_delete;
        }
    }
}
