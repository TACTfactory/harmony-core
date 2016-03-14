<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}ShowPage allow to create a new item based on ${curr.name} entity.
    /// This ${curr.name}ShowPage load graphical context and be managed by ${curr.name}ShowState.
    /// </summary>
    public sealed partial class ${curr.name}ShowPage : Page
    {
        // Used to manage ${curr.name}Show UserControl in ${curr.name}ShowState.
        public ${curr.name}ShowUserControl ${curr.name}ShowUserControl { get; set; }
        
        // Used to manage ShowBrowser in ${curr.name}ShowState
        public ShowBrowser ShowBrowser { get; set; }

        public ${curr.name}ShowPage()
        {
            this.InitializeComponent();

            this.${curr.name}ShowUserControl = this.${curr.name?lower_case}_show_usercontrol;
            this.ShowBrowser = this.show_broswer;
        }
    }
}
