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
    /// ${curr.name?cap_first}CheckListState manage current item Page for state machine processing.
    /// </summary>
    class ${curr.name?cap_first}CheckListState : ViewStateMachineState
    {
        private ${curr.name?cap_first}CheckListPage ${curr.name?lower_case}CheckListPage;

        /// <summary>
        /// Constructor that initialyze ${curr.name?cap_first}CheckListState stateID 
        /// to ${curr.name?cap_first}CheckListPageEnter for state machine base state on enter.
        /// </summary>
        public ${curr.name?cap_first}CheckListState()
        {
            this.stateID = StateID.${curr.name?cap_first}CheckListPageEnter;
        }

        /// <summary>
        /// Setup state machine action for DoBeforeEntering.
        /// In CheckListState only register current Page and let ViewStateMachineState processed.
        /// </summary>
        /// <param name="page">Current Page item where state machine have to navigate.</param>
        public override void DoBeforeEntering(Page page)
        {
            this.${curr.name?lower_case}CheckListPage = page as ${curr.name?cap_first}CheckListPage;
            base.DoBeforeEntering(page);
        }

        /// <summary>
        /// Setup state machine action for DoBeforeLeaving.
        /// In CheckListState unreference event binding.
        /// </summary>
        /// <param name="page">Here not used.</param>
        public override void DoBeforeLeaving(Page page)
        {
            this.${curr.name?lower_case}CheckListPage.BackBrowser.Btn_Back.Tapped -= Btn_Back_Tapped;
        }

        /// <summary>
        /// Setup state machine action for DoAfterEntering.
        /// In CheckListState reference event binding and process UI update if needed.
        /// </summary>
        public override void DoAfterEntering()
        {
            this.${curr.name?lower_case}CheckListPage.${curr.name?cap_first}CheckListUserControl.LoadItem();

            this.${curr.name?lower_case}CheckListPage.BackBrowser.Btn_Back.Tapped += Btn_Back_Tapped;
        }

        private void Btn_Back_Tapped(object sender, Windows.UI.Xaml.Input.TappedRoutedEventArgs e)
        {
            ViewStateMachine.Instance.Back();
        }
    }
}

