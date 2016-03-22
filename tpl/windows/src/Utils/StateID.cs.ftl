<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

namespace ${project_namespace}.Utils.StateMachine
{
    /// <summary>
    /// ViewStateMachine state ids.
    /// </summary>
    public enum StateID
    {
        NullStateID = 0, // Use this ID to represent a non-existing State in your system
        Start = 1, // Use this ID to represent a base state machine start State in your system
        HomePageEnter = 2, // Use this ID to represent home page navigation State in your system
        <#assign increment = 3 />
        <#list entities?values as entity>
        <#if !entity.internal>
        #region ${entity.name?cap_first}State base transitions.
        
        ${entity.name?cap_first}ListPageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPageEnter = ${increment},
        <#assign increment = increment + 1 />
            <#assign fields = ViewUtils.getAllFields(entity) />
            <#assign multi = false>
            <#assign mono = false>
            <#list fields?values as field >
                <#if field.relation??>
                    <#if field.relation.type == "ManyToMany">
                        <#assign multi = true>
                    <#elseif field.relation.type == "OneToMany" || field.relation.type == "ManyToOne">
                        <#assign multi = true>
                        <#assign mono = true>
                    <#else>
                        <#assign mono = true>
                    </#if>
                </#if>
            </#list>
            <#if multi>
        ${entity.name?cap_first}CheckListPageEnter = ${increment},
                <#assign increment = increment + 1 />
            </#if>
            <#if mono>
        ${entity.name?cap_first}RadioListPageEnter = ${increment},
                <#assign increment = increment + 1 />
            </#if>
        #endregion
        </#if>
    </#list>
    }
}