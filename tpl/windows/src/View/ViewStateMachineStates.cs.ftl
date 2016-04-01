<@header?interpret />

using ${project_namespace}.Utils.StateMachine;
using System.Diagnostics;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View
{
    /// <summary>
    /// Current state machine state manager containing base function ovveride
    /// and state machine start state.
    /// </summary>
    public class ViewStateMachineState : BaseViewStateMachineState
    {
        /// <summary>
        /// Default constructor.
        /// </summary>
        public ViewStateMachineState()
        {
            stateID = StateID.Start;
        }

        /// <summary>
        /// On state machine navigation setup current content with current
        /// navigated page.
        /// </summary>
        public override void Navigate(Page currentPage)
        {
            ((Frame)Window.Current.Content).Content = currentPage;
        }

        /// <summary>
        /// Call state machine before activating state to navigate to choisen page.
        /// </summary>
        public override void DoBeforeEntering(Page page)
        {
            this.Navigate(page);
        }
    }
}
