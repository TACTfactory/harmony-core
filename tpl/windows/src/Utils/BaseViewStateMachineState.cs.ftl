<@header?interpret />

using System.Collections.Generic;
using System.Diagnostics;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.Utils.StateMachine
{
    /// <summary>
    /// This class represents the States in the State Machine State System.
    /// Each state has a Dictionary with pairs (transition-state) showing
    /// which state the State Machine should be if a transition is fired while this state
    /// is the current state.
    /// </summary>
    public abstract class BaseViewStateMachineState
    {
        protected Dictionary<Transition, StateID> map = new Dictionary<Transition, StateID>();
        protected StateID stateID;
        
        /// <summary>
        /// State current state ID.
        /// </summary>
        public StateID ID { get { return stateID; } }

        /// <summary>
        /// Add transition in map of current state.
        /// </summary>
        public void AddTransition(Transition trans, StateID id)
        {
            // Check if anyone of the args is invalid
            if (trans == Transition.NullTransition)
            {
                Debug.Write("STATE MACHINE State ERROR: NullTransition is not allowed for a real transition");
                return;
            }

            if (id == StateID.NullStateID)
            {
                Debug.Write("STATE MACHINE State ERROR: NullStateID is not allowed for a real ID");
                return;
            }

            // Since this is a Deterministic FSM,
            //   check if the current transition was already inside the map
            if (map.ContainsKey(trans))
            {
                Debug.Write("STATE MACHINE State ERROR: State " + stateID.ToString() + " already has transition " + trans.ToString() +
                               "Impossible to assign to another state");
                return;
            }

            map.Add(trans, id);
        }

        /// <summary>
        /// This method deletes a pair transition-state from this state's map.
        /// If the transition was not inside the state's map, an ERROR message is printed.
        /// </summary>
        public void DeleteTransition(Transition trans)
        {
            // Check for NullTransition
            if (trans == Transition.NullTransition)
            {
                Debug.Write("STATE MACHINE State ERROR: NullTransition is not allowed");
                return;
            }

            // Check if the pair is inside the map before deleting
            if (map.ContainsKey(trans))
            {
                map.Remove(trans);
                return;
            }
            Debug.Write("STATE MACHINE State ERROR: Transition " + trans.ToString() + " passed to " + stateID.ToString() +
                           " was not on the state's transition list");
        }

        /// <summary>
        /// Retrieve state ID for transition if contains in state map.
        /// </summary>
        public StateID GetOutputState(Transition trans)
        {
            // Check if the map has this transition
            if (map.ContainsKey(trans))
            {
                return map[trans];
            }
            return StateID.NullStateID;
        }

        /// <summary>
        /// This method is used to set up the State condition before entering it.
        /// It is called automatically by the State Machine before assigning it
        /// to the current state.
        /// </summary>
        public virtual void DoBeforeEntering(Page page) { }

        /// <summary>
        /// This method is used to set up the State condition after entering it.
        /// It is called automatically by the State Machine after assigning it
        /// to the current state.
        /// </summary>
        public virtual void DoAfterEntering() { }

        /// <summary>
        /// This method is used to make anything necessary, as reseting variables
        /// before the State Machine changes to another one. It is called automatically
        /// by the State Machine before changing to a new state.
        /// </summary>
        public virtual void DoBeforeLeaving(Page page) { }

        /// <summary>
        /// This method is used to navigate throw the differents pages.
        /// </summary>
        public abstract void Navigate(Page currentPage);

    }
}