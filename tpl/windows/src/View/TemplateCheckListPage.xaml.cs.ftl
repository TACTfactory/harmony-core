<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}CheckListPage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}CheckListPage load graphical context and be managed by ${curr.name}CheckListState 
    /// </summary>
    public sealed partial class ${curr.name}CheckListPage : Page
    {
        // Used to manage ${curr.name}CheckList UserControl in ${curr.name}CheckListState
        public ${curr.name}CheckListUserControl ${curr.name}CheckListUserControl { get; set; }
        
        // Used to manage BackBrowser in ${curr.name}CheckListState
        public BackBrowser BackBrowser { get; set; }

        public ${curr.name}CheckListPage()
        {
            this.InitializeComponent();

            this.${curr.name}CheckListUserControl = this.${curr.name?lower_case}_usercontrol_check_list;
            this.BackBrowser = this.back_broswer;
        }
    }
}
