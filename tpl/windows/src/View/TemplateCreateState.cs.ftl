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
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.States
{
    /// <summary>
    /// ${curr.name?cap_first}CreateState manage current item Page for state machine processing.
    /// </summary>
    class ${curr.name?cap_first}CreateState : ViewStateMachineState
    {
        private ${curr.name?cap_first}CreatePage ${curr.name?lower_case}CreatePage;

        /// <summary>
        /// Constructor that initialyze ${curr.name?cap_first}CreateState stateID 
        /// to ${curr.name?cap_first}CreatePageEnter for state machine base state on enter.
        /// </summary>
        public ${curr.name?cap_first}CreateState()
        {
            this.stateID = StateID.${curr.name?cap_first}CreatePageEnter;
        }

        /// <summary>
        /// Setup state machine action for DoBeforeEntering.
        /// In CreateState only register current Page and let ViewStateMachineState processed.
        /// </summary>
        /// <param name="page">Current Page item where state machine have to navigate.</param>
        public override void DoBeforeEntering(Page page)
        {
            this.${curr.name?lower_case}CreatePage = page as ${curr.name?cap_first}CreatePage;
            base.DoBeforeEntering(page);
        }

        /// <summary>
        /// Setup state machine action for DoBeforeLeaving.
        /// In CreateState unreference event binding.
        /// </summary>
        /// <param name="page">Here not used.</param>
        public override void DoBeforeLeaving(Page page)
        {
            this.${curr.name?lower_case}CreatePage.BackBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
        }

        /// <summary>
        /// Setup state machine action for DoAfterEntering.
        /// In CreateState reference event binding and process UI update if needed.
        /// </summary>
        public override void DoAfterEntering()
        {
            this.${curr.name?lower_case}CreatePage.BackBrowser.Btn_Back.Tapped += Btn_Back_Tapped;
        <#if wishedfields?has_content>
            this.UpdateUI();
        </#if>    
        }
        
        <#if wishedfields?has_content>
        /// <summary>
        /// Update ${curr.name?cap_first}CreateUserControl UI to remove button if comming from other display tree state.
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
            {
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
                                    .Btn_add_${field.relation.targetEntity?lower_case});
                </#if>
            </#list>
            }
        }
        </#if>

        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.Back();
        }
    }
}
