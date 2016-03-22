<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

using ${project_namespace}.Entity;
using ${project_namespace}.Entity.Base;
using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View.Navigation.States;
using System;
using System.Linq;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View
{
    class ViewStateMachine
    {
        /// <summary>
        /// Singleton for C# (do not manage multi-threading).
        /// </summary>
        #region Singleton.
        private static ViewStateMachine instance;

        private ViewStateMachine()
        {
        }

        public static ViewStateMachine Instance
        {
            get
            {
                if (instance == null)
                {
                    instance = new ViewStateMachine();
                    instance.BuildStateMachine();
                }
                return instance;
            }
        }
        #endregion
        
        private BaseViewStateMachine bsm;

        public Transition NextTransition { get; set; }
        <#list entities?values as entity>
            <#if !entity.internal >
        public Entity.${entity.name?cap_first} ${entity.name?cap_first} { get; set; }
            </#if>
        </#list>

        public void SetTransition(Transition t, Page page, Object item = null)
        {
            if (item != null)
            {
                this.SetItem(item);
            }

            this.NextTransition = t;
            bsm.PerformTransition(t, page);
        }

        public void Back()
        {
            Page temp = this.bsm.CurrentPage;
            this.NextTransition = Transition.Back;
            bsm.PerformTransition(Transition.Back, this.bsm.Pages.ElementAt(this.bsm.Pages.IndexOf(temp) -1));
            bsm.Pages.RemoveAt(this.bsm.Pages.IndexOf(temp));
        }

        private void SetItem(Object item)
        {
            <#list entities?values as entity>
            <#if !entity.internal >
                <#if entity?is_first>
            if (item is Entity.${entity.name?cap_first})
            {
                this.${entity.name?cap_first} = item as Entity.${entity.name?cap_first};
            }
                <#else>
            else if (item is Entity.${entity.name?cap_first})
            {
                this.${entity.name?cap_first} = item as Entity.${entity.name?cap_first};
            }
                </#if>
            </#if>
            </#list>
        }

        private void BuildStateMachine()
        {
            /// <summary>
            /// Root navigation state to navigate to all list entities.
            /// </summary>
            #region HomeState base transitions.
            HomeState homeState = new HomeState();
            homeState.AddTransition(Transition.HomePage, StateID.HomePageEnter);
            <#list entities?values as entity>
                <#if !entity.internal >
            homeState.AddTransition(
                Transition.${entity.name?cap_first}ListPage, 
                    StateID.${entity.name?cap_first}ListPageEnter);
                </#if>
            </#list>
            #endregion
            
            <#list entities?values as entity>
                <#if !entity.internal >
            /// <summary>
            /// Base navigation into entities.
            /// Navigation states definitions :
            ///     - HomeState
            ///         - ListState
            ///             - CreateState
            ///                 - Back
            ///             - ShowState
            ///                 - EditState
            ///                     - Back
            ///                 - Back
            ///             - Back
            /// </summary>
            #region ${entity.name?cap_first}State base transitions.
            // ListState.
            ${entity.name?cap_first}ListState ${entity.name?lower_case}List = new ${entity.name?cap_first}ListState();
            ${entity.name?lower_case}List.AddTransition(Transition.${entity.name?cap_first}ListPage, StateID.${entity.name?cap_first}ListPageEnter);
            ${entity.name?lower_case}List.AddTransition(
                Transition.${entity.name?cap_first}CreatePage, 
                    StateID.${entity.name?cap_first}CreatePageEnter);
            ${entity.name?lower_case}List.AddTransition(
                Transition.${entity.name?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}ShowPageEnter);
            ${entity.name?lower_case}List.AddTransition(
                Transition.Back, 
                    StateID.HomePageEnter);
            
            // CreateState.      
            ${entity.name?cap_first}CreateState ${entity.name?lower_case}Create = new ${entity.name?cap_first}CreateState();
            ${entity.name?lower_case}Create.AddTransition(Transition.${entity.name?cap_first}CreatePage, StateID.${entity.name?cap_first}CreatePageEnter);
            ${entity.name?lower_case}Create.AddTransition(
                Transition.Back, 
                    StateID.${entity.name?cap_first}ListPageEnter);
                    
            // ShowState.      
            ${entity.name?cap_first}ShowState ${entity.name?lower_case}Show = new ${entity.name?cap_first}ShowState();
            ${entity.name?lower_case}Show.AddTransition(Transition.${entity.name?cap_first}ShowPage, StateID.${entity.name?cap_first}ShowPageEnter);
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}EditPageEnter);
            ${entity.name?lower_case}Show.AddTransition(
                Transition.Back, 
                    StateID.${entity.name?cap_first}ListPageEnter);
                    
            // EditState.      
            ${entity.name?cap_first}EditState ${entity.name?lower_case}Edit = new ${entity.name?cap_first}EditState();
            ${entity.name?lower_case}Edit.AddTransition(Transition.${entity.name?cap_first}EditPage, StateID.${entity.name?cap_first}EditPageEnter);
            ${entity.name?lower_case}Edit.AddTransition(
                Transition.Back, 
                    StateID.${entity.name?cap_first}ShowPageEnter);
                    
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
             ${entity.name?cap_first}CheckListState ${entity.name?lower_case}CheckList = new ${entity.name?cap_first}CheckListState();       
                    </#if>
                    <#if mono>
             //${entity.name?cap_first}RadioListState ${entity.name?lower_case}RadioList = new ${entity.name?cap_first}RadioListState();       
                    </#if>
                    
            #endregion
                </#if>
            </#list>
            
            <#list entities?values as entity>
                <#if !entity.internal >
            /// <summary>
            /// Relations navigation into entities.
            /// Relations navigation states definitions :
            ///     - CreateState :
            ///         - Associate ListState (ManyToMany, OneToMany)
            ///             - Associate CreateState
            ///                 - Back
            ///             - Associate ShowState
            ///                 - Back
            ///                 - Associate EditState
            ///                     - Back
            ///             - Associate CheckListState
            ///             - Back
            ///         - Associate ShowState (OneToOne, ManyToOne)
            ///             - Associate EditState
            ///                 - Back
            ///             - Associate RadioListState
            ///                 - Back
            ///             - Back
            ///     - ShowState :
            ///         - Associate ListState (ManyToMany, OneToMany)
            ///             - Associate CreateState
            ///                 - Back
            ///             - Associate ShowState
            ///                 - Back
            ///                 - Associate EditState
            ///                     - Back
            ///             - Associate CheckListState
            ///             - Back
            ///         - Associate ShowState (OneToOne, ManyToOne)
            ///             - Associate EditState
            ///                 - Back
            ///             - Associate RadioListState
            ///                 - Back
            ///             - Back
            /// </summary>
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
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ListPage, 
                    StateID.${entity.name?cap_first}MultiCreateListPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}List.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ListPage, 
                    StateID.${entity.name?cap_first}MultiCreateListPage${field.relation.targetEntity?cap_first});

            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CreatePage, 
                    StateID.${entity.name?cap_first}MultiCreateCreatePage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CreatePage, 
                    StateID.${entity.name?cap_first}MultiCreateCreatePage${field.relation.targetEntity?cap_first});

            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}MultiCreateShowPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}MultiCreateShowPage${field.relation.targetEntity?cap_first});

            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CheckListPage, 
                    StateID.${entity.name?cap_first}MultiCreateCheckListPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}CheckList.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CheckListPage, 
                    StateID.${entity.name?cap_first}MultiCreateCheckListPage${field.relation.targetEntity?cap_first});

            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}MultiCreateShowEditPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}MultiCreateShowEditPage${field.relation.targetEntity?cap_first});

            // ShowState.
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ListPage, 
                    StateID.${entity.name?cap_first}MultiShowListPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}List.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ListPage, 
                    StateID.${entity.name?cap_first}MultiShowListPage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CreatePage, 
                    StateID.${entity.name?cap_first}MultiShowCreatePage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CreatePage, 
                    StateID.${entity.name?cap_first}MultiShowCreatePage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}MultiShowShowPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}MultiShowShowPage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CheckListPage, 
                    StateID.${entity.name?cap_first}MultiShowCheckListPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}CheckList.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}CheckListPage, 
                    StateID.${entity.name?cap_first}MultiShowCheckListPage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}MultiShowShowEditPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}MultiShowShowEditPage${field.relation.targetEntity?cap_first});
            #endregion
                    <#else>
            #region ${entity.name?cap_first} OneToOne and ManyToOne relations.    
                
            // CreateState.
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}SoloCreateShowPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}SoloCreateShowPage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}SoloCreateEditPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}SoloCreateEditPage${field.relation.targetEntity?cap_first});
            
            //${entity.name?lower_case}Create.AddTransition(
            //    Transition.${field.relation.targetEntity?cap_first}RadioListPage, 
            //        StateID.${entity.name?cap_first}SoloCreateRadioListPage${field.relation.targetEntity?cap_first});
            //${field.relation.targetEntity?lower_case}RadioList.AddTransition(
            //    Transition.${field.relation.targetEntity?cap_first}RadioListPage, 
            //        StateID.${entity.name?cap_first}SoloCreateRadioListPage${field.relation.targetEntity?cap_first});
            
            // ShowState.
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}SoloShowShowPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}ShowPage, 
                    StateID.${entity.name?cap_first}SoloShowShowPage${field.relation.targetEntity?cap_first});
            
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}SoloShowEditPage${field.relation.targetEntity?cap_first});
            ${field.relation.targetEntity?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}EditPage, 
                    StateID.${entity.name?cap_first}SoloShowEditPage${field.relation.targetEntity?cap_first});
            
            //${entity.name?lower_case}Show.AddTransition(
            //    Transition.${field.relation.targetEntity?cap_first}RadioListPage, 
            //        StateID.${entity.name?cap_first}SoloShowRadioListPage${field.relation.targetEntity?cap_first});
            //${field.relation.targetEntity?lower_case}RadioList.AddTransition(
            //    Transition.${field.relation.targetEntity?cap_first}RadioListPage, 
            //        StateID.${entity.name?cap_first}SoloShowRadioListPage${field.relation.targetEntity?cap_first});
            #endregion        
                    </#if>
                </#list>
            #endregion
                </#if>
            </#list>

            // Create and add all states to state machine.
            bsm = new BaseViewStateMachine();
            bsm.AddState(homeState);
            
            <#list entities?values as entity>
                <#if !entity.internal >
            bsm.AddState(${entity.name?lower_case}List);
            bsm.AddState(${entity.name?lower_case}Create);
            bsm.AddState(${entity.name?lower_case}Show);
            bsm.AddState(${entity.name?lower_case}Edit);
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
            bsm.AddState(${entity.name?lower_case}CheckList);
                    </#if>
                    <#if mono>
            //bsm.AddState(${entity.name?lower_case}RadioList);
                    </#if>
                </#if>

            </#list>
        }
    }
}