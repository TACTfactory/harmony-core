<#assign utilityPath = "/tpl/android/ftl_methods/" />
<#import utilityPath + "general_methods.ftl" as Utils />
<#import utilityPath + "inheritance_methods.ftl" as InheritanceUtils />

<#function importRelatedSQLiteAdapters entity importInternalsToo=true importSelfToo=true importChilds=true>
    <#assign result = ""/>
    <#if importSelfToo>
        <#assign import_array = [entity.name] />
    <#else>
        <#assign import_array = [] />
    </#if>
    <#if InheritanceUtils.isExtended(entity)>
        <#assign import_array = import_array + [entity.inheritance.superclass.name] />
    </#if>
    <#if importChilds && entity.inheritance?? && entity.inheritance.subclasses??>
        <#list entity.inheritance.subclasses as subclass>
            <#assign import_array = import_array + [subclass.name] />
        </#list>
    </#if>
    <#list ViewUtils.getAllRelations(entity) as relation>
        <#if importInternalsToo || !relation.internal>
            <#if relation.relation.type == "ManyToMany">
                <#if (!Utils.isInArray(import_array, relation.relation.joinTable))>
                    <#assign import_array = import_array + [relation.relation.joinTable] />
                </#if>
            </#if>
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
                <#if entities[relation.relation.targetEntity].inheritance?? && entities[relation.relation.targetEntity].inheritance.superclass?? && entities[entities[relation.relation.targetEntity].inheritance.superclass.name]??>
                    <#assign import_array = import_array + [entities[relation.relation.targetEntity].inheritance.superclass.name] />
                </#if>
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
                <#assign result = result + "#import \"${import}SQLiteAdapter.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedContracts entity importInternalsToo=true importSelfToo=true importChilds=true>
    <#assign result = ""/>
    <#if importSelfToo>
        <#assign import_array = [entity.name] />
    <#else>
        <#assign import_array = [] />
    </#if>
    <#if InheritanceUtils.isExtended(entity)>
        <#assign import_array = import_array + [entity.inheritance.superclass.name] />
    </#if>
    <#if importChilds && entity.inheritance?? && entity.inheritance.subclasses??>
        <#list entity.inheritance.subclasses as subclass>
            <#assign import_array = import_array + [subclass.name] />
        </#list>
    </#if>
    <#list ViewUtils.getAllRelations(entity) as relation>
        <#if importInternalsToo || !relation.internal>
            <#if relation.relation.type == "ManyToMany">
                <#if (!Utils.isInArray(import_array, relation.relation.joinTable))>
                    <#assign import_array = import_array + [relation.relation.joinTable] />
                </#if>
            </#if>
            
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
                <#if entities[relation.relation.targetEntity].inheritance?? && entities[relation.relation.targetEntity].inheritance.superclass?? && entities[entities[relation.relation.targetEntity].inheritance.superclass.name]??>
                    <#assign import_array = import_array + [entities[relation.relation.targetEntity].inheritance.superclass.name] />
                </#if>
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
                <#assign result = result + "#import \"${import}Contract.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedEntities entity useInheritedFieldsToo=false>
    <#assign result = ""/>
    <#if entity.internal>
        <#assign import_array = [] />
    <#else>
        <#assign import_array = [entity.name] />
    </#if>
    <#if useInheritedFieldsToo>
        <#assign fields = ViewUtils.getAllFields(entity)?values />
    <#else>
        <#assign fields = entity.relations />
    </#if>
    <#list fields as relation>
        <#if relation.relation?? && !relation.internal>
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
        <#assign result = result + "#import \"${import}.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedEntitiesHeader entity useInheritedFieldsToo=false>
    <#assign result = ""/>
    <#if entity.internal>
        <#assign import_array = [] />
    <#else>
        <#assign import_array = [entity.name] />
    </#if>
    <#if useInheritedFieldsToo>
        <#assign fields = ViewUtils.getAllFields(entity)?values />
    <#else>
        <#assign fields = entity.relations />
    </#if>
    <#list fields as relation>
        <#if relation.relation?? && !relation.internal>
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
        <#if (import != entity.name)>
        <#assign result = result + "@class ${import};" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
        </#if>
    </#list>
    <#return result />
