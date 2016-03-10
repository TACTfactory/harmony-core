<#assign curr = entities[current_entity] />
<@header?interpret />

using ${curr.controller_namespace}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}CreatePage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}CreatePage load graphical context and be managed by ${curr.name}CreateState 
    /// </summary>
    public partial class ${curr.name}CreatePage : Page
    {
        // Used to manage ${curr.name}Create UserControl in ${curr.name}CreateState
        public ${curr.name}CreateUserControl ${curr.name}ItemCreate { get; set; }
        
        // Used to manage BackBrowser in ${curr.name}CreateState
        public BackBrowser BackBrowser { get; set; }

        public ${curr.name}CreatePage()
        {
            InitializeComponent();
            this.${curr.name}ItemCreate = this.${curr.name?lower_case}_usercontrol_create;
            this.BackBrowser = this.back_broswer;
        }
    }
}