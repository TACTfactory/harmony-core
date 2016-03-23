<@header?interpret />

using System.Collections.Generic;
using System.Diagnostics;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.Utils.StateMachine
{
    /// <summary>
    /// Base state machine mechanism class.
    /// </summary>
    public class BaseViewStateMachine
    {
        private List<BaseViewStateMachineState> states;

        ///// <summary>
        ///// Pages container for navigation undo.
        ///// </summary>
        //public List<Page> Pages = new List<Page>();
        
        /// <summary>
        /// Current Page container for navigation to.
        /// </summary>
        public Page CurrentPage { get; set; }

        // The only way one can change the state of the StateMachine is by performing a transition
        // Don't change the CurrentState directly
        private StateID currentStateID;
        private BaseViewStateMachineState currentState;
        
        /// <summary>
        /// State machine current state ID.
        /// </summary>
        public StateID CurrentStateID { get { return currentStateID; } }
        
        /// <summary>
        /// State machine current state.
        /// </summary>
        public BaseViewStateMachineState CurrentState { get { return currentState; } }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public BaseViewStateMachine()
        {
            states = new List<BaseViewStateMachineState>();
        }

        /// <summary>
        /// This method places new states inside the state machine
        /// or prints an ERROR message if the state was already inside the List.
        /// First state added is also the initial state.
        /// </summary>
        public void AddState(BaseViewStateMachineState s)
        {
            // Check for Null reference before deleting
            if (s == null)
            {
                Debug.Write("STATE MACHINE ERROR: Null reference is not allowed");
            }

            // First State inserted is also the Initial state
            // the state the machine is in when the simulation begins
            if (states.Count == 0)
            {
                states.Add(s);
                currentState = s;
                currentStateID = s.ID;
                return;
            }

            // Add the state to the List if it's not inside it
            foreach (BaseViewStateMachineState state in states)
            {
                if (state.ID == s.ID)
                {
                    Debug.Write("STATE MACHINE ERROR: Impossible to add state " + s.ID.ToString() +
                                   " because state has already been added");
                    return;
                }
            }
            states.Add(s);
        }

        /// <summary>
        /// This method delete a state from the State Machine List if it exists
        /// or prints an ERROR message if the state was not on the List.
        /// </summary>
        public void DeleteState(StateID id)
        {
            // Check for NullState before deleting
            if (id == StateID.NullStateID)
            {
                Debug.Write("STATE MACHINE ERROR: NullStateID is not allowed for a real state");
                return;
            }

            // Search the List and delete the state if it's inside it
            foreach (BaseViewStateMachineState state in states)
            {
                if (state.ID == id)
                {
                    states.Remove(state);
                    return;
                }
            }
            Debug.Write("STATE MACHINE ERROR: Impossible to delete state " + id.ToString() +
                           ". It was not on the list of states");
        }

        /// <summary>
        /// This method tries to change the state of State Machine is in based on
        /// the current state and the transition passed. If current state
        /// doesn't have a target state for the transition passed
        /// an ERROR message is printed.
        /// </summary>
        public void PerformTransition(Transition trans, Page page)
        {
            // Check for NullTransition before changing the current state
            if (trans == Transition.NullTransition)
            {
                Debug.Write("STATE MACHINE ERROR: NullTransition is not allowed for a real transition");
                return;
            }

            // Check if the currentState has the transition passed as argument
            StateID id = currentState.GetOutputState(trans);
            if (id == StateID.NullStateID)
            {
                Debug.Write("STATE MACHINE ERROR: State " + currentStateID.ToString() + " does not have a target state " +
                               " for transition " + trans.ToString());
                return;
            }

            // Update the currentStateID and currentState       
            currentStateID = id;
            foreach (BaseViewStateMachineState state in states)
            {
                if (state.ID == currentStateID)
                {
                    // Do the post processing of the state before setting the new one
                    currentState.DoBeforeLeaving(page);

                    currentState = state;
                    
                    this.CurrentPage = page;
                    //if (!this.Pages.Contains(this.CurrentPage))
                    //{
                    //    this.Pages.Add(this.CurrentPage);
                    //}

                    // Reset the state to its desired condition before it can reason or act
                    currentState.DoBeforeEntering(page);

                    // Do stuff after item loaded
                    currentState.DoAfterEntering();
                    break;
                }
            }
        }
    }
}
