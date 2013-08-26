<#function getCompleteNamespace entity>
	<#assign result = "" />
	<#assign motherClasses = getAllMothers([], entity) />
	<#assign cond = true />
	<#list motherClasses as motherClass>
		<#assign result = result + motherClass.name />
		<#if motherClass_has_next>
			<#assign result = result + "." />
		</#if>
	</#list>
	<#return result>
</#function>
<#function getAllMothers tab entity>
	<#if entity.mother??>
		<#return (getAllMothers(tab, entities[entity.mother]) + [entity]) />
	<#else>
		<#return ([entity]) />
	</#if>
</#function>

<#function isExtended entity>
	<#return (entity.extends?? && entities[entity.extends]??) />
</#function>

<#function getAllChildren curr>
	<#assign result = [curr]>
	<#list entities?values as entity>
		<#if (entity.extends?? && entity.extends == curr.name)>
			<#assign result = result + [entity] />
		</#if>
	</#list>
	<#return result />
</#function>
