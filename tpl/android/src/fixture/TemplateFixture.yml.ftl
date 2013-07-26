<#assign curr = entities[current_entity]>
${curr.name}:
#   ${curr.name}_name:
<#list curr.fields?values as field>
	<#if !field.internal>
#      ${field.name}:
	</#if>
</#list>
