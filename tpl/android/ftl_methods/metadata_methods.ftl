<#function getMappedField field>
	<#assign ref_entity = entities[field.relation.targetEntity] />
	<#list ref_entity.fields?values as ref_field>
		<#if ref_field.name == field.relation.mappedBy>
			<#return ref_field />
		</#if>
	</#list>
</#function>

<#function hasRelationOrIds entity>
	<#if (entity.ids?size>1)><#return true /></#if>
	<#list entity.relations as relation>
		<#if (relation.relation.type!="OneToMany" && relation.relation.type!="ManyToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function getInversingField field>
	<#assign entityT = entities[field.relation.targetEntity] />
	<#list entityT.relations as f>
		<#if field.relation.inversedBy??>
			<#if f.name == field.relation.inversedBy>
				<#return f />
			</#if>
		<#elseif field.relation.mappedBy??>
			<#if f.name == field.relation.mappedBy>
				<#return f />
			</#if>
		</#if>
	</#list>
</#function>

<#function hasFieldType entity fieldType>	
	<#list entity.fields?values as field>
		<#if field.harmony_type?lower_case==fieldType?lower_case>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasTime entity>	
	<#return hasFieldType(entity, "time") />
</#function>

<#function hasDate entity>	
	<#return hasFieldType(entity, "date") />
</#function>

<#function hasDateTime entity>	
	<#return hasFieldType(entity, "datetime") />
</#function>

<#function hasLocaleTime entity>
	<#assign hasLocaleTime = false />
	<#list entity.fields?values as field>
		<#if field.is_locale?? && field.is_locale>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
