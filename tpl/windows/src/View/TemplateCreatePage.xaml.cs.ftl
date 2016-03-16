<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name?cap_first}CreatePage allow to create a new item based on ${curr.name?cap_first} entity
    /// This ${curr.name?cap_first}CreatePage load graphical context and be managed by ${curr.name?cap_first}CreateState 
    /// </summary>
    public partial class ${curr.name?cap_first}CreatePage : Page
    {
        /// <summary>
        /// Used to manage ${curr.name?cap_first}Create UserControl in ${curr.name?cap_first}CreateState.
        /// </summary>
        public ${curr.name?cap_first}CreateUserControl ${curr.name?cap_first}CreateUserControl { get; set; }
        
        /// <summary>
        /// Used to manage BackBrowser in ${curr.name?cap_first}CreateState.
        /// </summary>
        public BackBrowser BackBrowser { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}CreatePage()
        {
            InitializeComponent();
            this.${curr.name?cap_first}CreateUserControl = this.${curr.name?lower_case}_create_usercontrol;
            this.BackBrowser = this.back_broswer;
        }
    }
}