</#function>

<#function importToManyRelatedEntities entity>
    <#assign result = ""/>
    <#assign import_array = [entity.name] />
    <#list ViewUtils.getAllRelations(entity) as relation>
        <#if !relation.internal && (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") >
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
        <#assign result = result + "#import \"${import}.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedEnums entity useInheritedFieldsToo=true>
    <#assign result = ""/>
    <#assign import_array = [entity.name] />
    <#if useInheritedFieldsToo>
        <#assign fields = ViewUtils.getAllFields(entity)?values />
    <#else>
        <#assign fields = entity.fields?values />
    </#if>
    <#list fields as field>
        <#if field.harmony_type?lower_case == "enum">
            <#if (!Utils.isInArray(import_array, field.enum.targetEnum)) >
                <#assign import_array = import_array + [field.enum.targetEnum] />
                <#assign enumClass = enums[field.enum.targetEnum] />
                <#assign result = result + "#import \"${InheritanceUtils.getCompleteNamespace(enumClass)}.h\";\n" />
            </#if>
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedProviderUtils entity useInheritedFieldsToo=false>
    <#assign result = ""/>
    <#assign import_array = [entity.name] />
    <#if useInheritedFieldsToo>
        <#assign fields = ViewUtils.getAllFields(entity)?values />
    <#else>
        <#assign fields = ViewUtils.getAllRelations(entity) />
    </#if>
    <#list fields as field>
        <#if (field.relation?? && !Utils.isInArray(import_array, field.relation.targetEntity?cap_first) && !field.internal) >
            <#assign import_array = import_array + [field.relation.targetEntity?cap_first] />
        </#if>
    </#list>
    <#list import_array as import>
            <#assign result = result + "#import \"${import}ProviderUtils.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedProviderAdapters entity importInternalsToo=true>
    <#assign result = ""/>
    <#assign import_array = [entity.name] />
    <#list ViewUtils.getAllRelations(entity) as relation>
        <#if importInternalsToo || !relation.internal>
            <#if relation.relation.type == "ManyToMany">
                <#if (!Utils.isInArray(import_array, relation.relation.joinTable))>
                    <#assign import_array = import_array + [relation.relation.joinTable] />
                    <#assign import_array = import_array + [relation.relation.targetEntity] />
                </#if>
            <#else>
                <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                    <#assign import_array = import_array + [relation.relation.targetEntity] />
                </#if>
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
            <#assign result = result + "#import \"${import}ProviderAdapter.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importManyToManyTargetSQLiteAdapters entity>
    <#assign result = ""/>
    <#assign import_array = [] />
    <#list ViewUtils.getAllRelations(entity) as relation>
        <#if relation.relation.type == "ManyToMany">
            <#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
                <#assign import_array = import_array + [relation.relation.targetEntity] />
            </#if>
        </#if>
    </#list>
    <#list import_array as import>
            <#assign result = result + "#import \"${import}SQLiteAdapter.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

<#function importRelatedCriterias entity>
    <#assign result = ""/>
    <#assign import_array = []/>
    <#if (MetadataUtils.hasToManyRelations(entity))>
        <#assign import_array = import_array + ["base.Criterion"] />
        <#assign import_array = import_array + ["base.Criterion.Type"] />
        <#assign import_array = import_array + ["base.value.ArrayValue"] />
    </#if>
    <#assign import_array = import_array + ["base.CriteriaExpression"] />
    <#assign import_array = import_array + ["base.CriteriaExpression.GroupType"] />
    <#list import_array as import>
            <#assign result = result + "#import \"${import}.h\"" />
        <#if import_has_next>
            <#assign result = result + "\n" />
        </#if>
    </#list>
    <#return result />
</#function>

