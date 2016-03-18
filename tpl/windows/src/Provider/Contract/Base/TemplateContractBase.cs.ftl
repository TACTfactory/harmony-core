<#include utilityPath + "all_imports.ftl" />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>
<@header?interpret />

namespace ${project_namespace}.Provider.Contract.Base
{
    public abstract class ${curr.name?cap_first}ContractBase
    {
        <#list curr_fields as field>
            <#if !field.relation??>
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
        public const string COL_${field.name?upper_case}_ID = "${field.name?lower_case}";
            </#if>
        </#list>

        <#list curr_fields as field>
            <#if !field.relation??>
        public const string ALIASED_COL_${field.name?upper_case} = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case};
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
        public const string ALIASED_COL_${field.name?upper_case}_ID = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID;
            </#if>
        </#list>

        public const string TABLE_NAME = "${curr.name?cap_first}";

        public static string[] COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne" >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}_ID,
                </#if>
            </#if>
        </#list>
        };

        public static string[] ALIASED_COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? >
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case},
                </#if>
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_ID
                <#else>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}_ID,
                </#if>
            </#if>
        </#list>
        };
    }
}