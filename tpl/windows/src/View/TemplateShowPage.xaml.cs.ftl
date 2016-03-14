<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.${curr.name}.UsersControls;
using ${project_namespace}.View.Navigation.UsersControls;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    /// <summary>
    /// ${curr.name}DetailPage allow to create a new item based on ${curr.name} entity
    /// This ${curr.name}DetailPage load graphical context and be managed by ${curr.name}DetailState 
    /// </summary>
    public sealed partial class ${curr.name}DetailPage : Page
    {
        // Used to manage ${curr.name}Detail UserControl in ${curr.name}DetailState
        public ${curr.name}DetailUserControl ${curr.name}ItemDetail { get; set; }
        
        // Used to manage DetailBrowser in ${curr.name}DetailState
        public DetailBrowser DetailBrowser { get; set; }

        public ${curr.name}DetailPage()
        {
            this.InitializeComponent();

            this.${curr.name}ItemDetail = this.${curr.name?lower_case}_detail;
            this.DetailBrowser = this.detail_broswer;
        }
    }
}
