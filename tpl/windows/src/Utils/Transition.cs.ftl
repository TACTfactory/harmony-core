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
        HomePage = 1,

        <#assign increment = 2 />
        <#list entities?values as entity>
        <#if !entity.internal >
            <#assign fields = ViewUtils.getAllFields(entity) />
            <#assign multi = false>
            <#assign mono = false>
            <#list fields?values as field >
                <#if field.relation??>
                    <#if field.relation.type == "ManyToMany" >
                        <#assign multi = true>
                    <#elseif field.relation.type == "ManyToOne" || field.relation.type == "OneToMany" >
                        <#assign multi = true>
                        <#assign mono = true>
                    <#else>
                        <#assign mono = true>
                    </#if>
                </#if>
            </#list>
        ${entity.name?cap_first}ListPage = ${increment}, // ${entity.name?cap_first} transition to list items page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePage = ${increment}, // ${entity.name?cap_first} transition to create item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPage = ${increment}, // ${entity.name?cap_first} transition to show item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPage = ${increment}, // ${entity.name?cap_first} transition to edit item page.
            <#assign increment = increment + 1 />

            <#if multi>
        ${entity.name?cap_first}CheckListPage = ${increment}, // ${entity.name?cap_first} transition to check list item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CheckListPageBack = ${increment},
            <#assign increment = increment + 1 />
            </#if>

            <#if mono>
        ${entity.name?cap_first}RadioListPage = ${increment}, // ${entity.name?cap_first} transition to check list item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}RadioListPageBack = ${increment}, // ${entity.name?cap_first} transition to check list item page.
            <#assign increment = increment + 1 />
            </#if>

        ${entity.name?cap_first}ListPageBack = ${increment},
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePageBack = ${increment},
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPageBack = ${increment},
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPageBack = ${increment},
            <#assign increment = increment + 1 />

        ${entity.name?cap_first}HomePageBack = ${increment},
            <#assign increment = increment + 1 />

        #region ${entity.name?cap_first}State relations transitions.
            <#if multi>
        #region ${entity.name?cap_first} ManyToMany and OneToMany relations.
            <#list fields?values as field >
                <#if field.relation??>
        ${entity.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}ListPage = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}CreatePage = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}ShowPage = ${increment},
        <#assign increment = increment + 1 />
        ${field.relation.targetEntity?cap_first}MultiTo${entity.name?cap_first}ListPageBack = ${increment},
        <#assign increment = increment + 1 />
        ${field.relation.targetEntity?cap_first}MultiTo${entity.name?cap_first}CreatePageBack = ${increment},
        <#assign increment = increment + 1 />
        ${field.relation.targetEntity?cap_first}MultiTo${entity.name?cap_first}ShowPageBack = ${increment},
        <#assign increment = increment + 1 />
                </#if>
            </#list>
        #endregion
        </#if>
        <#if mono>
        #region ${entity.name?cap_first} OneToOne and ManyToOne relations.
            <#list fields?values as field >
                <#if field.relation??>
        ${entity.name?cap_first}SoloTo${field.relation.targetEntity?cap_first}ShowPage = ${increment},
        <#assign increment = increment + 1 />
        ${field.relation.targetEntity?cap_first}SoloTo${entity.name?cap_first}ShowPageBack = ${increment},
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
