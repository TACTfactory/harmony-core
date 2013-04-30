<#assign curr = entities[current_entity]>
${curr.name}:
#   ${curr.name}_name:
<#list curr.fields as field>
	<#if !field.internal>
#      ${field.name}:
	</#if>
</#list>
