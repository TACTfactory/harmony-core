<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using System.ComponentModel;

namespace ${project_namespace}.Entity.Base
{
    /// <summary>
    /// Base class for all entities to handle property changed.
    /// </summary>
    public abstract class EntityBase : INotifyPropertyChanged
    {
        /// <summary>
        /// Event to send on change occurs.
        /// </summary>
        public event PropertyChangedEventHandler PropertyChanged;

        /// <summary>
        /// Method binded to property to handle change.
        /// </summary>
        public void OnPropertyChanged(string name)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
            {
                handler(this, new PropertyChangedEventArgs(name));
            }
        }
    }
}
