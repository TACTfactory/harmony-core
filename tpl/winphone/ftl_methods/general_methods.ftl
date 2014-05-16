<#function isInArray array var>
	<#list array as item>
		<#if (item==var)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function getIndentString indentLevel = 0>
	<#assign t = "" />
	<#if (indentLevel > 0) >
		<#list 1..indentLevel as x>
			<#assign t = t + "    " />
		</#list> 
	</#if>
	<#return t />
</#function>
