<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name?cap_first}CheckListPage allow to create a new item based on ${curr.name?cap_first} entity
    /// This ${curr.name?cap_first}CheckListPage load graphical context and be managed by ${curr.name?cap_first}CheckListState 
    /// </summary>
    public sealed partial class ${curr.name?cap_first}CheckListPage : Page
    {
        /// <summary>
        /// Used to manage ${curr.name?cap_first}CheckList UserControl in ${curr.name?cap_first}CheckListState.
        /// </summary>
        public ${curr.name?cap_first}CheckListUserControl ${curr.name?cap_first}CheckListUserControl { get; set; }
        
        /// <summary>
        /// Used to manage BackBrowser in ${curr.name?cap_first}CheckListState.
        /// </summary>
        public BackBrowser BackBrowser { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}CheckListPage()
        {
            this.InitializeComponent();

            this.${curr.name?cap_first}CheckListUserControl = this.${curr.name?lower_case}_check_list_usercontrol;
            this.BackBrowser = this.back_broswer;
        }
    }
}
