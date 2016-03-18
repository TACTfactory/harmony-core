<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

namespace ${project_namespace}.Provider.Contract.Base
{
    public abstract class ${curr.name?cap_first}ContractBase
    {
        <#list curr_fields as field>
            <#if !field.relation?? || field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            </#if>
        </#list>

        <#list curr_fields as field>
            <#if !field.relation?? || field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        public const string ALIASED_COL_${field.name?upper_case} = ${curr.name?cap_first}Contract.TABLE_NAME + "." + ${curr.name?cap_first}Contract.COL_${field.name?upper_case};
            </#if>
        </#list>

        public const string TABLE_NAME = "${curr.name?cap_first}";

        public static string[] COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? || field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                <#if field?is_last>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case},
                </#if>
            </#if>
        </#list>
        };

        public static string[] ALIASED_COLS = {
        <#list curr_fields as field>
            <#if !field.relation?? || field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
                <#if field?is_last>
            ${curr.name?cap_first}Contract.ALIASED_COL_${field.name?upper_case}
                <#else>
            ${curr.name?cap_first}Contract.COL_${field.name?upper_case},
                </#if>
            </#if>
        </#list>
        };
    }
}