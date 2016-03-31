<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using ${project_namespace}.Utils;
using ${project_namespace}.Utils.StateMachine;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}EditUserControl contain real edit display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}EditUserControl : BindingUserControl
    {
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter =
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);

        private Entity.${curr.name?cap_first} ${curr.name?lower_case}Item;

        /// <summary>
        /// ${curr.name?cap_first}Item use to fill view with item datas.
        /// </summary>
        public Entity.${curr.name?cap_first} ${curr.name?cap_first}Item
        {
            get
            {
                return this.${curr.name?lower_case}Item;
            }

            set
            {
                this.${curr.name?lower_case}Item = value;
                base.OnPropertyChanged("${curr.name?cap_first}Item");
            }
        }

        /// <summary>
        /// Default constructor.
        /// Initialize current DataContext and ${curr.name?cap_first}Item
        /// with appropriate datas extract from ViewStateMachine.
        /// </summary>
        public ${curr.name?cap_first}EditUserControl()
        {
            this.InitializeComponent();
            this.DataContext = this;
            if(ViewStateMachine.Instance.${curr.name?cap_first} != null)
            {
                this.${curr.name?cap_first}Item = ViewStateMachine.Instance.${curr.name?cap_first};
            }
            else
            {
                this.${curr.name?cap_first}Item = new Entity.${curr.name?cap_first}();
            }
        }

        /// <summary>
        /// Update button use to save datas in database.
        /// For any possible relations beetwen ${curr.name?cap_first}
        /// and others entities update all references if have to.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_update_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#assign item_count = 0/>
            <#list fields?values as field>
                <#if field.id>
                    <#assign id = field.name />
                </#if>
            </#list>
            <#if fields?values?has_content>
                <#list fields?values as field>
                    <#if (!field.internal && !field.hidden)>
                        <#if field.relation?? && (field.relation.type == "ManyToMany" || field.relation.type == "ManyToOne")>
                            <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                                <#assign item_count = 1/>
                            <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                            </#if>
            {
                int result = this.${curr.name?lower_case}Adapter.InsertOrUpdate(this.${curr.name?cap_first}Item);
                ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetById(result);
                            <#list entities?values as entity>
                                <#if entity.name == field.relation.targetEntity>
                                    <#assign relatedEntity = entity />
                                </#if>
                            </#list>
                            <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                            <#list fields?values as field>
                                <#if field.relation?? && field.relation.targetEntity == curr.name>
                                    <#assign field_mapped = field.name>
                                </#if>
                            </#list>
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first}.${field_mapped?cap_first}.Add(ViewStateMachine.Instance.${curr.name?cap_first});
                ${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.relation.targetEntity?cap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
                ${field.relation.targetEntity?cap_first}Adapter.Update(ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
                ViewStateMachine.Instance.SetTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${curr.name?cap_first}ShowPageBack,
                    new ${curr.name?cap_first}ShowPage());
            }
                        <#elseif field.relation??>
                            <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                                <#assign item_count = 1/>
                            <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                            </#if>
            {
                int result = this.${curr.name?lower_case}Adapter.InsertOrUpdate(this.${curr.name?cap_first}Item);
                ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetById(result);
                            <#list entities?values as entity>
                                <#if entity.name == field.relation.targetEntity>
                                    <#assign relatedEntity = entity />
                                </#if>
                            </#list>
                            <#assign fields = ViewUtils.getAllFields(relatedEntity) />
                            <#list fields?values as field>
                                <#if field.relation?? && field.relation.targetEntity == curr.name>
                                    <#assign field_mapped = field.name>
                                </#if>
                            </#list>
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first}.${field_mapped?cap_first} = ViewStateMachine.Instance.${curr.name?cap_first}.${id?cap_first};
                ${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.relation.targetEntity?cap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);
                ${field.relation.targetEntity?cap_first}Adapter.Update(ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
                ViewStateMachine.Instance.SetTransition(
                Transition.${field.relation.targetEntity?cap_first}SoloTo${curr.name?cap_first}ShowPageBack,
                    new ${curr.name?cap_first}ShowPage());
            }
                        </#if>
                    </#if>
                </#list>
            else
            {
                ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?cap_first}Item;
                this.${curr.name?lower_case}Adapter.Update(this.${curr.name?cap_first}Item);
                ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}EditPageBack,
                    new ${curr.name?cap_first}ShowPage());
            }
            <#else>
            ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetWithChildren(this.${curr.name?cap_first}Item);
            this.${curr.name?lower_case}Adapter.Update(ViewStateMachine.Instance.${curr.name?cap_first});
            ViewStateMachine.Instance.SetTransition(
                Transition.${curr.name?cap_first}EditPageBack,
                    new ${curr.name?cap_first}ShowPage());
            </#if>
        }
    }
}
