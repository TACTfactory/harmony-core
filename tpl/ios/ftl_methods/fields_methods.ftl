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
    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "boolean")>
        <#assign prefix = "is" />
    <#else>
        <#assign prefix = "get" />
    </#if>
    <#return prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter method call appended to object name -->
<#function generateCompleteGetter objectName field>
    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "boolean")>
        <#assign prefix = "is" />
    <#else>
        <#assign prefix = "get" />
    </#if>
    <#return objectName + "." + prefix + field.name?cap_first + "()" />
</#function>

<#-- Generate the getter of the given field, with its converter to string. -->
<#function generateStringGetter className field>
    <#local result = "" />
    <#switch FieldsUtils.getObjectiveType(field)?lower_case>
        <#case "datetime">
            <#local result = generateCompleteGetter(className field) + "isoStringToDate:dateTime())" />
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

<#-- Generate the getter of the given field, with its converter to string. -->
<#function generateFieldContentType className field>
    <#local result = "" />
    <#switch FieldsUtils.getObjectiveType(field)?lower_case>
        <#case "datetime">
            <#local result = "[DateUtils dateToISOString:" />
            <#break />
        <#case "string">
            <#local result = ""/>
            <#break />
        <#case "boolean">
            <#local result = "[NSNumber numberWithBool:"/>
            <#break />
        <#case "nsinteger">
            <#local result = "[NSNumber numberWithInteger:"/>
            <#break />
        <#case "zipcode">
        <#case "int">
        <#case "nsnumber">
            <#local result = "[NSNumber numberWithInt:"/>
            <#break />
        <#case "double">
            <#local result = "[NSNumber numberWithDouble:" />
            <#break />
        <#case "short">
            <#local result = "[NSNumber numberWithShort:" />
            <#break />
        <#case "char">
        <#case "character">
            <#local result = "[NSString stringWithFormat:@\"%c\"," />
            <#break />
        <#default>
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

<#-- Returns the objective c type of a given field. -->
<#function getObjectiveType field>
    <#local result = field.harmony_type />
    <#switch field.harmony_type?lower_case>
        <#case "enum">
            <#local result = "int" />
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
            <#if (field.nullable)>
                <#local result = "NSNumber" />
            <#else>
                <#local result = "int" />
            </#if>
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
        <#case "character">
            <#local result = "character" />
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

<#function convertToObjectiveType field>
    <#local result = field.harmony_type />
    <#switch field.harmony_type?lower_case>
        <#case "enum">
            <#local result = "enum" />
            <#break />
        <#case "collection">
            <#local result = "NSArray" />
            <#break />
        <#case "relation">
            <#if (field.relation.type!="OneToMany") && (field.relation.type!="ManyToMany")>
            <#local result = field.relation.targetEntity />
            <#else>
            <#local result = "NSArray" />
            </#if>
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
            <#local result = "NSString" />
            <#break />
        <#case "boolean">
            <#local result = "bool" />
            <#break />
        <#case "int">
        <#case "zipcode">
            <#if field.primitive>
                <#local result = "int" />
            <#else>
                <#local result = "NSInteger" />
            </#if>
            <#break />
        <#case "short">
            <#local result = "short" />
            <#break />
        <#case "byte">
            <#local result = "Byte" />
            <#break />
        <#case "char">
            <#local result = "char" />
            <#break />
        <#case "long">
            <#local result = "long long" />
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
            <#local result = "NSDate" />
            <#break />
    </#switch>
    <#return result />
</#function>

