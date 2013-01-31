<#function typeToParser className field>
	<#if (field.type == "date" || field.type == "time" || field.type == "datetime")>
		<#return className?lower_case+".get"+field.name?cap_first+"().toString(ISODateTimeFormat.dateTime().withZoneUTC())" />
	<#elseif (field.type == "boolean")>
		<#return "String.valueOf("+className?lower_case+".is"+field.name?cap_first+"())"/>
	<#else>
		<#return "String.valueOf("+className?lower_case+".get"+field.name?cap_first+"())"/>
	</#if>
</#function>

<#function javaType type>
	<#if (type=="integer" || type=="int")>
		<#return "int" />
	<#else>
		<#return type />
	</#if>
</#function>
