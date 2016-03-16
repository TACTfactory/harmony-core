<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using System.ComponentModel;

namespace ${project_namespace}.Entity.Base
{
    public abstract class EntityBase : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        protected void OnPropertyChanged(string name)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
            {
                handler(this, new PropertyChangedEventArgs(name));
            }
        }
    }
}