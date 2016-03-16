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
        
        #region ${entity.name?cap_first}State base transitions.
        
        ${entity.name?cap_first}ListPageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}CreatePageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}ShowPageEnter = ${increment},
        <#assign increment = increment + 1 />
        ${entity.name?cap_first}EditPageEnter = ${increment},
        <#assign increment = increment + 1 />
        #endregion
        
        #region ${entity.name?cap_first}State relations transitions.
            <#assign fields = ViewUtils.getAllFields(entity) />
            <#assign wishedfields = [] />
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden)>
                    <#if field.relation?? && ((field.relation.type=="OneToMany") || (field.relation.type=="ManyToMany") || (field.relation.type=="OneToOne") || (field.relation.type=="ManyToOne"))>
                        <#assign wishedfields = wishedfields + [field]/>
                    </#if>
                </#if>
            </#list>
            <#list wishedfields as field>
                <#if field.relation?? && ((field.relation.type=="OneToMany") || (field.relation.type=="ManyToMany"))>
        
        #region ${entity.name?cap_first} ManyToMany and OneToMany relations.
        
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
        #endregion
                <#else>
        
        #region ${entity.name?cap_first} OneToOne and ManyToOne relations.        
        
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
        #endregion        
                </#if>
            </#list>
        #endregion 
        </#list>
        
    }
}