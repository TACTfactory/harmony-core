<#function getAllIdsGetters entity>
    <#local result = [] />
    <#list entity.ids as id>
        <#if id.relation??>
            <#list getAllIdsGetters(entities[id.relation.targetEntity]) as realRefId>
                <#local result = result + [".get" + id.name?cap_first + "()" + realRefId] />
            </#list>
        <#else>
            <#local result = result + [".get" + id.name?cap_first + "()"] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getAllIdsGettersFromArray ids>
    <#local result = [] />
    <#list ids as id>
        <#if id.relation??>
            <#list IdsUtils.getAllIdsGetters(entities[id.relation.targetEntity]) as realId>
                <#local result = result + [id.name + realId] />
            </#list>
        <#else>
            <#local result = result + [id.name] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getAllIdsSetters entity>
    <#local result = [] />
    <#list entity.ids as id>
        <#if id.relation??>
            <#list getAllIdsSetters(id.relation.targetEntity) as realRefId>
                <#local result = result + [".get" + id.name?cap_first + "()" + realRefId] />
            </#list>
        <#else>
            <#local result = result + [".set" + id.name?cap_first + "("] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getAllIdsSettersFromArray ids>
    <#local result = [] />
    <#list ids as id>
        <#if id.relation??>
            <#list IdsUtils.getAllIdsSetters(entities[id.relation.targetEntity]) as realId>
                <#local result = result + [".get" + id.name?cap_first + "()" + realId] />
            </#list>
        <#else>
            <#local result = result + [".set" + id.name?cap_first + "("] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getAllIdsColsFromArray ids aliased=false>
    <#local result = [] />
    <#list ids as id>
        <#local result = result + ContractUtils.getFieldsNames(id, aliased) />
    </#list>
    <#return result />
</#function>

<#function getAllIdsTypesFromArray ids>
    <#local result = [] />
    <#list ids as id>
        <#if id.relation??>
            <#local result = result + getAllIdsTypesFromArray(entities[id.relation.targetEntity].ids) />
        <#else>
            <#local result = result + [FieldsUtils.getJavaType(id)] />
        </#if>
    </#list>
    <#return result />
</#function>

<#function getAllIdsNamesFromArray ids>
    <#local result = [] />
    <#list ids as id>
        <#if id.relation??>
            <#list getAllIdsNamesFromArray(entities[id.relation.targetEntity].ids) as refId>
                <#local result = result + [id.name + refId?cap_first] />
            </#list>
        <#else>
            <#local result = result + [id.name] />
        </#if>
    </#list>
    <#return result />
</#function>
