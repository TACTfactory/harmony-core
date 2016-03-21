<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

using ${project_namespace}.Entity.Base;
using ${project_namespace}.Provider.Contract;
using SQLite.Net.Attributes;
using SQLiteNetExtensions.Attributes;
using System;
using System.Collections.Generic;


namespace ${project_namespace}.Entity
{
    <#if (!curr.inheritance?? || (curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0))>
    [Table(${curr.name?cap_first}Contract.TABLE_NAME)]
    </#if>
    <#if (curr.inheritance?? && curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
        <#list curr.inheritance.subclasses as entity>
    [InheritanceMapping(Code = "${entity.name}", Type = typeof(${entity.name}))]
        </#list>
    [InheritanceMapping(Code = "${curr.name}", Type = typeof(${curr.name}), IsDefault = true)]
    </#if>
    public class ${curr.name}<#if (curr.inheritance?? && (curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??))> : ${curr.inheritance.superclass.name}<#else> : EntityBase</#if>
    {
<#if !curr.internal>
        <#list curr_fields as field>
            <#if !field.id && !field.relation?? && !field.enum??>
        private ${FieldsUtils.getJavaType(field)} ${field.name};
            <#elseif field.enum??>
        private ${enums[field.enum.targetEnum].name} ${field.name};
            <#elseif field.id>
        private ${FieldsUtils.getJavaType(field)} ${field.name};
            <#elseif field.relation?? && !field.internal>
                <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        private Int32 ${field.name};
                <#else>
        private List<${FieldsUtils.getJavaType(field)}> ${field.name};
                </#if>
            </#if>
        </#list>
        
        <#if (curr.inheritance?? && curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
        [Column(IsDiscriminator = true)]
        public string DiscKey;
        
        </#if>
        <#list curr_fields as field>
            <#if !field.id && !field.relation?? && !field.enum??>
        [Column(${curr.name?cap_first}Contract.COL_${field.name?upper_case})]
        public ${FieldsUtils.getJavaType(field)} ${field.name?cap_first}
            <#elseif field.enum??>
        public ${enums[field.enum.targetEnum].name} ${field.name?cap_first}
            <#elseif field.id>
        [PrimaryKey, AutoIncrement]
        [Column(${curr.name?cap_first}Contract.COL_${field.name?upper_case})]
        public ${FieldsUtils.getJavaType(field)} ${field.name?cap_first}
            <#elseif field.relation??>
                <#if field.relation.type == "ManyToOne">
        [Column(${curr.name?cap_first}Contract.COL_${field.relation.targetEntity?upper_case}_ID),
            ForeignKey(typeof(${field.relation.targetEntity?cap_first}))]
        public Int32 ${field.name?cap_first}
                <#elseif field.relation.type == "OneToOne">
        [Column(${curr.name?cap_first}Contract.COL_${field.relation.targetEntity?upper_case}_ID),
            ForeignKey(typeof(${field.relation.targetEntity?cap_first}))]
        public Int32 ${field.name?cap_first}
                <#elseif field.relation.type == "OneToMany">
        [OneToMany]
        public List<${FieldsUtils.getJavaType(field)}> ${field.name?cap_first}        
                <#elseif field.relation.type == "ManyToMany">
        [ManyToMany(typeof(${field.relation.joinTable}))]
        public List<${FieldsUtils.getJavaType(field)}> ${field.name?cap_first}
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
                return this.${field.name};
                    <#else>
                return this.${field.name};
                    </#if>
                </#if>
            }
            
            set
            {
                <#if field.id || !field.relation??>
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                <#elseif field.relation??>
                    <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                
                    <#else>
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                    </#if>
                </#if>
            }
        }
            </#if>
        </#list>
<#else>
        private Int32 ${curr.name?lower_case}_id;
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.id && !field.relation?? && !field.enum??>
        private ${FieldsUtils.getJavaType(field)} ${field.name};
            <#elseif field.enum??>
        private ${enums[field.enum.targetEnum].name} ${field.name};
            <#elseif field.relation??>
                <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        private Int32 ${field.name};
                <#else>
        private List<${FieldsUtils.getJavaType(field)}> ${field.name};
                </#if>
            </#if>
            </#if>
        </#list>
        
        <#if (curr.inheritance?? && curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
        [Column(IsDiscriminator = true)]
        public string DiscKey;
        
        </#if>
        [PrimaryKey, AutoIncrement]
        [Column(${curr.name?cap_first}Contract.COL_${curr.name?upper_case}_ID)]
        public Int32 ${curr.name?cap_first}_Id
        {
            get
            {
                return this.${curr.name?lower_case}_id;
            }
            
            set
            {
                this.${curr.name?lower_case}_id = value;
                OnPropertyChanged("${curr.name?cap_first}_Id");
            }
        }
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.id && !field.relation?? && !field.enum??>
        [Column(${curr.name?cap_first}Contract.COL_${field.name?upper_case})]
        public ${FieldsUtils.getJavaType(field)} ${field.name?cap_first}
            <#elseif field.enum??>
        public ${enums[field.enum.targetEnum].name} ${field.name?cap_first}
            <#elseif field.relation??>
                <#if field.relation.type == "ManyToOne">
        [Column(${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID),
            ForeignKey(typeof(${field.relation.targetEntity?cap_first}))]
        public Int32 ${field.name?cap_first}
                <#elseif field.relation.type == "OneToOne">
        [Column(${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID),
            ForeignKey(typeof(${field.relation.targetEntity?cap_first}))]
        public Int32 ${field.name?cap_first}
                <#elseif field.relation.type == "OneToMany">
        [OneToMany]
        public List<${FieldsUtils.getJavaType(field)}> ${field.name?cap_first}        
                <#elseif field.relation.type == "ManyToMany">
        [ManyToMany(typeof(PoneyToJockey))]
        public List<${FieldsUtils.getJavaType(field)}> ${field.name?cap_first}
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
                return this.${field.name};
                    <#else>
                return this.${field.name};
                    </#if>
                </#if>
            }
            
            set
            {
                <#if field.id || !field.relation??>
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                <#elseif field.relation??>
                    <#if field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                    <#else>
                this.${field.name} = value;
                OnPropertyChanged("${field.name}");
                    </#if>
                </#if>
            }
        }
            </#if>
            </#if>
        </#list>
</#if>

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name}()
        {
        <#list curr_fields as field>
            <#if field.relation?? && !field.internal>
                <#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany">
            this.${field.name} = new List<${field.relation.targetEntity}>();
                </#if>
            </#if>
        </#list>
        }
    }
}