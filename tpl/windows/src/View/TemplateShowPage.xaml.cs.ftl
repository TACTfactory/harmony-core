<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name?cap_first}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name?cap_first}
{
    /// <summary>
    /// ${curr.name}ShowPage allow to create a new item based on ${curr.name?cap_first} entity.
    /// This ${curr.name?cap_first}ShowPage load graphical context and be managed by ${curr.name?cap_first}ShowState.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}ShowPage : Page
    {
        /// <summary>
        /// Used to manage ${curr.name?cap_first}Show UserControl in ${curr.name?cap_first}ShowState.
        /// </summary>
        public ${curr.name?cap_first}ShowUserControl ${curr.name?cap_first}ShowUserControl { get; set; }

        /// <summary>
        /// Used to manage ShowBrowser in ${curr.name?cap_first}ShowState.
        /// </summary>
        public ShowBrowser ShowBrowser { get; set; }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}ShowPage()
        {
            this.InitializeComponent();

            this.${curr.name?cap_first}ShowUserControl = this.${curr.name?lower_case}_show_usercontrol;
            this.ShowBrowser = this.show_broswer;
        }
    }
}
