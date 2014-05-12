<#assign curr = entities[current_entity] />
<@header?interpret />
using Microsoft.Phone.Controls;

namespace ${project_namespace}.View.${curr.name}
{
    public partial class ${curr.name}ListPage : PhoneApplicationPage
    {
        public ${curr.name}ListPage()
        {
            InitializeComponent();
        }
    }
}
