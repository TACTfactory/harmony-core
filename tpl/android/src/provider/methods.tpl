<#function typeToParser className field>
	<#if (field.type == "Date")>
		<#return "String.valueOf(dateFormat.format("+className?lower_case+".get"+field.name?cap_first+"()))">
	<#elseif (field.type == "boolean")>
		<#return "String.valueOf("+className?lower_case+".is"+field.name?cap_first+"())">
	<#elseif (field.type == "int") || (field.type == "integer")>
		<#return "String.valueOf("+className?lower_case+".get"+field.name?cap_first+"())">
	<#else>
		<#return "String.valueOf("+className?lower_case+".get"+field.name?cap_first+"())">
	</#if>
</#function>

<#function javaType type>
	<#if type=="integer" || type=="int">
		<#return "int">
	<#else>
		<#return type>
	</#if>
</#function>
