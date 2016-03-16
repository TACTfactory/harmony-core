<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name?cap_first}EditPage allow to create a new item based on ${curr.name?cap_first} entity
    /// This ${curr.name?cap_first}EditPage load graphical context and be managed by ${curr.name?cap_first}EditState 
    /// </summary>
    public sealed partial class ${curr.name?cap_first}EditPage : Page
    {
        // Used to manage ${curr.name?cap_first}Edit UserControl in ${curr.name?cap_first}EditState
        public ${curr.name?cap_first}EditUserControl ${curr.name?cap_first}EditUserControl { get; set; }
        
        // Used to manage BackBrowser in ${curr.name?cap_first}EditState
        public BackBrowser BackBrowser { get; set; }
        
        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}EditPage()
        {
            this.InitializeComponent();
            
            this.${curr.name?cap_first}EditUserControl = this.${curr.name?lower_case}_edit_usercontrol;
            this.BackBrowser = this.back_broswer;
        }
    }
}

