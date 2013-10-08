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

<#function hasRelations entity>
	<#return (entity.relations?? && entity.relations?size > 0) />
</#function>

<#function hasToManyRelations entity>
	<#list entity.relations as field>
		<#if (field.relation.type == "ManyToMany" || field.relation.type == "OneToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasToOneRelations entity>
	<#list entity.relations as field>
		<#if (field.relation.type == "ManyToOne" || field.relation.type == "OneToOne")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasOnlyRecursiveRelations entity>
	<#list entity.relations as relation>
		<#if relation.relation.targetEntity!=entity.name>
			<#return false>
		</#if>
	</#list>
	<#return true>
</#function>
<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if (entity.fields?size!=0 && hasOnlyRecursiveRelations(entity))>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !Utils.isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>
			<#if (entity.fields?size>0)>
				<#if !Utils.isInArray(ret, entity.name)>
					<#if isOnlyDependantOf(entity, ret)>
						<#assign ret = ret + [entity.name] />
					</#if>
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>

<#function isPrimitive field>
	<#if (field.type == "int" ||
			field.type == "double" ||
			field.type == "long" ||
			field.type == "float" ||
			field.type == "char" ||
			field.type == "byte" ||
			field.type == "boolean" ||
			field.type == "short")>
		<#return true />
	<#else>
		<#return false />
	</#if>
</#function>

<#function hasManyToOneRelation entity>
	<#list entity.relations as relation>
		<#if relation.relation.type == "ManyToOne">
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasOneToManyRelation entity>
	<#list entity.relations as relation>
		<#if relation.relation.type == "OneToMany">
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasManyToManyRelation entity>
	<#list entity.relations as relation>
		<#if relation.relation.type == "ManyToMany">
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasOneToOneRelation entity>
	<#list entity.relations as relation>
		<#if relation.relation.type == "OneToOne">
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
