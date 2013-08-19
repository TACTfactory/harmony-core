<#function javaType type>
	<#if (type=="integer" || type=="int")>
		<#return "int" />
	<#else>
		<#return type />
	</#if>
</#function>
