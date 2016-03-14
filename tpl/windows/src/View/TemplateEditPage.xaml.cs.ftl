<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}EditPage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}EditPage load graphical context and be managed by ${curr.name}EditState 
    /// </summary>
    public sealed partial class ${curr.name}EditPage : Page
    {
        // Used to manage ${curr.name}Edit UserControl in ${curr.name}EditState
        public ${curr.name}EditUserControl ${curr.name}EditUserControl { get; set; }
        
        // Used to manage BackBrowser in ${curr.name}EditState
        public BackBrowser BackBrowser { get; set; }
        
        public ${curr.name}EditPage()
        {
            this.InitializeComponent();
            
            this.${curr.name}EditUserControl = this.${curr.name?lower_case}_edit_usercontrol;
            this.BackBrowser = this.back_broswer;
        }
    }
}

