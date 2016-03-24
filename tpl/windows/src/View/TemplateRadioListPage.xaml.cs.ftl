<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name?cap_first}RadioListPage allow to create a new item based on ${curr.name?cap_first} entity
    /// This ${curr.name?cap_first}RadioListPage load graphical context and be managed by ${curr.name?cap_first}RadioListState 
    /// </summary>
    public sealed partial class ${curr.name?cap_first}RadioListPage : Page
    {
        /// <summary>
        /// Used to manage ${curr.name?cap_first}RadioList UserControl in ${curr.name?cap_first}RadioListState.
        /// </summary>
        public ${curr.name?cap_first}RadioListUserControl ${curr.name?cap_first}RadioListUserControl { get; set; }
        
        /// <summary>
        /// Used to manage BackBrowser in ${curr.name?cap_first}RadioListState.
        /// </summary>
        public BackBrowser BackBrowser { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}RadioListPage()
        {
            this.InitializeComponent();

            this.${curr.name?cap_first}RadioListUserControl = this.${curr.name?lower_case}_radio_list_usercontrol;
            this.BackBrowser = this.back_broswer;
        }
    }
}
