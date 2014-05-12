<#function getFieldType field>
    <#if (field.harmony_type == "string")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "text")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "boolean")>
        <#assign result = "Boolean" />
    <#elseif (field.harmony_type == "integer")>
        <#assign result = "Int32" />
    <#elseif (field.harmony_type == "int")>
        <#assign result = "Int32" />
    <#elseif (field.harmony_type == "short")>
        <#assign result = "Int16" />
    <#elseif (field.harmony_type == "byte")>
        <#assign result = "Byte" />
    <#elseif (field.harmony_type == "char")>
        <#assign result = "Char" />
    <#elseif (field.harmony_type == "character")>
        <#assign result = "Char" />
    <#elseif (field.harmony_type == "long")>
        <#assign result = "Int64" />
    <#elseif (field.harmony_type == "float")>
        <#assign result = "Single" />
    <#elseif (field.harmony_type == "double")>
        <#assign result = "Double" />
    <#elseif (field.harmony_type == "datetime")>
        <#assign result = "DateTime" />
    <#elseif (field.harmony_type == "date")>
        <#assign result = "DateTime" />
    <#elseif (field.harmony_type == "time")>
        <#assign result = "DateTime" />
    <#elseif (field.harmony_type == "login")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "password")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "email")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "phone")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "city")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "zipcode")>
        <#assign result = "Int32" />
    <#elseif (field.harmony_type == "country")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "ean")>
        <#assign result = "String" />
    <#elseif (field.harmony_type == "enum")>
        <#assign result = field.type />
    <#elseif (field.relation??)>
        <#assign result = field.relation.targetEntity />
    <#else>
        <#assign result = "FAILED" />
    </#if>
    
    <#if (field.isIterable)>
        <#assign result = "List<" + result + ">" />
    </#if>
    
    <#return result />
</#function>

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

<#function getStringParser type varName>
	<#local result = "" />
	<#if type?lower_case == "int" || type?lower_case == "integer">
		<#local result = "Integer.parseInt(${varName})" />
	<#else>
		<#local result = "${varName}" />
	</#if>
	<#return result />
</#function>


