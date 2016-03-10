<#assign curr = entities[current_entity] />
<@header?interpret />
using ${curr.controller_namespace}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${curr.controller_namespace}
{
    public partial class ${curr.name}CreatePage : Page
    {
        public ${curr.name}CreateUserControl ${curr.name}ItemCreate { get; set; }
        public BackBrowser BackBrowser { get; set; }

        public ${curr.name}CreatePage()
        {
            InitializeComponent();
            this.${curr.name}ItemCreate = this.${curr.name}_usercontrol_create;
            this.BackBrowser = this.back_broswer;
        }
    }
}