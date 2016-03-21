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
         ${entity.name?cap_first}CheckListPage = ${increment}, // ${entity.name?cap_first} transition to checklist items page.
                <#assign increment = increment + 1 />
            </#if>
            <#if mono>
        ${entity.name?cap_first}RadioListPage = ${increment}, // ${entity.name?cap_first} transition to checklist items page.
                <#assign increment = increment + 1 />
            </#if>
        </#if>

        </#list>
    }
}
