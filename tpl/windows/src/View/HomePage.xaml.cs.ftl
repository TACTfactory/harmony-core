<@header?interpret />
using Microsoft.Phone.Controls;

namespace ${project_namespace}.View
{
    public partial class HomePage : PhoneApplicationPage
    {
        public HomePage()
        {
            InitializeComponent();
        }
        <#list entities?values as entity>
            <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
        
        private void Button${entity.name}_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            this.NavigationService.Navigate(new System.Uri(
                "/View/${entity.name}/${entity.name}ListPage.xaml",
                System.UriKind.Relative));
        }
            </#if>
        </#list>
    }
}