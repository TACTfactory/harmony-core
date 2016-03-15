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

using ${project_namespace}.Harmony.Util.StateMachine;
using ${project_namespace}.View.${curr.name?cap_first};
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.States
{
    /// <summary>
    /// ${curr.name?cap_first}ShowState manage current item Page for state machine processing.
    /// </summary>
    class ${curr.name?cap_first}ShowState : ViewStateMachineState
    {
        private ${curr.name?cap_first}ShowPage ${curr.name?lower_case}ShowPage;
        
        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl adapter to save and load ${curr.name?cap_first} informations.
        /// </summary>
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(new ${project_name?cap_first}SQLiteOpenHelper());

        /// <summary>
        /// Constructor that initialyze ${curr.name?cap_first}ShowState stateID 
        /// to ${curr.name?cap_first}ShowPageEnter for state machine base state on enter.
        /// </summary>
        public ${curr.name?cap_first}ShowState()
        {
            this.stateID = StateID.${curr.name?cap_first}ShowPageEnter;
        }

        /// <summary>
        /// Setup state machine action for DoBeforeEntering.
        /// In ShowState only register current Page and let ViewStateMachineState processed.
        /// </summary>
        /// <param name="page">Current Page item where state machine have to navigate.</param>
        public override void DoBeforeEntering(Page page)
        {
            this.${curr.name?lower_case}ShowPage = page as ${curr.name?cap_first}ShowPage;
            base.DoBeforeEntering(page);
        }

        /// <summary>
        /// Setup state machine action for DoBeforeLeaving.
        /// In ShowState unreference event binding.
        /// </summary>
        /// <param name="page">Here not used.</param>
        public override void DoBeforeLeaving(Page page)
        {
            this.${curr.name?lower_case}ShowPage.ShowBrowser.Btn_Edit.Tapped -= Btn_Edit_Tapped;
            this.${curr.name?lower_case}ShowPage.ShowBrowser.Btn_Delete.Tapped -= Btn_Delete_Tapped;
            this.${curr.name?lower_case}ShowPage.ShowBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
        }

        /// <summary>
        /// Setup state machine action for DoAfterEntering.
        /// In ShowState reference event binding and process UI update if needed.
        /// </summary>
        public override void DoAfterEntering()
        {
            this.${curr.name?lower_case}ShowPage.ShowBrowser.Btn_Back.Tapped += Btn_Back_Tapped;
            this.${curr.name?lower_case}ShowPage.${curr.name?cap_first}ShowUserControl.JockeyItem = ViewStateMachine.Instance.Jockey;

        <#if wishedfields?has_content>
            this.UpdateUI();
        </#if> 
        }

        <#if wishedfields?has_content>
        /// <summary>
        /// Update ${curr.name?cap_first}ShowUserControl UI to remove button if comming from other display tree state.
        /// </summary>
        public void UpdateUI()
        {
            <#list wishedfields as field>
                <#if (field?is_first && field?is_last)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                <#elseif (field?is_first)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null
                <#elseif (field?is_last)>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                <#else>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null
                </#if>
            </#list>
            <#list wishedfields as field>
                <#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany">
                this.${curr.name?lower_case}CreatePage
                    .${curr.name?cap_first}CreateUserControl
                        .Stackpanel_btn.Children.Remove(
                            this.${curr.name?lower_case}CreatePage
                                .${curr.name?cap_first}CreateUserControl
                                    .Btn_list_related_${field.relation.targetEntity?lower_case});
                <#else>      
                this.${curr.name?lower_case}CreatePage
                    .${curr.name?cap_first}CreateUserControl
                        .Stackpanel_btn.Children.Remove(
                            this.${curr.name?lower_case}CreatePage
                                .${curr.name?cap_first}CreateUserControl
                                    .Btn_show_${field.relation.targetEntity?lower_case});
                </#if>
            </#list>
            }
        }
        </#if>

        /// <summary>
        /// Call state machine to navigate back.
        /// If item have come from another item set it to null.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
        <#if wishedfields?has_content>
            <#list wishedfields as field>
                <#if (field?is_first && field?is_last)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                <#elseif (field?is_first)>
            if ( ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null
                <#elseif (field?is_last)>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                <#else>
                || ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null
                </#if>
            </#list>
            <#list wishedfields as field>
                <#if field.relation.type == "ManyToMany" || field.relation.type == "OneToMany">
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} = null;
                ViewStateMachine.Instance.SetTransition(
                    Transition.Back, new ${curr.name?cap_first}ListPage());
                <#else>
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} = null;
                ViewStateMachine.Instance.SetTransition(
                    Transition.Back, new ${field.relation.targetEntity?cap_first}ShowPage());
                </#if>
            </#list>
            }
        }
        <#else> 
            ViewStateMachine.Instance.Back();
        }
        </#if>

        /// <summary>
        /// Delete current item and navigate back.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_Delete_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            this.${curr.name?lower_case}Adapter.Delete(
                    this.${curr.name?lower_case}ShowPage.${curr.name?cap_first}ShowUserControl
                        .JockeyItem);
            ViewStateMachine.Instance.Back();
        }

        /// <summary>
        /// Navigate to current item EditPage.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void Btn_Edit_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}EditPage, 
                    new ${curr.name?cap_first}EditPage());
        }
    }
}
