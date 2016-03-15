<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name?cap_first}ListPage allow to create a new item based on ${curr.name?cap_first} entity
    /// This ${curr.name?cap_first}ListPage load graphical context and be managed by ${curr.name?cap_first}ListState 
    /// </summary>
    public partial class ${curr.name?cap_first}ListPage : Page
    {
        // Used to manage ${curr.name?cap_first}List UserControl in ${curr.name?cap_first}ListState
        public ${curr.name?cap_first}ListUserControl ${curr.name?cap_first}ListUserControl { get; set; }
        
        // Used to manage NavigationBrowser in ${curr.name?cap_first}ListState
        public NavigationBrowser NavigationBrowser { get; set; }

        public ${curr.name?cap_first}ListPage()
        {
            InitializeComponent();

            this.${curr.name?cap_first}ListUserControl = this.${curr.name?lower_case}_list_usercontrol;
            this.NavigationBrowser = this.navigation_broswer;
        }
    }
}

