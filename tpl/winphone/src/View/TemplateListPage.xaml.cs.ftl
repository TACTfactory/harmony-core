<#assign curr = entities[current_entity] />
<@header?interpret />
using ${project_namespace}.Data;
using Microsoft.Phone.Controls;
using System.Collections.ObjectModel;

namespace ${project_namespace}.View.${curr.name}
{
    public partial class ${curr.name}ListPage : PhoneApplicationPage
    {
        public ${curr.name}ListPage()
        {
            InitializeComponent();
            
            ObservableCollection<Entity.${curr.name}> obs =
                new ObservableCollection<Entity.${curr.name}>(
                    new DemactSqlOpenHelper().${curr.name});

            this.itemsList.ItemsSource = obs;
        }
    }
}
