<#function getColumnsNames field>
    <#assign result = [] />
    <#if field.relation??>
        <#list entities[field.relation.targetEntity].ids as id>
            <#assign result = result + [field.name + "_" + id.name] />
        </#list>
    <#else>
        <#assign result = result + [field.name] />
    </#if>
    <#return result />
</#function>

<#function getFieldsNames field aliased=false>
    <#assign columnNames = ContractUtils.getColumnsNames(field) />
    <#assign result = [] />
    <#list columnNames as name>
        <#if aliased>
            <#assign result = result + [ContractUtils.getContractClass(entities[field.owner]) + ".ALIASED_" + NamingUtils.alias(name)] />
        <#else>
            <#assign result = result + [ContractUtils.getContractClass(entities[field.owner]) + "." + NamingUtils.alias(name)] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getFieldsDeclarationsHeader field entity>
    <#assign fieldsNames = ContractUtils.getColumnsNames(field) />
    <#assign result = "" />
    <#list fieldsNames as fieldName>
        <#assign result = result + "/** ${fieldName}. */\n" />
        <#assign result = result + "+ (NSString*) ${NamingUtils.alias(fieldName)};\n" />
        <#assign result = result + "/** Alias. */\n" />
        <#assign result = result + "+ (NSString*) ALIASED_${NamingUtils.alias(fieldName)};" />
    </#list>
    <#return result />
</#function>

<#function getFieldsStaticDeclarations field entity>
    <#assign fieldsNames = ContractUtils.getColumnsNames(field) />
    <#assign result = "" />
    <#list fieldsNames as fieldName>
        <#assign result = result + "static NSString* ${NamingUtils.alias(fieldName)};\n" />
        <#assign result = result + "static NSString* ALIASED_${NamingUtils.alias(fieldName)};\n" />
    </#list>
    <#return result />
</#function>

<#function getFieldsGetter field entity>
    <#if field.columnName??>
        <#assign fieldsNames = ContractUtils.getColumnsNames(field) />
        <#assign result = "" />
        <#list fieldsNames as fieldName>
            <#assign result = result + "/** ${fieldName}. */\n" />
            <#assign result = result + "+ (NSString*) ${NamingUtils.alias(fieldName)} {\n" />
            <#assign result = result + "    return ${NamingUtils.alias(fieldName)};\n" />
            <#assign result = result + "}\n\n"/>
            <#assign result = result + "/** Alias. */\n" />
            <#assign result = result + "+ (NSString*) ALIASED_${NamingUtils.alias(fieldName)} {\n" />
            <#assign result = result + "    return ALIASED_${NamingUtils.alias(fieldName)};\n" />
            <#assign result = result + "}\n" />
        </#list>
    </#if>
    <#return result />
</#function>

<#function getContractFieldInit field entity>
    <#assign fieldsNames = ContractUtils.getColumnsNames(field) />
    <#assign result = "" />
    <#list fieldsNames as fieldName>
        <#assign result = result + "    ${NamingUtils.alias(fieldName)} =" />
                <#if field.relation??>
                    <#assign result = result + "@\"${field.columnName}_${field.relation.field_ref[fieldName_index].name}\";\n"/>
                <#else>
                    <#assign result = result + "@\"${field.columnName}\";\n" />
                </#if>
        <#assign result = result + "    ALIASED_${NamingUtils.alias(fieldName)} = [NSString stringWithFormat:@\"%@.%@\", TABLE_NAME, ${NamingUtils.alias(fieldName)}];\n" />
    </#list>
    <#return result />
</#function>

<#function getContractCols entity aliased=false>
    <#if aliased>
        <#return ContractUtils.getContractClass(entity) + ".ALIASED_COLS" />
    <#else>
        <#return ContractUtils.getContractClass(entity) + ".COLS" />
    </#if>
</#function>

<#function getContractTableName entity>
    <#return ContractUtils.getContractClass(entity) + ".TABLE_NAME" />
</#function>

<#function getContractCol field aliased=false>
    <#if aliased>
        <#return ContractUtils.getContractClass(entities[field.owner]) + ".ALIASED_COL_${field.name?upper_case}" />
    <#else>
        <#return ContractUtils.getContractClass(entities[field.owner]) + ".COL_${field.name?upper_case}" />
    </#if>
</#function>

<#function getContractClass entity>
    <#return "${entity.name}Contract" />
</#function>

<#function getContractItemToContentValues entity>
    <#return ContractUtils.getContractClass(entity) + " itemToContentValues" />
</#function>

<#function getContractCursorToItem entity>
    <#return ContractUtils.getContractClass(entity) + " cursorToItem" />
</#function>

<#function getContractParcel entity>
    <#return ContractUtils.getContractClass(entity) + ".PARCEL" />
</#function>

<#function getContractColId entity>
    <#assign result = "" />
    <#assign count = 0 />

    <#if entity.inheritance?? && entity.inheritance.superclass??
            && entity.inheritance.superclass.name != entity.name
            && InheritanceUtils.isExtended(entity)>
        <#assign result = ContractUtils.getContractColId(entity.inheritance.superclass) />
    <#else>
        <#list entity.ids as id>
            <#assign result = ContractUtils.getContractClass(entity) + ".COL_" + id.name?upper_case />
            <#assign count = count + 1 />
        </#list>
    </#if>

    <#if (count > 1)>
        <#assign result = "" />
    </#if>

    <#return result />
</#function>

