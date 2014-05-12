<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using System;
using System.Collections.Generic;
using System.Data.Linq;
using System.Data.Linq.Mapping;
using System.Linq;


namespace ${project_namespace}.Entity
{
    <#if (!curr.inheritance?? || (curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0))>
    [Table]
    </#if>
    <#if (curr.inheritance?? && curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
        <#list curr.inheritance.subclasses as entity>
    [InheritanceMapping(Code = "${entity.name}", Type = typeof(${entity.name}))]
        </#list>
    [InheritanceMapping(Code = "${curr.name}", Type = typeof(${curr.name}), IsDefault = true)]
    </#if>
    public class ${curr.name}<#if (curr.inheritance?? && (curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??))> : ${curr.inheritance.superclass.name}</#if>
    {
        <#list curr_fields as field>
            <#if !field.id && !field.relation??>
        private ${FieldsUtils.getFieldType(field)} ${field.name};
            <#elseif field.id>
        private ${FieldsUtils.getFieldType(field)} ${field.name};
            <#elseif field.relation?? && !field.internal>
                <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        [Column]
        internal int ${field.name}Id;
        private EntityRef<${FieldsUtils.getFieldType(field)}> ${field.name};
                <#else>
        private EntitySet<${FieldsUtils.getFieldType(field)}> ${field.name};
                </#if>
            </#if>
        </#list>
        
        <#if (curr.inheritance?? && curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
        [Column(IsDiscriminator = true)]
        public string DiscKey;
        
        </#if>
        <#list curr_fields as field>
            <#if !field.id && !field.relation??>
        [Column]
        public ${FieldsUtils.getFieldType(field)} ${field.name?cap_first}
            <#elseif field.id>
        [Column(
            IsPrimaryKey = true,
            IsDbGenerated = true,
            CanBeNull = false,
            AutoSync = AutoSync.OnInsert)]
        public ${FieldsUtils.getFieldType(field)} ${field.name?cap_first}
            <#elseif field.relation?? && !field.internal>
                <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        [Association(Storage = "${field.name}", ThisKey = "${field.name}Id")]
        public ${FieldsUtils.getFieldType(field)} ${field.name?cap_first}
                <#else>
        [Association(Storage = "${field.name}", OtherKey = "Id")] 
        public List<${FieldsUtils.getFieldType(field)}> ${field.name?cap_first}
                </#if>
            </#if>
            <#if !field.internal>
        {
            get
            {
                <#if field.id || !field.relation??>
                return this.${field.name};
                <#elseif field.relation??>
                    <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                return this.${field.name}.Entity;
                    <#else>
                return this.${field.name}.ToList();
                    </#if>
                </#if>
            }
            
            set
            {
                <#if field.id || !field.relation??>
                this.${field.name} = value;
                <#elseif field.relation??>
                    <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                this.${field.name}.Entity = value;
                
                if (value != null)
                {
                    this.${field.name}Id = value.Id;
                }
                    <#else>
                this.${field.name}.Assign(value);
                    </#if>
                </#if>
            }
        }
        
            </#if>
        </#list>
        public ${curr.name}()
        {
            <#list curr_fields as field>
                <#if field.relation?? && !field.internal 
                    && field.relation.type != "ManyToOne" && field.relation.type != "OneToOne">
            this.${field.name} = new EntitySet<${FieldsUtils.getFieldType(field)}>(
                <#if field.relation.type != "ManyToMany">
                new Action<${FieldsUtils.getFieldType(field)}>(this.attach_${FieldsUtils.getFieldType(field)}),
                new Action<${FieldsUtils.getFieldType(field)}>(this.detach_${FieldsUtils.getFieldType(field)})
                </#if>
                );
                </#if>
            </#list>
        }
        <#list curr_fields as field>
            <#if field.relation?? && !field.internal 
                && field.relation.type != "ManyToOne" && field.relation.type != "OneToOne"
                && field.relation.type != "ManyToMany">
                <#assign item = FieldsUtils.getFieldType(field)/>
        
        // Called during an add operation 
        private void attach_${item}(${item} ${item?lower_case})
        {
            //NotifyPropertyChanging("${item}");
            //${item?lower_case}.${curr.name} = this;
        }

        // Called during a remove operation 
        private void detach_${item}(${item} ${item?lower_case})
        {
            //NotifyPropertyChanging("${item}");
            //${item?lower_case}.${curr.name} = null;
        }
            </#if>
        </#list>
    }
}