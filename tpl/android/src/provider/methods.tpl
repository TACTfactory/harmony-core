<#function typeToParser className field>
	<#if (field.type == "date" || field.type == "time" || field.type == "datetime")>
		<#return className?lower_case+".get"+field.name?cap_first+"().toString()">
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

<#function setJsonLoader name field>
	<#assign type=field.type>
	<#assign ret=name?lower_case>
	<#if type=="boolean">
		<#assign ret=ret+".setChecked(this.model.is"+field.name?cap_first+"());">
	<#else>
		<#assign getter="this.model.get"+field.name?cap_first+"()">
		<#assign ret=ret+".setText(">
		<#if type=="string" || type=="email" || type=="login" || type=="password" || type=="city" || type=="text" || type=="phone" || type=="country">
			<#assign ret=ret+getter>
		<#elseif type=="datetime" || type=="date" || type=="time">
			<#assign ret=ret+getter+".toString()">
		<#elseif type == "int" || type=="long" || type=="ean" || type=="zipcode" || type=="float">
			<#assign ret=ret+"String.valueOf("+getter+")"> 
		</#if>
		<#assign ret=ret+");">
	</#if>
	<#return ret>
</#function>
