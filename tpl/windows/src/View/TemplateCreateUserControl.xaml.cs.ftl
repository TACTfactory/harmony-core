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
using ${project_namespace}.Harmony.Util.StateMachine;
<#if wishedrelation?has_content>
    <#list wishedrelation as targetEntity> 
using ${project_namespace}.View.${targetEntity};
    </#list>
</#if>
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name}.UsersControls
{
    /// <summary>
    /// ${curr.name}CreateUserControl contain real create display mechanism for ${curr.name} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}CreateUserControl : UserControl
    {
        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl button stackpanel 
        /// use in state item view part to manage UI navigation display.
        /// </summary>
        public StackPanel Stackpanel_btn { get; set; }
    
        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl adapter to save and load ${curr.name?cap_first} informations.
        /// </summary>
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?cap_first}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(new DemactSQLiteOpenHelper());

        /// <summary>
        /// Default constructor
        /// </summary>
        public ${curr.name?cap_first}CreateUserControl()
        {
            this.InitializeComponent();
            this.Stackpanel_btn = this.stackpanel_btn;
        }

        /// <summary>
        /// Retrieve all informations to save current ${curr.name?cap_first} item 
        /// check if comming from other state to save parent item association.
        /// </summary>
        private void btn_validate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#if wishedrelation?has_content>
                <#list wishedrelation as targetEntity>
                    <#if (targetEntity?is_first)>
            if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                        <#list fields?values as field>
                            <#if (!field.internal && !field.hidden)>
                                <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                    <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                                    <#else>
                this.text_box_${field.name?lower_case}.Text,
                                    </#if>
                                </#if>
                            </#if>
                        </#list>
                )
                , ViewStateMachine.Instance.${targetEntity?cap_first});
                ViewStateMachine.Instance.${curr.name} = ${curr.name?cap_first}Adapter.GetById(result);
            }
                    <#else>
            else if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                        <#list fields?values as field>
                            <#if (!field.internal && !field.hidden)>
                                <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                    <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                                    <#else>
                this.text_box_${field.name?lower_case}.Text,
                                    </#if>
                                </#if>
                            </#if>
                        </#list>
                )
                , ViewStateMachine.Instance.${targetEntity?cap_first});
                ViewStateMachine.Instance.${curr.name} = ${curr.name?cap_first}Adapter.GetById(result);
            }
                    </#if>
                </#list>
            else
            {
                int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                <#list fields?values as field>
                    <#if (!field.internal && !field.hidden)>
                        <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                            <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                            <#else>
                this.text_box_${field.name?lower_case}.Text,
                            </#if>
                        </#if>
                    </#if>
                </#list>
                ));
                ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?cap_first}Adapter.GetById(result);
            }
            ViewStateMachine.Instance.Back();
            <#else>
            int result = ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                <#list fields?values as field>
                    <#if (!field.internal && !field.hidden)>
                        <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                            <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                            <#else>
                this.text_box_${field.name?lower_case}.Text,
                            </#if>
                        </#if>
                    </#if>
                </#list>
            ));
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?cap_first}Adapter.GetById(result);
            
            ViewStateMachine.Instance.Back();
            </#if>
        }

        <#if curr.relations?has_content>
            <#list curr.relations as rel>
                <#if rel.relation.type == "ManyToMany" || rel.relation.type == "OneToMany">
        /// <summary>
        /// Retrieve all informations to save current ${curr.name?cap_first} item 
        /// and navigate to display relative ${rel.relation.targetEntity?cap_first} list.
        /// </summary>
        private void btn_list_related_${rel.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                    <#list fields?values as field>
                        <#if (!field.internal && !field.hidden)>
                            <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                                <#else>
                this.text_box_${field.name?lower_case}.Text,
                                </#if>
                            </#if>
                        </#if>
                    </#list>
            ));
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?cap_first}Adapter.GetById(result);
            ViewStateMachine.Instance.SetTransition(Transition.${rel.relation.targetEntity?cap_first}ListPage, new ${rel.relation.targetEntity?cap_first}ListPage());
        }
                <#else>
        /// <summary>
        /// Retrieve all informations to save current ${curr.name?cap_first} item 
        /// and navigate to add relative ${rel.relation.targetEntity?cap_first} item.
        /// </summary>
        private void btn_add_${rel.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            ${curr.name?cap_first}Adapter.Insert(new ${curr.name?cap_first}(
                    <#list fields?values as field>
                        <#if (!field.internal && !field.hidden)>
                            <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                <#if (field.harmony_type?lower_case == "boolean")>
                this.checkbox_${field.name?lower_case}.IsChecked,
                                <#else>
                this.text_box_${field.name?lower_case}.Text,
                                </#if>
                            </#if>
                        </#if>
                    </#list>
            ));
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?cap_first}Adapter.GetById(result);
            ViewStateMachine.Instance.SetTransition(Transition.${rel.relation.targetEntity?cap_first}ListPage, new ${rel.relation.targetEntity?cap_first}ListPage());
        }            
                </#if>
            </#list>
        </#if>

        //TODO setup in state part
        <#if curr.relations?has_content>
        /// <summary>
        /// Update ${curr.name?cap_first}UserControl UI to remove button if comming from other display tree state.
        /// </summary>
        public void UpdateUI()
        {
            <#list curr.relations as rel>
                <#if (rel?is_first && rel?is_last)>
            if ( ViewStateMachine.Instance.${rel.relation.targetEntity?cap_first} != null)
                <#elseif (rel?is_first)>
            if ( ViewStateMachine.Instance.${rel.relation.targetEntity?cap_first} != null
                <#elseif (rel?is_last)>
                || ViewStateMachine.Instance.${rel.relation.targetEntity?cap_first} != null)
                <#else>
                || ViewStateMachine.Instance.${rel.relation.targetEntity?cap_first} != null
                </#if>
            </#list>
            <#list curr.relations as rel>
                <#if rel.relation.type == "ManyToMany" || rel.relation.type == "OneToMany">
                this.stackpanel_btn.Children.Remove(this.btn_list_related_${rel.relation.targetEntity?lower_case});
                <#else>      
                this.stackpanel_btn.Children.Remove(this.btn_add_${rel.relation.targetEntity?lower_case});          
                </#if>
            </#list>
            }
        }
        </#if>
    }
}