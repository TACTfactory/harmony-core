<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity]>
${curr.name}:
#   ${curr.name}_name:
<#list ViewUtils.getAllFields(curr)?values as field>
	<#if !field.internal>
#      ${field.name}:
	</#if>
</#list>
