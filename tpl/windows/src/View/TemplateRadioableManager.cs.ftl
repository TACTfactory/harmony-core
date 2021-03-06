<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.Data;
using ${project_namespace}.View.Radioable.Manager;
using System.Collections.Generic;

namespace ${project_namespace}.View.${curr.name?cap_first}.Radioable.Manager
{
    /// <summary>
    /// Manager for all action on RadioableEntities.
    /// Have to implement one IRadioableManagerBase
    /// and multiple IRadioableManagerParent.
    /// </summary>
    class ${curr.name?cap_first}RadioableManager : IRadioableManagerBase<Entity.${curr.name?cap_first}><#list curr_fields as field><#if field.relation?? && !field.internal><#if field.relation.type == "OneToOne" || field.relation.type == "OneToMany">, IRadioableManagerParent<Entity.${field.relation.targetEntity?cap_first}></#if></#if></#list>
    {
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter =
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
        private List<${curr.name?cap_first}Radioable> ${curr.name?lower_case}Radioables;

        /// <summary>
        /// Item Radioable list.
        /// </summary>
        public List<${curr.name?cap_first}Radioable> ${curr.name?cap_first}Radioables
        {
            get { return ${curr.name?lower_case}Radioables; }
            set { ${curr.name?lower_case}Radioables = value; }
        }

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}RadioableManager()
        {
            this.${curr.name?lower_case}Radioables = new List<${curr.name?cap_first}Radioable>();
        }

        /// <summary>
        /// Update current ${curr.name?cap_first} entity to set it Radioable.
        /// </summary>
        /// <param name="${curr.name?lower_case}">Base item list.</param>
        public void ParseInRadioables(List<Entity.${curr.name?cap_first}> ${curr.name?lower_case})
        {
            foreach (var item in ${curr.name?lower_case})
            {
                this.${curr.name?lower_case}Radioables.Add(new ${curr.name?cap_first}Radioable(item));
            }
        }
            <#assign fields = ViewUtils.getAllFields(curr) />
            <#list fields?values as field>
                <#if field.id>
                    <#assign id = field.name />
                </#if>
            </#list>
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden && field.relation??)>
                    <#if field.relation?? && (field.relation.type == "OneToMany" || field.relation.type == "OneToOne")>
        /// <summary>
        /// Setup current ${curr.name?cap_first} entity radioable list radioed for current item
        /// linked to ${field.relation.targetEntity?cap_first} entity.
        /// </summary>
        /// <param name="${field.relation.targetEntity?lower_case}">Linked ${field.relation.targetEntity?lower_case} use to retrieve checked item.</param>
        public void SetRadioed(Entity.${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?lower_case})
        {
                        <#list entities?values as entity>
                            <#if entity.name == field.relation.targetEntity>
                                <#assign relatedEntity = entity />
                            </#if>
                        </#list>
                        <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                        <#list fields?values as field>
                            <#if field.relation?? && field.relation.targetEntity == curr.name>
                                <#assign field_mapped = field.name>
                            </#if>
                        </#list>
            if (this.${curr.name?lower_case}Radioables.Find(i => i.${curr.name?cap_first}.${id?cap_first} == ${field.relation.targetEntity?lower_case}.${field_mapped?cap_first}) != null)
            {
                this.${curr.name?lower_case}Radioables.Find(i => i.${curr.name?cap_first}.${id?cap_first} == ${field.relation.targetEntity?lower_case}.${field_mapped?cap_first}).Radio = true;
            }
        }
                    </#if>
                </#if>
            </#list>
        <#list curr_fields as field>
            <#if field.relation?? && !field.internal && field.relation.type != "ManyToMany" && field.relation.type != "ManyToOne" >
                <#list entities?values as entity>
                    <#if entity.name == field.relation.targetEntity>
                        <#assign relatedEntity = entity />
                    </#if>
                </#list>
                <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                <#list fields?values as field>
                    <#if field.id>
                    </#if>
                </#list>

        /// <summary>
        /// Save current ${curr.name?cap_first} items relations for ${field.relation.targetEntity?cap_first} entity.
        /// </summary>
        /// <param name="${field.relation.targetEntity?lower_case}">Linked ${field.relation.targetEntity?cap_first} to save change.</param>
        public void Save(Entity.${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?lower_case})
        {
                        <#list entities?values as entity>
                            <#if entity.name == field.relation.targetEntity>
                                <#assign relatedEntity = entity />
                            </#if>
                        </#list>
                        <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                        <#list fields?values as field>
                            <#if field.relation?? && field.relation.targetEntity == curr.name>
                                <#assign field_mapped = field.name>
                            </#if>
                        </#list>
                <#if field.relation.type == "OneToMany" >
            ${field.relation.targetEntity?cap_first}SQLiteAdapter adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
            Entity.${curr.name?cap_first} ${curr.name?lower_case} = this.GetBaseItem();
            if (${curr.name?lower_case} != null)
            {
                ${field.relation.targetEntity?lower_case}.${field_mapped?cap_first} = ${curr.name?lower_case}.${id?cap_first};
                adapter.Update(${field.relation.targetEntity?lower_case});
            }
                <#elseif field.relation.type == "OneToOne" >
            ${field.relation.targetEntity?cap_first}SQLiteAdapter adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
            Entity.${curr.name?cap_first} ${curr.name?lower_case} = this.GetBaseItem();
            if (${curr.name?lower_case} != null)
            {
                ${field.relation.targetEntity?lower_case}.${field_mapped?cap_first} = ${curr.name?lower_case}.${id?cap_first};
                adapter.Update(${field.relation.targetEntity?lower_case});
            }
                </#if>
        }
            </#if>
        </#list>

        /// <summary>
        /// This method is used to retrieve Radioable selected items from list items.
        /// </summary>
        public Entity.${curr.name?cap_first} GetBaseItem()
        {
            ${curr.name?cap_first}Radioable ${curr.name?lower_case}Radioable = this.${curr.name?lower_case}Radioables.Find(i => i.Radio == true);
            if (${curr.name?lower_case}Radioable != null)
            {
                return ${curr.name?lower_case}Radioable.${curr.name?cap_first};
            }
            return null;
        }
    }
}
