<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

using ${project_namespace}.Entity;
using ${project_namespace}.Entity.Base;
using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View.Navigation.States;
using System.Collections.Generic;
using System;
using System.Linq;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View
{
    // <summary>
    /// Application state macine for views.
    /// </summary>
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

        /// <summary>
        /// Instance of current state machine.
        /// </summary>
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

        /// <summary>
        /// Last played transition or will be played.
        /// </summary>
        public Transition NextTransition { get; set; }
        <#list entities?values as entity>
            <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
        public Entity.${entity.name?cap_first} ${entity.name?cap_first} { get; set; }
            </#if>
        </#list>

        /// <summary>
        /// Setup a transition to navigate to.
        /// </summary>
        /// <param name="t">Transition to do.</param>
        /// <param name="page">Page to navigate to.</param>
        /// <param name="item">Passed item (default = null).</param>
        public void SetTransition(Transition t, Page page, Object item = null)
        {
            if (item != null)
            {
                this.SetItem(item);
            }

            this.NextTransition = t;
            this.bsm.PerformTransition(t, page);
        }

        /// <summary>
        /// All state machine entities item set to null.
        /// </summary>
        public void SetItemsNull()
        {
        <#list entities?values as entity>
            <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
            this.${entity.name?cap_first} = null;
            </#if>
        </#list>
            this.bsm.Transitions = new List<Transition>();
        }

        /// <summary>
        /// Call base state machine DidTransitionContains.
        /// </summary>
        /// <param name="trans">Transition to check for.</param>
        /// <returns>true if finded elsewhere false.</returns>
        public Boolean DidTransitionContains(Transition trans)
        {
            return this.bsm.DidTransitionContains(trans);
        }

        /// <summary>
        /// Used when SetTransition was called with an object to setup state machine
        /// element with current object value.
        /// </summary>
        /// <param name="item">Item to check for state machine.</param>
        private void SetItem(Object item)
        {
            <#list entities?values as entity>
            <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
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

        /// <summary>
        /// Build up state machine with all transition states to provide navigation between pages.
        /// </summary>
        private void BuildStateMachine()
        {
            /// <summary>
            /// Root navigation state to navigate to all list entities.
            /// </summary>
            #region HomeState base transitions.
            HomeState homeState = new HomeState();
            homeState.AddTransition(Transition.HomePage, StateID.HomePageEnter);
            <#list entities?values as entity>
                <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
            homeState.AddTransition(
                Transition.${entity.name?cap_first}ListPage,
                    StateID.${entity.name?cap_first}ListPageEnter);
                </#if>
            </#list>
            #endregion

            <#list entities?values as entity>
                <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
            /// <summary>
            /// Navigation into entities.
            /// Navigation states transition definitions :
            ///     - HomeState
            ///         - ListState :
            ///             - Back => HomeState
            ///             - Create New => CreateSate :
            ///                 - Back => ListState
            ///                 - List related entity (if have) => RelatedEntity ListState
            ///                 - Add entity (if have) => AddEntity ShowState
            ///                 - Validate => ListState
            ///             - Erase All
            ///             - Item click => ShowState :
            ///                 - Back => ListState
            ///                 - Edit => EditState :
            ///                     - Back => ShowState
            ///                     - Update => ShowState
            ///                 - Delete => ListState
            ///                 - List related entity (if have or if on list base item) => RelatedEntity ListState
            ///                 - Show entity (if have or if on list base item) => ShowEntity ShowState
            ///                 - Use Existing (if not on list base item) => UseExisting ExistingState
            ///             - Use Existing (if not on list base item) => UseExisting ExistingState
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
                Transition.${entity.name?cap_first}HomePageBack,
                    StateID.HomePageEnter);

            // CreateState.
            ${entity.name?cap_first}CreateState ${entity.name?lower_case}Create = new ${entity.name?cap_first}CreateState();
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${entity.name?cap_first}CreatePage,
                    StateID.${entity.name?cap_first}CreatePageEnter);
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${entity.name?cap_first}CreatePageBack,
                    StateID.${entity.name?cap_first}ListPageEnter);

            // ShowState.
            ${entity.name?cap_first}ShowState ${entity.name?lower_case}Show = new ${entity.name?cap_first}ShowState();
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}ShowPage,
                    StateID.${entity.name?cap_first}ShowPageEnter);
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}EditPage,
                    StateID.${entity.name?cap_first}EditPageEnter);
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ListPageEnter);

            // EditState.
            ${entity.name?cap_first}EditState ${entity.name?lower_case}Edit = new ${entity.name?cap_first}EditState();
            ${entity.name?lower_case}Edit.AddTransition(
                Transition.${entity.name?cap_first}EditPage,
                    StateID.${entity.name?cap_first}EditPageEnter);
            ${entity.name?lower_case}Edit.AddTransition(
                Transition.${entity.name?cap_first}EditPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);
                    <#assign fields = ViewUtils.getAllFields(entity) />
                    <#assign multi = false>
                    <#assign mono = false>
                    <#list fields?values as field >
                        <#if field.relation??>
                            <#if field.relation.type == "ManyToMany" || field.relation.type == "ManyToOne" >
                                <#assign multi = true>
                            <#else>
                                <#assign mono = true>
                            </#if>
                        </#if>
                    </#list>
                    <#if multi>

            // CheckState.
            ${entity.name?lower_case}List.AddTransition(
                Transition.${entity.name?cap_first}CheckListPage,
                    StateID.${entity.name?cap_first}CheckListPageEnter);
            ${entity.name?cap_first}CheckListState ${entity.name?lower_case}CheckList = new ${entity.name?cap_first}CheckListState();
            ${entity.name?lower_case}CheckList.AddTransition(
                Transition.${entity.name?cap_first}CheckListPageBack,
                    StateID.${entity.name?cap_first}ListPageEnter);
                    </#if>
                    <#if mono>

            // RadioState.
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}RadioListPage,
                    StateID.${entity.name?cap_first}RadioListPageEnter);
            ${entity.name?cap_first}RadioListState ${entity.name?lower_case}RadioList = new ${entity.name?cap_first}RadioListState();
            ${entity.name?lower_case}RadioList.AddTransition(
                Transition.${entity.name?cap_first}RadioListPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);
                    </#if>
            #endregion
                </#if>

            </#list>
            <#list entities?values as entity>

                <#if !entity.internal >
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
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${entity.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}ListPage,
                    StateID.${field.relation.targetEntity?cap_first}ListPageEnter);
            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}MultiTo${field.relation.targetEntity?cap_first}ListPage,
                    StateID.${field.relation.targetEntity?cap_first}ListPageEnter);

            ${entity.name?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);

            ${field.relation.targetEntity?lower_case}List.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);
            ${field.relation.targetEntity?lower_case}List.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}MultiTo${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);
            #endregion
                    <#else>
            #region ${entity.name?cap_first} OneToOne and ManyToOne relations.

            ${entity.name?lower_case}Show.AddTransition(
                Transition.${entity.name?cap_first}SoloTo${field.relation.targetEntity?cap_first}ShowPage,
                    StateID.${field.relation.targetEntity?cap_first}ShowPageEnter);
            ${entity.name?lower_case}Create.AddTransition(
                Transition.${entity.name?cap_first}SoloTo${field.relation.targetEntity?cap_first}ShowPage,
                    StateID.${field.relation.targetEntity?cap_first}ShowPageEnter);

            ${entity.name?lower_case}Edit.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);

            ${field.relation.targetEntity?lower_case}Show.AddTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${entity.name?cap_first}ShowPageBack,
                    StateID.${entity.name?cap_first}ShowPageEnter);
            #endregion
                    </#if>
                </#list>
            #endregion

                </#if>
            </#list>

            // Create and add all states to state machine.
            this.bsm = new BaseViewStateMachine();
            this.bsm.AddState(homeState);

            <#list entities?values as entity>
                <#if !entity.internal && FieldsUtils.hasShowableFields(entity.fields?values)>
            this.bsm.AddState(${entity.name?lower_case}List);
            this.bsm.AddState(${entity.name?lower_case}Create);
            this.bsm.AddState(${entity.name?lower_case}Show);
            this.bsm.AddState(${entity.name?lower_case}Edit);
                    <#assign fields = ViewUtils.getAllFields(entity) />
                    <#assign multi = false>
                    <#assign mono = false>
                    <#list fields?values as field >
                        <#if field.relation??>
                            <#if field.relation.type == "ManyToMany" || field.relation.type == "ManyToOne" >
                                <#assign multi = true>
                            <#else>
                                <#assign mono = true>
                            </#if>
                        </#if>
                    </#list>
                    <#if multi>
            this.bsm.AddState(${entity.name?lower_case}CheckList);
                    </#if>
                    <#if mono>
            this.bsm.AddState(${entity.name?lower_case}RadioList);
                    </#if>
                </#if>

            </#list>
        }
    }
}
