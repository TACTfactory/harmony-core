<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

using ${project_namespace}.Provider.Contract.Base;

namespace ${project_namespace}.Provider.Contract
{
    /// <summary>
    /// ${curr.name?cap_first} contract for SQLite database mapping.
    /// This can be override if you need to setup modifications.
    /// </summary>
    public abstract class ${curr.name?cap_first}Contract : ${curr.name?cap_first}ContractBase
    {
        public const string COL_${curr.name?upper_case}_ID = "${curr.name?lower_case}_id";
        <#list curr_fields as field>
            <#assign test = field.name?uncap_first />
            <#if field.name == test>
            <#if !field.relation?? >
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        public const string COL_${field.name?upper_case}_ID = "${field.name?lower_case}";
            </#if>
            </#if>
        </#list>

        public const string TABLE_NAME = "${curr.name?cap_first}";
    }
}
