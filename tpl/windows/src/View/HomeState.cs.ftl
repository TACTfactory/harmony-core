<@header?interpret />

using ${project_namespace}.Utils.StateMachine;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.States
{
    /// <summary>
    /// Base entry point of state machine to start navigation.
    /// </summary>
    class HomeState : ViewStateMachineState
    {
        /// <summary>
        /// Constructor that initialyze HomeState stateID 
        /// to HomeStatePageEnter for state machine base state on enter.
        /// </summary>
        public HomeState()
        {
            this.stateID = StateID.HomePageEnter;
        }
    }
}