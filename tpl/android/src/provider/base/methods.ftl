<#function typeToParser className field>
	<#if (field.type?lower_case == "datetime")>
		<#if (field.harmony_type == "date" || field.harmony_type == "time" || field.harmony_type == "datetime")>
			<#if field.is_locale>
				<#return className?lower_case+".get"+field.name?cap_first+"().toLocalDateTime().toString()" />	
			<#else>
				<#return className?lower_case+".get"+field.name?cap_first+"().toString(ISODateTimeFormat.dateTime())" />
			</#if>
		</#if>			
	<#elseif (field.type?lower_case == "boolean")>
		<#return "String.valueOf("+className?lower_case+".is"+field.name?cap_first+"())"/>
	<#elseif (field.type?lower_case == "int" || field.type?lower_case == "integer" || field.type?lower_case == "double" || field.type?lower_case == "short" || field.type?lower_case == "long" || field.type?lower_case == "byte" || field.type?lower_case == "char" || field.type?lower_case == "float" || field.type?lower_case == "character")>
		<#return "String.valueOf("+className?lower_case+".get"+field.name?cap_first+"())"/>
	<#elseif (field.type?lower_case == "string")>
		<#return className?lower_case+".get"+field.name?cap_first+"()"/>
	<#else>
		<#return className?lower_case+".get"+field.name?cap_first+"().getValue()"/>
	</#if>
</#function>

<#function javaType type>
	<#if (type=="integer" || type=="int")>
		<#return "int" />
	<#else>
		<#return type />
	</#if>
</#function>
