<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.View.Checkable;

namespace ${project_namespace}.View.${curr.name?cap_first}.Checkable
{
    /// <summary>
    /// ${curr.name?cap_first}Checkable entity.
    /// </summary>
    public class ${curr.name?cap_first}Checkable : BaseCheckable
    {
        private Entity.${curr.name?cap_first} ${curr.name?lower_case};

        /// <summary>
        /// Base item which have to add Check property.
        /// </summary>
        public Entity.${curr.name?cap_first} ${curr.name?cap_first}
        {
            get { return ${curr.name?lower_case}; }
            set
            {
                ${curr.name?lower_case} = value;
                OnPropertyChanged("${curr.name?cap_first}");
            }
        }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}Checkable(Entity.${curr.name?cap_first} ${curr.name?lower_case})
        {
            this.${curr.name?lower_case} = ${curr.name?lower_case};
        }
    }
}
