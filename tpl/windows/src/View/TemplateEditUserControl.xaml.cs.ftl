<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />
<#assign wishedrelation = []/>
<#list curr.relations as rel>
    <#if ((rel.relation.type=="OneToMany") || (rel.relation.type=="ManyToMany") || (rel.relation.type=="OneToOne") || (rel.relation.type=="ManyToOne"))>
        <#assign wishedrelation = wishedrelation + [rel.relation.targetEntity]/>
    </#if>
</#list>

using ${project_namespace}.Data;
using ${project_namespace}.Entity;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}EditUserControl contain real edit display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}EditUserControl : UserControl
    {
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(new ${project_name?cap_first}SQLiteOpenHelper());

        /// <summary>
        /// ${curr.name?cap_first}Item use to fill view with item datas.
        /// </summary>
        public ${curr.name?cap_first} ${curr.name?cap_first}Item { get; set; }
        
        /// <summary>
        /// Default constructor.
        /// Initialize current DataContext and ${curr.name?cap_first}Item 
        /// with appropriate datas extract from ViewStateMachine.
        /// </summary>
        public ${curr.name?cap_first}EditUserControl()
        {
            this.InitializeComponent();
            this.DataContext = this;
            this.${curr.name?cap_first}Item = ViewStateMachine.Instance.${curr.name?cap_first};
        }

        /// <summary>
        /// Update button use to save datas in database.
        /// For any possible relations beetwen ${curr.name?cap_first} 
        /// and others update all references if have to. 
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_update_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#assign item_count = 0/>
            <#if fields?values?has_content>
                <#list fields?values as field>
                    <#if (!field.internal && !field.hidden)>
                        <#if field.relation?? && (field.relation.type == "ManyToMany" || field.relation.type == "OneToMany")>
                            <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                                <#assign item_count = 1/>
                            <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                            </#if>
            {
                int result = this.${curr.name?lower_case}Adapter.Insert(this.${curr.name?cap_first}Item);
                ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetById(result);
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first}.${field.relation.mappedBy}.Add(ViewStateMachine.Instance.${curr.name?cap_first});
                ${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.relation.targetEntity?cap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(new ${project_name?cap_first}SQLiteOpenHelper());
                ${field.relation.targetEntity?cap_first}Adapter.Update(ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                        <#elseif field.relation??>
                            <#if (item_count == 0)>
            if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                                <#assign item_count = 1/>
                            <#else>
            else if (ViewStateMachine.Instance.${field.relation.targetEntity?cap_first} != null)
                            </#if>
            {
                int result = this.${curr.name?lower_case}Adapter.Insert(this.${curr.name?cap_first}Item);
                ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetById(result);
                ViewStateMachine.Instance.${field.relation.targetEntity?cap_first}.${field.relation.mappedBy} = ViewStateMachine.Instance.${curr.name?cap_first};
                ${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.relation.targetEntity?cap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(new ${project_name?cap_first}SQLiteOpenHelper());
                ${field.relation.targetEntity?cap_first}Adapter.Update(ViewStateMachine.Instance.${field.relation.targetEntity?cap_first});
            }
                        </#if>
                    </#if>
                </#list>
            <#else>
            ViewStateMachine.Instance.${curr.name?cap_first} = this.${curr.name?lower_case}Adapter.GetWithChildren(this.${curr.name?cap_first}Item);
            this.${curr.name?lower_case}Adapter.Update(ViewStateMachine.Instance.${curr.name?cap_first});

            </#if>
            ViewStateMachine.Instance.Back();
        }
    }
}