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
using ${project_namespace}.Utils.StateMachine;
<#if wishedrelation?has_content>
    <#list wishedrelation as targetEntity> 
using ${project_namespace}.View.${targetEntity?cap_first};
    </#list>
</#if>
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

namespace ${project_namespace}.View.${curr.name?cap_first}.UsersControls
{
    /// <summary>
    /// ${curr.name?cap_first}CreateUserControl contain real create display mechanism for ${curr.name?cap_first} entity.
    /// </summary>
    public sealed partial class ${curr.name?cap_first}CreateUserControl : UserControl
    {
        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl button stackpanel 
        /// use in state item view part to manage UI navigation display.
        /// </summary>
        public StackPanel Stackpanel_btn { get; set; }

        <#list fields?values as field>
            <#if (!field.internal && !field.hidden && field.relation??)>
        /// <summary>
        /// Use to navigate to linked ${field.relation.targetEntity?cap_first} entity.
        /// </summary>
                <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
        public Button Btn_list_related_${field.relation.targetEntity?lower_case} { get; set; }
                <#else>
        public Button Btn_add_${field.relation.targetEntity?lower_case} { get; set; }
                </#if>
            </#if>
        </#list>

        /// <summary>
        /// ${curr.name?cap_first}CreateUserControl adapter to save and load ${curr.name?cap_first} informations.
        /// </summary>
        private ${curr.name?cap_first}SQLiteAdapter ${curr.name?lower_case}Adapter = 
            new ${curr.name?cap_first}SQLiteAdapter(${project_name?cap_first}SQLiteOpenHelper.Instance);

        /// <summary>
        /// Default constructor.
        /// </summary>
        public ${curr.name?cap_first}CreateUserControl()
        {
            this.InitializeComponent();
            this.Stackpanel_btn = this.stackpanel_btn;
        <#list fields?values as field>
            <#if (!field.internal && !field.hidden && field.relation??)>
                <#if (field.relation.type == "OneToMany" || field.relation.type == "ManyToMany")>
            this.Btn_list_related_${field.relation.targetEntity?lower_case} = this.btn_list_related_${field.relation.targetEntity?lower_case};
                <#else>
            this.Btn_add_${field.relation.targetEntity?lower_case} = this.btn_add_${field.relation.targetEntity?lower_case};
                </#if>
            </#if>
        </#list>
        }

        /// <summary>
        /// Retrieve all informations to save current ${curr.name?cap_first} item 
        /// check if comming from other state to save parent item association.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_validate_Tapped(object sender, TappedRoutedEventArgs e)
        {
            <#if wishedrelation?has_content>
                <#list wishedrelation as targetEntity>
                    <#if (targetEntity?is_first)>
            if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                Entity.${curr.name?cap_first} ${curr.name?lower_case} = Get${curr.name?cap_first}FromView();
                int result = ${curr.name?lower_case}Adapter.Insert(${curr.name?lower_case}
                , ViewStateMachine.Instance.${targetEntity?cap_first});
                ViewStateMachine.Instance.${curr.name} = ${curr.name?lower_case}Adapter.GetById(result);
            }
                    <#else>
            else if (ViewStateMachine.Instance.${targetEntity?cap_first} != null)
            {
                int result = ${curr.name?lower_case}Adapter.Insert(Get${curr.name?cap_first}FromView()
                , ViewStateMachine.Instance.${targetEntity?cap_first});
                ViewStateMachine.Instance.${curr.name} = ${curr.name?lower_case}Adapter.GetById(result);
            }
                    </#if>
                </#list>
            else
            {
                int result = ${curr.name?lower_case}Adapter.Insert(Get${curr.name?cap_first}FromView());
                ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?lower_case}Adapter.GetById(result);
            }
            ViewStateMachine.Instance.Back();
            <#else>
            int result = ${curr.name?lower_case}Adapter.Insert(Get${curr.name?cap_first}FromView());
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?lower_case}Adapter.GetById(result);
            
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
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_list_related_${rel.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            int result = ${curr.name?lower_case}Adapter.Insert(Get${curr.name?cap_first}FromView());
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?lower_case}Adapter.GetById(result);
            ViewStateMachine.Instance.SetTransition(Transition.${rel.relation.targetEntity?cap_first}ListPage, new ${rel.relation.targetEntity?cap_first}ListPage());
        }
                <#else>
        /// <summary>
        /// Retrieve all informations to save current ${curr.name?cap_first} item 
        /// and navigate to add relative ${rel.relation.targetEntity?cap_first} item.
        /// </summary>
        /// <param name="sender">Tapped item.</param>
        /// <param name="e">Tapped event.</param>
        private void btn_add_${rel.relation.targetEntity?lower_case}_Tapped(object sender, TappedRoutedEventArgs e)
        {
            int result = ${curr.name?lower_case}Adapter.Insert(Get${curr.name?cap_first}FromView());
            ViewStateMachine.Instance.${curr.name?cap_first} = ${curr.name?lower_case}Adapter.GetById(result);
            ViewStateMachine.Instance.SetTransition(Transition.${rel.relation.targetEntity?cap_first}ListPage, new ${rel.relation.targetEntity?cap_first}ListPage());
        }            
                </#if>
            </#list>
        </#if>

        private Entity.${curr.name?cap_first} Get${curr.name?cap_first}FromView()
        {
            Entity.${curr.name?cap_first} ${curr.name?lower_case} = new Entity.${curr.name?cap_first}();
                        <#list fields?values as field>
                            <#if (!field.internal && !field.hidden)>
                                <#if (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany" && field.relation.type!="OneToOne" && field.relation.type!="ManyToOne"))>
                                    <#if (field.harmony_type?lower_case == "boolean")>
            ${curr.name?lower_case}.${field.name?cap_first} = this.checkbox_${field.name?lower_case}.IsChecked;
                                    <#else>
            ${curr.name?lower_case}.${field.name?cap_first} = this.text_box_${field.name?lower_case}.Text;
                                    </#if>
                                </#if>
                            </#if>
                        </#list>
            return ${curr.name?lower_case};
        }
    }
}