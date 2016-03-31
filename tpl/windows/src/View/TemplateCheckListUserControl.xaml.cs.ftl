<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

<#list fields?values as field>
    <#if field.relation??>
using ${project_namespace}.View.${field.relation.targetEntity?cap_first};
    </#if>
</#list>
using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.View.${curr.name?cap_first}.Checkable;
using ${project_namespace}.View.${curr.name?cap_first}.Checkable.Manager;
using ${project_namespace}.Utils.StateMachine;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}CheckListUserControl contain
    /// real check list display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}CheckListUserControl : UserControl
    {
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter =
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);

        private ${curr.name?cap_first}CheckableManager ${curr.name?lower_case}CheckableManager =
            new ${curr.name?cap_first}CheckableManager();

        private ObservableCollection<${curr.name?cap_first}Checkable> obs;

        /// <summary>
        /// Default constructor.
        /// Initialize base list with ${curr.name?cap_first}Checkable items.
        /// </summary>
        public ${curr.name?cap_first}CheckListUserControl()
        {
            this.InitializeComponent();
            obs = new ObservableCollection<${curr.name?cap_first}Checkable>();
            this.itemsList.ItemsSource = obs;
        }

        /// <summary>
        /// Load items and set them checked if have to.
        /// </summary>
        public void LoadItem()
        {
            List<Entity.${curr.name?cap_first}> items = ${curr.name?lower_case}Adapter.GetAll();

            ${curr.name?lower_case}CheckableManager.ParseInCheckables(items);

            <#assign item_count = 0/>
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden && field.relation??)>
                    <#if field.relation?? && (field.relation.type == "ManyToMany" || field.relation.type == "ManyToOne")>
                        <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                ${curr.name?lower_case}CheckableManager.SetChecked(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                            <#assign item_count = 1/>
                        <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                ${curr.name?lower_case}CheckableManager.SetChecked(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                        </#if>
                    </#if>
                </#if>
            </#list>

            obs.Clear();
            foreach (var item in ${curr.name?lower_case}CheckableManager.${curr.name?cap_first}Checkables)
            {
                obs.Add(item);
            }
        }

        /// <summary>
        /// Validate button use to save datas change in database.
        /// For any possible relations beetwen ${curr.name?cap_first}
        /// and others entities mapped with ManyToMany or OneToMany
        /// update all references if have to.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_validate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#assign item_count = 0/>
            <#list fields?values as field>
                <#if (!field.internal && !field.hidden && field.relation??)>
                    <#if field.relation?? && (field.relation.type == "ManyToMany" || field.relation.type == "ManyToOne")>
                        <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                ${curr.name?lower_case}CheckableManager.Save(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                            <#assign item_count = 1/>
                        <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
            {
                ${curr.name?lower_case}CheckableManager.Save(
                    ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                        </#if>
                    </#if>
                </#if>
            </#list>

            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}CheckListPageBack,
                    new ${curr.name?cap_first}ListPage());
        }

        private void itemsList_ItemClick(object sender, ItemClickEventArgs e)
        {
            ${curr.name?cap_first}Checkable item = e.ClickedItem as ${curr.name?cap_first}Checkable;
            if (item.Check)
            {
                item.Check = false;
            }
            else
            {
                item.Check = true;
            }
        }
    }
}
