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

<#list fields?values as field>
    <#if field.relation??>
using ${project_namespace}.View.${field.relation.targetEntity?cap_first};
    </#if>
</#list>
using ${project_namespace}.Utils.StateMachine;
using ${project_namespace}.View.${curr.name?cap_first};
using Windows.UI.Xaml.Controls;

namespace ${project_namespace}.View.Navigation.States
{
    /// <summary>
    /// ${curr.name?cap_first}RadioListState manage current item Page for state machine processing.
    /// </summary>
    class ${curr.name?cap_first}RadioListState : ViewStateMachineState
    {
        private ${curr.name?cap_first}RadioListPage ${curr.name?lower_case}RadioListPage;

        /// <summary>
        /// Constructor that initialyze ${curr.name?cap_first}RadioListState stateID 
        /// to ${curr.name?cap_first}RadioListPageEnter for state machine base state on enter.
        /// </summary>
        public ${curr.name?cap_first}RadioListState()
        {
            this.stateID = StateID.${curr.name?cap_first}RadioListPageEnter;
        }

        /// <summary>
        /// Setup state machine action for DoBeforeEntering.
        /// In RadioListState only register current Page and let ViewStateMachineState processed.
        /// </summary>
        /// <param name="page">Current Page item where state machine have to navigate.</param>
        public override void DoBeforeEntering(Page page)
        {
            this.${curr.name?lower_case}RadioListPage = page as ${curr.name?cap_first}RadioListPage;
            base.DoBeforeEntering(page);
        }

        /// <summary>
        /// Setup state machine action for DoBeforeLeaving.
        /// In RadioListState unreference event binding.
        /// </summary>
        /// <param name="page">Here not used.</param>
        public override void DoBeforeLeaving(Page page)
        {
            this.${curr.name?lower_case}RadioListPage.BackBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
        }

        /// <summary>
        /// Setup state machine action for DoAfterEntering.
        /// In RadioListState reference event binding and process UI update if needed.
        /// </summary>
        public override void DoAfterEntering()
        {
            this.${curr.name?lower_case}RadioListPage.${curr.name?cap_first}RadioListUserControl.LoadItem();

            this.${curr.name?lower_case}RadioListPage.BackBrowser.Btn_Back.Tapped += Btn_Back_Tapped;
        }

        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}RadioListPageBack, 
                    new ${curr.name?cap_first}ShowPage());
        }
    }
}

