<#function typeToParser className field>
	<#if (field.type == "date" || field.type == "time" || field.type == "datetime")>
		<#if field.is_locale>
			<#return className?lower_case+".get"+field.name?cap_first+"().toLocalDateTime().toString()" />	
		<#else>
			<#return className?lower_case+".get"+field.name?cap_first+"().toString(ISODateTimeFormat.dateTime().withZoneUTC())" />
		</#if>
	<#elseif (field.type == "boolean")>
		<#return "String.valueOf("+className?lower_case+".is"+field.name?cap_first+"())"/>
	<#elseif (field.type == "int" || field.type == "integer")>
		<#return className?lower_case+".get"+field.name?cap_first+"()"/>
	<#elseif (field.type == "string")>
		<#return className?lower_case+".get"+field.name?cap_first+"()"/>
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
