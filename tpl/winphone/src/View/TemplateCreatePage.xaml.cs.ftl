<#assign curr = entities[current_entity] />
<@header?interpret />
using Microsoft.Phone.Controls;

namespace ${curr.controller_namespace}
{
    public partial class ${curr.name}CreatePage : PhoneApplicationPage
    {
        public ${curr.name}CreatePage()
        {
            InitializeComponent();
        }
    }
}
