<#function isPrimitive field>
	<#assign primitiveTab = ["int", "double", "float", "boolean", "short", "byte", "char", "long"] />
	<#return primitiveTab?seq_contains(field.type) />
</#function>
<#assign curr = enums[current_entity] />
<#assign equal = ".equals" />
<#if (isPrimitive(curr.fields[curr.id])) >
	<#assign equal = " == " />
</#if>
${indentLevel}/** Retrieve an enum constant given a certain id.
${indentLevel} * @param value The enum id
${indentLevel} * @return The enum constant matching the given value
${indentLevel} */
${indentLevel}public static ${curr.name} fromValue(${curr.fields[curr.id].type} value) {
${indentLevel}    ${curr.name} result = null;
		<#list curr.names as enumName>
${indentLevel}    if (${enumName?upper_case}.getValue()${equal}(value)) {
${indentLevel}    	result = ${enumName?upper_case};
${indentLevel}    } <#if enumName_has_next> else</#if>
		</#list>
${indentLevel}     return result;
${indentLevel}}
