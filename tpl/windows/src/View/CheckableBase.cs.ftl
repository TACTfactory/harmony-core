<@header?interpret />

using ${project_namespace}.Entity.Base;
using System;

namespace ${project_namespace}.View.Checkable
{
    /// <summary>
    /// Used to make entity items checkables.
    /// </summary>
    public abstract class BaseCheckable : EntityBase
    {
        private Boolean check;

        /// <summary>
        /// Check property.
        /// </summary>
        public Boolean Check
        {
            get { return check; }
            set
            {
                check = value;
                OnPropertyChanged("Check");
            }
        }
    }
}