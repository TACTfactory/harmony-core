<@header?interpret />

using ${project_namespace}.Harmony.Util.StateMachine;
using Windows.Graphics.Display;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View
{
    public partial class HomePage : Page
    {
        public HomePage()
        {
            InitializeComponent();
        }
        <#list entities?values as entity>
            <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
        
        private void Button${entity.name}_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(
                Transition.${entity.name}ListPage, 
                new ${entity.name}.${entity.name}ListPage());
        }
            </#if>
        </#list>
    }
}