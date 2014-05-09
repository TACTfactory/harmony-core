<#include utilityPath + "all_imports.ftl" />
<#assign curr = enums[current_entity] />
<#assign equal = ".equals" />
<#if (curr.fields[curr.id].primitive) >
	<#assign equal = " == " />
</#if>
${indentLevel}/** Retrieve an enum constant given a certain id.
${indentLevel} * @param value The enum id
${indentLevel} * @return The enum constant matching the given value
${indentLevel} */
${indentLevel}public static ${curr.name} fromValue(${FieldsUtils.getJavaType(curr.fields[curr.id])} value) {
${indentLevel}    ${curr.name} result = null;
		<#list curr.names as enumName>
${indentLevel}    if (${enumName?upper_case}.getValue()${equal}(value)) {
${indentLevel}    	result = ${enumName?upper_case};
${indentLevel}    } <#if enumName_has_next> else</#if>
		</#list>
${indentLevel}     return result;
${indentLevel}}
