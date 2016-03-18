<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

using ${project_namespace}.Provider.Contract.Base;

namespace ${project_namespace}.Provider.Contract
{
    public abstract class ${curr.name?cap_first}Contract : ${curr.name?cap_first}ContractBase
    {
        <#list curr_fields as field>
            <#if !field.relation?? || field.relation.type == "ManyToOne" || field.relation.type == "OneToOne">
        public const string COL_${field.name?upper_case} = "${field.name?lower_case}";
            </#if>
        </#list>
        
        public const string TABLE_NAME = "${curr.name?cap_first}";
    }
}