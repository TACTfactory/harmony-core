<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.Data;
using ${project_namespace}.View.Checkable.Manager;
using System.Collections.Generic;
using System.Linq;

namespace ${project_namespace}.View.${curr.name?cap_first}.Checkable.Manager
{
    /// <summary>
    /// Manager for all action on CheckableEntities.
    /// Have to implement one ICheckableManagerBase 
    /// and multiple ICheckableManagerParent.
    /// </summary>
    class ${curr.name?cap_first}CheckableManager : ICheckableManagerBase<Entity.${curr.name?cap_first}><#list curr_fields as field><#if field.relation?? && !field.internal><#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany">, ICheckableManagerParent<Entity.${field.relation.targetEntity?cap_first}></#if></#if></#list>
    {
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
        private List<${curr.name?cap_first}Checkable> ${curr.name?lower_case}Checkables;

        /// <summary>
        /// Item checkable list.
        /// </summary>
        public List<${curr.name?cap_first}Checkable> ${curr.name?cap_first}Checkables
        {
            get { return ${curr.name?lower_case}Checkables; }
            set { ${curr.name?lower_case}Checkables = value; }
        }
        
        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}CheckableManager()
        {
            this.${curr.name?lower_case}Checkables = new List<${curr.name?cap_first}Checkable>();
        }

        /// <summary>
        /// Update current ${curr.name?cap_first} entity to set it checkable.
        /// </summary>
        /// <param name="${curr.name?lower_case}">Base item list.</param>
        public void ParseInSelectables(List<Entity.${curr.name?cap_first}> ${curr.name?lower_case})
        {
            foreach (var item in ${curr.name?lower_case})
            {
                this.${curr.name?lower_case}Checkables.Add(new ${curr.name?cap_first}Checkable(item));
            }
        }
        <#list curr_fields as field>
            <#if field.id>
                <#assign Id = field.name?cap_first />
            </#if>
        </#list>
        <#list curr_fields as field>
            <#if field.relation?? && !field.internal>
        /// <summary>
        /// Setup current ${curr.name?cap_first} entity checkable list checked for all items 
        /// linked to ${field.relation.targetEntity?cap_first} entity.
        /// </summary>
        /// <param name="${field.relation.targetEntity?lower_case}">Linked ${field.relation.targetEntity?lower_case} use to retrieve checked item.</param>
        public void SetChecked(Entity.${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?lower_case})
        {
                <#if field.relation.type == "ManyToMany" >
            List<Entity.${curr.name?cap_first}> checkedItems = ${curr.name?lower_case}Adapter.GetByParentId(${field.relation.targetEntity?lower_case});
            foreach (var item in checkedItems)
            {
                this.${curr.name?lower_case}Checkables.First(i => i.${curr.name?cap_first}.${Id} == item.${Id}).Check = true;
            }    
                <#elseif field.relation.type == "OneToMany" >
            this.${curr.name?lower_case}Checkables.First(
                i => i.${curr.name?cap_first}.${Id} == ${curr.name?lower_case}Adapter.GetByParentId(${field.relation.targetEntity?lower_case})
                    .${Id}).Check = true;
                </#if>
        }
            </#if>
        </#list>
        <#list curr_fields as field>
            <#if field.relation?? && !field.internal && field.relation.type != "OneToOne" && field.relation.type != "ManyToOne" >
        /// <summary>
        /// Save current ${curr.name?cap_first} items relations for ${field.relation.targetEntity?cap_first} entity. 
        /// </summary>
        /// <param name="${field.relation.targetEntity?lower_case}">Linked ${field.relation.targetEntity?cap_first} to save change.</param>
        public void Save(Entity.${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?lower_case})
        {
                <#if field.relation.type == "ManyToMany" >
            ${field.relation.targetEntity?cap_first}SQLiteAdapter adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
            this.${curr.name?lower_case}Checkables = this.${curr.name?lower_case}Checkables.FindAll(i => i.Check == true);
            ${field.relation.targetEntity?lower_case}.${field.relation.mappedBy?cap_first} = this.GetBaseList();
            adapter.Update(${field.relation.targetEntity?lower_case});
                <#elseif field.relation.type == "OneToMany" >
            ${field.relation.targetEntity?cap_first}SQLiteAdapter adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
            this.${curr.name?lower_case}Checkables = this.${curr.name?lower_case}Checkables.FindAll(i => i.Check == true);
            ${field.relation.targetEntity?lower_case}.${field.relation.mappedBy?cap_first} = this.GetBaseList().ElementAt(0).Id;
            adapter.Update(${field.relation.targetEntity?lower_case});
                </#if>
        }
            </#if>
        </#list>

        /// <summary>
        /// This method is used to retrieve checkable list items to list items.
        /// </summary>
        public List<Entity.${curr.name?cap_first}> GetBaseList()
        {
            List<Entity.${curr.name?cap_first}> ${curr.name?lower_case}s = new List<Entity.${curr.name?cap_first}>();
            foreach (var item in this.${curr.name?lower_case}Checkables)
            {
                ${curr.name?lower_case}s.Add(item.${curr.name?cap_first});
            }

            return ${curr.name?lower_case}s;
        }
    }
}