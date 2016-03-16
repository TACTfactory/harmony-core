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
        <#assign increment = 2 />
        <#list entities?values as entity>
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}ListPage = ${increment}, // ${entity.name?cap_first} transition to list items page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePage = ${increment}, // ${entity.name?cap_first} transition to create item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPage = ${increment}, // ${entity.name?cap_first} transition to show item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPage = ${increment}, // ${entity.name?cap_first} transition to edit item page.
            <#assign increment = increment + 1 />
        ${entity.name?cap_first}CheckListPage = ${increment}, // ${entity.name?cap_first} transition to checklist items page.
        </#list>
    }
}
