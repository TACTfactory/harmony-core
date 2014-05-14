<#assign curr = enums[current_entity] />
<@header?interpret />

using System;

namespace ${project_namespace}.Entity
{
    public enum ${curr.name}
    {
        <#list curr.names as field>
        ${field} = 0,
        </#list>
    }
}