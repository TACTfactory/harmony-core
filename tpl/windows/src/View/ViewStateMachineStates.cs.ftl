<@header?interpret />

using ${project_namespace}.Utils.StateMachine;
using System.Diagnostics;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View
{
    public class ViewStateMachineState : BaseViewStateMachineState
    {
        public ViewStateMachineState()
        {
            stateID = StateID.Start;
        }

        public override void Navigate(Page currentPage)
        {
            Debug.Write("Load Page " + currentPage.Name);
            ((Frame)Window.Current.Content).Content = currentPage;
        }

        public override void DoBeforeEntering(Page page)
        {
            this.Navigate(page);
        }
    }
}
