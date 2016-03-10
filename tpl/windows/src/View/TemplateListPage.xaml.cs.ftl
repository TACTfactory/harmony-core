<#assign curr = entities[current_entity] />
<@header?interpret />
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;
using ${curr.controller_namespace}.UsersControls;

namespace ${curr.controller_namespace}
{
    public partial class ${curr.name}ListPage : Page
    {
        public ${curr.name}ListUserControl ${curr.name}ItemsList { get; set; }
        public NavigationBrowser NavigationBrowser { get; set; }

        public ${curr.name}ListPage()
        {
            InitializeComponent();

            this.${curr.name}ItemsList = this.${curr.name}_items_list;
            this.NavigationBrowser = this.navigation_broswer;
        }
    }
}

