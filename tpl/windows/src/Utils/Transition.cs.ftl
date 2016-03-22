<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

namespace ${project_namespace}.Utils.StateMachine
{
    /// <summary>
    /// ViewStateMachine transitions.
    /// </summary>
    public enum Transition
    {
        NullTransition = 0, // Use this transition to represent a non-existing transition in the system.
        Back = 1, // Back transtition use for navigation rollback.
        HomePage = 2,
        
        <#assign increment = 3 />
        <#list entities?values as entity>
        <#if !entity.internal >
        ${entity.name?cap_first}ListPage = ${increment}, // ${entity.name?cap_first} transition to list items page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePage = ${increment}, // ${entity.name?cap_first} transition to create item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPage = ${increment}, // ${entity.name?cap_first} transition to show item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPage = ${increment}, // ${entity.name?cap_first} transition to edit item page.
            <#assign increment = increment + 1 />
        #region ${entity.name?cap_first}State relations transitions.
        
        <#assign fields = ViewUtils.getAllFields(entity) />
            <#assign multi = false>
            <#assign mono = false>
            <#list fields?values as field >
                <#if field.relation??>
                    <#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany" >
                        <#assign multi = true>
                    <#else>
                        <#assign mono = true>
                    </#if>
                </#if>
            </#list>
            <#if multi>
        #region ${entity.name?cap_first} ManyToMany and OneToMany relations.
        
            <#list fields?values as field >
                <#if field.relation??>
        // CreateState.
        ${entity.name?cap_first}MultiCreateListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiCreateCreatePage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiCreateShowPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiCreateCheckListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiCreateShowEditPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
                    
        // ShowState.
        ${entity.name?cap_first}MultiShowListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiShowCreatePage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiShowShowPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiShowCheckListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiShowShowEditPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
                </#if>
            </#list>
        #endregion
        </#if>
        <#if mono>
        #region ${entity.name?cap_first} OneToOne and ManyToOne relations.
            <#list fields?values as field >
                <#if field.relation??>

        // CreateState.
        ${entity.name?cap_first}SoloCreateShowPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}SoloCreateEditPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}SoloCreateRadioListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
            
        // ShowState.
        ${entity.name?cap_first}SoloShowShowPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}SoloShowEditPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}SoloShowRadioListPage${field.relation.targetEntity?cap_first} = ${increment},
        <#assign increment = increment + 1 />  
                </#if>
            </#list>  
        #endregion    
        </#if>

        #endregion 
        </#if>
        </#list>
        
    }
}
