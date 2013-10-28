<#function getCompleteNamespace entity>
	<#assign result = "" />
	<#assign motherClasses = getAllOuter([], entity) />
	<#assign cond = true />
	<#list motherClasses as motherClass>
		<#assign result = result + motherClass.name />
		<#if motherClass_has_next>
			<#assign result = result + "." />
		</#if>
	</#list>
	<#return result>
</#function>
<#function getAllOuter tab entity>
	<#if entity.outerClass??>
		<#return (getAllOuter(tab, entities[entity.outerClass]) + [entity]) />
	<#else>
		<#return ([entity]) />
	</#if>
</#function>

<#function isExtended entity >
	<#return (entity.inheritance?? && entity.inheritance.superclass?? && entities[entity.inheritance.superclass.name]??) />
</#function>

<#function getAllChildren curr>
	<#assign result = [curr]>
	<#list entities?values as entity>
		<#if (entity.inheritance?? && entity.inheritance.superclass?? && entity.inheritance.superclass.name == curr.name)>
			<#assign result = result + [entity] />
		</#if>
	</#list>
	<#return result />
</#function>
