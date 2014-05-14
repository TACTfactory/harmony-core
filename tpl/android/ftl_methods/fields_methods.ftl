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
	<#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
		<#assign prefix = "is" />
	<#else>
		<#assign prefix = "get" />
	</#if>
	<#return prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter method call appended to object name -->
<#function generateCompleteGetter objectName field>
	<#if (FieldsUtils.getJavaType(field)?lower_case == "boolean")>
		<#assign prefix = "is" />
	<#else>
		<#assign prefix = "get" />
	</#if>
	<#return objectName + "." + prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter of the given field, with its converter to string. -->
<#function generateStringGetter className field>
	<#local result = "" />
	<#switch FieldsUtils.getJavaType(field)?lower_case>
		<#case "datetime">
			<#local result = generateCompleteGetter(className field) + ".toString(ISODateTimeFormat.dateTime())" />
			<#break />
		<#case "string">
			<#local result = generateCompleteGetter(className field) + ""/>
			<#break />
		<#case "boolean">
			<#local result = generateCompleteGetter(className field) + ""/>
			<#break />
		<#case "enum">
			<#if (enums[field.enum.targetEnum].id??) >
				<#local result = generateCompleteGetter(className field) + ".getValue()"/>
			<#else>
				<#local result = generateCompleteGetter(className field) + ".name()"/>
			</#if>
			<#break />
		<#default>
			<#local result = "String.valueOf(" + generateCompleteGetter(className field) + ")" />
			<#break />
	</#switch>
	<#return result />
</#function>

<#function getStringParser type varName>
	<#local result = "" />
	<#if type?lower_case == "int" || type?lower_case == "integer">
		<#local result = "Integer.parseInt(${varName})" />
	<#else>
		<#local result = "${varName}" />
	</#if>
	<#return result />
</#function>

<#-- Returns the java type of a given field. -->
<#function getJavaType field>
	<#local result = field.harmony_type />
	<#switch field.harmony_type?lower_case>
		<#case "enum">
			<#local result = "enum" />
			<#break />
		<#case "relation">
			<#local result = field.relation.targetEntity />
			<#break />
		<#case "email">
		<#case "phone">
		<#case "city">
		<#case "country">
		<#case "ean">
		<#case "string">
		<#case "text">
		<#case "login">
		<#case "password">
			<#local result = "String" />
			<#break />
		<#case "boolean">
			<#local result = "boolean" />
			<#break />
		<#case "int">
		<#case "zipcode">
			<#local result = "int" />
			<#break />
		<#case "short">
			<#local result = "short" />
			<#break />
		<#case "byte">
			<#local result = "byte" />
			<#break />
		<#case "char">
			<#local result = "char" />
			<#break />
		<#case "long">
			<#local result = "long" />
			<#break />
		<#case "float">
			<#local result = "float" />
			<#break />
		<#case "double">
			<#local result = "double" />
			<#break />
		<#case "datetime">
		<#case "date">
		<#case "time">
			<#local result = "DateTime" />
			<#break />
	</#switch>
	<#return result />
</#function>

