<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<#assign wishedfields = [] />
<#list fields?values as field>
    <#if (!field.internal && !field.hidden)>
        <#if field.relation?? && ((field.relation.type=="OneToMany") || (field.relation.type=="ManyToMany") || (field.relation.type=="OneToOne") || (field.relation.type=="ManyToOne"))>
            <#assign wishedfields = wishedfields + [field]/>
        </#if>
    </#if>
</#list>
<@header?interpret />

using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View.${curr.name?cap_first};
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.States
{
    /// <summary>
    /// ${curr.name?cap_first}ListState manage current item Page for state machine processing.
    /// </summary>
    class ${curr.name?cap_first}ListState : ViewStateMachineState
    {
        private ${curr.name?cap_first}ListPage ${curr.name?lower_case}ListPage;

        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl adapter to save and load ${curr.name?cap_first} informations.
        /// </summary>
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(new ${project_name?cap_first}SQLiteOpenHelper());
            
        /// <summary>
        /// Constructor that initialyze ${curr.name?cap_first}CreateState stateID 
        /// to ${curr.name?cap_first}ListStateEnter for state machine base state on enter.
        /// </summary>
        public ${curr.name?cap_first}ListState()
        {
            this.stateID = StateID.${curr.name?cap_first}ListPageEnter;
        }

        /// <summary>
        /// Setup state machine action for DoBeforeEntering.
        /// In ListState only register current Page and let ViewStateMachineState processed.
        /// </summary>
        /// <param name="page">Current Page item where state machine have to navigate.</param>
        public override void DoBeforeEntering(Page page)
        {
            this.${curr.name?lower_case}ListPage = page as ${curr.name?cap_first}ListPage;
            base.DoBeforeEntering(page);
        }

        /// <summary>
        /// Setup state machine action for DoBeforeLeaving.
        /// In ListState unreference event binding.
        /// </summary>
        /// <param name="page">Here not used.</param>
        public override void DoBeforeLeaving(Page page)
        {
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_New.Tapped -= Btn_New_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Erase_All.Tapped -= Btn_Erase_All_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Existing.Tapped -= Btn_Existing_Tapped;
        }

        /// <summary>
        /// Setup state machine action for DoAfterEntering.
        /// In ListState reference event binding and process UI update if needed.
        /// </summary>
        public override void DoAfterEntering()
        {
            this.${curr.name?lower_case}ListPage.${curr.name?cap_first}ListUserControl.LoadItem();

            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_New.Tapped += Btn_New_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Erase_All.Tapped += Btn_Erase_All_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Existing.Tapped += Btn_Existing_Tapped;
            this.${curr.name?lower_case}ListPage.NavigationBrowser.Btn_Back.Tapped += Btn_Back_Tapped;

        <#if wishedfields?has_content>
            this.UpdateUI();
        </#if> 
        }

        <#if wishedfields?has_content>
        /// <summary>
        /// Update ${curr.name?cap_first}ListUserControl UI to remove existing button 
        /// if do not comming from other display tree state.
        /// </summary>
        public void UpdateUI()
        {
            <#list wishedfields as field>
                <#if (field?is_first && field?is_last)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} == null)
                <#elseif (field?is_first)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} == null
                <#elseif (field?is_last)>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} == null)
                <#else>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} == null
                </#if>
            </#list>
                this.${curr.name?lower_case}ListPage.NavigationBrowser
                    .Stackpanel_Btn.Children.Remove(
                        this.${curr.name?lower_case}ListPage
                            .NavigationBrowser.Btn_Existing);
            }
        }
        </#if>

        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.Back();
        }

        /// <summary>
        /// Erase all datas displayed.
        /// This can occurs by navigated to custom list or with full list.
        /// On custom list case, only navigated from item relations, can be deleted.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_Erase_All_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            <#if wishedfields?has_content>
                <#list wishedfields as field>
                    <#if (field?is_first)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                this.${curr.name?lower_case}Adapter.Clear(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                    <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                this.${curr.name?lower_case}Adapter.Clear(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                    </#if>
                </#list>
            else
            {
                this.${curr.name?lower_case}Adapter.Clear();
            }
            <#else>
            this.${curr.name?lower_case}Adapter.Clear();
            </#if>

            // Remove items on current displayed view.
            this.${curr.name?lower_case}ListPage.${curr.name?cap_first}ListUserControl.Obs.Clear();
        }

        /// <summary>
        /// Navigate to ${curr.name?cap_first}CreatePage.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_New_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}CreatePage, 
                    new ${curr.name?cap_first}.${curr.name?cap_first}CreatePage());
        }

        /// <summary>
        /// Navigate to display list of checkable items.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_Existing_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}CheckListPage, 
                    new ${curr.name?cap_first}.${curr.name?cap_first}CheckListPage());
        }
    }
}

