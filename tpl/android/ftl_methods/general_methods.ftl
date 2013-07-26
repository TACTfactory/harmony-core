<#function isInArray array var>
	<#list array as item>
		<#if (item==var)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
