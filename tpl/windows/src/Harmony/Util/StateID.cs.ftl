<@header?interpret />

namespace com.tactfactory.demact.Harmony.Util.StateMachine
{
    /// <summary>
    /// Place the labels for the States in this enum.
    /// Don't change the first label, NullTransition as FSMSystem class uses it.
    /// </summary>
    public enum StateID
    {
        NullStateID = 0, // Use this ID to represent a non-existing State in your system
        Start = 1,

        HomePageEnter = 10, // Use this ID to represent an NPC that see player State in your system
        HomePageExit = 11, // Use this ID to represent an NPC that follow is way State in your system

        JockeyListPageEnter = 20,
        JockeyCreatePageEnter = 21,
        JockeyDetailPageEnter = 22,

        PoneyListPageEnter = 30,
        PoneyCreatePageEnter = 31,
        PoneyCheckListEnter = 33,
    }
}