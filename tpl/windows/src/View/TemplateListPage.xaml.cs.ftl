<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;
using ${project_namespace}.View.${curr.name}.UsersControls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}ListPage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}ListPage load graphical context and be managed by ${curr.name}ListState 
    /// </summary>
    public partial class ${curr.name}ListPage : Page
    {
        // Used to manage ${curr.name}List UserControl in ${curr.name}ListState
        public ${curr.name}ListUserControl ${curr.name}ItemsList { get; set; }
        
        // Used to manage NavigationBrowser in ${curr.name}ListState
        public NavigationBrowser NavigationBrowser { get; set; }

        public ${curr.name}ListPage()
        {
            InitializeComponent();

            this.${curr.name}ItemsList = this.${curr.name?lower_case}_items_list;
            this.NavigationBrowser = this.navigation_broswer;
        }
    }
}

