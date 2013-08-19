<#function hasManyToOneRelation fields>
	<#list fields as field>
		<#if (field.relation?? && field.relation.type == "ManyToOne")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasOneToManyRelation fields>
	<#list fields as field>
		<#if (field.relation?? && field.relation.type == "OneToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasManyToManyRelation fields>
	<#list fields as field>
		<#if (field.relation?? && field.relation.type == "ManyToMany")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasOneToOneRelation fields>
	<#list fields as field>
		<#if (field.relation?? && field.relation.type == "OneToOne")>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasToManyRelations fields>
	<#return hasOneToManyRelation(fields) || hasManyToManyRelation(fields) />
</#function>

<#function hasFieldType fields fieldType>
	<#list fields as field>
		<#if field.harmony_type?lower_case==fieldType?lower_case>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>

<#function hasTime fields>
	<#return hasFieldType(fields, "time") />
</#function>

<#function hasDate fields>
	<#return hasFieldType(fields, "date") />
</#function>

<#function hasDateTime fields>
	<#return hasFieldType(fields, "datetime") />
</#function>

<#function hasRelations fields>
	<#list fields as field>
		<#if (field.relation??)>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
