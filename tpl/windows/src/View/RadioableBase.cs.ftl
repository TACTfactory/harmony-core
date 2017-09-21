<@header?interpret />

using ${project_namespace}.Entity.Base;
using System;

namespace ${project_namespace}.View.Radioable
{
    /// <summary>
    /// Used to make entity items radioables.
    /// </summary>
    public abstract class BaseRadioable : EntityBase
    {
        private Boolean radio;

        /// <summary>
        /// Radio property.
        /// </summary>
        public Boolean Radio
        {
            get { return radio; }
            set
            {
                radio = value;
                OnPropertyChanged("Radio");
            }
        }
    }
}
