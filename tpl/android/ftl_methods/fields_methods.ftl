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


<#function hasShowableFields fields>
	<#assign result = false />
	<#list fields as field>
		<#if (!field.hidden)>
			<#assign result = true />
		</#if>
	</#list>
	<#return result />
</#function>



<#-- Methods used to generate fields extractors, getter, setter, etc... -->

<#-- Generate the getter method call of the given field -->
<#function generateGetter field>
	<#if (field.type?lower_case == "boolean")>
		<#assign prefix = "is" />
	<#else>
		<#assign prefix = "get" />
	</#if>
	<#return prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter method call appended to object name -->
<#function generateCompleteGetter objectName field>
	<#if (field.type?lower_case == "boolean")>
		<#assign prefix = "is" />
	<#else>
		<#assign prefix = "get" />
	</#if>
	<#return objectName + "." + prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter of the given field, with its converter to string. -->
<#function generateStringGetter className field>
	<#if (field.type?lower_case == "datetime")>																		<#-- If datetime -->
		<#return generateCompleteGetter(className field) + ".toString(ISODateTimeFormat.dateTime())" />
	<#elseif (field.type?lower_case == "string")>																	<#-- If String (field) -->
		<#return generateCompleteGetter(className field) + ""/>
	<#elseif (field.type?lower_case == "boolean")>																	<#-- If boolean (field) -->
		<#return generateCompleteGetter(className field) + ""/>
	<#elseif (field.harmony_type?lower_case == "enum")>																<#-- If Enum (field.getValue() || field.name()) -->
		<#if (enums[field.type].id??) >
			<#return generateCompleteGetter(className field) + ".getValue()"/>
		<#else>
			<#return generateCompleteGetter(className field) + ".name()"/>
		</#if>
	<#else>																											<#-- For all other cases (String.valueOf(field)) -->
		<#return "String.valueOf(" + generateCompleteGetter(className field) + ")" />
	</#if>
</#function>




